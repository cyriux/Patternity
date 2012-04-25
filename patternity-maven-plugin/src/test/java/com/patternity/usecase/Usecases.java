package com.patternity.usecase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.patternity.ast.ClassElement;
import com.patternity.ast.ClassHandler;
import com.patternity.ast.ClassScanner;
import com.patternity.ast.InMemoryModelRepository;
import com.patternity.ast.ModelRepository;
import com.patternity.ast.PackageElement;
import com.patternity.ast.asm.AsmScanner;

/**
 *
 */
public class Usecases {

	public static ClassScanner newScanner() {
		return new AsmScanner();
	}

	public static String formatResourceName(final Class<?> clazz) {
		return toResourcePath(clazz.getName()) + ".class";
	}

	private static String toResourcePath(final String name) {
		return "/" + name.replace('.', '/');
	}

	public static PackageElement scanPackage(final Class<?> type) throws IOException {
		final String resourceName = toResourcePath(type.getPackage().getName()) + "/package-info.class";
		final ClassElement clazz = scanClass(resourceName);
		return new PackageElement(clazz.getQualifiedName(), clazz.getAnnotations());
	}

	public static ClassElement scanClass(final Class<?> type) throws IOException {
		return scanClass(formatResourceName(type));
	}

	private static ClassElement scanClass(final String resourceName) throws IOException {
		return scanClass(openStreamOf(resourceName));
	}

	private static ClassElement scanClass(final InputStream stream) throws IOException {
		final SingleClassHandlerCollector handler = new SingleClassHandlerCollector();
		final ClassScanner scanner = newScanner();
		try {
			scanner.scan(stream, handler);
			return handler.getCollected();
		} finally {
			close(stream);
		}
	}

	public static ModelRepository createAndFillRepository(ClassScanner scanner, Set<String> resourceNames)
			throws IOException {
		final ModelRepository repository = new InMemoryModelRepository();
		scanResources(scanner, resourceNames, repository);
		return repository;
	}

	private static void scanResources(ClassScanner scanner, Set<String> resourceNames, ModelRepository repository)
			throws IOException {
		final ClassHandler handler = repositoryClassHandler(repository);
		for (String resourceName : resourceNames) {
			final InputStream stream = openStreamOf(resourceName);
			try {
				scanner.scan(stream, handler);
			} finally {
				close(stream);
			}
		}
	}

	private static ClassHandler repositoryClassHandler(final ModelRepository repository) {
		return new ClassHandler() {
			@Override
			public void handleClass(ClassElement model) {
				repository.add(model);
			}
		};
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

	private static InputStream openStreamOf(String resourceName) {
		return Usecases.class.getResourceAsStream(resourceName);
	}

}
