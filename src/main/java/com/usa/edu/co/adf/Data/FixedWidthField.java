/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usa.edu.co.adf.Data;

import java.lang.annotation.ElementType;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Retention( RUNTIME )
@Target(FIELD)
/**
 *
 * @author Labing
 */

public @interface FixedWidthField {
    int position()default 0;
    int width()default 0;
}
