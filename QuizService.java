//package quiz

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Functionality to be offered by the quiz server
 */
public interface QuizService extends Remote {

	/**
	 * Creates a new quiz
	 *
	 * @return the id of the new quiz
	 */
	int createQuiz() throws RemoteException;

	/**
	 * Start a specified quiz
	 *
	 * @param id the id of the quiz to start
	 */
	void startQuiz(int id) throws RemoteException;

	/**
	 * Closes a specified quiz
	 *
	 * Prints the name of the winner and all player details
	 *
	 * @param id the id of the quiz to close
	 */
	void closeQuiz(int id) throws RemoteException;
}
