package com.patternity.ast.asm;

import com.patternity.ast.*;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 *
 */
public class ScannerContext {

    private static Logger logger = LoggerFactory.getLogger(ScannerContext.class);


    private final Stack<Model> stack = new Stack<Model>();
    private String scannedClass;
    //
    private ClassVisitorAdapter classVisitor;
    private AnnotationVisitorAdapter annotationVisitor;
    private FieldVisitorAdapter fieldVisitor;
    private SignatureVisitorAdapter signatureVisitor;
    private MethodVisitorAdapter methodVisitor;
    //
    private ClassHandler classHandler;

    public ScannerContext(ClassHandler classHandler) {
        this.classHandler = classHandler;
        this.classVisitor = new ClassVisitorAdapter(this);
        this.fieldVisitor = new FieldVisitorAdapter(this);
        this.methodVisitor = new MethodVisitorAdapter(this);
        this.signatureVisitor = new SignatureVisitorAdapter(this);
        this.annotationVisitor = new AnnotationVisitorAdapter(this);
    }

    public ClassVisitorAdapter getClassVisitor() {
        return classVisitor;
    }

    public AnnotationVisitorAdapter getAnnotationVisitor() {
        return annotationVisitor;
    }

    public SignatureVisitor getSignatureVisitor() {
        return signatureVisitor;
    }

    public FieldVisitorAdapter getFieldVisitor() {
        return fieldVisitor;
    }

    public MethodVisitorAdapter getMethodVisitor() {
        return methodVisitor;
    }

    private ClassModel peekClassModel() {
        return (ClassModel) peek();
    }

    public Model peek() {
        Model model = stack.peek();
        logger.trace("Peeking from stack: <{}>", model);
        return model;
    }

    public Model pop() {
        Model model = stack.pop();
        logger.debug("Popping from stack: <{}> (remaining {})", model, stack.size());

        // only notify root class
        if (stack.empty()) {
            ClassModel classModel = (ClassModel) model;
            classHandler.handleClass(classModel);
        }
        return model;
    }

    public void push(Model model) {
        logger.debug("Pushing to stack: <{}>", model);
        stack.push(model);
    }

    public void addInternalName(final String name) {
        addType(Type.getObjectType(name));
    }

    public void addInternalNames(final String[] names) {
        if (names != null) {
            for (String name : names) {
                addInternalName(name);
            }
        }
    }

    public void addMethodDesc(final String desc) {
        addType(Type.getReturnType(desc));
        Type[] types = Type.getArgumentTypes(desc);
        for (int i = 0; i < types.length; i++) {
            addType(types[i]);
        }
    }

    public void addDesc(final String desc) {
        addType(Type.getType(desc));
    }

    public void addType(final Type t) {
        switch (t.getSort()) {
            case Type.ARRAY:
                addType(t.getElementType());
                break;
            case Type.OBJECT:
                declareDependency(t.getInternalName());
                break;
        }
    }

    private void declareDependency(String qualifiedName) {
        // don't fire self-dependency :)
        if (isScannedOrInnerClass(qualifiedName))
            return;

        peek().dependsOn(qualifiedName);
    }

    private boolean isScannedOrInnerClass(String qualifiedName) {
        if (qualifiedName.startsWith(scannedClass)) {
            if (qualifiedName.length() == scannedClass.length())
                // self
                return true;
                // at this point, one is sure string length is greater than scannedClass
            else if (qualifiedName.charAt(scannedClass.length()) == '$')
                // inner class
                return true;
            else
                // similar class prefix
                return false;
        }
        return false;
    }

    public void addSignature(final String signature) {
        if (signature != null) {
            new SignatureReader(signature).accept(getSignatureVisitor());
        }
    }

    public void addTypeSignature(final String signature) {
        if (signature != null) {
            new SignatureReader(signature).acceptType(getSignatureVisitor());
        }
    }

    public void enterClass(ClassModel model) {
        push(model);
        this.scannedClass = model.getQualifiedName();
    }

    public FieldVisitor enterField(FieldModel model) {
        peekClassModel().addFieldModel(model);
        push(model);
        return getFieldVisitor();
    }

    public MethodVisitor enterMethod(MethodModel model) {
        peekClassModel().addMethodModel(model);
        push(model);
        return getMethodVisitor();
    }

    protected AnnotationVisitor enterAnnotation(AnnotationModel model) {
        peek().addAnnotation(model);
        push(model);
        return getAnnotationVisitor();
    }

    public AnnotationVisitor enterClassAnnotation(AnnotationModel model) {
        return enterAnnotation(model);
    }

    public AnnotationVisitor enterMethodAnnotation(AnnotationModel model) {
        return enterAnnotation(model);
    }

    public AnnotationVisitor enterFieldAnnotation(AnnotationModel model) {
        return enterAnnotation(model);
    }

    public AnnotationVisitor enterMethodParameterAnnotation(AnnotationModel model) {
        return enterAnnotation(model);
    }

}
