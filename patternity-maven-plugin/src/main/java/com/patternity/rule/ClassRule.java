package com.patternity.rule;

import com.patternity.AnalysisLogger;
import com.patternity.ast.ClassElement;
import com.patternity.ast.ModelRepository;

/**
 *
 */
public interface ClassRule {
    void validate(ClassElement model, RuleContext context);
}
