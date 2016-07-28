package com.ancientshores.Ancient.GUI.Events;

import com.ancientshores.Ancient.GUI.GUIExtension;

public class TestExtension extends GUIExtension {

    @Override
    public void runExtension() {
        player.sendMessage( "§9Test successfully !" );
    }
    
}
