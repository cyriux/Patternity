package com.patternity.annotation.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An object that never changes once built. This enables to share it freely
 * between any processes, threads, and even untrusted code as it can never be
 * changed.
 * 
 * @author Cyrille.Martraire
 */
@Retention(RetentionPolicy.CLASS)
@Inherited
@Documented
public @interface Immutable {

}
