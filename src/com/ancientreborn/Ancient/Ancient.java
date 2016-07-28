package com.ancientreborn.Ancient;

import com.ancientreborn.Ancient.Addon.AncientAddonLoader;
import com.ancientreborn.Ancient.Loader.Commands;
import com.ancientreborn.Ancient.Loader.Components;
import com.ancientreborn.Ancient.Loader.Dependencys;
import com.ancientreborn.Ancient.Loader.Events;
import com.ancientreborn.Ancient.Loader.Prefixes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Ancient extends JavaPlugin {
    
    //All required variables behind this point:
    
    public static String ConsoleBrand;  //Prefix used in the console
    public static String ChatBrand;    //Prefix used in the chat
    
    public FileConfiguration config;    //Plugin config / Ancient main-config
    
    private static Ancient instance;
    
    //END OF VARIABLES
    
    //Load it up
    @Override
    public void onEnable()
    {
        
        instance = this;
        
        loadComponents();
        
        loadCommands();
        
        registerEvents();
        
        loadAddons();
        
    }
    
    //Shut it down
    @Override
    public void onDisable()
    {
        
    }

    //Check dependencies
    private void checkDependencies()
    {
        /*
         *  Check all required or optional dependencies
         */
        
        Dependencys dependencyLoader = new Dependencys( this );
        
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
    
    //Load prefix
    private void loadPrefixes()
    {
        /*
         *  Load all required Prefixes
         */
        
        Prefixes prefixLoader = new Prefixes( this );
        
    }
    
    //Try to load addons
    private void loadAddons()
    {
        
        AncientAddonLoader AddonLoader = new AncientAddonLoader();
        
    }
    
    //Get the current instance of Ancient as Ancient
    public static Ancient getInstance()
    {
        
        return instance;
        
    }
    
    //Get the current instance of Ancient as Plugin
    public static Plugin getPluginInstance()
    {
        
        return Bukkit.getPluginManager().getPlugin( Ancient.getInstance().getDescription().getName() );
        
    }
    
}
