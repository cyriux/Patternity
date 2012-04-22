package com.patternity.ast.asm;

import com.patternity.ast.ClassHandler;
import com.patternity.ast.ClassScanner;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class AsmScanner implements ClassScanner {

    @Override
    public void scan(InputStream clazz, ClassHandler handler) throws IOException {
        if (clazz == null)
            throw new IllegalArgumentException("Stream cannot be null");
        ClassReader cr = new ClassReader(clazz);
        ScannerContext context = new ScannerContext(handler);
        cr.accept(context.getClassVisitor(), 0);
    }
}
