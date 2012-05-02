package com.patternity.ast.asm;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ClassHandler;
import com.patternity.ast.ClassParser;
import com.patternity.ast.ClassScanner;

/**
 * An implementation of a class scanner that uses ASM to analyse the .class
 * bytecode
 */
public class AsmScanner implements ClassScanner, ClassParser {

	@Override
	public void scan(InputStream stream, ClassHandler handler) throws IOException {
		if (stream == null)
			throw new IllegalArgumentException("Stream cannot be null");
		ClassReader cr = new ClassReader(stream);
		ScannerContext context = new ScannerContext(handler);
		cr.accept(context.getClassVisitor(), 0);
	}

	@Override
	public ClassElement scan(final InputStream stream) throws IOException {
		final SingleClassHandlerCollector handler = new SingleClassHandlerCollector();
		scan(stream, handler);
		return handler.getCollected();
	}
}
