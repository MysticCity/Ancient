package com.ancientshores.AncientRPG.Classes.Spells;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 13.02.13
 * Time: 00:06
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ParameterDescription {
    String description();

    int amount();

    String returntype();

    String name();
}
