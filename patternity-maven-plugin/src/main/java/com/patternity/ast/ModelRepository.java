package com.patternity.ast;

import com.patternity.util.Visitor;

/**
 *
 */
public interface ModelRepository {
    void add(ClassElement model);

    ClassElement findModel(String qualifiedName);

    void traverseModels(Visitor<ClassElement> visitor);
}
