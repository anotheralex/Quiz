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
	 * Adds a new Quiz to the list
	 *
	 * @param quiz the Quiz object to add
	 */
	void addQuiz(Quiz quiz) throws RemoteException;

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
	 * @return List of recent records for the quoted quiz
	 */
	List<Record> closeQuiz(int id) throws RemoteException;

	/**
	 * Add a new player
	 *
	 * @param name the name of the new player
	 *
	 * @return the id of the new player
	 */
	Player addPlayer(String name) throws RemoteException;

	/**
	 * Add a new player
	 *
	 * @param name the name of the new player
	 *
	 * @return the id of the new player
	 */
	void addPlayer(Player player) throws RemoteException;

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
	 * Get a list of all quizzes that are live
	 *
	 * @return players a List<Quiz> of players
	 */
	List<Quiz> getLiveQuizzes() throws RemoteException;

	/**
	 * Print a message to the console
	 */
	void printMessage(String message) throws RemoteException;
	
	/**
	 * Find the quiz with a given ID
	 * 
	 * @param id the ID of the quiz
	 * @return the Quiz object with ID id
	 * @throws RemoteException
	 */
	Quiz getQuizFromId(int id) throws RemoteException;
	
	/**
	 * Check if a quiz with a given ID exists
	 * 
	 * @param id the ID of the quiz
	 * @return true if quiz exists otherwise false
	 */
	boolean quizWithIdExists(int id) throws RemoteException;
	
	/**
	 * Add a question to a specific quiz
	 * 
	 * @param id the  ID of the quiz to add the question to
	 * @throws RemoteException 
	 */
	void addQuestions(int id) throws RemoteException;

	/**
	 * Get the next available player ID
	 * 
	 * @return the next ID available and assumes it will be used
	 */
	int getNextPlayerId() throws RemoteException;

	/**
	 * Get the next available quiz ID
	 * 
	 * @return the next ID available and assumes it will be used
	 */
	int getNextQuizId() throws RemoteException;

	/**
	 * Create a record from a quiz play
	 * @param record the Record object to store
	 */
	void addRecord(Record record) throws RemoteException;
	
	/**
	 * Get the next available record ID
	 * 
	 * @return the next ID available and assumes it will be used
	 */
	int getNextRecordId() throws RemoteException;
	
	/**
	 * Create a list of the most recent quiz play records
	 * List is size 10 (or less if there are fewer than 10 records)
	 */
	List<Record> getRecentHistory() throws RemoteException;
	
	/**
	 * Save data to disk
	 * 
	 * @param data the List to write
	 */
	void flush(String data) throws RemoteException;
	
	/**
	 * Load data from disk
	 * 
	 * @param data a String representing the particular data to write to disk
	 */
	List<Player> loadPlayerData(String filename) throws RemoteException;

	/**
	 * Load player data from disk
	 * 
	 * @param filename the name of the file to load
	 * @return players a List of Player objects
	 */
	List<Quiz> loadQuizData(String filename) throws RemoteException;

	/**
	 * Load player data from disk
	 * 
	 * @param filename the name of the file to load
	 * @return players a List of Player objects
	 */
	List<Record> loadHistory(String filename) throws RemoteException;

	/**
	 * Create a list of the most recent quiz play records for a specific quiz
	 * List is size 10 (or less if there are fewer than 10 records)
	 * 
	 * @param quizId the ID of the quiz
	 */
	List<Record> getRecentHistory(int quizId) throws RemoteException;

}
