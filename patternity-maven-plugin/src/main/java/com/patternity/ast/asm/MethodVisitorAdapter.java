package com.patternity.ast.asm;

import com.patternity.ast.AnnotationElement;
import org.objectweb.asm.*;

/**
 *
 */
public class MethodVisitorAdapter implements MethodVisitor {

    private final ScannerContext context;

    public MethodVisitorAdapter(ScannerContext context) {
        this.context = context;
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return context.getAnnotationVisitor();
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visibleAtRuntime) {
        context.addDesc(desc);
        AnnotationElement model = AsmUtils.createAnnotationFromDesc(desc, visibleAtRuntime);
        return context.enterMethodAnnotation(model);
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(final int parameter, final String desc, final boolean visibleAtRuntime) {
        context.addDesc(desc);
        AnnotationElement model = new AnnotationElement(desc, visibleAtRuntime);
        return context.enterMethodParameterAnnotation(model);
    }

    @Override
    public void visitTypeInsn(final int opcode, final String type) {
        context.addType(Type.getObjectType(type));
    }

    @Override
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
        context.addInternalName(owner);
        context.addDesc(desc);
    }

    @Override
    public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc) {
        context.addInternalName(owner);
        context.addMethodDesc(desc);
    }

    @Override
    public void visitLdcInsn(final Object cst) {
        if (cst instanceof Type) {
            context.addType((Type) cst);
        }
    }

    @Override
    public void visitMultiANewArrayInsn(final String desc, final int dims) {
        context.addDesc(desc);
    }

    @Override
    public void visitLocalVariable(final String name, final String desc, final String signature, final Label start,
                                   final Label end, final int index) {
        context.addTypeSignature(signature);
    }

    @Override
    public void visitCode() {
    }

    @Override
    public void visitFrame(final int type, final int nLocal, final Object[] local, final int nStack,
                           final Object[] stack) {
    }

    @Override
    public void visitInsn(final int opcode) {
    }

    @Override
    public void visitIntInsn(final int opcode, final int operand) {
    }

    @Override
    public void visitVarInsn(final int opcode, final int var) {
    }

    @Override
    public void visitJumpInsn(final int opcode, final Label label) {
    }

    @Override
    public void visitLabel(final Label label) {
    }

    @Override
    public void visitIincInsn(final int var, final int increment) {
    }

    @Override
    public void visitTableSwitchInsn(final int min, final int max, final Label dflt, final Label[] labels) {
    }

    @Override
    public void visitLookupSwitchInsn(final Label dflt, final int[] keys, final Label[] labels) {
    }

    @Override
    public void visitTryCatchBlock(final Label start, final Label end, final Label handler, final String type) {
        context.addInternalName(type);
    }

    @Override
    public void visitLineNumber(final int line, final Label start) {
    }

    @Override
    public void visitMaxs(final int maxStack, final int maxLocals) {
    }

    @Override
    public void visitEnd() {
        context.pop();
    }

    @Override
    public void visitAttribute(Attribute attribute) {
    }


}
