package to.tinypota.ollivanders.common.storage;

import to.tinypota.ollivanders.common.spell.Spell;

import java.util.HashMap;

public class OllivandersPlayerState {
	private String suitedWand = "";
	private String suitedCore = "";
    private String currentSpell = "empty";
    private HashMap<Spell, Double> skillLevels = new HashMap<>();
	
	public HashMap<Spell, Double> getSkillLevels() {
		return skillLevels;
	}
	
	public void setSkillLevels(HashMap<Spell, Double> skillLevels) {
		this.skillLevels = skillLevels;
	}
	
	public void addSkillLevel(Spell spell, double amount) {
		double currentLevel = skillLevels.getOrDefault(spell, 0.0);
		skillLevels.put(spell, currentLevel + amount);
	}
	
	public void subtractSkillLevel(Spell spell, double amount) {
		double currentLevel = skillLevels.getOrDefault(spell, 0.0);
		double newLevel = currentLevel - amount;
		if (newLevel < 0) {
			newLevel = 0;
		}
		skillLevels.put(spell, newLevel);
	}
	
	public double getSkillLevel(Spell spell) {
		return skillLevels.getOrDefault(spell, 0.0);
	}
	
	public String getSuitedWand() {
		return suitedWand;
	}
	
	String getSuitedCore() {
		return suitedCore;
	}
	
	public void setSuitedWand(String suitedWand) {
		this.suitedWand = suitedWand;
	}
	
	void setSuitedCore(String suitedCore) {
		this.suitedCore = suitedCore;
	}
	
	String getCurrentSpell() {
		return currentSpell;
	}
	
	void setCurrentSpell(String currentSpell) {
		this.currentSpell = currentSpell;
	}
}