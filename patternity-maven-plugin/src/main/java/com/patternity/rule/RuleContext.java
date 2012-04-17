package com.patternity.rule;

import com.patternity.AnalysisLogger;
import com.patternity.ast.ClassModel;
import com.patternity.ast.ModelRepository;

/**
 *
 */
public class RuleContext {
    private ModelRepository repository;
    private AnalysisLogger logger;
    private Configuration configuration;

    public void reportViolation(String rule, String message) {
        logger.reportViolation(rule, message);
    }

    public ModelRepository getModelRepository() {
        return repository;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
