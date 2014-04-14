import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * A client for setting up new quizzes
 */
public interface QuizSetupClient {

	/**
	 * Set up a connection to the remote server
	 *
	 * @return a handle to the service
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	void connect() throws NotBoundException, RemoteException, MalformedURLException;

	//TODO Should this be a private method?
	/**
	 * Display a menu with user options
	 */
	void showMenu();

	/**
	 * Get the details of a player to add
	 *
	 * @return the name the player
	 */
	String getPlayerDetails();	

	/**
	 * Get the details of a quiz to add
	 *
	 * @return the title the player
	 */
	String getQuizDetails();	

	/**
	 * Add a new quiz
	 *
	 * @return the ID of the quiz
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
	 * @throws RemoteException 
	 */
	void showPlayers() throws RemoteException;

	/**
	 * Show the details of a specified quiz
	 *
	 * @param id the ID of the quiz
	 */
	void showQuiz(int id);
	
	/**
	 * Show a list of all quizzes
	 * @throws RemoteException 
	 */
	void showQuizzes() throws RemoteException;

	/**
	 * Launcher for main client interaction
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	void launch() throws RemoteException, MalformedURLException, NotBoundException;
}
