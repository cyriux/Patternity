package com.patternity.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base class of each meta-model element
 */
// @Entity
public abstract class ModelElement<T extends ModelElement<T>> {

	private final List<AnnotationElement> annotations = new ArrayList<AnnotationElement>();
	private final Modifiers modifiers = new Modifiers();
	private Set<String> dependencies;

	public abstract ModelType getModelType();

	public void traverseModelTree(ModelVisitor visitor) {
		visitor.enterModel(this);
		selfTraverse(visitor);
		visitor.exitModel(this);
	}

	protected void selfTraverse(ModelVisitor visitor) {
		for (AnnotationElement annotationModel : annotations) {
			annotationModel.traverseModelTree(visitor);
			if (visitor.isDone())
				return;
		}
	}

	@SuppressWarnings("unchecked")
	public T addAnnotation(AnnotationElement annotationElement) {
		annotations.add(annotationElement);
		return (T) this;
	}

	public List<AnnotationElement> getAnnotations() {
		return annotations;
	}

	public Modifiers getModifiers() {
		return modifiers;
	}

	public boolean hasAnnotation(String qualifiedName) {
		return getAnnotation(qualifiedName) != null;
	}

	public AnnotationElement getAnnotation(String qualifiedName) {
		for (AnnotationElement model : annotations) {
			if (model.getQualifiedName().equals(qualifiedName))
				return model;
		}
		return null;
	}

	public Set<String> getDependencies() {
		if (dependencies == null)
			return Collections.emptySet();
		return dependencies;
	}

	@SuppressWarnings("unchecked")
	public T dependsOn(String qualifiedName) {
		if (dependencies == null)
			dependencies = new HashSet<String>();
		dependencies.add(qualifiedName);
		return (T) this;
	}

}
