//package quiz

import java.rmi.RemoteException;
import java.rmi.UnicastRemoteObject;

/**
 * A quiz server that serves the QuizService service to remote clients
 *
 * Uses RMI to allow clients to create, play and close quizzes
 */
public class QuizServer extends UnicastRemoteObject implements QuizService {

	private int quizId;
	private List<Quiz> quizzes;
	
	private int playerId:
	private List<Player> players;

	public QuizServer() throws RemoteException() {
		// next ID for quizzes and players
		// both start at 1
		quizId = 1;
		playerId = 1;
	}

	/**
	 * Creates a new quiz
	 *
	 * @return the id of the new quiz
	 */
	public int addQuiz() throws RemoteException {
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
	 * Logs quiz play details noting quiz id, player id
	 */
	private void writeLog() {
	}

	/**
	 * Add a new player
	 *
	 * @param name the name of the new player
	 *
	 * @return the id of the new player
	 */
	public int addPlayer(String name) {
		if (name == null) {
			throw new NullPointerException();
		} else {
			Player newPlayer = new Player(playerId, name);
			players.add(newPlayer);
			playerId++;

			// confirm new player details
			StringBuilder sb = new StringBuilder();
			sb.append("Added ");
			sb.append(newPlayer.getName());
			sb.append(" (");
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
				sb.append(q.getName());
				System.out.println(sb.toString());
			}
		}
	}
}
