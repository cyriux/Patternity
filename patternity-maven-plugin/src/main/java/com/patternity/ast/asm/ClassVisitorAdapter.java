package com.patternity.ast.asm;

import com.patternity.ast.AnnotationModel;
import com.patternity.ast.ClassModel;
import com.patternity.ast.FieldModel;
import com.patternity.ast.MethodModel;
import org.objectweb.asm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassVisitorAdapter implements ClassVisitor {

    private static Logger logger = LoggerFactory.getLogger(ClassVisitorAdapter.class);

    private final ScannerContext context;

    public ClassVisitorAdapter(ScannerContext context) {
        this.context = context;
    }

    @Override
    public void visit(final int version, //
                      final int access, //
                      final String name, //
                      final String signature, //
                      final String superName, //
                      final String[] interfaces) {

        logger.info("Visiting class <{}> sig: <{}>", name, signature);

        ClassModel model = new ClassModel(name);
        AsmUtils.applyModifiers(model.getModifiers(), access);
        model.declareImplements(interfaces);
        model.setSuperQualifiedName(superName);
        context.enterClass(model);

        if (signature == null) {
            context.addInternalName(superName);
            context.addInternalNames(interfaces);
        } else {
            context.addSignature(signature);
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visibleAtRuntime) {
        logger.debug("Visiting class annotation <{}>", desc);

        AnnotationModel model = AsmUtils.createAnnotationFromDesc(desc, visibleAtRuntime);
        AnnotationVisitor annotationVisitor = context.enterClassAnnotation(model);

        // define dependency once annotation model is the current model (top of the stack)
        context.addDesc(desc);
        return annotationVisitor;
    }

    @Override
    public void visitAttribute(final Attribute attr) {
    }

    @Override
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature,
                                   final Object value) {
        logger.debug("Visiting class field <{}> <{}>", name, desc);

        FieldModel model = new FieldModel(name);
        AsmUtils.applyModifiers(model.getModifiers(), access);

        FieldVisitor fieldVisitor = context.enterField(model);

        // define dependency once field model is the current model (top of the stack)
        if (signature == null) {
            context.addDesc(desc);
        } else {
            context.addTypeSignature(signature);
        }
        if (value instanceof Type) {
            context.addType((Type) value);
        }
        return fieldVisitor;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                                     final String[] exceptions) {
        logger.debug("Visiting class method <{}> <{}>", name, desc);

        MethodModel model = new MethodModel(name);
        AsmUtils.applyModifiers(model.getModifiers(), access);

        MethodVisitor methodVisitor = context.enterMethod(model);

        // define dependency once field model is the current model (top of the stack)
        if (signature == null) {
            context.addMethodDesc(desc);
        } else {
            context.addSignature(signature);
        }
        context.addInternalNames(exceptions);
        return methodVisitor;
    }

    @Override
    public void visitSource(final String source, final String debug) {
    }

    @Override
    public void visitInnerClass(final String name, final String outerName, final String innerName, final int access) {
        ClassModel classModel = (ClassModel) context.peek();
        logger.debug("Visiting inner class field outerName: <{}> ({})", outerName, classModel);
        // addName( outerName);
        // addName( innerName);
    }

    @Override
    public void visitOuterClass(final String owner, final String name, final String desc) {
        ClassModel classModel = (ClassModel) context.peek();
        classModel.innerClassOf(owner);
        logger.debug("Visiting outer class field owner: <{}> ({})", owner, classModel);
        // addName(owner);
        // addMethodDesc(desc);
    }

    @Override
    public void visitEnd() {
        context.pop();
    }
}
