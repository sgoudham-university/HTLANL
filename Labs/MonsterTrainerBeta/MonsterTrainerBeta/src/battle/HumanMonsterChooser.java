package battle;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import monster.Monster;

public class HumanMonsterChooser implements MonsterChooser {
	
	private Scanner scanner;
	
	public HumanMonsterChooser(Scanner scanner) {
		this.scanner = scanner;
	}
	
	private Monster chooseMonster(Set<Monster> monsters) {
		int chosenMonster;
		List<Monster> monsterList = monsters.stream().filter(m -> m.getHitPoints() > 0).collect(Collectors.toList());
		while (true) {
			for (int i = 0; i < monsterList.size(); i++) {
				System.out.println(i + ":" + monsterList.get(i));
			}
			System.out.println("Please choose a monster from the above list.");
			try {
				chosenMonster = scanner.nextInt();
				if (chosenMonster < 0 || chosenMonster >= monsterList.size()) {
					System.out.println("Invalid input!");
				} else {
					break;
				}
			} catch (InputMismatchException ex) {
				System.out.println("Invalid input!");
				scanner.next();
			}
		}
		return monsterList.get(chosenMonster);
	}

	@Override
	public Monster chooseAttackMonster(Set<Monster> monsters) {
		System.out.println("Choosing an ATTACKING monster");
		return chooseMonster(monsters);
	}

	@Override
	public Monster chooseDefenseMonster(Set<Monster> monsters) {
		System.out.println("Choosing a DEFENDING monster");
		return chooseMonster(monsters);
	}

}
