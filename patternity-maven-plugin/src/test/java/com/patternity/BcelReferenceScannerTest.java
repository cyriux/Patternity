package com.patternity;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.ConstantClass;
import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
import com.sun.org.apache.bcel.internal.classfile.DescendingVisitor;
import com.sun.org.apache.bcel.internal.classfile.EmptyVisitor;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Method;
import com.sun.org.apache.bcel.internal.generic.Type;

public class BcelReferenceScannerTest {

	public class DependencyEmitter extends EmptyVisitor {

		private JavaClass javaClass;

		@Override
		public void visitMethod(Method method) {
			if (Type.VOID != method.getReturnType()) {
				System.out.println("Method \t"+method.getName() + "\t Returned value " + method.getReturnType().toString().replace(".", "/"));
			}
		}

		public DependencyEmitter(JavaClass javaClass) {
			this.javaClass = javaClass;
		}

		@Override
		public void visitConstantClass(ConstantClass constantClass) {
			ConstantPool cp = javaClass.getConstantPool();
			String bytes = constantClass.getBytes(cp);
			System.out.println(bytes);
		}
	}

	@Test
	public void aFieldIsAReference() throws IOException {
		final InputStream stream = this.getClass().getResourceAsStream("ClassWithDependencies.class");

		final JavaClass javaClass = new ClassParser(stream, "com/patternity/ClassWithDependencies").parse();
		DependencyEmitter visitor = new DependencyEmitter(javaClass);
		DescendingVisitor classWalker = new DescendingVisitor(javaClass, visitor);
		classWalker.visit();

	}

}
