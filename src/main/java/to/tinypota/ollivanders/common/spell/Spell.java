package to.tinypota.ollivanders.common.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import to.tinypota.ollivanders.api.spell.SpellPowerLevel;
import to.tinypota.ollivanders.api.spell.SpellType;
import to.tinypota.ollivanders.registry.common.OllivandersSpells;

import java.util.Objects;

public class Spell {
	public static final Spell EMPTY = OllivandersSpells.register("empty", new Spell("empty", new Spell.Settings().type(SpellType.SELF)));
	private final String castName;
	private final SpellType type;
	private final boolean hitsBlocks;
	private final boolean hitsEntities;
	private final boolean hitsWater;
	
	public Spell(String castName, Settings settings) {
		this.castName = castName;
		type = settings.type;
		hitsBlocks = settings.hitsBlocks;
		hitsEntities = settings.hitsEntities;
		hitsWater = settings.hitsWater;
	}
	
	public boolean isEmpty() {
		return this == EMPTY;
	}
	
	public SpellPowerLevel getMaxPowerLevel() {
		return SpellPowerLevel.NORMAL;
	}
	
	public SpellPowerLevel getAvailablePowerLevel(double skillLevel) {
		return SpellPowerLevel.NORMAL;
	}
	
	public boolean canUsePowerLevel(SpellPowerLevel powerLevel) {
		return powerLevel.getNumerical() >= getAvailablePowerLevel(0).getNumerical();
	}
	
	public boolean canUsePowerLevel(SpellPowerLevel powerLevel, double skillLevel) {
		return powerLevel.getNumerical() >= getAvailablePowerLevel(skillLevel).getNumerical();
	}
	
	/*
	 * Called whenever a block is hit by a raycast or projectile entity. May or may not include water, depending on the spell.
	 * If it is shot with a projectile entity, you may return ActionResult.PASS to have the spell pass through the block.
	 */
	public ActionResult onHitBlock(SpellPowerLevel powerLevel, World world, BlockHitResult hitResult, Entity caster) {
		return onHitBlock(powerLevel, world, hitResult);
	}
	
	/*
	 * Called whenever a block is hit by a raycast or projectile entity. May or may not include water, depending on the spell.
	 * If it is shot with a projectile entity, you may return ActionResult.PASS to have the spell pass through the block.
	 */
	protected ActionResult onHitBlock(SpellPowerLevel powerLevel, World world, BlockHitResult hitResult) {
		return ActionResult.PASS;
	}
	
	/*
	 * Called whenever an entity is hit by a raycast or projectile entity.
	 * If it is shot with a projectile entity, you may return ActionResult.PASS to have the spell pass through the entity.
	 */
	public ActionResult onHitEntity(SpellPowerLevel powerLevel, World world, EntityHitResult hitResult, Entity caster) {
		return onHitEntity(powerLevel, world, hitResult);
	}
	
	/*
	 * Called whenever an entity is hit by a raycast or projectile entity.
	 * If it is shot with a projectile entity, you may return ActionResult.PASS to have the spell pass through the entity.
	 */
	protected ActionResult onHitEntity(SpellPowerLevel powerLevel, World world, EntityHitResult hitResult) {
		return ActionResult.PASS;
	}
	
	public ActionResult onSelfCast(SpellPowerLevel powerLevel, LivingEntity entity) {
		return ActionResult.PASS;
	}
	
	public String getCastName() {
		return castName;
	}
	
	public SpellType getType() {
		return type;
	}
	
	public boolean shouldHitBlocks() {
		return hitsBlocks;
	}
	
	public boolean shouldHitEntities() {
		return hitsEntities;
	}
	
	public boolean shouldHitWater() {
		return hitsWater;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var spell = (Spell) o;
		return hitsBlocks == spell.hitsBlocks && hitsEntities == spell.hitsEntities && hitsWater == spell.hitsWater && Objects.equals(castName, spell.castName) && type == spell.type;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(castName, type, hitsBlocks, hitsEntities, hitsWater);
	}
	
	public static class Settings {
		SpellType type;
		boolean hitsBlocks = true;
		boolean hitsEntities = true;
		boolean hitsWater = false;
		
		public Spell.Settings type(SpellType type) {
			this.type = type;
			return this;
		}
		
		public Spell.Settings hitsBlocks(boolean hits) {
			hitsBlocks = hits;
			return this;
		}
		
		public Spell.Settings hitsEntities(boolean hits) {
			hitsEntities = hits;
			return this;
		}
		
		public Spell.Settings hitsWater() {
			hitsWater = true;
			return this;
		}
		
		public Spell.Settings hitsWater(boolean hits) {
			hitsWater = hits;
			return this;
		}
		
		public Spell.Settings of(Settings settings) {
			hitsBlocks = settings.hitsBlocks;
			hitsEntities = settings.hitsEntities;
			hitsWater = settings.hitsWater;
			return this;
		}
	}
}
