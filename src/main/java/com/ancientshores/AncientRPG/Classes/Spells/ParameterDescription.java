package com.ancientshores.AncientRPG.Classes.Spells;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface ParameterDescription {
    String description();

    int amount();

    String returntype();

    String name();
}