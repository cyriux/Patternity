package com.patternity.ast;

import java.util.HashSet;
import java.util.Set;

/**
 * Collects dependencies by walking the metamodel
 */
public class DependenciesCollector extends ModelVisitor {
	private final Set<String> dependencies = new HashSet<String>();

	@Override
	public void enterModel(ModelElement<?> model) {
		Set<String> modelDependencies = model.getDependencies();
		if (modelDependencies != null)
			dependencies.addAll(modelDependencies);
	}

	public Set<String> getDependencies() {
		return dependencies;
	}
}
