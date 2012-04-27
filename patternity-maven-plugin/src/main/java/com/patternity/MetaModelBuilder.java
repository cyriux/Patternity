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
import com.patternity.ast.ClassHandler;
import com.patternity.ast.ClassScanner;
import com.patternity.ast.asm.AsmScanner;
import com.patternity.util.Files;

/**
 * The procesor that walks the files from the root and scans each class to build
 * the metamodel.
 */
public class MetaModelBuilder {

	private final List<ClassElement> classes = new ArrayList<ClassElement>();
	private final ClassHandler handler = new ClassHandler() {
		@Override
		public void handleClass(ClassElement element) {
			classes.add(element);
		}
	};

	public MetaModel build(final File root) {
		classes.clear();
		for (File file : new Files(root)) {
			collect(file);
		}
		return new MapBasedMetaModel(classes);
	}

	private void collect(File file) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
			System.out.println("scanning: " + file.getName());
			final ClassScanner scanner = new AsmScanner();
			scanner.scan(stream, handler);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			close(stream);
		}
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
