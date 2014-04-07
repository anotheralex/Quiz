// package quiz;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.MalformedURLException;

/**
 * A client for setting up new quizzes
 */
public class QuizSetClientImpl implements QuizSetupClient {



	/**
	 * Set up a connection to the remote server
	 *
	 * @return a handle to the service
	 */
	public QuizService connect() throws NotBoundException, RemoteException, MalformedURLException {
		URL = "127.0.0.1:1099/quizserver";
		Remote service = Naming.lookup(URL);
		QuizService quizService = (QuizService) service;
		return quizService;
	}

	/**
	 * Display a menu with user options
	 */
	public void showMenu() {
		System.out.println("1\tShow current players");
		System.out.println("2\tShow current quizzes");
		System.out.println("3\tAdd new player");
		System.out.println("4\tAdd new player");
	}

	/**
	 * Add a new player
	 *
	 * @return the ID the player
	 */
	int addPlayer();
	
	/**
	 * Add a new quiz
	 *
	 * @return the IF of the quiz
	 */
	int addQuiz();

	/**
	 * Show the details of a specified player
	 *
	 * @param id the ID of the player
	 */
	void showPlayer(int id);
	
	/**
	 * Show a list of all players
	 */
	void showPlayers();

	/**
	 * Show the details of a specified quiz
	 *
	 * @param id the ID of the quiz
	 */
	void showQuiz(int id);
	
	/**
	 * Show a list of all quizzes
	 */
	void showQuizzes();
}
