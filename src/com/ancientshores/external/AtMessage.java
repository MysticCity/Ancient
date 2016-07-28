/*
 * @author: Th3Shadowbroker
 * @description: All requirements for AtMessage
 *
 */
package com.ancientshores.external;

import com.ancientshores.Ancient.Ancient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class AtMessage {
    
    public Ancient plugin;
    
    //Construction
    public AtMessage( Ancient plugin )
    {
        
        this.plugin = plugin;
        
        registerEvents();
        
    }
    
    //Check for AtMessage plugin
    public boolean isInstalled()
    {
        
        if ( plugin.getServer().getPluginManager().isPluginEnabled("AtMessage") ) //Plugin installed ?
        {
            
            return true; //Is installed
            
        }else{
            
            try{
                
                Plugin loc = Bukkit.getPluginManager().getPlugin("AtMessage"); //Plugin decleration
                
                Bukkit.getPluginManager().enablePlugin(loc); //Try to enable AtMessage
                
                return true; //Is installed
                
            }catch (Exception ex){
                
                return false; //Is not installed
                
            }
            
        }

    }
    
    //Register events
    private void registerEvents()
    {
        try {
            
            //Events that need to be registered
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
