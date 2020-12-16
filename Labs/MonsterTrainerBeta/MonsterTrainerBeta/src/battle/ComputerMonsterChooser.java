package battle;

import java.util.Comparator;
import java.util.Set;

import monster.Monster;

public class ComputerMonsterChooser implements MonsterChooser {
	
	private static final Comparator<Monster> AP_COMPARATOR = new Comparator<>() {
		@Override
		public int compare(Monster m1, Monster m2) {
			return Integer.compare(m2.getAttackPoints(), m1.getAttackPoints());
		}
	};

	/**
	 * Chooses a monster to attack in battle. This implementation chooses the
	 * non-knocked-out Monster with the maximum Attack Points.
	 * 
	 * @return The selected Monster, or null if no such Monster can be found
	 */
	public Monster chooseAttackMonster(Set<Monster> monsters) {
		return monsters.stream().filter(m -> m.getHitPoints() > 0).sorted(AP_COMPARATOR).findFirst().orElse(null);
	}

	/**
	 * Chooses a monster to defend in battle. This implementation chooses the
	 * non-knocked-out Monster with the maximum Hit Points.
	 * 
	 * @return The selected Monster, or null if no such Monster can be found
	 */
	public Monster chooseDefenseMonster(Set<Monster> monsters) {
		return monsters.stream().sorted().filter(m -> m.getHitPoints() > 0).findFirst().orElse(null);
	}

}
