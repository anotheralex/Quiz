//package quiz;

/**
 * A player in the quiz
 */
public class PlayerImpl implements Player {

	private int id;
	private String name;

	public PlayerImpl(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Getter for Player ID
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Getter for Player name
	 */
	public String getName() {
		return this.name;
	}
}
