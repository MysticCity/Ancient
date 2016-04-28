package com.ancientshores.Ancient.Classes.Spells;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ArgumentDescription
{
  String description();
  
  String[] parameterdescription();
  
  ParameterType[] rparams();
  
  ParameterType returntype();
}
