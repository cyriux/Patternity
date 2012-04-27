package com.patternity.ast;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the metamodel of packages, classes and their methods and fields,
 * including their annotations. The purpose of this model is to make it easy for
 * the verification rules to do their job.
 */
public class MetaModel {

	private final Map<String, ClassElement> classes = new HashMap<String, ClassElement>();

	public MetaModel(ClassElement... elements) {
		for (ClassElement element : elements) {
			classes.put(element.getQualifiedName(), element);
		}
	}

	public ClassElement findElement(String qualifiedName) {
		return classes.get(qualifiedName);
	}

	public boolean isMarked(final ModelElement<?> element, final String tag) {
		if (element == null) {
			return false;
		}
		if (element.hasAnnotation(tag)) {
			return true;
		}
		if (element instanceof ClassElement) {
			final ClassElement classElement = (ClassElement) element;
			return isPackageMarked(classElement, tag);
		}
		return false;
	}

	private boolean isPackageMarked(ClassElement classElement, String tag) {
		final ClassElement packageInfo = findPackageInfo(classElement);
		return isMarked(packageInfo, tag);
	}

	private ClassElement findPackageInfo(ClassElement classElement) {
		return findElement(classElement.getPackageName() + "/package-info");
	}

	public int size() {
		return classes.size();
	}

	@Override
	public String toString() {
		return "MetaModel: " + size() + " classes";
	}

}
