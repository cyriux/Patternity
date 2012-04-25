package com.patternity.rule;

import com.patternity.AnalysisLogger;
import com.patternity.ast.ClassElement;
import com.patternity.ast.ModelElement;
import com.patternity.ast.ModelRepository;

/**
 * Represents the context each rule uses to perform its verification and to
 * report diagnostics
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

	public boolean isMarked(final ModelElement<?> element, final String marker) {
		if ("ValueObject".equalsIgnoreCase(marker)) {
			return configuration.isValueObject((ClassElement) element);
		}
		if ("Entity".equalsIgnoreCase(marker)) {
			return configuration.isEntity((ClassElement) element);
		}
		return false;
	}
}
