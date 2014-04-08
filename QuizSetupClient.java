// package quiz;

/**
 * A client for setting up new quizzes
 */
public interface QuizSetupClient {

	/**
	 * Set up a connection to the remote server
	 *
	 * @return a handle to the service
	 */
	QuizService connect();

	//TODO Should this be a private method?
	/**
	 * Display a menu with user options
	 */
	void showMenu();

	/**
	 * Add a new player
	 *
	 * @return the ID the player
	 */
	void addPlayer();
	
	/**
	 * Add a new quiz
	 *
	 * @return the IF of the quiz
	 */
	void addQuiz();

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
