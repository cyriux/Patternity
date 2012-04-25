package com.patternity.ast;

/**
 * Represents a field in the metamodel
 */
public class FieldElement extends ModelElement<FieldElement> {
    private final String name;

    public FieldElement(String name) {
        this.name = name;
    }

    @Override
    public ModelType getModelType() {
        return ModelType.FIELD;
    }

    public String getFieldName() {
        return name;
    }

    @Override
    public String toString() {
        return "FieldModel{" +
                "name='" + name + '\'' +
                ", dependsOn:'" + getDependencies() + '\'' +
                '}';
    }
}
