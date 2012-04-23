package com.patternity.ast;

import java.util.*;

/**
 *
 */
public class ClassModel extends Model<ClassModel> {
    private final String qualifiedName;
    private String outerClassName;
    private String superQualifiedName;
    private Set<String> implementQualifiedNames;
    private List<FieldModel> fieldModels;
    private List<MethodModel> methodModels;

    public ClassModel(String qualifiedName) {
        super();
        this.qualifiedName = qualifiedName;
        this.implementQualifiedNames = new HashSet<String>();
    }

    @Override
    protected void selfTraverse(ModelVisitor visitor) {
        super.selfTraverse(visitor);
        if (visitor.isDone())
            return;
        if (traverseFields(visitor))
            return;
        if(traverseMethods(visitor))
            return;
    }

    private boolean traverseMethods(ModelVisitor visitor) {
        if (methodModels != null) {
            for (MethodModel model : methodModels) {
                model.traverseModelTree(visitor);
                if (visitor.isDone())
                    return true;
            }
        }
        return false;
    }

    private boolean traverseFields(ModelVisitor visitor) {
        if (fieldModels != null) {
            for (FieldModel model : fieldModels) {
                model.traverseModelTree(visitor);
                if (visitor.isDone())
                    return true;
            }
        }
        return false;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public ModelType getModelType() {
        return ModelType.CLASS;
    }

    public Set<String> collectAllDependencies() {
        DependenciesCollector collector = new DependenciesCollector();
        traverseModelTree(collector);
        return collector.getDependencies();
    }


    public Set<String> getImplementQualifiedNames() {
        return implementQualifiedNames;
    }

    public void declareImplements(String... implementQualifedNames) {
        for (String implementQualifedName : implementQualifedNames)
            implementQualifiedNames.add(implementQualifedName);
    }

    public String getSuperQualifiedName() {
        return superQualifiedName;
    }

    public void setSuperQualifiedName(String superQualifiedName) {
        this.superQualifiedName = superQualifiedName;
    }

    /**
     * @param outerClassName
     * @see #getOuterClassName()
     */
    public void innerClassOf(String outerClassName) {
        this.outerClassName = outerClassName;
    }

    /**
     * @return
     * @see #innerClassOf(String)
     */
    public String getOuterClassName() {
        return outerClassName;
    }

    public List<FieldModel> getFieldModels() {
        return listOrEmpty(fieldModels);
    }

    public void addFieldModel(FieldModel model) {
        if (fieldModels == null)
            fieldModels = new ArrayList<FieldModel>();
        fieldModels.add(model);
    }

    public List<MethodModel> getMethodModels() {
        return listOrEmpty(methodModels);
    }

    private <T> List<T> listOrEmpty(List<T> elements) {
        if(elements==null)
            return Collections.emptyList();
        return elements;
    }

    public void addMethodModel(MethodModel model) {
        if (methodModels == null)
            methodModels = new ArrayList<MethodModel>();
        methodModels.add(model);
    }

    @Override
    public String toString() {
        return "ClassModel{" + //
                "qualifiedName='" + qualifiedName + "'" + //
                ((outerClassName == null) ? "" : (", innerClassOf: " + outerClassName)) + //
                ((superQualifiedName == null) ? "" : (", super: " + superQualifiedName)) + //
                ((implementQualifiedNames.isEmpty()) ? "" : (", implements: " + implementQualifiedNames)) + //
                '}';
    }

}
