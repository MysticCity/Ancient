package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;


public class PartyCommandInvite {
    @SuppressWarnings("deprecation")
	public static void processInvite(CommandSender sender, String[] args, Ancient main) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            if (mPlayer.hasPermission(AncientParty.pNodeCreate)) {
                if (AncientParty.getPlayersParty(mPlayer.getUniqueId()) == null) {
                    Player invitedPlayer = main.getServer().getPlayer(args[1]);
                    if (invitedPlayer != null && invitedPlayer != mPlayer) {
                        if (invitedPlayer.hasPermission(AncientParty.pNodeJoin)) {
                            if (AncientParty.getPlayersParty(invitedPlayer.getUniqueId()) == null) {
                                if (!AncientParty.invites.containsKey(invitedPlayer.getUniqueId())) {
                                    if (!AncientParty.invites.containsKey(mPlayer.getUniqueId())) {
                                        if (!AncientParty.mIgnoreList.contains(invitedPlayer.getUniqueId())) {
                                            final AncientParty mParty = new AncientParty(mPlayer.getUniqueId());
                                            AncientParty.partys.add(mParty);

                                              
                                              try{
                                                  
                                                //Send notification message
                                                invitedPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You were invited to a party by " + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + ".");

                                                //Check the servers operating system (Bungee or Spigot are required for used ComponentBuilder)
                                                if ( Bukkit.getVersion().toLowerCase().contains("spigot") | Bukkit.getVersion().toLowerCase().contains("bungee") ) {
                                                    
                                                    /*
                                                     * Accept party invite
                                                     */
                                                    TextComponent interactiveAccept = new TextComponent("► Accept invite"); //What will be transmitted
                                                    ClickEvent onClickAccept = new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/party accept"); //Set action
                                                    interactiveAccept.setColor(net.md_5.bungee.api.ChatColor.GREEN); //Set message-color
                                                    interactiveAccept.setClickEvent(onClickAccept); //Set ClickEvent
                                                    
                                                    invitedPlayer.spigot().sendMessage( interactiveAccept );//Send the whole stuff
                                                    
                                                    /*
                                                     * Reject party invite
                                                     */
                                                    TextComponent interactiveReject = new TextComponent("► Reject invite"); //What will be transmitted
                                                    ClickEvent onClickReject = new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/party reject"); //Set action
                                                    interactiveReject.setColor(net.md_5.bungee.api.ChatColor.RED); //Set message-color
                                                    interactiveReject.setClickEvent(onClickReject); //Set ClickEvent
                                                    
                                                    invitedPlayer.spigot().sendMessage( interactiveReject );//Send the whole stuff
                                                    
                                                    /*
                                                     * Ignore party invites
                                                     */
                                                    TextComponent interactiveIgnore = new TextComponent("► Ignore invite"); //What will be transmitted
                                                    ClickEvent onClickIgnore = new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/party ignore"); //Set action
                                                    interactiveIgnore.setColor(net.md_5.bungee.api.ChatColor.GOLD); //Set message-color
                                                    interactiveIgnore.setClickEvent(onClickIgnore); //Set ClickEvent
                                                    
                                                    invitedPlayer.spigot().sendMessage( interactiveIgnore );//Send the whole stuff
                                                    
                                                }else{
                                                    
                                                    invitedPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You were invited to a party by " + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + ".");
                                                    invitedPlayer.sendMessage(ChatColor.BLUE + "Use /party accept or /party reject to join the party or reject the invitation.");
                                                    invitedPlayer.sendMessage(ChatColor.BLUE + "If you want to ignore the invites of all players use /party ignore.");
                                                    
                                                }
                                               
                                                //Send success message   
                                                mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "Succesfully invited " + ChatColor.GOLD + invitedPlayer.getName() + ChatColor.BLUE + " to your new party.");
                                                
                                              }catch (Exception ex) {
                                                  
                                                  ex.printStackTrace();
                                                  
                                              }

                                            if (AncientParty.invites.containsKey(invitedPlayer.getUniqueId())) {
                                                AncientParty.invites.remove(invitedPlayer.getUniqueId());
                                            }
                                            AncientParty.invites.put(invitedPlayer.getUniqueId(), mParty);
                                        } else {
                                            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "This player ignores all invitations.");
                                        }
                                    } else {
                                        mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You are already invited to a party, please reject (/party reject) it first.");
                                    }
                                } else {
                                    mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "This player already is invited. He has to reject (/party reject) it first.");
                                }
                            } else {
                                sender.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "This player is already in a party.");
                            }
                        } else {
                            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "This player doesn't have the permissions to join a party.");
                        }
                    } else {
                        mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "This player doesn't exist");
                    }
                } else {
                    Player invitedPlayer = main.getServer().getPlayer(args[1]);
                    AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
                    if (mParty.getMemberNumber() < AncientParty.maxPlayers) {
                        if (invitedPlayer != null && invitedPlayer != mPlayer) {
                            if (Ancient.permissionHandler == null || Ancient.permissionHandler.has(invitedPlayer, AncientParty.pNodeJoin)) {
                                if (AncientParty.getPlayersParty(invitedPlayer.getUniqueId()) == null) {
                                    if (!AncientParty.invites.containsKey(invitedPlayer.getUniqueId())) {
                                        if (!AncientParty.invites.containsKey(mPlayer.getUniqueId())) {
                                            if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                                                if (!AncientParty.mIgnoreList.contains(invitedPlayer.getUniqueId())) {
                                                                                                      
                                                            //Send notification message
                                                            invitedPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You were invited to a party by " + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + ".");
                                                    
                                                            //Check the servers operating system (Bungee or Spigot are required for used ComponentBuilder)
                                                        if ( Bukkit.getVersion().toLowerCase().contains("spigot") | Bukkit.getVersion().toLowerCase().contains("bungee") ) {

                                                            /*
                                                             * Accept party invite
                                                             */
                                                            TextComponent interactiveAccept = new TextComponent("► Accept invite"); //What will be transmitted
                                                            ClickEvent onClickAccept = new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/party accept"); //Set action
                                                            interactiveAccept.setColor(net.md_5.bungee.api.ChatColor.GREEN); //Set message-color
                                                            interactiveAccept.setClickEvent(onClickAccept); //Set ClickEvent

                                                            invitedPlayer.spigot().sendMessage( interactiveAccept );//Send the whole stuff

                                                            /*
                                                             * Reject party invite
                                                             */
                                                            TextComponent interactiveReject = new TextComponent("► Reject invite"); //What will be transmitted
                                                            ClickEvent onClickReject = new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/party reject"); //Set action
                                                            interactiveReject.setColor(net.md_5.bungee.api.ChatColor.RED); //Set message-color
                                                            interactiveReject.setClickEvent(onClickReject); //Set ClickEvent

                                                            invitedPlayer.spigot().sendMessage( interactiveReject );//Send the whole stuff

                                                            /*
                                                             * Ignore party invites
                                                             * @TODO Remove after quickmenu is created
                                                             */
                                                            TextComponent interactiveIgnore = new TextComponent("► Ignore invite"); //What will be transmitted
                                                            ClickEvent onClickIgnore = new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/party ignore"); //Set action
                                                            interactiveIgnore.setColor(net.md_5.bungee.api.ChatColor.GOLD); //Set message-color
                                                            interactiveIgnore.setClickEvent(onClickIgnore); //Set ClickEvent

                                                            invitedPlayer.spigot().sendMessage( interactiveIgnore );//Send the whole stuff

                                                        }else{

                                                            invitedPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You were invited to a party by " + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + ".");
                                                            invitedPlayer.sendMessage(ChatColor.BLUE + "Use /party accept or /party reject to join the party or reject the invitation.");
                                                            invitedPlayer.sendMessage(ChatColor.BLUE + "If you want to ignore the invites of all players use /party ignore.");

                                                        }

                                                        //Send success message   
                                                        mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "Succesfully invited " + ChatColor.GOLD + invitedPlayer.getName() + ChatColor.BLUE + " to your new party.");
                                                    
                                                    if (AncientParty.invites.containsKey(invitedPlayer.getUniqueId())) {
                                                        AncientParty.invites.remove(invitedPlayer.getUniqueId());
                                                    }
                                                    AncientParty.invites.put(invitedPlayer.getUniqueId(), mParty);
                                                } else {
                                                    mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "This player is ignoring all party invitations.");
                                                }
                                            } else {
                                                mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You are not the leader of the party.");
                                            }
                                        } else {
                                            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You are already invited to a party, please reject (/party reject) it first.");
                                        }
                                    } else {
                                        mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "This player already is invited. He has to reject (/party reject) it first.");
                                    }
                                } else {
                                    sender.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "This player is already in a party.");
                                }
                            } else {
                                mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "This player hasn't the required permissions to join a party.");
                            }
                        } else {
                            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "This player doesn't exist");
                        }
                    } else {
                        mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "Your party is already full.");
                    }
                }
            } else {
                mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "You don't have the permissions to create a party.");
            }
        } else {
            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "Correct usage: /party invite <name>");
        }
    }
}