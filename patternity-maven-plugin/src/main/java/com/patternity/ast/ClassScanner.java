package com.patternity.ast;

import java.io.IOException;
import java.io.InputStream;

public interface ClassScanner {
    void scan(InputStream clazz, ClassHandler handler) throws IOException;
}
