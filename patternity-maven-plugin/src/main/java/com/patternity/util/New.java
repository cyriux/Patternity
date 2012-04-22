package com.patternity.util;

import java.util.HashSet;

/**
 *
 */
public class New {

    public static <T> HashSet<T> hashSet(T... values) {
        HashSet<T> set = new HashSet<T>();
        for (T value : values)
            set.add(value);
        return set;
    }
}
