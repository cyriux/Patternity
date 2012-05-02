package com.patternity.ast.asm;

import org.objectweb.asm.signature.SignatureVisitor;

/**
 *
 */
public class SignatureVisitorAdapter implements SignatureVisitor {

    private final ScannerContext context;
    private String signatureClassName;

    public SignatureVisitorAdapter(ScannerContext context) {
        this.context = context;
    }

    public void visitFormalTypeParameter(final String name) {
    }

    public SignatureVisitor visitClassBound() {
        return this;
    }

    public SignatureVisitor visitInterfaceBound() {
        return this;
    }

    public SignatureVisitor visitSuperclass() {
        return this;
    }

    public SignatureVisitor visitInterface() {
        return this;
    }

    public SignatureVisitor visitParameterType() {
        return this;
    }

    public SignatureVisitor visitReturnType() {
        return this;
    }

    public SignatureVisitor visitExceptionType() {
        return this;
    }

    public void visitBaseType(final char descriptor) {
    }

    public void visitTypeVariable(final String name) {
    }

    public SignatureVisitor visitArrayType() {
        return this;
    }

    public void visitClassType(final String name) {
        signatureClassName = name;
        context.addInternalName(name);
    }

    public void visitInnerClassType(final String name) {
        signatureClassName = signatureClassName + "$" + name;
        context.addInternalName(signatureClassName);
    }

    public void visitTypeArgument() {
    }

    public SignatureVisitor visitTypeArgument(final char wildcard) {
        return this;
    }

    @Override
    public void visitEnd() {
    }
}
