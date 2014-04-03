package quiz;

/**
 * A data structure for holding a String containing the text of 
 * an answer and a boolean indicating whether the answer is
 * correct or not
 */
public interface Answer {
	
	/**
	 * Setter
	 */
	void addAnswer(String text);

	/**
	 * Getter for Answer content
	 */
	String getAnswer();

	/**
	 * Check is answer is correct
	 *
	 * @return isCorrect a boolean that is true when the answer is correct
	 */
	boolean isCorrect();

}
