package battle;

import java.util.Scanner;

public class BattleMain {
	public static void main(String[] args) {

		Trainer t1 = Trainer.loadFromFile("C:\\Users\\sgoud\\University Work\\HTLANL\\Labs\\MonsterTrainerBeta\\MonsterTrainerBeta\\trainer1.txt");
		Trainer t2 = Trainer.loadFromFile("C:\\Users\\sgoud\\University Work\\HTLANL\\Labs\\MonsterTrainerBeta\\MonsterTrainerBeta\\trainer2.txt");
		t1.setMonsterChooser(new HumanMonsterChooser(new Scanner(System.in)));
		t2.setMonsterChooser(new ComputerMonsterChooser());

		Battle b = new Battle (t1, t2);
		Trainer winner = b.doBattle();
		System.out.println("Winner is: " + winner.getName());
	}
}
