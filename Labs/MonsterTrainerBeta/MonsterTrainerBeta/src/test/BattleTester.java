package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import battle.ComputerMonsterChooser;
import battle.HumanMonsterChooser;
import battle.MonsterChooser;
import battle.Trainer;
import monster.Monster;
import monster.Types;

class BattleTester {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private Monster pikachu, rowlet, litten, turtonator, alolanMarowak, gyarados, megaGyarados;
	private Trainer ash, kiawe;
	private MonsterChooser cmc;

	@BeforeEach
	void setUp() {
		ash = new Trainer("Ash");
		pikachu = new Monster("Pikachu", Types.ELECTRIC, 35, 55, 100);
		rowlet = new Monster("Rowlet", Types.GRASS, 68, 55, 24);
		litten = new Monster("Litten", Types.FIRE, 45, 65, 78);
		ash.addMonster(pikachu);
		ash.addMonster(rowlet);
		ash.addMonster(litten);

		turtonator = new Monster("Turtonator", Types.FIRE, 60, 78, 67);
		alolanMarowak = new Monster("Alolan Marowak", Types.FIRE, 60, 80, 20);
		kiawe = new Trainer("Kiawe");
		kiawe.addMonster(turtonator);
		kiawe.addMonster(alolanMarowak);

		gyarados = new Monster("Gyarados", Types.WATER, 95, 155, 72);
		megaGyarados = new Monster("Mega Gyarados", Types.WATER, 115, 175, 45);
		cmc = new ComputerMonsterChooser();
	}

	@AfterEach
	void tearDown() {
		ash = null;
		kiawe = null;
		pikachu = null;
		rowlet = null;
		litten = null;
		turtonator = null;
		alolanMarowak = null;
		gyarados = null;
		megaGyarados = null;
		cmc = null;
	}

	@Test
	public void fireMonsterDodgeWorksProperly() {
		Assertions.assertTrue(
				litten.dodge(), "First call to Fire Monster.dodge() should return true");
		Assertions.assertFalse(
				litten.dodge(), "Second call to Fire Monster.dodge() should return false");
		Assertions.assertTrue(
				litten.dodge(), "Third call to Fire Monster.dodge() should return true");
		Assertions.assertTrue(turtonator.dodge(), "One fire monster dodging should not affect another");
	}
	
	@Test
	public void waterMonsterDodgeWorksProperly() {
		Assertions.assertTrue(
				megaGyarados.dodge(), "Water Monster.dodge() should return true if HP is over 100");
		Assertions.assertFalse(
				gyarados.dodge(), "Water Monster.dodge() should return false if HP is less than 100");
	}
	
	@Test
	public void electricMonsterDodgeWorksProperly() {
		Assertions.assertFalse(
				pikachu.dodge(), "Electric Monster.dodge() should always return false");
		Assertions.assertFalse(
				pikachu.dodge(), "Electric Monster.dodge() should always return false");
	}
	
	@Test
	public void monsterEqualsWorksProperly() {
		Monster pikachu2 = new Monster("Pikachu", Types.ELECTRIC, pikachu.getHitPoints(), pikachu.getAttackPoints(), pikachu.getDefence());
		Assertions.assertEquals(pikachu, pikachu2, "Two identical monsters should be considered equal");
		Monster similar = new Monster("Pikachu2", Types.ELECTRIC, pikachu.getHitPoints(), pikachu.getAttackPoints(), pikachu.getDefence());
		Assertions.assertNotEquals(pikachu, similar, "Two similar monsters should not be considered equal");
		similar = new Monster("Pikachu", Types.FIRE, pikachu.getHitPoints(), pikachu.getAttackPoints(), pikachu.getDefence());
		Assertions.assertNotEquals(pikachu, similar, "Two similar monsters should not be considered equal");
		similar = new Monster("Pikachu", Types.ELECTRIC, pikachu.getHitPoints() + 10, pikachu.getAttackPoints(), pikachu.getDefence());
		Assertions.assertNotEquals(pikachu, similar, "Two similar monsters should not be considered equal");
		similar = new Monster("Pikachu", Types.ELECTRIC, pikachu.getHitPoints(), pikachu.getAttackPoints() + 10, pikachu.getDefence());
		Assertions.assertNotEquals(pikachu, similar, "Two similar monsters should not be considered equal");
	}
	
