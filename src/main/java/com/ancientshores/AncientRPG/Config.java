package com.ancientshores.AncientRPG;

import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Mana.ManaSystem;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Config
{
	final AncientRPG instance;

	public Config(AncientRPG instance)
	{
		this.instance = instance;
		this.directory = AncientRPG.plugin.getDataFolder().getPath();
		this.file = new File(directory + File.separator + "config.yml");
	}

	public final String directory;
	final File file;

	public void configCheck()
	{
	}

	public void addDefaults()
	{
		try
		{
			if (file.exists())
			{
				instance.getConfig().load(file);
			}
		} catch (FileNotFoundException e)
		{
		} catch (IOException e)
		{
		} catch (InvalidConfigurationException e)
		{
		}
		instance.getConfig().set("AncientRPG.language", AncientRPG.languagecode);
		// Party Config
		if (AncientRPG.classExists("com.ancientshores.AncientRPG.Party.AncientRPGParty"))
		{
			AncientRPGParty.writeConfig(instance);
		}
		if (AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.AncientRPGGuild"))
		{
			AncientRPGGuild.writeConfig(instance);
		}
		if (AncientRPG.classExists("com.ancientshores.AncientRPG.HP.DamageConverter") && AncientRPG.classExists(
				"com.ancientshores.AncientRPG.HP.AncientRPGHP"))
		{
			DamageConverter.writeConfig(instance);
		}
		if (AncientRPG.classExists("com.ancientshores.AncientRPG.Experience.AncientRPGExperience"))
		{
			AncientRPGExperience.writeConfig(instance);
		}
		CreatureHp.writeConfig(instance);
		AncientRPGClass.writeConfig(instance);
		AncientRPGRace.writeRacesConfig(instance);
		ManaSystem.writeConfig(instance);
		Spell.writeConfig(instance.getConfig());
		instance.saveConfig();
	}

	public void loadkeys()
	{
		try
		{
			if (file.exists())
			{
				instance.getConfig().load(file);
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AncientRPG.languagecode = instance.getConfig().getString("AncientRPG.language", AncientRPG.languagecode);
		if (AncientRPG.classExists("com.ancientshores.AncientRPG.Party.AncientRPGParty"))
		{
			AncientRPGParty.loadConfig(instance);
		}
		if (AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.AncientRPGGuild"))
		{
			AncientRPGGuild.loadConfig(instance);
		}

		if (AncientRPG.classExists("com.ancientshores.AncientRPG.HP.DamageConverter") && AncientRPG.classExists(
				"com.ancientshores.AncientRPG.HP.AncientRPGHP"))
		{
			DamageConverter.loadConfig(instance);
		}
		if (AncientRPG.classExists("com.ancientshores.AncientRPG.Experience.AncientRPGExperience"))
		{
			AncientRPGExperience.loadConfig(instance);
		}
		CreatureHp.loadConfig(instance);
		AncientRPGClass.loadConfig(instance);
		AncientRPGRace.loadRacesConfig(instance);
		ManaSystem.loadConfig(instance);
		Spell.loadConfig(instance.getConfig());
		instance.loadConfig(instance.getConfig());
		if(file.exists())
		{
			file.renameTo(new File(file.getPath()+".old"));
		}

		/*
		 * // Guild Config if (Main.classExisting("de.pylamo.rpgplugin.Guild"))
		 * { Guild.maxPlayers = (int) Math.round(this
		 * .readDouble(Guild.gConfigSize)); Guild.enabled =
		 * this.readBoolean(Guild.gConfigEnabled); Guild.Iconomyenabled = this
		 * .readBoolean(Guild.gConfigIconomyEnabled); Guild.cost =
		 * this.readDouble(Guild.gConfigCost); } if
		 * (AncientRPG.classExisting("de.pylamo.rpgplugin.Guild")) { if
		 * (Main.classExisting("de.pylamo.rpgplugin.GuildHouse")) { //
		 * Guildhouse config GuildHouse.GuildhouseEnabled = this
		 * .readBoolean(GuildHouse.ghConfigEnabled); GuildHouse.costperblock =
		 * this .readDouble(GuildHouse.ghCostperBlock); } if
		 * (AncientRPG.classExisting("de.pylamo.rpgplugin.GuildSafe")) { //
		 * GuildSafe config GuildSafe.guildsafeEnabled = this
		 * .readBoolean(GuildSafe.ghConfigEnabled); GuildSafe.maxSize = (int)
		 * Math.round(this .readDouble(GuildSafe.ghConfigMax)); GuildSafe.Cost =
		 * this.readDouble(GuildSafe.ghConfigCosts); } }
		 * if(AncientRPG.classExisting("de.pylamo.rpgplugin.Achievement")){
		 * Achievement.enabled =
		 * this.readBoolean(Achievement.aConfigAchievementEnabled); }
		 */
	}
}