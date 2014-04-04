package quiz;

/**
 * A data structure for holding a String containing the text of 
 * an answer and an int indicating whether the answer is
 * correct or not
 */
public interface Answer {
	
	/**
	 * Setter for Answer text
	 */
	void setText(String text);

	/**
	 * Getter for Answer text
	 */
	String getText();

	/**
	 * Check if an answer is correct
	 *
	 * @return 0 if incorrect and 1 if correct
	 */
	int getScore();

	/**
	 * Specificy whether an answer is correct or not
	 */
	void setScore();

}
