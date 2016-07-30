package com.ancientshores.external.locstats;

import com.ancientshores.Ancient.API.AncientPartyJoinEvent;
import com.ancientshores.external.LocStats;
import com.th3shadowbroker.loc.obj.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Loc_Ancient_Party_Join implements Listener{
    
    protected LocStats loader;
    
    //Construct
    public Loc_Ancient_Party_Join(LocStats loaderClass)
    {
        
        this.loader = loaderClass;
        
    }
    
    @EventHandler
    public void PlayerJoinParty(AncientPartyJoinEvent e) //Player join into a party
    {
        
        Player p = Bukkit.getPlayer(e.getUUID());
        
        try{
            
            PlayerProfile profile = new PlayerProfile(loader.plugin, p);//Players LOCStats-profile
            
            int before = profile.getStatInt("PartiesJoined"); //Before this one
            int amount = 1; //Amount
            
            profile.setStat("PartiesJoined", before + amount); //Increase +1 PartiesJoined
            
        }catch (Exception ex) {
            
            //Currently nothing
            
        }
        
    }
    
}
