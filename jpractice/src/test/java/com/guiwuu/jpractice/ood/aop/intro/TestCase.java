package com.guiwuu.jpractice.ood.aop.intro;

/**
 *
 * @author guiwuu
 */
public class TestCase {

    public void call() {
        return;
    }

    @Run
    public void call2() {
        return;
    }

    @Run
    public void call3() {
        throw new RuntimeException();
    }

    @Run
    private void call4() {
        return;
    }
    
    @Run
    public static void call5() {
        return;
    }
}