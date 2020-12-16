/* Monster.java **/
package monster;

import java.util.Objects;

/**
 * Represents a Monster for a battling game.
 */
public class Monster implements Comparable<Monster> {

	// Fields
	protected String name;
	protected Types types;
	protected int hitPoints;
	protected int attackPoints;
	protected int defence;

	/** Creates a new Monster with the given properties */
	public Monster(String name, Types types, int hitPoints, int attackPoints, int defence) {
		this.types = types;
		this.name = name;
		this.hitPoints = hitPoints;
		this.attackPoints = attackPoints;
		this.defence = defence;
	}

	// Getters and setters
	public int getHitPoints() {
		return hitPoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public Types getType() {
		return this.types;
	}
	
	public String getName() {
		return this.name;
	}

	public int getDefence() { return defence; }

	/**
	 * Attacks another Monster
	 * @param otherMonster The other Monster to attack
	 * @throws MonsterException if either Monster is knocked out, or if otherMonster == this
	 */
	public void attack(Monster otherMonster) throws MonsterException {
		// A monster cannot attack itself
		if (otherMonster == this) {
			throw new MonsterException("A monster cannot attack itself");
		}

		// A monster cannot attack or be attacked if it is knocked out
		if (this.hitPoints <= 0 || otherMonster.getHitPoints() <= 0) {
			throw new MonsterException("Knocked out monsters cannot attack or be attacked");
		}

		if (otherMonster.dodge()) {
			this.removeHitPoints(10);
		} else {
			// Check if the other monster is weak against our types
			boolean otherIsWeak = otherMonster.isWeakAgainst(types);
			int pointsToRemove = (otherIsWeak) ? this.attackPoints + 20 : this.attackPoints;
			otherMonster.removeHitPoints(pointsToRemove);
		}
	}
	
	private int dodgeCount = 0;
	/**
	 * Allows a Monster to dodge in battle.
	 * 
	 * @return True if the Monster will dodge when next attacked, and false if not
	 */
	public boolean dodge() {
		dodgeCount++;
		switch (types) {
		case FIRE:
			return (dodgeCount % 2) == 1;
			
		case WATER:
			return (hitPoints >= 100);

		default:
			return false;
		}
	}
	
	/**
	 * Checks if this Monster is weak against another types.
	 * 
	 * @param otherTypes The types to look for
	 * @return True if otherType is a weakness of this Monster, and false if not
	 */
	public boolean isWeakAgainst(Types otherTypes) {
		return this.types.isWeakAgainst(otherTypes);
	}

	/**
	 * Removes the indicated number of hit points from this Monster. Hit points cannot go below zero.
	 * 
	 * @param points The points to remove
	 */
	private void removeHitPoints(int points) {
		this.hitPoints -= points;
		if (hitPoints <= 0) {
			// Monster is knocked out
			hitPoints = 0;
		}
	}

	@Override
	public String toString() {
		return name + "(type:" + types + ", hp:" + hitPoints + ", ap:" + attackPoints + ", d:" + defence + ")";
	}

	@Override
	public int compareTo (Monster otherMonster) {
		int result = Integer.compare(otherMonster.hitPoints, this.hitPoints);
		if (result == 0) {
			result = Integer.compare(otherMonster.attackPoints, this.attackPoints);
		}
		if (result == 0) {
			result = Integer.compare(otherMonster.defence, this.defence);
		}
		if (result == 0) {
			result = this.types.compareTo(otherMonster.types);
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Monster monster = (Monster) o;
		return hitPoints == monster.hitPoints && attackPoints == monster.attackPoints && defence == monster.defence && dodgeCount == monster.dodgeCount && Objects.equals(name, monster.name) && types == monster.types;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, types, hitPoints, attackPoints, defence, dodgeCount);
	}
}