package com.ancientshores.AncientRPG.Classes.Spells;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;

public class ElseIfSection extends ICodeSection
{
	final ICodeSection parentSection;
	Condition c;

	public ElseIfSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent) throws IOException
	{
		super("endelseif", "elseif", p);
		parentSection = parent;
		parseRaw(curline, bf, linenumber);
		try
		{
			String condString = firstline.substring(firstline.indexOf(',') + 1);
			c = new Condition(condString, mSpell);
		} catch (Exception e)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Error in if command which starts with " + this.firstline + " in spell " + p.name);
		}
	}

	public void executeElseIf(final Player mPlayer, final SpellInformationObject so)
	{
		if ((so.canceled || sections.size() == 0) && parentSection != null)
		{
			parentSection.executeCommand(mPlayer, so);
			return;
		}
		if (!playersindexes.containsKey(so))
		{
			playersindexes.put(so, 0);
		}
		if (playersindexes.get(so) >= sections.size())
		{
			playersindexes.remove(so);
			if (parentSection != null)
				parentSection.executeCommand(mPlayer, so);
			else if (parentSection == null)
			{
				so.finished = true;
				AncientRPGClass.executedSpells.remove(so);
			}
			return;
		}
		if (playersindexes.get(so) == 0 && !c.conditionFulfilled(mPlayer, so))
		{
			playersindexes.remove(so);
			if (!so.canceled)
			{
				if (parentSection.playersindexes.get(so) < parentSection.sections.size())
				{
					if (parentSection.sections.get(parentSection.playersindexes.get(so)) instanceof ElseSection)
					{
						ElseSection es = (ElseSection) parentSection.sections.get(parentSection.playersindexes.get(so));
						parentSection.playersindexes.put(so, parentSection.playersindexes.get(so) + 1);
						es.executeElse(mPlayer, so);
						return;
					}
					if (parentSection.sections.get(parentSection.playersindexes.get(so)) instanceof ElseIfSection)
					{
						ElseIfSection es = (ElseIfSection) parentSection.sections.get(parentSection.playersindexes.get(so));
						parentSection.playersindexes.put(so, parentSection.playersindexes.get(so) + 1);
						es.executeElseIf(mPlayer, so);
						return;
					}
				}
				parentSection.executeCommand(mPlayer, so);
			}
			return;
		}
		if (sections.size() == 0)
			return;
		final ICodeSection cs = sections.get(playersindexes.get(so));
		playersindexes.put(so, playersindexes.get(so) + 1);
		try
		{
			int t = so.waittime;
			so.waittime = 0;
			AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
			{
				public void run()
				{
					cs.executeCommand(mPlayer, so);
					if (so.canceled)
					{
						so.canceled = true;
					}
				}
			}, Math.round(t / 50));
		} catch (Exception e)
		{
			playersindexes.remove(so);
			so.canceled = true;
			parentSection.executeCommand(mPlayer, so);
		}
	}

	public void executeCommand(final Player mPlayer, final SpellInformationObject so)
	{
		if (playersindexes.containsKey(so))
		{
			executeElseIf(mPlayer, so);
		} else
		{
			parentSection.executeCommand(mPlayer, so);
		}
	}
}
