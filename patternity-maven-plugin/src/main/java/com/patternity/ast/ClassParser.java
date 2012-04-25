package com.patternity.ast;

import java.io.IOException;
import java.io.InputStream;

/**
 * The interface of a class scanner that reads from an input stream and returns
 * the class element built from it
 */
public interface ClassParser {
	Iterable<ClassElement> scan(InputStream clazz) throws IOException;
}
