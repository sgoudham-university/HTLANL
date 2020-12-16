/* Trainer.java **/
package battle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import monster.Monster;
import monster.Types;

/**
 * Represents a Trainer, who has a set of Monsters and can battle other
 * Trainers.
 */
public class Trainer {

	/** This Trainer's name */
	private String name;
	/** The set of Monsters on this Trainer's team */
	private Set<Monster> monsters;
	/** The mechanism for choosing monsters */
	private MonsterChooser monsterChooser;

	/**
	 * Creates a new trainer with the given name.
	 * 
	 * @param name The name of the Trainer
	 */
	public Trainer(String name) {
		this.name = name;
		this.monsters = new HashSet<>();
	}
	
	/**
	 * Sets the monster chooser
	 */
	public void setMonsterChooser(MonsterChooser monsterChooser) {
		this.monsterChooser = monsterChooser;
	}

	/**
	 * Adds a Monster to this Trainer's set.
	 * 
	 * @param m The Monster to add
	 */
	public void addMonster(Monster m) {
		this.monsters.add(m);
	}

	/**
	 * Returns all Monsters owned by this Trainer.
	 * 
	 * @return The set of Monster
	 */
	public Set<Monster> getMonsters() {
		return monsters;
	}

	/**
	 * Returns this Trainer's name
	 * 
	 * @return This Trainer's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the set of Monsters owned by this Trainer, divided by their types.
	 * 
	 * @return All of this Trainer's Monsters, categorised by types
	 */
	public Map<Types, Set<Monster>> getMonstersByType() {
		Map<Types, Set<Monster>> result = new HashMap<>();

		for (Monster m : monsters) {
			Set<Monster> typeMonsters = result.get(m.getType());
			if (typeMonsters == null) {
				typeMonsters = new HashSet<>();
				result.put(m.getType(), typeMonsters);
			}
			typeMonsters.add(m);
		}
		return result;
	}

	/**
	 * Checks whether this Trainer can still continue in a battle.
	 * 
	 * @return True if there is at least one monster with positive HP, and false if
	 *         not
	 */
	public boolean canFight() {
		return monsters.stream().anyMatch(m -> m.getHitPoints() > 0);
	}

	/**
	 * Loads a Trainer from the given file and returns the result.
	 * 
	 * @param filename The file to load from
	 * @return A Trainer based on the information in the file
	 */
	public static Trainer loadFromFile(String filename) {
		// Load the whole file into a List<String> in memory
		List<String> trainerData = new ArrayList<>();

		// Reading in lines to List of Strings
		try (Scanner input = new Scanner(new File(filename))) {
			while (input.hasNextLine()) {
				trainerData.add(input.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// First line is name, read it in and create a new Trainer object
		Trainer trainer = new Trainer(trainerData.get(0));

		// Process the rest of the lines into Monster objects
		for (int i = 1; i < trainerData.size(); i++) {

			// Split the line
			String[] pokemonData = trainerData.get(i).split("\\^");

			// Use the fields to create a new Monster and add it
			trainer.addMonster(new Monster(pokemonData[0], Types.valueOf(pokemonData[1]), Integer.parseInt(pokemonData[2]), Integer.parseInt(pokemonData[3]), Integer.parseInt(pokemonData[4])));
		}

		// Return the newly created Trainer
		return trainer;

	}
	
	/**
	 * Saves the current Trainer to the given file.
	 * 
	 * @param filename The file to save to
	 * @throws IOException If there is an error accessing the file.
	 *
	*/
	public void saveToFile(String filename) throws IOException {
		String delimiter = "^";
		PrintWriter printWriter = new PrintWriter(new FileWriter(filename ,false));
		printWriter.println(getName());

		monsters.forEach(monster -> printWriter.println(monster.getName() + delimiter + monster.getType() + delimiter + monster.getHitPoints() + delimiter + monster.getAttackPoints() + delimiter + monster.getDefence()));

		printWriter.close();

	}
	
	public Monster chooseAttackMonster() {
		return monsterChooser.chooseAttackMonster(monsters);
	}
	
	public Monster chooseDefenseMonster() {
		return monsterChooser.chooseDefenseMonster(monsters);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((monsters == null) ? 0 : monsters.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trainer other = (Trainer) obj;
		if (monsters == null) {
			if (other.monsters != null)
				return false;
		} else if (!monsters.equals(other.monsters))
			return false;
		if (name == null) {
			return other.name == null;
		} else return name.equals(other.name);
	}
}
