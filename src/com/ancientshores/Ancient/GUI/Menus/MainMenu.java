package com.ancientshores.Ancient.GUI.Menus;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.GUI.GUIItemAction;
import com.ancientshores.Ancient.GUI.GUIItemStack;
import com.ancientshores.Ancient.GUI.GUIMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainMenu {
    
    private GUIMenu menu;
    private Player p;
    private Ancient plugin;
    
    //Construction
    public MainMenu( Player p , Ancient plugin )
    {
        this.plugin = plugin;
        this.menu = new GUIMenu( Ancient.lang.getText( "GUI.MainMenu.Title" ).replaceAll( "&" , "§" ) , 9 );
        this.p = p;
        
        loadMenu();
        
    }
    
    //Load menu-items
    private void loadMenu()
    {
        
        GUIItemStack helpItem = new GUIItemStack( Ancient.lang.getText( "GUI.MainMenu.Items.Help.Name" ).replaceAll( "&" , "§" ) , Material.BOOK , null, 8 );
        GUIItemAction helpAction = new GUIItemAction( plugin , menu , helpItem , "ancient help" );
        menu.addItem( helpItem );
        
        GUIItemStack statItem = new GUIItemStack( Ancient.lang.getText( "GUI.MainMenu.Items.Stats.Name" ).replaceAll( "&" , "§" ) , Material.SKULL_ITEM , null, 7 );
        GUIItemAction statAction = new GUIItemAction( plugin , menu , statItem , null );
        menu.addItem( statItem );
        
        GUIItemStack classItem = new GUIItemStack( Ancient.lang.getText( "GUI.MainMenu.Items.Classes.Name" ).replaceAll( "&" , "§" ) , Material.STONE_SWORD , null, 6 );
        GUIItemAction classAction = new GUIItemAction( plugin , menu , classItem , null );
        menu.addItem( classItem );
        
        
        helpAction.setExtension( new MainMenu_Extension_Help() );
        statAction.setExtension( new MainMenu_Extension_Stats() );
        
    }
    
    //Open to player
    public void open()
    {
        menu.openToUser( p );
    }
    
}
