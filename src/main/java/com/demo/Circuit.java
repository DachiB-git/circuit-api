package com.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Circuit {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String json;
    private Gate<?, ?> root;

    public Circuit(String json) {
        this.json = json;
    }

    public Gate<?, ?> getRoot() {
        return root;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void buildCircuit() throws IOException {
        Map.Entry<String, JsonNode> rootNodes = objectMapper.readTree(json).fields().next();
        String rootType = rootNodes.getKey();
        JsonNode leftNode = rootNodes.getValue().get("leftInput");
        JsonNode rightNode = rootNodes.getValue().get("rightInput");
        this.root = parseNode(rootType, leftNode, rightNode);
    }

    public Gate<?, ?> parseNode(String type, JsonNode leftNode, JsonNode rightNode) {
        if (leftNode.asText().matches("[10]") && rightNode.asText().matches("[10]")) {
            return buildGate(type, leftNode.asInt(), rightNode.asInt());
        } else if (!leftNode.asText().matches("[10]") && !rightNode.asText().matches("[10]")) {
            Map.Entry<String, JsonNode> newLeftEntry = leftNode.fields().next();
            Map.Entry<String, JsonNode> newRightEntry = rightNode.fields().next();
            String newLeftType = newLeftEntry.getKey();
            String newRightType = newRightEntry.getKey();
            JsonNode newLeftNodeOne = newLeftEntry.getValue().get("leftInput");
            JsonNode newLeftNodeTwo = newLeftEntry.getValue().get("rightInput");
            JsonNode newRightNodeOne = newRightEntry.getValue().get("leftInput");
            JsonNode newRightNodeTwo = newRightEntry.getValue().get("rightInput");
            return buildGate(type, parseNode(newLeftType, newLeftNodeOne, newLeftNodeTwo),
                    parseNode(newRightType, newRightNodeOne, newRightNodeTwo));
        } else {
            Map.Entry<String, JsonNode> newEntry;
            if (leftNode.asText().matches("[10]")) {
                newEntry = rightNode.fields().next();
            } else {
                newEntry = leftNode.fields().next();
            }
            String newType = newEntry.getKey();
            JsonNode newLeftNode = newEntry.getValue().get("leftInput");
            JsonNode newRightNode = newEntry.getValue().get("rightInput");
            if (leftNode.asText().matches("[10]")) {
                return buildGate(type, leftNode.asInt(), parseNode(newType, newLeftNode, newRightNode));
            } else {
                return buildGate(type, parseNode(newType, newLeftNode, newRightNode), rightNode.asInt());
            }
        }
    }

    public <T, K> Gate<?, ?> buildGate(String type, T leftInput, K rightInput) {
        return switch (type) {
            case "AND" -> new AND<>(leftInput, rightInput);
            case "OR" -> new OR<>(leftInput, rightInput);
            case "XOR" -> new XOR<>(leftInput, rightInput);
            case "NOT" -> new NOT<>(leftInput, rightInput);
            case "NAND" -> new NAND<>(leftInput, rightInput);
            case "NOR" -> new NOR<>(leftInput, rightInput);
            case "XNOR" -> new XNOR<>(leftInput, rightInput);
            default -> null;
        };
    }

    public Integer output() {
        return root.output();
    }
}
