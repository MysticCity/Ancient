package com.ancientshores.external.locstats;

import com.ancientshores.external.LocStats;
import com.th3shadowbroker.loc.obj.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Loc_Deaths implements Listener
{
    
    protected LocStats loader;
    
    public Loc_Deaths(LocStats loaderClass)
    {
        
        this.loader = loaderClass;
        
    }
    
    @EventHandler
    public void loc_player_die(PlayerDeathEvent e)
    {
        
        Player dead = e.getEntity().getPlayer(); //Killed player
        Player killer = e.getEntity().getKiller(); //Killer
        
        try{
            
            if (dead instanceof Player)
            {
                
                PlayerProfile profile = new PlayerProfile(loader.plugin, dead); //Load LOCStats-profile
                
                int before = profile.getStatInt("PlayerDeaths"); //Deaths before this one
                
                profile.setStat("PlayerDeaths", before + 1);
 
                /////////////////////////////////////////////////////////////////////////////////////////
                
                if (killer instanceof Player) //Add a kill to killers stats
                {
                    
                    PlayerProfile profile2 = new PlayerProfile(loader.plugin, killer); //Load LOCStats-profile
                
                    int before2 = profile2.getStatInt("PlayerKills"); //Deaths before this one

                    profile2.setStat("PlayerKills", before2 + 1);
                    
                }
                
            }
            
        }catch (Exception ex){
            
            //Currently nothing
            
        }
        
    }
    
}
