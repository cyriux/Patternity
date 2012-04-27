package com.patternity;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.patternity.ast.ClassElement;

public class ProcessorTest {

	@Test
	public void testProcess() {
		final String base = ClassesBuilder.class.getResource("/").getFile();
		final File root = new File(base + "..", "classes");
		System.out.println(root.getAbsoluteFile());
		System.out.println("classesFile: " + root.getAbsolutePath());
		final List<ClassElement> classes = new ClassesBuilder().parseAll(root);
		System.out.println("scanned " + classes.size() + " classes ************");
		final RuleEngine engine = new RuleEngine();
		Collections.emptyList();
	}

}
