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
	public synchronized void addQuiz(String title) throws RemoteException {
		Quiz newQuiz = new QuizImpl(this.quizId, title);
		this.quizzes.add(newQuiz);
		this.quizId++;

		// confirm new quiz details
		StringBuilder sb = new StringBuilder();
		sb.append("Added new quiz: ");
		sb.append(newQuiz.getTitle());
		sb.append(" (ID: ");
		sb.append(newQuiz.getId());
		sb.append(")");
		System.out.println(sb.toString());
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
	 * Add a new player
	 *
	 * @param name the name of the new player
	 *
	 * @return the id of the new player
	 */
	public synchronized void addPlayer(String name) {
		if (name == null) {
			throw new NullPointerException();
		} else {
			Player newPlayer = new PlayerImpl(this.playerId, name);
			this.players.add(newPlayer);
			this.playerId++;

			// confirm new player details
			StringBuilder sb = new StringBuilder();
			sb.append("Added new player: ");
			sb.append(newPlayer.getName());
			sb.append(" (ID: ");
			sb.append(newPlayer.getId());
			sb.append(")");
			System.out.println(sb.toString());
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
}
