package com.ancientshores.Ancient.Chat;

import com.ancientshores.Ancient.Language.CustomYml;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class AncientChat {
    
    protected static CustomYml configFile;
    protected static FileConfiguration config;
    
    //Write the config-file
    public static void writeConfig(Plugin plugin)
    {
        try{

            configFile = new CustomYml(plugin, "chatconfig.yml");
            config = configFile.getConfig();
            loadDefaultConfig();
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
    //Load defaults
    private static void loadDefaultConfig()
    {
        try{
        
            config.addDefault("Chat.ModuleEnabled", true);
            config.addDefault("Chat.Enabled.Whispering",true);
            config.addDefault("Chat.Enabled.RangeChat", true);
            config.addDefault("Chat.RangeChat.RangeInChunks", 100);

            config.options().copyDefaults(true);
            saveConfig();
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
    //Save the config
    private static void saveConfig()
    {
        try{
            
            configFile.saveConfig();
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
    //Is chat enabled ?
    public static boolean chatIsEnabled()
    {
        if ( config.getBoolean("Chat.ModuleEnabled") == true ) {
            return true;
        }else{
            return false;
        }
    }
    
    //Is whiper-chat enabled ?
    public static boolean whisperingIsEnabled()
    {
        if ( config.getBoolean("Chat.Enabled.Whispering") == true ) {
            return true;
        }else{
            return false;
        }
    }
    
    //Is range-chat enabled ?
    public static boolean rangeChatIsEnabled()
    {
        if ( config.getBoolean("Chat.Enabled.RangeChat") == true ) {
            return true;
        }else{
            return false;
        }
    }
    
    //Set range-chat range
    public static void setChatRange(int i)
    {
        config.set("Chat.RangeChat.RangeInChunks", i);
        saveConfig();
    }
    
    //Get the range-chat range
    public static int getChatRange()
    {
        return config.getInt("Chat.RangeChat.RangeInChunks");
    }

}
