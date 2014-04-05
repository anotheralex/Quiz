//package quiz;

/**
 * A player in the quiz
 */
public class PlayerImpl implements Player {

	private int id;
	private String name;

	/**
	 * Constructor for player
	 *
	 * @param id the id the player
	 * @param name the name of the player
	 */
	public PlayerImpl(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Getter for Player ID
	 *
	 * @return the id of the player
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Getter for Player name
	 *
	 * @return the name of the Player
	 */
	public String getName() {
		return this.name;
	}
}
