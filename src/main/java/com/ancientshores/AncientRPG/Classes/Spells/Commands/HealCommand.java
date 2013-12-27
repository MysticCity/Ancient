package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class HealCommand extends ICommand
{
	@CommandDescription(description = "<html>Heals the target the specified amount of health</html>",
			argnames = {"entity", "amount"}, name = "Heal", parameters = {ParameterType.Entity, ParameterType.Number})

	public HealCommand()
	{
		ParameterType[] buffer = { ParameterType.Entity, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number)
			{
				final Entity[] target = (Entity[]) ca.params.get(0);
				final double heal = ((Number) ca.params.get(1)).doubleValue();
				if (target != null && target.length > 0)
				{
					for (final Entity targetPlayer : target)
					{
						if (targetPlayer == null || !(targetPlayer instanceof LivingEntity))
							continue;
						if (targetPlayer instanceof Player && DamageConverter.isEnabled() && DamageConverter.isWorldEnabled(ca.caster))
						{
							Player p = (Player) targetPlayer;
							EntityRegainHealthEvent e = new EntityRegainHealthEvent(targetPlayer, heal, RegainReason.CUSTOM);
							Bukkit.getPluginManager().callEvent(e);
							if(!e.isCancelled())
							{
								if(p.getHealth() + e.getAmount() > p.getMaxHealth())
									p.setHealth(p.getMaxHealth());
								else if(p.getHealth() + e.getAmount() > p.getMaxHealth())
									p.setHealth(0);
								else
									p.setHealth(p.getHealth()+e.getAmount());
							}
						} else if (targetPlayer instanceof Creature && CreatureHp.isEnabled(targetPlayer.getWorld()))
						{
							EntityRegainHealthEvent e = new EntityRegainHealthEvent(targetPlayer, heal, RegainReason.CUSTOM);
							Bukkit.getPluginManager().callEvent(e);
							if(!e.isCancelled())
							{
								double newhealth = ((Creature) targetPlayer).getHealth()+heal;
								if(newhealth > ((Creature) targetPlayer).getMaxHealth()){
									newhealth = ((Creature) targetPlayer).getMaxHealth();
								}
								((Creature) targetPlayer).setHealth(newhealth);
							}
						} else
						{
							EntityRegainHealthEvent e = new EntityRegainHealthEvent(targetPlayer, heal, RegainReason.CUSTOM);
							Bukkit.getPluginManager().callEvent(e);
							if(!e.isCancelled())
							{
								double health = ((LivingEntity) targetPlayer).getHealth() + e.getAmount();
								if (health < 0)
								{
									health = 0;
									((LivingEntity) targetPlayer).setHealth(0);
									targetPlayer.playEffect(EntityEffect.DEATH);
								}
								if (health > ((LivingEntity) targetPlayer).getMaxHealth())
									health = ((LivingEntity) targetPlayer).getMaxHealth();
								((LivingEntity) targetPlayer).setHealth(health);
							}
						}
					}
					return true;
				}
			}
		} catch (IndexOutOfBoundsException ignored)
		{

		}
		return false;
	}
}
