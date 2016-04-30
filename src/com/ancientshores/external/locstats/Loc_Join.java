package com.ancientshores.external.locstats;

import com.ancientshores.external.LocStats;
import com.th3shadowbroker.loc.obj.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Loc_Join implements Listener{
    
    protected LocStats loader;
    
    public Loc_Join(LocStats loaderClass)
    {
        
        this.loader = loaderClass;
        
    }
    
    @EventHandler
    public void loc_player_join(PlayerJoinEvent e) //Triggered on PlayerJoin
    {
        
        Player p = e.getPlayer(); //Joined Player
        
        try{
            
            PlayerProfile profile = new PlayerProfile(loader.plugin, p); //Create his profile
            
            if ( profile.getStatInt("PlayerDeaths") == null )
            {
                /*
                 * Default settings here !
                 */
                
                profile.setStat("PlayerDeaths", 0); //Set default stats
                profile.setStat("PlayerKills", 0);
                
            }
            
        }catch(Exception ex){
            
            //Currently nothing
            
        }
        
    }
    
}
