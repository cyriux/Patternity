package com.patternity.ast;

import java.util.List;

/**
 *
 */
public class ClassHandlerCollector implements ClassHandler {

    private ClassModel collected;

    @Override
    public void handleClass(ClassModel model) {
        this.collected = model;
    }

    public ClassModel getCollected() {
        return collected;
    }
}
