package com.patternity.rule;

import com.patternity.ast.ClassModel;

/**
 *
 */
public class Configuration {

    private String valueObjectAnnotation;
    private String entityAnnotation;

    public void setValueObjectAnnotation(String valueObjectAnnotation) {
        this.valueObjectAnnotation = valueObjectAnnotation;
    }

    public void setEntityAnnotation(String entityAnnotation) {
        this.entityAnnotation = entityAnnotation;
    }

    public boolean isValueObject(ClassModel classModel) {
        return classModel.hasAnnotation(valueObjectAnnotation);
    }

    public boolean isEntity(ClassModel classModel) {
        return classModel.hasAnnotation(entityAnnotation);
    }
}
