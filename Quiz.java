package quiz;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A quiz is a collection of questions, each with multiple answers, only one
 * of which is correct
 *
 * A quiz has an ID, a name, a list of questions, with answers for each
 * question, and a flag indicating if each answer is correct
 *
 */

public interface Quiz extends Remote {

	/**
	 * Return the quiz ID
	 *
	 * @return id the id of the quiz
	 */
	int getId();

	/**
	 * Return the name of the quiz
	 *
	 * @return name the name of the quiz
	 */
	String getName();

	/**
	 * Add a new question (and associated potential answers) to a quiz
	 */
	void addQuestion();

	/**
	 * Get a list of the questions in a quiz
	 *
	 * @return a list of Questions
	 */
	List<Question> getQuestions();

	/**
	 * Print the questions in the current quiz
	 */
	void printQuestions();

	/**
	 * Close a quiz
	 *
	 * @param id the id of the quiz
	 */
	void closeQuiz(int id);
}
