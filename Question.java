package quiz;

/**
 * A question is a String with a collection of Answers, each of which is right or wrong
 *
 * A question has an ID, content, and a list of answers, only one of which is correct
 *
 */
public interface Question {

	/**
	 * Return the question ID
	 *
	 * @return id the id of the question
	 */
	int getId();

	/**
	 * Return the text of the question
	 *
	 * @return content the content of the question
	 */
	String getQuestion();

	/**
	 * Add an answer to a question
	 */
	void addAnswer();

	/**
	 * Return a list of answers for the question
	 *
	 * @return a list of answers
	 */
	List<Answer> getAnswers();



}
