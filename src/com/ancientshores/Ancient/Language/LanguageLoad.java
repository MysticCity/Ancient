package com.ancientshores.Ancient.Language;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class LanguageLoad {
    
    //Load up the language system
    public static void loadLanguageConfig(Plugin plugin)
    {
        
        CustomYml langConfig = new CustomYml(plugin, "languages.yml");
        
        loadDefaults(langConfig);
        setupLanguages(plugin);
        
    }
    
    //Load default settings
    private static void loadDefaults(CustomYml langConfig)
    {
        
        FileConfiguration config = langConfig.getConfig();
        
        config.addDefault("language", "en");
        
        config.options().copyDefaults(true);
        
        langConfig.saveConfig();
        
    }
    
    //Get the current languagecodes
    public static LanguageCode getLanguageCode(Plugin plugin)
    {
        
        CustomYml langConfig = new CustomYml(plugin, "languages.yml");
        String currentLang = langConfig.getConfig().getString("language").toLowerCase();
        
        if ( currentLang.equalsIgnoreCase( LanguageCode.NONE.name() ) ){ //If language set to default
            return LanguageCode.EN;
        }
        
        if ( currentLang.equalsIgnoreCase( LanguageCode.EN.name() ) ){ //If language set to english
            return LanguageCode.EN;
        }
        
        if ( currentLang.equalsIgnoreCase( LanguageCode.US.name() ) ){ //If language set to us-english
            return LanguageCode.US;
        }
        
        if ( currentLang.equalsIgnoreCase( LanguageCode.DE.name() ) ){ //If language set to german
            return LanguageCode.DE;
        }
        
        return LanguageCode.EN; //Default language
        
    }
    
    //Setup the default languages
    public static void setupLanguages(Plugin plugin)
    {
        
        /*
        *                       HOW TO SETUP DEFAULT LANGUAGES
        *   Use the language file for the language you want to setup by simply using
        *                       EN.setTextOnce(<Path to text>, <Text>);
        *
        */
        
        CustomYml EN_C = new CustomYml(plugin, "languages/en.yml"); //English YML
        CustomYml US_C = new CustomYml(plugin, "languages/us.yml"); //US-English YML
        CustomYml DE_C = new CustomYml(plugin, "languages/de.yml"); //German YML
        
        LanguageFile EN = new LanguageFile(plugin, LanguageCode.EN); //English lang-config
        LanguageFile US = new LanguageFile(plugin, LanguageCode.US); //US-English lang-config
        LanguageFile DE = new LanguageFile(plugin, LanguageCode.DE); //German lang-config
        
        /*
         *  EN-Defaults
         */
        
        EN.setTextOnce("Test", "test");
        
        /*
         *  US-Defaults
         */
        
        US.setTextOnce("Test", "test");
        
        /*
         *  DE-Defaults
         */
        
        DE.setTextOnce("Test", "test");
        
    }
    
}
