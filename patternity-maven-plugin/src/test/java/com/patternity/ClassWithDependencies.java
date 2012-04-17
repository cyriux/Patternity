package com.patternity;

import java.io.FileInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy class with various kinds of dependencies for testing, after it is
 * compiled into a .class
 *
 * @author Mohamed Bourogaa
 * @author Cyrille Martraire
 */
public final class ClassWithDependencies implements Serializable {

    /**
     * nested class
     */
    public static class MyValue {
    }

    private Integer name;
    private final BigDecimal[] test = new BigDecimal[1];
    private List<Integer> list = new ArrayList<Integer>();

    public MyValue getMyValue() {
        return null;
    }

    public FileInputStream[] doSameThing() {
        return null;
    }

    @Override
    public String toString() {
        return "" + name;// implicit StringBuilder
    }
}