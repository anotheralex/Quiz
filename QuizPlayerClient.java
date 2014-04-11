import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * A client for playing quizzes
 */
public interface QuizPlayerClient {

	/**
	 * Launcher for main client interaction
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	void launch() throws RemoteException, MalformedURLException, NotBoundException;

	/**
	 * Set up a connection to the remote server
	 *
	 * @return a handle to the service
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	void connect() throws NotBoundException, RemoteException, MalformedURLException;

	/**
	 * Display a menu with user options
	 */
	void showMenu();

	/**
	 * Show a list of all players
	 * @throws RemoteException 
	 */
	void showPlayers() throws RemoteException;

	/**
	 * Show a list of all quizzes
	 * @throws RemoteException 
	 */
	void showQuizzes() throws RemoteException;
		
	/**
	 * Show the questions of a specified quiz
	 *
	 * @param id the ID of the quiz
	 */
	void showQuizQuestions(int id) throws RemoteException;
	
	/**
	 * Play a quiz game
	 */
	void playQuiz() throws RemoteException;
	
	/**
	 * Show the top scores for all quizzes
	 */
	void getTopScores() throws RemoteException;
}