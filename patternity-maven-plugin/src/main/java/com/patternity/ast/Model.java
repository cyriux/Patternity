package com.patternity.ast;

import com.patternity.util.Visitor;

import java.util.*;

/**
 *
 */
public abstract class Model<T extends Model<T>> {
    private final List<AnnotationModel> annotationModels;
    private final Modifiers modifiers = new Modifiers();

    private Set<String> dependencies;

    protected Model() {
        annotationModels = new ArrayList<AnnotationModel>();
    }

    public abstract ModelType getModelType();

    public void traverseModelTree(ModelVisitor visitor) {
        visitor.enterModel(this);
        selfTraverse(visitor);
        visitor.exitModel(this);
    }

    protected void selfTraverse(ModelVisitor visitor) {
        for (AnnotationModel annotationModel : annotationModels) {
            annotationModel.traverseModelTree(visitor);
            if (visitor.isDone())
                return;
        }
    }

    public T addAnnotation(AnnotationModel annotationModel) {
        annotationModels.add(annotationModel);
        return (T) this;
    }

    public List<AnnotationModel> getAnnotationModels() {
        return annotationModels;
    }

    public Modifiers getModifiers() {
        return modifiers;
    }

    public boolean hasAnnotation(String qualifiedName) {
        return getAnnotation(qualifiedName) != null;
    }

    public AnnotationModel getAnnotation(String qualifiedName) {
        for (AnnotationModel model : annotationModels) {
            if (model.getQualifiedName().equals(qualifiedName))
                return model;
        }
        return null;
    }

    public Set<String> getDependencies() {
        if(dependencies==null)
            return Collections.emptySet();
        return dependencies;
    }

    public T dependsOn(String qualifiedName) {
        if (dependencies == null)
            dependencies = new HashSet<String>();
        dependencies.add(qualifiedName);
        return (T) this;
    }

}
