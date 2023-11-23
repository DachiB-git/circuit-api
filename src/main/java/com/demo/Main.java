package com.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @CrossOrigin()
    @GetMapping("/gates")
    public String[] getGates() {
        return new String[]{"AND", "OR", "NOT", "XOR", "NAND", "NOR", "XNOR"};
    }

    @CrossOrigin()
    @RequestMapping(value = "/gates/image", method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)

    public void getImage(HttpServletResponse response, @RequestParam("name") String gateName) throws IOException {
        System.out.println(gateName);
        var imgFile = new ClassPathResource("AND.png");
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }

    @PostMapping("/circuit")
    public String makeCircuit(@RequestBody String jsonString) throws IOException {
        Circuit circuit = new Circuit(jsonString);
        circuit.buildCircuit();
        return circuit.getRoot().name() + ": " + circuit.output();
    }
}
