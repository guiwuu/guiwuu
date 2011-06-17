package com.guiwuu.design.oo.inherit;

public class Son extends Parent {

    @Override
    public void print() {
        System.out.println("Son");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Parent p = new Son();
        p.execute();
    }
}
