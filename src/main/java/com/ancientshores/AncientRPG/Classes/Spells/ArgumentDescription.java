package com.ancientshores.AncientRPG.Classes.Spells;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface ArgumentDescription {
    String description();

    String[] parameterdescription();

    ParameterType[] rparams();

    ParameterType returntype();
}