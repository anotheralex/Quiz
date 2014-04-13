//package quiz

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * A quiz server that serves the QuizService service to remote clients
 *
 * Uses RMI to allow clients to create, play and close quizzes
 */
public class QuizServer extends UnicastRemoteObject implements QuizService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int quizId;
	private List<Quiz> quizzes;
	
	private int playerId;
	private List<Player> players;

	public QuizServer() throws RemoteException {
		// next ID for quizzes and players
		// both start at 1
		quizId = 1;
		playerId = 1;
		
		quizzes = new ArrayList<>();
		players = new ArrayList<>();

	}

	/**
	 * Creates a new quiz
	 */
	public synchronized Quiz addQuiz(String title) throws RemoteException {
		Quiz newQuiz = new QuizImpl(this.quizId, title);
		this.quizzes.add(newQuiz);
		this.quizId++;
		return newQuiz;

		/*
		// confirm new quiz details
		StringBuilder sb = new StringBuilder();
		sb.append("Added new quiz: ");
		sb.append(newQuiz.getTitle());
		sb.append(" (ID: ");
		sb.append(newQuiz.getId());
		sb.append(")");
		System.out.println(sb.toString());
		*/
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

	/**
	 * Add a new player and add it to the list of players
	 *
	 * @param name the name of the new player
	 *
	 * @return the new Player object
	 */
	public synchronized Player addPlayer(String name) {
		if (name == null) {
			throw new NullPointerException();
		} else {
			Player newPlayer = new PlayerImpl(this.playerId, name);
			this.players.add(newPlayer);
			this.playerId++;
			return newPlayer;
		}
	}

	/**
	 * List all available quizzes
	 */
	public void listQuizzes() {
		if (this.quizzes.isEmpty()) {
			System.out.println("No quizzes yet.");
		} else {
			System.out.println("ID\tName");
			for (Quiz q : quizzes) {
				StringBuilder sb = new StringBuilder();
				sb.append("ID: ");
				sb.append(q.getId());
				sb.append("\t");
				sb.append(q.getTitle());
				System.out.println(sb.toString());
			}
		}
	}

	/**
	 * Get a list of all players
	 *
	 * @return a List<Player> of players
	 */
	public List<Player> getPlayers() {
		return this.players;
	}

	/**
	 * Get a list of all quizzes
	 *
	 * @return a List<Quiz> of quizzes
	 */
	public List<Quiz> getQuizzes() {
		return this.quizzes;
	}
	
	/**
	 * Print a status message to the console
	 */
	public void printMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Get the Quiz object associated with an ID
	 * 
	 * @param id the ID of a quiz
	 * @return quiz the Quiz object with ID id
	 * or null if there is no matching quiz
	 */
	public Quiz getQuizFromId(int id) {
		for (Quiz quiz : quizzes) {
			if (quiz.getId() == id) {
				return quiz;
			}
		}
		return null;
	}

	/**
	 * Check if a quiz with a given ID exists
	 * 
	 * @param id the ID of the quiz
	 * @return true if quiz exists otherwise false
	 */
	public boolean quizWithIdExists(int id) {
		for (Quiz q : this.quizzes) {
			if (q.getId() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add questions to a specific quiz
	 * 
	 * @param id the ID of the quiz to add the questions to
	 * @return true if successfully added and false otherwise
	 * @throws RemoteException 
	 */
	public void addQuestions(int id) throws RemoteException {
		if (this.quizWithIdExists(id)) {
			this.getQuizFromId(id).addQuestions();
		} else {
			System.out.println("Sorry, there is no quiz with ID " + id);
		}
	}

	
	
}