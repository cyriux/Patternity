package com.patternity.ast;

import java.util.List;

/**
 * Represents a class (or interface) in the metamodel
 */
public class PackageElement extends ModelElement<PackageElement> {
	private final String qualifiedName;

	public PackageElement(final ClassElement classElement) {
		this(classElement.getQualifiedName(), classElement.getAnnotations());
	}

	public PackageElement(final String qualifiedName, final List<AnnotationElement> annotations) {
		this.qualifiedName = qualifiedName;
		for (AnnotationElement annotation : annotations) {
			addAnnotation(annotation);
		}
	}

	@Override
	protected void selfTraverse(ModelVisitor visitor) {
		super.selfTraverse(visitor);
		if (visitor.isDone())
			return;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	@Override
	public ModelType getModelType() {
		return ModelType.PACKAGE;
	}

	@Override
	public String toString() {
		return "ClassModel{" + "qualifiedName='" + qualifiedName + "}";
	}

}
