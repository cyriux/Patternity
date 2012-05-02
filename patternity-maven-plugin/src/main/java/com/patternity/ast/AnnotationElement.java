package com.patternity.ast;

/**
 *
 */
public class AnnotationElement extends ModelElement<AnnotationElement> {

	private final String qualifiedName;
	private final boolean visibleAtRuntime;

	public AnnotationElement(String qualifiedName) {
		this(qualifiedName, false);
	}

	public AnnotationElement(String qualifiedName, boolean visibleAtRuntime) {
		this.qualifiedName = qualifiedName;
		this.visibleAtRuntime = visibleAtRuntime;
	}

	@Override
	public ModelType getModelType() {
		return ModelType.ANNOTATION;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	@Override
	public String toString() {
		return "AnnotationModel{" + "qualifiedName='" + qualifiedName + '\'' + ", visibleAtRuntime=" + visibleAtRuntime
				+ '}';
	}
}
