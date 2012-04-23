package com.patternity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AnalysisLogger {

    private List<String> violations = new ArrayList<String>();

    public void reportViolation(String rule, String message) {
        violations.add("[" + rule + "]: " + message);
    }
}
