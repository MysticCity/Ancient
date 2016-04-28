package com.ancientshores.Ancient.Classes.Spells;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface CommandDescription {
    String description();

    String[] argnames();

    ParameterType[] parameters();

    String name();
}