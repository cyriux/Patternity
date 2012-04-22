package com.patternity.ast;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class DependenciesCollector extends ModelVisitor {
    private Set<String> dependencies = new HashSet<String>();

    @Override
    public void enterModel(Model<?> model) {
        Set<String> modelDependencies = model.getDependencies();
        if(modelDependencies!=null)
            dependencies.addAll(modelDependencies);
    }

    public Set<String> getDependencies() {
        return dependencies;
    }
}
