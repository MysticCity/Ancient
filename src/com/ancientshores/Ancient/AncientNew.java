package com.ancientshores.Ancient;

import com.ancientshores.Ancient.Loader.Commands;
import com.ancientshores.Ancient.Loader.Components;
import com.ancientshores.Ancient.Loader.Events;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class AncientNew extends JavaPlugin {
    
    //All required variables behind this point:
    
    public String ConsoleBrand;  //Prefix used in the console
    public String ChatBrand;    //Prefix used in the chat
    
    public FileConfiguration config;    //Plugin config / Ancient main-config
    
    //END OF VARIABLES
    
    //Load it up
    @Override
    public void onEnable()
    {
        loadComponents();
        loadCommands();
        registerEvents();
    }
    
    //Shut it down
    @Override
    public void onDisable()
    {
        
    }

    //Load commands
    private void loadCommands()
    {
        /*
         *  Load all required commands
         */
        
        Commands commandLoader = new Commands( this );
        
    }
    
    //Register events
    private void registerEvents()
    {
        /*
         *  Register all required commands
         */
        
        Events eventLoader = new Events( this );
        
    }
    
    //Load components
    private void loadComponents()
    {
        /*
         *  Add all required components to load them on startup
         */
        
        Components componentLoader = new Components( this );
        
    }
    
}
