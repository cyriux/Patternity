package com.patternity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ClassHandler;
import com.patternity.ast.ClassScanner;
import com.patternity.ast.asm.AsmScanner;

/**
 * Goal to verify allowed dependencies.
 * 
 * Example: mvn patternity:verify-dependencies
 * 
 * @goal verify-dependencies
 * @phase test
 * 
 * @author Mohamed Bourogaa
 * @author Cyrille Martraire
 */
public class PatternityMojo extends AbstractMojo {

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 */
	private File outputDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {
		System.out.println("PatternityMojo verify-dependencies starting...");
		final Collection<Violation> violations = processClasses();
		if (!violations.isEmpty()) {
			System.err.println("PatternityMojo verify-dependencies found " + violations.size() + " violations: "
					+ violations);
			for (Violation violation : violations) {
				System.err.println(violation);
			}
			throw new MojoFailureException(violations.toString());
		}
	}

	protected Collection<Violation> processClasses() {
		final File root = new File(outputDirectory, "classes");
		final List<ClassElement> classes = scanClasses(root);
		System.out.println("scanned " + classes.size() + " classes");
		final RuleEngine engine = new RuleEngine();
		return Collections.emptyList();
	}

	private final static List<ClassElement> scanClasses(final File root) {
		final List<ClassElement> classes = new ArrayList<ClassElement>();
		final ClassHandler handler = new ClassHandler() {
			@Override
			public void handleClass(ClassElement element) {
				classes.add(element);
			}
		};
		System.out.println("classesFile: " + root.getAbsolutePath());
		final List<File> files = walkFiles(root);
		for (File file : files) {
			try {
				final InputStream stream = new FileInputStream(file);
				scanClass(stream, handler);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return classes;
	}

	private final static void scanClass(final InputStream stream, ClassHandler handler) throws IOException {
		final ClassScanner scanner = newScanner();
		try {
			scanner.scan(stream, handler);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(stream);
		}
	}

	public final static ClassScanner newScanner() {
		return new AsmScanner();
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

	private final static List<File> walkFiles(final File file) {
		final ArrayList<File> files = new ArrayList<File>();
		walkFiles(file, files);
		return files;
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
	public String toString() {
		return "PatternityMojo 'verify-dependencies' outputDirectory=" + outputDirectory;
	}

}
