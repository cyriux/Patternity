package com.patternity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ModelElement;

/**
 * A very simple metamodel implementation
 */
public class MapBasedMetaModel implements MetaModel {
	private final Map<String, ClassElement> classes = new HashMap<String, ClassElement>();

	public MapBasedMetaModel(Iterable<ClassElement> elements) {
		for (ClassElement element : elements) {
			classes.put(element.getQualifiedName(), element);
		}
	}

	public MapBasedMetaModel(ClassElement... elements) {
		this(Arrays.asList(elements));
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
			return classElement.isPackageInfo() ? false : isPackageMarked(classElement, tag);
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

	@Override
	public Iterator<ClassElement> iterator() {
		return classes.values().iterator();
	}

	public int size() {
		return classes.size();
	}

	@Override
	public String toString() {
		return "MetaModel: " + size() + " classes";
	}

}
