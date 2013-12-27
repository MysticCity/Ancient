package com.ancientshores.AncientRPG.Classes.Spells;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 12.02.13
 * Time: 00:54
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CommandDescription {
    String description();

    String[] argnames();

    ParameterType[] parameters();

    String name();
}
