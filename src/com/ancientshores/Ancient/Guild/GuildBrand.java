package com.ancientshores.Ancient.Guild;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import org.bukkit.entity.Player;


public class GuildBrand {
    
    public static String getPlayersGuild( Player p )
    {
        
        if ( AncientGuild.getPlayersGuild(p.getUniqueId()) != null ) {
            
            String GuildBrand = Ancient.systemLang.getText("Guild.GuildBrands");
            GuildBrand = GuildBrand.replaceAll("%GUILD_NAME%", AncientGuild.getPlayersGuild(p.getUniqueId()).getGuildName() );
            
            return Prefix.get(GuildBrand);
            
        } else {
            
            String GuildBrand = Ancient.systemLang.getText("Guild.GuildBrands");
            GuildBrand = GuildBrand.replaceAll("%GUILD_NAME%", "&2Guilds" );
            
            return Prefix.get(GuildBrand);
            
        }
        
    }
    
    public static String getGuildBrand(AncientGuild guild)
    {
        
        String GuildBrand = Ancient.systemLang.getText("Guild.GuildBrands");
        GuildBrand = GuildBrand.replaceAll("%GUILD_NAME%", guild.getGuildName() );
        
        return GuildBrand;
        
    }
    
    public static String getDefaultGuildBrand()
    {
        
        String GuildBrand = Ancient.systemLang.getText("Guild.GuildBrands");
        GuildBrand = GuildBrand.replaceAll("%GUILD_NAME%", "&2Guilds" );
            
            return Prefix.get(GuildBrand);
        
    }
    
}
