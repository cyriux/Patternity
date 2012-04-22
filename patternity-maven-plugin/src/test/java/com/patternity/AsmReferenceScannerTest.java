package com.patternity;

import com.patternity.asm.DependencyVisitor;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;

public class AsmReferenceScannerTest {

    @Test
    public void aFieldIsAReference() throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("ClassWithDependencies.class");
        final DependencyVisitor v = new DependencyVisitor();
        final ClassReader cr = new ClassReader(stream);
        cr.accept(v, 0);
        System.out.println(v.getClasses());
    }
}
