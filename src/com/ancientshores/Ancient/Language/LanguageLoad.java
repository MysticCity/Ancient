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
        
        config.addDefault("language", LanguageCode.EN.name() );
        
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
        
        if ( currentLang.equalsIgnoreCase( LanguageCode.SYSTEM.name() ) ){ //If language set to system
            return LanguageCode.SYSTEM;
        }
        
        return LanguageCode.EN; //Default language
        
    }
    
    //Setup the default languages
    public static void setupLanguages(Plugin plugin)
    {
        
        /*
        *                       HOW TO SETUP DEFAULT LANGUAGES
        *   Use the language file for the language you want to setup by simply using
        *                       EN:DE:US.setTextOnce(<Path to text>, <Text>);
        *
        */
        
        CustomYml EN_C = new CustomYml(plugin, "languages/en.yml"); //English YML
        CustomYml US_C = new CustomYml(plugin, "languages/us.yml"); //US-English YML
        CustomYml DE_C = new CustomYml(plugin, "languages/de.yml"); //German YML
        
        LanguageFile EN = new LanguageFile(plugin, LanguageCode.EN); //English lang-config
        LanguageFile US = new LanguageFile(plugin, LanguageCode.US); //US-English lang-config
        LanguageFile DE = new LanguageFile(plugin, LanguageCode.DE); //German lang-config
        
        LanguageFile SYS = new LanguageFile(plugin, LanguageCode.SYSTEM); //System lang-config (required for prefixes etc.)
        
        /*
         *  EN-Defaults
         */
        
        EN.setTextOnce("Test", "test");
        
        //GUI-Texts
        
        EN.setTextOnce("GUI.MainMenu.Title", "&eMenu");
        EN.setTextOnce("GUI.MainMenu.Items.Help.Name", "&eHelp");
        EN.setTextOnce("GUI.MainMenu.Items.Help.Description", "&9If you need help");
        
        EN.setTextOnce("GUI.MainMenu.Items.Stats.Name", "&cStats");
        EN.setTextOnce("GUI.MainMenu.Items.Stats.Description", "&9View your stats");
        
        EN.setTextOnce("GUI.MainMenu.Items.Classes.Name", "&9Classes");
        EN.setTextOnce("GUI.MainMenu.Items.Classes.Description", "&9Change your class");
        
        EN.setTextOnce("GUI.StatMenu.Items.Kills.Name", "&2Kills");
        EN.setTextOnce("GUI.StatMenu.Items.Kills.Description", "%&2%AMOUNT% kills");
        
        EN.setTextOnce("GUI.StatMenu.Items.Deaths.Name", "&cDeaths");
        EN.setTextOnce("GUI.StatMenu.Items.Deaths.Description", "&c%AMOUNT% deaths");
        
        /*
         *  US-Defaults
         */
        
        US.setTextOnce("Test", "test");
        
        //GUI-Texts
                
        US.setTextOnce("GUI.MainMenu.Title", "&eMenu");
        US.setTextOnce("GUI.MainMenu.Help.Name", "&eHelp");
        US.setTextOnce("GUI.MainMenu.Help.Description", "&9If you need help");
        
        US.setTextOnce("GUI.MainMenu.Items.Stats.Name", "&cStats");
        US.setTextOnce("GUI.MainMenu.Items.Stats.Description", "&9View your stats");
        
        US.setTextOnce("GUI.MainMenu.Items.Classes.Name", "&9Classes");
        US.setTextOnce("GUI.MainMenu.Items.Classes.Description", "&9Change your class");
        
        US.setTextOnce("GUI.StatMenu.Items.Kills.Name", "&2Kills");
        US.setTextOnce("GUI.StatMenu.Items.Kills.Description", "%&2%AMOUNT% kills");
        
        US.setTextOnce("GUI.StatMenu.Items.Deaths.Name", "&cDeaths");
        US.setTextOnce("GUI.StatMenu.Items.Deaths.Description", "&c%AMOUNT% deaths");
        
        /*
         *  DE-Defaults
         */
        
        DE.setTextOnce("Test", "test");
        
        //GUI-Texts
               
        DE.setTextOnce("GUI.MainMenu.Title", "&eMenü");
        DE.setTextOnce("GUI.MainMenu.Help.Name", "&eHilfe");
        DE.setTextOnce("GUI.MainMenu.Help.Description", "&9Falls du hilfe brauchst");
        
        DE.setTextOnce("GUI.MainMenu.Items.Stats.Name", "&cStatistik");
        DE.setTextOnce("GUI.MainMenu.Items.Stats.Description", "&9Sieh dir deine Statistik");
        
        DE.setTextOnce("GUI.MainMenu.Items.Classes.Name", "&9Klassen");
        DE.setTextOnce("GUI.MainMenu.Items.Classes.Description", "&9Aendere deine Klasse");
        
        DE.setTextOnce("GUI.StatMenu.Items.Kills.Name", "&2Kills");
        DE.setTextOnce("GUI.StatMenu.Items.Kills.Description", "%&2%AMOUNT% kills");
        
        DE.setTextOnce("GUI.StatMenu.Items.Deaths.Name", "&cDeaths");
        DE.setTextOnce("GUI.StatMenu.Items.Deaths.Description", "&c%AMOUNT% deaths");
        
        /*
         * System-Defaults
         */
        
        //Chat prefixes/ConsoleBrands
        SYS.setTextOnce("Ancient.ConsoleBrand", "[Ancient]");
        SYS.setTextOnce("Ancient.ChatBrand", "&e[Ancient]");
        SYS.setTextOnce("Party.PartyBrand", "&9[Party]");
        SYS.setTextOnce("Guild.GuildBrands", "&2[%GUILD_NAME%]");
    }
    
}
