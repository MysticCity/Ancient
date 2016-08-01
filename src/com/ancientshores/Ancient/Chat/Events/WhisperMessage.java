package com.ancientshores.Ancient.Chat.Events;

import com.ancientshores.Ancient.Chat.ChatEventLoader;
import com.ancientshores.Ancient.Interactive.CommandSuggestion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class WhisperMessage implements Listener {
    
    protected ChatEventLoader loader;
    
    public WhisperMessage( ChatEventLoader loaderClass )
    {
        
        this.loader = loaderClass;
        
    }
    
    @EventHandler
    public void whispered( AsyncPlayerChatEvent e )
    {
     
        String[] message = e.getMessage().split(" "); //Message in arguments
        String raw_message = String.valueOf( e.getMessage().replaceAll( message[0] , "" ) ); //Message without @<Playername>
        Player player = e.getPlayer(); //Message sender
        
        try{
            
            if ( message[0].startsWith("@") ) { //Check for requirements
                
                if ( Bukkit.getPlayer( message[0].replaceFirst("@", "") ) != null ) { //Try to get target
                    
                    if ( message.length > 1 ) {
                        
                        Player target = Bukkit.getPlayer( message[0].replaceFirst("@", "") ); //Player's whisper target
                    
                        CommandSuggestion target_cmdS = new CommandSuggestion( ChatColor.AQUA + "[@" + player.getName() + " ► You]" + raw_message , "@" + player.getName() + " " ); 
                        target_cmdS.sendToPlayer(target); //Send message to target

                        CommandSuggestion sender_cmdS = new CommandSuggestion( ChatColor.AQUA + "[You ► @" + target.getName() + "]" + raw_message , "@" + target.getName() + " " );
                        sender_cmdS.sendToPlayer(player); //Send message to sender
                    
                        e.setCancelled(true);
                        
                    } else {
                        
                        player.sendMessage(loader.plugin.ChatBrand + ChatColor.RED + "Please use " + ChatColor.AQUA + "@<Player> <message>");
                        e.setCancelled(true);
                        
                    }
                    
                } else {
                    
                    player.sendMessage(loader.plugin.ChatBrand + ChatColor.RED + "Player not found/online"); //If player is offline
                    e.setCancelled(true);
                }
                
            }
            
        } catch ( Exception ex ) {
            
            //Something wrong happend ;(
            player.sendMessage(loader.plugin.ChatBrand + ChatColor.RED + "Something went wrong :(");
            player.sendMessage(loader.plugin.ChatBrand + ChatColor.RED + "Please use " + ChatColor.AQUA + "@<Player> <message>");
            e.setCancelled(true);
        }

    }
    
}
