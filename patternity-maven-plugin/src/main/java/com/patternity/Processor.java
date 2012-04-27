/**
 * 
 */
package com.patternity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ClassParser;
import com.patternity.ast.asm.AsmScanner;
import com.patternity.util.Files;

/**
 * The procesor that walks the files from the root and scans each class to build
 * the metamodel.
 */
public class Processor {

	public List<ClassElement> parseAll(final File root) {
		final List<ClassElement> classes = new ArrayList<ClassElement>();
		for (File file : new Files(root)) {
			final ClassElement scanned = foreach(file);
			if (scanned != null) {
				classes.add(scanned);
			}
		}
		return classes;
	}

	private static ClassElement foreach(File file) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
			System.out.println("scanning: " + file.getName());
			final ClassParser scanner = new AsmScanner();
			return scanner.scan(stream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(stream);
		}
		return null;
	}

	private static void close(InputStream stream) {
		if (stream == null)
			return;
		try {
			stream.close();
		} catch (IOException e) {
			// ignore...
		}
	}

}
