package com.ancientshores.Ancient.Classes.Spells;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandDescription
{
  String description();
  
  String[] argnames();
  
  ParameterType[] parameters();
  
  String name();
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\CommandDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */