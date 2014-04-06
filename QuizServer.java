//package quiz

import java.rmi.RemoteException;
import java.rmi.UnicastRemoteObject;

/**
 * A quiz server that serves the QuizService service to remote clients
 *
 * Uses RMI to allow clients to create, play and close quizzes
 */
public class QuizServer extends UnicastRemoteObject {

	public QuizServer() throws RemoteException() {
		// nothing to put here now
	}

	/**
	 * Creates a new quiz
	 *
	 * @return the id of the new quiz
	 */
	public int createQuiz() throws RemoteException {
	}


	/**
	 * Start a specified quiz
	 *
	 * @param id the id of the quiz to start
	 */
	public void startQuiz(int id) throws RemoteException {
	}


	/**
	 * Closes a specified quiz
	 *
	 * Prints the name of the winner and all player details
	 *
	 * @param id the id of the quiz to close
	 */
	public void closeQuiz(int id) throws RemoteException  {
	}

}
