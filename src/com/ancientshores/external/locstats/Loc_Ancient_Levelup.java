package com.ancientshores.external.locstats;

import com.ancientshores.Ancient.API.AncientLevelupEvent;
import com.ancientshores.external.LocStats;
import com.th3shadowbroker.loc.obj.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Loc_Ancient_Levelup implements Listener{
    
    protected LocStats loader;
    
    //Construct
    public Loc_Ancient_Levelup(LocStats loaderClass)
    {
        
        this.loader = loaderClass;
        
    }
    
    @EventHandler
    public void playerLevelup(AncientLevelupEvent e)//Player gets a LevelUp
    {
        
        Player p = Bukkit.getPlayer(e.uuid);
        
        try{
            
            PlayerProfile profile = new PlayerProfile(loader.plugin, p); //Player LOCStats profile
            
            int before = profile.getStatInt("PlayerLevel"); //Set stat
            int amount = 1; //Amount
            
            profile.setStat("PlayerLevel", before + amount); //Increase stat +1
            
        } catch (Exception ex) {
            
            //Currently nothing
            
        }
        
    }
    
}
