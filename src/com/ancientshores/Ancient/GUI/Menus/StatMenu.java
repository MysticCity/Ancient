package com.ancientshores.Ancient.GUI.Menus;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.GUI.GUIItemAction;
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
        this.menu = new GUIMenu( Ancient.lang.getText( "GUI.StatMenu.Title" ).replaceAll( "&" , "§" ) , 9 );
        this.p = p;
        
        loadMenu();
        
    }
    
    //Load items into the menu
    private void loadMenu()
    {
        
        PlayerProfile profile = new PlayerProfile( plugin , p );
        
        
        //Try to load player-profile
        int PlayerKills = 0;
        int PlayerDeaths = 0;
        
        try {                                                   //Get PlayerKills-Stat via LocalStats
            PlayerKills = profile.getStatInt( "PlayerKills" );
        } catch ( Exception ex ) {
            profile.setStat( "PlayerKills" , 0 );
            PlayerKills = 0;
        }
        //=================================
        try {                                                   //Get PlayerDeaths-Stat via LocalStats
            PlayerDeaths = profile.getStatInt( "PlayerDeaths" );
        } catch ( Exception ex ) {
            profile.setStat( "PlayerDeaths" , 0 );
            PlayerDeaths = 0;
        }
        //=================================

        //Setup item-stacks and there descriptions
        List<String> PlayerKillsItemDesc = new ArrayList<String>();
        List<String> PlayerDeathsItemDesc = new ArrayList<String>();
        
        //Add values to item-description
        PlayerKillsItemDesc.add( plugin.lang.getText( "GUI.StatMenu.Items.Kills.Description" ).replaceAll( "&" , "§" ).replaceAll( "%AMOUNT%" , String.valueOf( PlayerKills ) ) );
        PlayerDeathsItemDesc.add( plugin.lang.getText( "GUI.StatMenu.Items.Deaths.Description" ).replaceAll( "&" , "§" ).replaceAll( "%AMOUNT%" , String.valueOf( PlayerDeaths ) ) );
        
        GUIItemStack PlayerKillsItem = new GUIItemStack( plugin.lang.getText( "GUI.StatMenu.Items.Kills.Name" ).replaceAll( "&" , "§" ) , Material.STONE_SWORD , PlayerKillsItemDesc , 0 );
        GUIItemStack PlayerDeathsItem = new GUIItemStack( plugin.lang.getText( "GUI.StatMenu.Items.Deaths.Name" ).replaceAll( "&" , "§" ) , Material.SKULL_ITEM , PlayerDeathsItemDesc , 1 );
        
        //Add some actions to prevent items from moving/removing
        GUIItemAction PlayerKillsItemAction = new GUIItemAction( plugin, menu , PlayerKillsItem , null );
        GUIItemAction PlayerDeathsItemAction = new GUIItemAction( plugin, menu , PlayerDeathsItem , null );
        
        menu.addItem( PlayerKillsItem );
        menu.addItem( PlayerDeathsItem );
        
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
