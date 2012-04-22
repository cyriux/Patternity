package com.patternity.ast;

import com.patternity.util.Visitor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
public class InMemoryModelRepository implements ModelRepository {

    private ConcurrentMap<String, ClassModel> models = new ConcurrentHashMap<String, ClassModel>();

    @Override
    public void add(ClassModel model) {
        models.put(model.getQualifiedName(), model);
    }

    @Override
    public ClassModel findModel(String qualifiedName) {
        return models.get(qualifiedName);
    }

    @Override
    public void traverseModels(Visitor<ClassModel> visitor) {
        for (ClassModel classModel : models.values()) {
            visitor.visit(classModel);
            if (visitor.isDone())
                return;
        }
    }
}
