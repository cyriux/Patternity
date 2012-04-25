package com.patternity.ast;


/**
 *
 */
public class ClassHandlerCollector implements ClassHandler {

    private ClassElement collected;

    @Override
    public void handleClass(ClassElement model) {
        this.collected = model;
    }

    public ClassElement getCollected() {
        return collected;
    }
}
