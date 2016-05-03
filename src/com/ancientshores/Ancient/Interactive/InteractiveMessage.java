package com.ancientshores.Ancient.Interactive;

import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class InteractiveMessage {
    
    protected String message = "";
    protected String action = "";
    protected String m = "\"";
    
    //Construction
    public InteractiveMessage(String message, String command)
    {
        
        this.message = "{\"text\":\"%MSG% \",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/%CMD%\"}}";
        this.message = this.message.replaceAll("%MSG%", message.replaceAll("&", "§"));
        this.message = this.message.replaceAll("%CMD%", command);
        
    }
    
    //Send as a package
    public void sendToPlayer(Player p)
    {
        
        try {
            
                IChatBaseComponent comp = ChatSerializer.a(message);
                
                PacketPlayOutChat packet = new PacketPlayOutChat(comp);
                
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
        
    }
}
