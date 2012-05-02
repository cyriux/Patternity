package com.patternity.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a class (or interface) in the metamodel
 */
public class ClassElement extends ModelElement<ClassElement> {
	private final String qualifiedName;

	private String outerClassName;// @Nullable
	private String superQualifiedName;// @Nullable
	private Set<String> implementQualifiedNames = new HashSet<String>();
	private List<FieldElement> fieldModels;// @Nullable @LazyInitialization
	private List<MethodElement> methodModels;// @Nullable @LazyInitialization

	public ClassElement(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	@Override
	protected void selfTraverse(ModelVisitor visitor) {
		super.selfTraverse(visitor);
		if (visitor.isDone())
			return;
		if (traverseFields(visitor))
			return;
		if (traverseMethods(visitor))
			return;
	}

	private boolean traverseMethods(ModelVisitor visitor) {
		if (methodModels == null) {
			return false;
		}

		for (MethodElement model : methodModels) {
			model.traverseModelTree(visitor);
			if (visitor.isDone())
				return true;
		}
		return false;
	}

	private boolean traverseFields(ModelVisitor visitor) {
		if (fieldModels == null) {
			return false;
		}
		for (FieldElement model : fieldModels) {
			model.traverseModelTree(visitor);
			if (visitor.isDone())
				return true;
		}
		return false;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public boolean isPackageInfo() {
		return qualifiedName.endsWith("package-info");
	}

	public String getPackageName() {
		final int index = qualifiedName.lastIndexOf('/');
		return qualifiedName.substring(0, index);
	}

	@Override
	public ModelType getModelType() {
		return ModelType.CLASS;
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

	public List<FieldElement> getFields() {
		return listOrEmpty(fieldModels);
	}

	public void addField(FieldElement model) {
		if (fieldModels == null)
			fieldModels = new ArrayList<FieldElement>();
		fieldModels.add(model);
	}

	public List<MethodElement> getMethods() {
		return listOrEmpty(methodModels);
	}

	public void addMethod(MethodElement model) {
		if (methodModels == null)
			methodModels = new ArrayList<MethodElement>();
		methodModels.add(model);
	}

	// @Utility
	private final static <T> List<T> listOrEmpty(final List<T> elements) {
		if (elements == null)
			return Collections.emptyList();
		return elements;
	}

	@Override
	public int hashCode() {
		return qualifiedName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ClassElement other = (ClassElement) obj;
		return qualifiedName.equals(other.qualifiedName);
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
