//package quiz;

/**
 * A data structure for holding a String containing the text of 
 * an answer and an int indicating whether the answer is
 * correct or not
 */
public class AnswerImpl implements Answer {
	
	private String text;
	private int score;

	/**
	 * Constructor
	 */
	public AnswerImpl(String text, int score) {
		this.text = text;
		this.score = score;
	}

	/**
	 * Setter for Answer text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for Answer text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Check if an answer is correct
	 *
	 * @return 0 if incorrect and 1 if correct
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Specificy whether an answer is correct or not
	 * @param score equals 1 for correct and 0 for incorrect
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
