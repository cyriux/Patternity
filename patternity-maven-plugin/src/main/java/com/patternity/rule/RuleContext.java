package com.patternity.rule;

import com.patternity.MetaModel;
import com.patternity.ViolationReporter;

/**
 * Represents the context each rule uses to perform its verification and to
 * report diagnostics
 */
public interface RuleContext extends MetaModel, ViolationReporter {

}
