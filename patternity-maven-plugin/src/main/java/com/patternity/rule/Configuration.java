package com.patternity.rule;

import com.patternity.ast.ClassElement;

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

    public boolean isValueObject(ClassElement classModel) {
        return classModel.hasAnnotation(valueObjectAnnotation);
    }

    public boolean isEntity(ClassElement classModel) {
        return classModel.hasAnnotation(entityAnnotation);
    }
}
