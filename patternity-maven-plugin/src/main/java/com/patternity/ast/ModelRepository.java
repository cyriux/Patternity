package com.patternity.ast;

import com.patternity.util.Visitor;

/**
 *
 */
public interface ModelRepository {
    void add(ClassModel model);

    ClassModel findModel(String qualifiedName);

    void traverseModels(Visitor<ClassModel> visitor);
}
