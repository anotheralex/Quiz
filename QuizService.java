//package quiz

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Functionality to be offered by the quiz server
 */
public interface QuizService extends Remote {

	/**
	 * Creates a new quiz
	 *
	 * @return the id of the new quiz
	 */
	Quiz addQuiz(String title) throws RemoteException;

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

	/**
	 * Add a new player
	 *
	 * @param name the name of the new player
	 *
	 * @return the id of the new player
	 */
	Player addPlayer(String name) throws RemoteException;

	/**
	 * List all available quizzes
	 */
	void listQuizzes() throws RemoteException;

	/**
	 * Get a list of all players
	 *
	 * @return players a List<Player> of players
	 */
	List<Player> getPlayers() throws RemoteException;

	/**
	 * Get a list of all quizzes
	 *
	 * @return players a List<Quiz> of players
	 */
	List<Quiz> getQuizzes() throws RemoteException;

	/**
	 * Print a message to the console
	 */
	void printMessage(String message) throws RemoteException;
}
