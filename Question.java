//package quiz;

/**
 * A question is a String with a collection of Answers, each of which is 
 * right or wrong
 *
 * A question has an ID, content, and a list of answers, only one of which 
 * is correct
 *
 */
public interface Question {

	/**
	 * Return the question ID
	 *
	 * @return the id of the question
	 */
	int getId();

	/**
	 * Return the text of the question
	 *
	 * @return the text of the question
	 */
	String getText();

	/**
	 * Set the text of a question
	 */
	void setText(String text);

	/**
	 * Add an answer to a question
	 *
	 * @param answer the answer to be added;
	 * the answer includes a score specifying if it is correct
	 */
	void addAnswer(Answer answer);

	/**
	 * Return a list of answers for the question
	 *
	 * @return a list of all answers associated with this question
	 */
	List<Answer> getAnswers();
}
