package com.patternity.ast;

import java.io.IOException;
import java.io.InputStream;

/**
 * The interface of a class scanner that reads from an input stream, creates a
 * class element and hands it to the given class handler
 */
public interface ClassScanner {
	void scan(InputStream clazz, ClassHandler handler) throws IOException;
}
