package battle;

import java.util.Set;

import monster.Monster;

public interface MonsterChooser {
	
	Monster chooseAttackMonster(Set<Monster> monsters);
	
	Monster chooseDefenseMonster(Set<Monster> monsters);
}
