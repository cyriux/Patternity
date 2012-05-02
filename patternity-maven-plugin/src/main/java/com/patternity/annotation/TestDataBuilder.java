/**
 *
 */
package com.patternity.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A specific builder to help create instances of immutable objects for testing.
 * 
 * @see <a
 *      href="http://nat.truemesh.com/archives/000714.html">TestDataBuilder</a>
 * 
 * @author Cyrille.Martraire
 */
@Retention(RetentionPolicy.CLASS)
@Inherited
@Documented
public @interface TestDataBuilder {

}