	@Test
	public void grassMonsterDodgeWorksProperly() {
		Assertions.assertFalse(
				rowlet.dodge(), "ElectricMonster.dodge() should always return false");
		Assertions.assertFalse(
				rowlet.dodge(), "ElectricMonster.dodge() should always return false");
	}
		
	@Test
	public void trainerEqualsWorksProperly() {
		Trainer ash2 = new Trainer("Ash");
		ash2.addMonster(new Monster("Pikachu", Types.ELECTRIC, pikachu.getHitPoints(), pikachu.getAttackPoints(), pikachu.getDefence()));
		ash2.addMonster(new Monster("Rowlet", Types.GRASS, rowlet.getHitPoints(), rowlet.getAttackPoints(), rowlet.getDefence()));
		ash2.addMonster(new Monster("Litten", Types.FIRE, litten.getHitPoints(), litten.getAttackPoints(), litten.getDefence()));
		Assertions.assertEquals(ash, ash2, "Two identical trainers should be considered equal");
		
		Trainer ash3 = new Trainer("Ash");
		Assertions.assertNotEquals(ash, ash3, "Two trainers with same name but different monsters are not equal");
		
		Trainer other = new Trainer("Other guy");
		other.addMonster(new Monster("Pikachu", Types.ELECTRIC, pikachu.getHitPoints(), pikachu.getAttackPoints(), pikachu.getDefence()));
		other.addMonster(new Monster("Rowlet", Types.GRASS, rowlet.getHitPoints(), rowlet.getAttackPoints(), rowlet.getDefence()));
		other.addMonster(new Monster("Litten", Types.FIRE, litten.getHitPoints(), litten.getAttackPoints(), litten.getDefence()));
		Assertions.assertNotEquals(ash, other, "Two trainers with same monsters but different name are not equal");
	}

	@Test
	public void fireMonsterTypesWeaknessesCorrect() {
		Assertions.assertTrue (Types.FIRE.isWeakAgainst(Types.WATER), "Fire monster should be weak against Water");
		Assertions.assertTrue (Types.FIRE.isWeakAgainst(Types.FIRE), "Fire monster should be weak against Fire");
		Assertions.assertFalse (Types.FIRE.isWeakAgainst(Types.ELECTRIC), "Fire monster should not be weak against Electric");
		Assertions.assertFalse (Types.FIRE.isWeakAgainst(Types.GRASS), "Fire monster should not be weak against Grass");
	}
	
	@Test
	public void waterMonsterTypesWeaknessesCorrect() {
		Assertions.assertTrue (Types.WATER.isWeakAgainst(Types.WATER), "Water monster should be weak against Water");
		Assertions.assertTrue (Types.WATER.isWeakAgainst(Types.GRASS), "Water monster should be weak against Grass");
		Assertions.assertFalse (Types.WATER.isWeakAgainst(Types.ELECTRIC), "Water monster should not be weak against Electric");
		Assertions.assertFalse (Types.WATER.isWeakAgainst(Types.FIRE), "Water monster should not be weak against Fire");
	}
	
	@Test
	public void electricMonsterTypesWeaknessesCorrect() {
		Assertions.assertTrue (Types.ELECTRIC.isWeakAgainst(Types.ELECTRIC), "Electric monster should be weak against Electric");
		Assertions.assertTrue (Types.ELECTRIC.isWeakAgainst(Types.GRASS), "Electric monster should be weak against Grass");
		Assertions.assertFalse (Types.ELECTRIC.isWeakAgainst(Types.FIRE), "Electric monster should not be weak against Fire");
		Assertions.assertFalse (Types.ELECTRIC.isWeakAgainst(Types.WATER), "Electric monster should not be weak against Water");
	}
	
	@Test
	public void grassMonsterTypesWeaknessesCorrect() {
		Assertions.assertTrue (Types.GRASS.isWeakAgainst(Types.FIRE), "Grass monster should be weak against Fire");
		Assertions.assertTrue (Types.GRASS.isWeakAgainst(Types.GRASS), "Grass monster should be weak against Grass");
		Assertions.assertFalse (Types.GRASS.isWeakAgainst(Types.ELECTRIC), "Grass monster should not be weak against Electric");
		Assertions.assertFalse (Types.GRASS.isWeakAgainst(Types.WATER), "Grass monster should not be weak against Water");
	}
	
