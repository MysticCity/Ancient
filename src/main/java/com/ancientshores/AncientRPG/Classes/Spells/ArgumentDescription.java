package com.ancientshores.AncientRPG.Classes.Spells;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 11.02.13
 * Time: 20:25
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ArgumentDescription
{
	String description();
	String[] parameterdescription();
	ParameterType[] rparams();
	ParameterType returntype();
}
