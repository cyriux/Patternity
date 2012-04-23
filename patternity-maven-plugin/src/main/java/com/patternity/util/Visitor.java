package com.patternity.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Visitor<T> {
    private List<T> collected;
    private boolean isDone;

    public void markDone() {
        this.isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    public void visit(T element) {
    }

    public void collect(T element) {
        if(collected==null)
            collected = new ArrayList<T>();
        collected.add(element);
    }

    public List<T> getCollected() {
        return collected;
    }

    public boolean hasElementCollected() {
        return collected!=null && !collected.isEmpty();
    }
}
