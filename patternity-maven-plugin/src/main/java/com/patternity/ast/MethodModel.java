package com.patternity.ast;

/**
 *
 */
public class MethodModel extends Model<MethodModel> {
    private final String name;

    public MethodModel(String name) {
        super();
        this.name = name;
    }

    @Override
    public ModelType getModelType() {
        return ModelType.METHOD;
    }
}
