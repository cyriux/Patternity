package com.patternity.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An Iterable over every file under the given root file
 */
public class Files implements Iterable<File> {

	private final File root;
	private transient List<File> files = null;

	public Files(File root) {
		this.root = root;
	}

	private final static void walkFiles(final File file, List<File> files) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				walkFiles(child, files);
			}
		} else {
			if (file.getName().endsWith(".class")) {
				files.add(file);
			}
		}
	}

	@Override
	public Iterator<File> iterator() {
		if (files == null) {
			files = new ArrayList<File>();
			walkFiles(root, files);
		}
		return files.iterator();
	}

	@Override
	public String toString() {
		return "Files root: " + root.getAbsolutePath();
	}

}
