package com.patternity.rule;

import com.patternity.ast.ClassElement;

/**
 * Represents the configuration of 
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

    public boolean isValueObject(ClassElement classElement) {
        return classElement.hasAnnotation(valueObjectAnnotation);
    }

    public boolean isEntity(ClassElement classElement) {
        return classElement.hasAnnotation(entityAnnotation);
    }
}
