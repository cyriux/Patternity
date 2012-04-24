/**
 *
 */
package com.patternity.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents an entity in the DDD sense, with an identity and a continuity over
 * time
 * 
 * @author Cyrille.Martraire
 */
@Retention(RetentionPolicy.CLASS)
@Inherited
@Documented
public @interface Entity {

}
