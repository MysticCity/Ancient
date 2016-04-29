/*
 * @author: Th3Shadowbroker
 * @description: This class handles everything to do with LOCStats 
 *
 */
package com.ancientshores.external;

import com.ancientshores.Ancient.Ancient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LocStats {

    protected Ancient plugin;
    
    //Construct class
    public LocStats(Ancient plugin)
    {
        
        this.plugin = plugin; //Set plugin
        
    }
    
    //Check for LOCStats plugin
    public boolean isInstalled()
    {
        
        if ( plugin.getServer().getPluginManager().isPluginEnabled("LOCStats") ) //Plugin installed ?
        {
            
            return true; //Is installed
            
        }else{
            
            try{
                
                Plugin loc = Bukkit.getPluginManager().getPlugin("LOCStats"); //Plugin decleration
                
                Bukkit.getPluginManager().enablePlugin(loc); //Try to enable LOCStats
                
                return true; //Is installed
                
            }catch (Exception ex){
                
                return false; //Is not installed
                
            }
            
        }

    }
    
    //Registers all LOCStats required Listeners
    public static void registerEvents()
    {
        //Currently nothing
    }
    
}
