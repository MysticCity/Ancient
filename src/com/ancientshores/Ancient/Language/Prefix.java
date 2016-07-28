package com.ancientshores.Ancient.Language;

public class Prefix {
    
    public static String get(String input)
    {
        return input.replaceAll("&", "§") + " ";
    }
    
}
