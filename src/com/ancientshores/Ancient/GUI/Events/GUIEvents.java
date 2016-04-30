package com.ancientshores.Ancient.GUI.Events;

import com.ancientshores.Ancient.Ancient;

public class GUIEvents {
    
    protected Ancient plugin;
    
    public GUIEvents(Ancient plugin)
    {
        
        this.plugin = plugin;
        
        try{
            
            registerGuiEvents();
            
        } catch (Exception ex) {
          
            ex.printStackTrace();
            
        }
        
    }
    
    //Register all GUI-Events
    private void registerGuiEvents()
    {
        
        //@NAME: TestEvent
        //@DOES: Is a test-component
        //plugin.getServer().getPluginManager().registerEvents(new TestEvent(this), plugin);
        
        //@NAME: PlayerMenu
        //@DOES: Players main menu
        plugin.getServer().getPluginManager().registerEvents(new PlayerMenu_Click(this), plugin);
        
        //@NAME: Open menu-gui
        //@DOES: Give menu-opener to player
        plugin.getServer().getPluginManager().registerEvents(new GuiMenuOpener(this), plugin);
        
    }
    
}
