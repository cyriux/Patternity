package com.patternity.ast;

import java.lang.reflect.Modifier;

/**
 *
 */
public class Modifiers {

    private int modifiers;

    public Modifiers markPublic() {
        unflag(Modifier.PRIVATE);
        unflag(Modifier.PROTECTED);
        return flag(Modifier.PUBLIC);
    }

    public boolean isPublic() {
        return Modifier.isPublic(modifiers);
    }

    public Modifiers markPrivate() {
        unflag(Modifier.PUBLIC);
        unflag(Modifier.PROTECTED);
        return flag(Modifier.PRIVATE);
    }

    public boolean isPrivate() {
        unflag(Modifier.PUBLIC);
        unflag(Modifier.PRIVATE);
        return Modifier.isPrivate(modifiers);
    }

    public Modifiers markProtected() {
        return flag(Modifier.PROTECTED);
    }

    public boolean isProtected() {
        return Modifier.isProtected(modifiers);
    }

    public Modifiers markDefault() {
        unflag(Modifier.PUBLIC);
        unflag(Modifier.PROTECTED);
        unflag(Modifier.PRIVATE);
        return this;
    }

    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }

    public Modifiers markFinal() {
        return flag(Modifier.FINAL);
    }

    public Modifiers markStatic() {
        return flag(Modifier.STATIC);
    }

    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }

    protected Modifiers flag(int mask) {
        modifiers = modifiers | mask;
        return this;
    }

    protected Modifiers unflag(int mask) {
        modifiers = modifiers & ~mask;
        return this;
    }

}
