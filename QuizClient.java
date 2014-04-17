// package quiz;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

/**
 * A client for setting up new quizzes
 */
public abstract class QuizClient {

	private QuizService quizService;
	
	/**
	 * Set up a connection to the remote server
	 *
	 * @return a handle to the service
	 */
	public void connect() throws NotBoundException, RemoteException, MalformedURLException {
		String URL = "//localhost/quizservice";
		Remote service = Naming.lookup(URL);
		this.quizService = (QuizService) service;
	}

	/**
	 * Get the string for a new player name
	 * String cannot be empty
	 *
	 * @return the name string
	 */
	public String getPlayerDetails() {
		String name;
		do {
			System.out.print("Enter player name: ");
			name = System.console().readLine();
		} while (name.equals(""));
		return name;
	}

	/**
	 * Add a new player
	 * Players have unique usernames and an ID
	 * 
	 * @return playerId the ID of the newly created player
	 * @throws RemoteException 
	 */
	public int addPlayer() throws RemoteException {
		int playerId = this.quizService.getNextPlayerId();
		String name = this.getPlayerDetails();
		
		Player newPlayer = new PlayerImpl(playerId, name);
		this.quizService.addPlayer(newPlayer);
		
		System.out.println("Player added.\n");
		quizService.printMessage("Player added.");

		return newPlayer.getId();
	}
	
	/**
	 * Show a list of all players
	 * @throws RemoteException 
	 */
	public void showPlayers() throws RemoteException {
		if (this.quizService.getPlayers().isEmpty()) {
			System.out.println("No players yet.");
		} else {
			System.out.println("\nAll players");
			System.out.println("ID\tName");
			for (Player p : this.quizService.getPlayers()) {
				StringBuilder sb = new StringBuilder();
				sb.append(p.getId());
				sb.append("\t");
				sb.append(p.getName());
				System.out.println(sb.toString());
			}
		}
	}

	/**
	 * Show the details of a specified quiz
	 *
	 * @param id the ID of the quiz
	 */
	public void showQuiz(int id) {
	}
	
	/**
	 * Show a list of all quizzes
	 * @throws RemoteException 
	 */
	public void showQuizzes() throws RemoteException {
		if (this.quizService.getQuizzes().isEmpty()) {
			System.out.println("No quizzes yet.");
		} else {
			System.out.println("\nAll quizzes");
			System.out.println("ID\tName");
			for (Quiz q : this.quizService.getQuizzes()) {
				StringBuilder sb = new StringBuilder();
				sb.append(q.getId());
				sb.append("\t");
				sb.append(q.getTitle());
				System.out.println(sb.toString());
			}
		}
	}
}