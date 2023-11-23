package com.demo;

public abstract class Gate<T, K> {

    private T leftInput;
    private K rightInput;

    public Gate() {
    }

    public Gate(T leftInput) {
        this.leftInput = leftInput;
        this.rightInput = null;
    }

    public Gate(T leftInput, K rightInput) {
        this.leftInput = leftInput;
        this.rightInput = rightInput;
    }

    public T getLeftInput() {
        return leftInput;
    }

    public K getRightInput() {
        return rightInput;
    }

    public Integer getDeepLeftInput() {
        Integer left;
        if (leftInput instanceof Gate<?, ?>) {
            left = ((Gate<?, ?>) leftInput).output();
        } else {
            left = (Integer) leftInput;
        }
        return left;
    }

    public Integer getDeepRightInput() {
        Integer right;
        if (rightInput instanceof Gate<?, ?>) {
            right = ((Gate<?, ?>) rightInput).output();
        } else {
            right = (Integer) rightInput;
        }
        return right;
    }

    public String name() {
        return this.getClass().getSimpleName();
    }

    public abstract Integer output();

}

class AND<T, K> extends Gate<T, K> {

    public AND(T leftInput, K rightInput) {
        super(leftInput, rightInput);
    }

    public Integer output() {
        return getDeepLeftInput() + getDeepRightInput() == 2 ? 1 : 0;
    }
}

class OR<T, K> extends Gate<T, K> {

    public OR() {}

    public OR(T leftInput, K rightInput) {
        super(leftInput, rightInput);
    }

    public Integer output() {
        return getDeepLeftInput() + getDeepRightInput() >= 1 ? 1 : 0;
    }

}

class NOT<T, K> extends Gate<T, K> {

    public NOT(T leftInput, K rightInput) {
        super(leftInput, rightInput);
    }

    public Integer output() {
        return getDeepLeftInput() == 1 ? 0 : 1;
    }

}

class XOR<T, K> extends Gate<T, K> {

    public XOR(T leftInput, K rightInput) {
        super(leftInput, rightInput);
    }

    public Integer output() {
        return !getDeepLeftInput().equals(getDeepRightInput()) ? 1 : 0;
    }
}

class NAND<T, K> extends Gate<T, K> {

    public NAND(T leftInput, K rightInput) {
        super(leftInput, rightInput);
    }

    public Integer output() {
        return getDeepLeftInput() + getDeepRightInput() != 2 ? 1 : 0;
    }
}

class NOR<T, K> extends Gate<T, K> {

    public NOR(T leftInput, K rightInput) {
        super(leftInput, rightInput);
    }

    public Integer output() {
        return getDeepLeftInput() + getDeepRightInput() == 2 ? 1 : 0;
    }
}

class XNOR<T, K> extends Gate<T, K> {

    public XNOR(T leftInput, K rightInput) {
        super(leftInput, rightInput);
    }

    public Integer output() {
        return !getDeepLeftInput().equals(getDeepRightInput()) ? 0 : 1;
    }
}

