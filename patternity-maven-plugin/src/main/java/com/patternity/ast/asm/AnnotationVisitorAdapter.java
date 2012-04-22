package com.patternity.ast.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

/**
 */
public class AnnotationVisitorAdapter implements AnnotationVisitor {

    private final ScannerContext context;

    public AnnotationVisitorAdapter(ScannerContext context) {
        this.context = context;
    }

    @Override
    public void visit(final String name, final Object value) {
        if (value instanceof Type) {
            context.addType((Type) value);
        }
    }

    @Override
    public void visitEnum(final String name, final String desc, final String value) {
        context.addDesc(desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String name, final String desc) {
        context.addDesc(desc);
        return this;
    }

    @Override
    public AnnotationVisitor visitArray(final String name) {
        return this;
    }

    @Override
    public void visitEnd() {
        context.pop();
    }
}
