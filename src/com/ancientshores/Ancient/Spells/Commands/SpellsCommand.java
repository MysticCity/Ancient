package com.ancientshores.Ancient.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Commands.ClassCastCommand;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import com.ancientshores.Ancient.Util.PageBuilder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SpellsCommand
{
  public static void spellListCommand(String[] args, Player p)
  {
    int page = 1;
    if (args.length == 1) {
      try
      {
        page = Integer.parseInt(args[0]);
      }
      catch (Exception ignored) {}
    }
    showSpellList(p, page);
  }
  
  public static void showSpellList(Player p, int page)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    PageBuilder pb = new PageBuilder();
    if (AncientClass.classList.containsKey(pd.getClassName().toLowerCase()))
    {
      pb.addMessage(Ancient.brand2 + "Class spells:");
      for (Map.Entry<String, Spell> s : ((AncientClass)AncientClass.classList.get(pd.getClassName().toLowerCase())).spellList.entrySet()) {
        if ((!((Spell)s.getValue()).buff) && 
        
          (!((Spell)s.getValue()).hidden))
        {
          String message;
          String message;
          if (ClassCastCommand.canCast(pd, p, (String)s.getKey()))
          {
            message = ChatColor.GREEN + ((Spell)s.getValue()).name;
          }
          else
          {
            String message;
            if ((AncientExperience.isWorldEnabled(p.getWorld())) && (pd.getXpSystem().level < ((Spell)s.getValue()).minlevel)) {
              message = ChatColor.RED + "[lvl " + ((Spell)s.getValue()).minlevel + "] " + ((Spell)s.getValue()).name;
            } else {
              message = ChatColor.RED + ((Spell)s.getValue()).name;
            }
          }
          if ((((Spell)s.getValue()).shortdescription != null) && (((Spell)s.getValue()).shortdescription.length() > 0)) {
            message = message + ChatColor.WHITE + " - " + ChatColor.GOLD + ((Spell)s.getValue()).shortdescription;
          }
          pb.addMessage(message);
        }
      }
      pb.addMessage(Ancient.brand2 + "Global spells:");
      for (Map.Entry<String, Spell> s : AncientClass.globalSpells.entrySet()) {
        if ((!((Spell)s.getValue()).buff) && 
        
          (!((Spell)s.getValue()).hidden))
        {
          String message;
          String message;
          if (ClassCastCommand.canCast(pd, p, (String)s.getKey()))
          {
            message = ChatColor.GREEN + ((Spell)s.getValue()).name;
          }
          else
          {
            String message;
            if ((AncientExperience.isWorldEnabled(p.getWorld())) && (pd.getXpSystem().level < ((Spell)s.getValue()).minlevel)) {
              message = ChatColor.RED + "[lvl " + ((Spell)s.getValue()).minlevel + "] " + ((Spell)s.getValue()).name;
            } else {
              message = ChatColor.RED + ((Spell)s.getValue()).name;
            }
          }
          if ((((Spell)s.getValue()).shortdescription != null) && (((Spell)s.getValue()).shortdescription.length() > 0)) {
            message = message + ChatColor.WHITE + " - " + ChatColor.GOLD + ((Spell)s.getValue()).shortdescription;
          }
          pb.addMessage(message);
        }
      }
      AncientClass stance = (AncientClass)((AncientClass)AncientClass.classList.get(pd.getClassName().toLowerCase())).stances.get(pd.getStance());
      if (stance != null)
      {
        pb.addMessage("-----------------------------------");
        pb.addMessage("Stance spells:");
        for (Map.Entry<String, Spell> s : stance.spellList.entrySet()) {
          if ((!((Spell)s.getValue()).buff) && 
          
            (!((Spell)s.getValue()).hidden))
          {
            String message;
            String message;
            if (ClassCastCommand.canCast(pd, p, (String)s.getKey()))
            {
              message = ChatColor.GREEN + ((Spell)s.getValue()).name;
            }
            else
            {
              String message;
              if ((AncientExperience.isWorldEnabled(p.getWorld())) && (pd.getXpSystem().level < ((Spell)s.getValue()).minlevel)) {
                message = ChatColor.RED + "[lvl " + ((Spell)s.getValue()).minlevel + "] " + ((Spell)s.getValue()).name;
              } else {
                message = ChatColor.RED + ((Spell)s.getValue()).name;
              }
            }
            if ((((Spell)s.getValue()).shortdescription != null) && (((Spell)s.getValue()).shortdescription.length() > 0)) {
              message = message + ChatColor.WHITE + " - " + ChatColor.GOLD + ((Spell)s.getValue()).shortdescription;
            }
            pb.addMessage(message);
          }
        }
      }
      AncientRace race = AncientRace.getRaceByName(pd.getRacename());
      if (race != null)
      {
        pb.addMessage("-----------------------------------");
        pb.addMessage("Race spells:");
        for (Spell sp : race.raceSpells) {
          if (!sp.hidden)
          {
            String message = sp.name;
            if (!sp.buff)
            {
              if ((sp.shortdescription != null) && (sp.shortdescription.length() > 0)) {
                message = message + ChatColor.WHITE + " - " + ChatColor.GOLD + sp.shortdescription;
              }
              pb.addMessage(message);
            }
          }
        }
      }
    }
    else
    {
      pb.addMessage(Ancient.brand2 + "You don't have a class");
      pb.addMessage("Global spells:");
      for (Map.Entry<String, Spell> s : AncientClass.globalSpells.entrySet()) {
        if ((!((Spell)s.getValue()).buff) && 
        
          (!((Spell)s.getValue()).hidden))
        {
          String message;
          String message;
          if (ClassCastCommand.canCast(pd, p, (String)s.getKey()))
          {
            message = ChatColor.GREEN + ((Spell)s.getValue()).name;
          }
          else
          {
            String message;
            if ((AncientExperience.isWorldEnabled(p.getWorld())) && (pd.getXpSystem().level < ((Spell)s.getValue()).minlevel)) {
              message = ChatColor.RED + "[lvl " + ((Spell)s.getValue()).minlevel + "] " + ((Spell)s.getValue()).name;
            } else {
              message = ChatColor.RED + ((Spell)s.getValue()).name;
            }
          }
          if ((((Spell)s.getValue()).shortdescription != null) && (((Spell)s.getValue()).shortdescription.length() > 0)) {
            message = message + ChatColor.WHITE + " - " + ChatColor.GOLD + ((Spell)s.getValue()).shortdescription;
          }
          pb.addMessage(message);
        }
      }
    }
    pb.printPage(p, page, 8);
  }
}
