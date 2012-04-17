package com.patternity.ast.asm;

import com.patternity.ast.AnnotationModel;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;

/**
 *
 */
public class FieldVisitorAdapter implements FieldVisitor {

    private final ScannerContext context;

    public FieldVisitorAdapter(ScannerContext context) {
        this.context = context;
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visibleAtRuntime) {
        context.addDesc(desc);
        AnnotationModel model = AsmUtils.createAnnotationFromDesc(desc, visibleAtRuntime);
        return context.enterFieldAnnotation(model);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
    }

    @Override
    public void visitEnd() {
        context.pop();
    }
}
