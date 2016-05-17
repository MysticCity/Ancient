package com.ancientshores.Ancient.GUI;

import com.ancientshores.Ancient.Ancient;
import org.bukkit.entity.Player;

public abstract class GUIExtension {
    
    public Ancient plugin;
    public Player player;
    public GUIMenu menu;
    
    public abstract void runExtension();
    
}
