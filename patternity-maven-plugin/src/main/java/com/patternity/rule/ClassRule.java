package com.patternity.rule;

import com.patternity.AnalysisLogger;
import com.patternity.ast.ClassModel;
import com.patternity.ast.ModelRepository;

/**
 *
 */
public interface ClassRule {
    void validate(ClassModel model, RuleContext context);
}
