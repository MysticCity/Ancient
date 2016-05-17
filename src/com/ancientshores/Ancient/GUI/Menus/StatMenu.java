package com.ancientshores.Ancient.GUI.Menus;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.GUI.GUIItemStack;
import com.ancientshores.Ancient.GUI.GUIMenu;
import com.th3shadowbroker.loc.obj.PlayerProfile;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class StatMenu {
    
    private GUIMenu menu;
    private Player p;
    private Ancient plugin;
    
    //Construction
    public StatMenu( Player p , Ancient plugin )
    {
        this.plugin = plugin;
        this.menu = new GUIMenu( Ancient.lang.getText( "GUI.MainMenu.Title" ).replaceAll( "&" , "§" ) , 9 );
        this.p = p;
        
        loadMenu();
        
    }
    
    //Load items into the menu
    private void loadMenu()
    {
        
        PlayerProfile profile = new PlayerProfile( plugin , p );
        
        if ( profile.getStatStr("PlayerKills") != null )
        {
            
            List<String> description = new ArrayList<String>();
            description.add( plugin.lang.getText( "GUI.StatMenu.Items.Kills.Description" ).replaceAll( "%AMOUNT%" , profile.getStatInt( "PlayerKills" ).toString() ) );
            
            GUIItemStack killsItem = new GUIItemStack( plugin.lang.getText("GUI.StatMenu.Items.Kills.Name").replaceAll( "&" , "§" ) , Material.STONE_SWORD , null , 1);
        
        
        }
        
        if ( profile.getStatStr("PlayerDeaths") != null )
        {
            
            GUIItemStack deathsItem = new GUIItemStack( plugin.lang.getText("GUI.StatMenu.Items.Deaths.Name").replaceAll( "&" , "§" ) , Material.SKULL_ITEM , null , 1);

            
        }
        
    }   
    
    //Open-menu
    public void open()
    {
        menu.openToUser( p );
    }
    
    //Get the menu
    public GUIMenu getMenu()
    {
        return menu;
    }
    
}