	@Test
	public void typeOrderCorrect() {
		Types[] typeOrder = new Types[] { Types.FIRE, Types.WATER, Types.ELECTRIC, Types.GRASS };
		Assertions.assertArrayEquals(typeOrder, Types.values(), "Types order incorrect");
	}
	
	@Test
	public void canFightReturnsTrueIfMonsterAvailable() {
		Assertions.assertTrue(ash.canFight(), "canFight() should return true if trainer has at least one monster with hit points");
	}
	
	@Test
	public void canFightReturnsFalseIfNoMonstersAvailable() {
		Trainer trainer = new Trainer("Test");
		trainer.addMonster(new Monster("M1", Types.ELECTRIC, 0, 400, 200));
		trainer.addMonster(new Monster("M2", Types.FIRE, 0, 200, 200));
		Assertions.assertFalse(trainer.canFight(), "canFight() should return false if all trainer's monsters are knocked out");
	}
	
	@Test
	public void canFightReturnsFalseIfNoMonsters() {
		Trainer trainer = new Trainer("Test");
		Assertions.assertFalse(trainer.canFight(), "canFight() should return false if trainer has no monsters");
	}
	
	@Test
	public void chooseAttackMonsterWorksProperly() {
		Assertions.assertEquals(litten, cmc.chooseAttackMonster(ash.getMonsters()), "chooseAttackMonster should choose monster with most AP");
	}
	
	@Test
	public void chooseAttackMonsterWorksProperlyWithKnockOut() {
		ash.addMonster(new Monster("M1", Types.ELECTRIC, 0, 400, 20));
		Assertions.assertEquals(litten, cmc.chooseAttackMonster(ash.getMonsters()), "chooseAttackMonster should choose monster with most AP, ignoring knocked-out monsters");
	}
	
	@Test
	public void chooseDefenseMonsterWorksProperly() {
		Assertions.assertEquals(rowlet, cmc.chooseDefenseMonster(ash.getMonsters()), "chooseDefenseMonster should choose monster with most HP");
	}
	
	@Test
	public void testHumanPlayerGetAttackMonsterShouldReadFromStdin() {
		// Simulate stdin and stdout
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));
		Scanner scanner = new Scanner("50 aaa 0");
		
		MonsterChooser hmc = new HumanMonsterChooser(scanner);
		Monster m = hmc.chooseAttackMonster(kiawe.getMonsters());
		
		Assertions.assertFalse(baos.toString().isEmpty(), "HumanMonsterChooser.getAttackMonster() should prompt to stdout");
		Assertions.assertTrue(kiawe.getMonsters().contains(m), "Result of HumanMonsterChooser.getAttackMonster() should be one of the trainer's monsters");
	}
	
	@Test
	public void testHumanPlayerGetDefenseMonsterShouldReadFromStdin() {
		// Simulate stdin and stdout
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));
		Scanner scanner = new Scanner("50 aaa 0");
		
		MonsterChooser hmc = new HumanMonsterChooser(scanner);
		Monster m = hmc.chooseDefenseMonster(kiawe.getMonsters());
		
		Assertions.assertFalse(baos.toString().isEmpty(), "HumanMonsterChooser.getDefenseMonster() should prompt to stdout");
		Assertions.assertTrue(kiawe.getMonsters().contains(m), "Result of HumanMonsterChooser.getDefenseMonster() should be one of the trainer's monsters");
	}

	// Get a file name for loading/saving wishlists, and makes sure the file doesn't exist before returning
	private Path getOutputFile() throws IOException {
		tempFolder.create();
		Path path = tempFolder.newFile().toPath();
		Files.deleteIfExists(path);
		return path;
	}

	@Test
	public void testSaveAndLoadShouldWorkTogether() throws Exception {
		Path path = getOutputFile();
		ash.saveToFile(path.toString());
		Trainer trainer2 = Trainer.loadFromFile(path.toString());
		Assertions.assertEquals(ash, trainer2, "Trainer should be identical after load/save");
	}

	@Test
	public void testSaveShouldProduceNonEmptyFile() throws Exception {
		Path path = getOutputFile();
		kiawe.saveToFile(path.toString());
		Assertions.assertTrue(Files.exists(path), "Trainer output file does not exist after save()");
		Assertions.assertTrue(Files.size(path) > 0, "Trainer output file is empty after save()");
	}

}
