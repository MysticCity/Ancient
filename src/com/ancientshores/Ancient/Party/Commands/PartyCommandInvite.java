package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Interactive.InteractiveMessage;
import com.ancientshores.Ancient.Party.AncientParty;


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

                                                
                                                    
                                                    /*
                                                     * Accept party invite
                                                     */
                                                    InteractiveMessage accept = new InteractiveMessage("&2► Accept invite","party accept");
                                                    accept.sendToPlayer(invitedPlayer);
                                                    
                                                    /*
                                                     * Reject party invite
                                                     */
                                                    InteractiveMessage reject = new InteractiveMessage("&c► Reject invite","party reject");
                                                    reject.sendToPlayer(invitedPlayer);
                                                    
                                                    /*
                                                     * Ignore party invites
                                                     */
                                                   InteractiveMessage ignore = new InteractiveMessage("&e► Ignore invites","party ignore");
                                                   ignore.sendToPlayer(invitedPlayer);

                                                    
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
                                                    
                                                               /*
                                                                * Accept party invite
                                                                */
                                                               InteractiveMessage accept = new InteractiveMessage("&2► Accept invite","party accept");
                                                               accept.sendToPlayer(invitedPlayer);

                                                               /*
                                                                * Reject party invite
                                                                */
                                                               InteractiveMessage reject = new InteractiveMessage("&c► Reject invite","party reject");
                                                               reject.sendToPlayer(invitedPlayer);

                                                               /*
                                                                * Ignore party invites
                                                                */
                                                              InteractiveMessage ignore = new InteractiveMessage("&e► Ignore invites","party ignore");
                                                              ignore.sendToPlayer(invitedPlayer);


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