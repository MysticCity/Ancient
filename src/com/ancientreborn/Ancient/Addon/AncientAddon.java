package com.ancientreborn.Ancient.Addon;

import com.ancientreborn.Ancient.Ancient;
import org.bukkit.Bukkit;
import org.bukkit.Server;

/*                          ANCIENT ADDON API-Interface
*              This interface is required for addons for ancient.
*
*
*
*/

public interface AncientAddon 
{
    public final Ancient AncientPlugin = Ancient.getInstance();
    public final AncientAPI AncientAPI = new AncientAPI();
    public final Server CurrentServer = Bukkit.getServer();
    public void OnStartup();
    public void OnShutdown();
}
