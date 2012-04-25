package com.patternity.ast;

import com.patternity.util.Visitor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
public class InMemoryModelRepository implements ModelRepository {

    private ConcurrentMap<String, ClassElement> models = new ConcurrentHashMap<String, ClassElement>();

    @Override
    public void add(ClassElement model) {
        models.put(model.getQualifiedName(), model);
    }

    @Override
    public ClassElement findModel(String qualifiedName) {
        return models.get(qualifiedName);
    }

    @Override
    public void traverseModels(Visitor<ClassElement> visitor) {
        for (ClassElement classModel : models.values()) {
            visitor.visit(classModel);
            if (visitor.isDone())
                return;
        }
    }
}
