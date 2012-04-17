package com.patternity.ast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ModelVisitor {
    private boolean isDone;

    protected void markDone() {
        this.isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    public void enterModel(Model<?> model) {
    }

    public void exitModel(Model<?> model) {
    }

}
