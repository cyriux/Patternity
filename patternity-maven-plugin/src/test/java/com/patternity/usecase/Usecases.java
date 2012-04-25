package com.patternity.usecase;

import com.patternity.ast.*;
import com.patternity.ast.asm.AsmScanner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 *
 */
public class Usecases {

    public static ClassScanner newScanner() {
        return new AsmScanner();
    }

    public static String formatResourceName(Class<?> clazz) {
        return formatResourceName(clazz, "");
    }

    public static String formatResourceName(Class<?> clazz, String classNameSuffix) {
        return "/" + clazz.getName().replace('.', '/') + classNameSuffix + ".class";
    }

    public static ClassElement scanClass(Class<?> type) throws IOException {
        String resourceName = formatResourceName(type);
        InputStream stream = openStreamOf(resourceName);

        try {
            ClassHandlerCollector handler = new ClassHandlerCollector();
            newScanner().scan(stream, handler);
            return handler.getCollected();
        } finally {
            close(stream);
        }
    }


    public static InMemoryModelRepository createAndFillRepository(
            ClassScanner scanner,
            Set<String> resourceNames) throws IOException {
        InMemoryModelRepository repository = new InMemoryModelRepository();
        scanResources(scanner, resourceNames, repository);
        return repository;
    }

    private static void scanResources(ClassScanner scanner, Set<String> resourceNames, ModelRepository repository) throws IOException {
        ClassHandler handler = repositoryClassHandler(repository);
        for (String resourceName : resourceNames) {
            InputStream stream = openStreamOf(resourceName);
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
