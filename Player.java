package quiz;

/**
 * A player in the quiz
 */
public interface Player {

	/**
	 * Getter for Player ID
	 */
	void getId();

	/**
	 * Setter for Player name
	 */
	void setName(String text);

	/**
	 * Getter for Player name
	 */
	String getName();
}
