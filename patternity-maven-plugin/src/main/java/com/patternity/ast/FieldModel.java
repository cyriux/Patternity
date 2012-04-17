package com.patternity.ast;

/**
 *
 */
public class FieldModel extends Model<FieldModel> {
    private final String name;

    public FieldModel(String name) {
        super();
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
