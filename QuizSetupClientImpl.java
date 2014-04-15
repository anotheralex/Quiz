// package quiz;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

/**
 * A client for setting up new quizzes
 */
public class QuizSetupClientImpl implements QuizSetupClient {

	private QuizService quizService;

	public static void main(String[] args) throws MalformedURLException, NotBoundException {
		QuizSetupClient qs = new QuizSetupClientImpl();
		try {
			qs.launch();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void launch() throws RemoteException, MalformedURLException, NotBoundException {
		System.out.println("Connecting to server...");
		this.connect();
		System.out.println("Connected to server.");
		this.switcher();
	}
		
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
	 * Display a menu with user options
	 */
	public void showMenu() {
		System.out.println("QUIZ SETUP CLIENT MAIN MENU");
		System.out.println("");
		System.out.println("Select one of the following options:");
		System.out.println("1. Add new player");
		System.out.println("2. Add new quiz");
		System.out.println("3. Show current players");
		System.out.println("4. Show current quizzes");
		System.out.println("5. Quit");
		System.out.println("");
		System.out.print("Option (1-5): ");
	}

	/**
	 * Take input from main menu and select appropriate method to run
	 * @throws RemoteException 
	 */
	private void switcher() throws RemoteException {
		boolean run = true;
		while (run) {
			showMenu();
		
			int choice = 0;
			try {
				choice = Integer.parseInt(System.console().readLine());
			} catch (NumberFormatException ex) {
				System.out.println("Not a number.");
			}

			/*
			 * Switch on the value of the selected option and choose
			 * the appropriate method to run
			 */
			switch (choice) {
				case 1:
					this.addPlayer();
					break;
				case 2:
					this.addQuiz();
					break;
				case 3:
					this.showPlayers();
					break;
				case 4:
					this.showQuizzes();
					break;
				case 5:
					run = false;
					break;
				default:
					System.out.println("Invalid option. Please try again.");
			}
		}
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
	 * Get the string for a new quiz title
	 * String cannot be empty
	 *
	 * @return the title string
	 */
	public String getQuizDetails() {
		String title;
		do {
			System.out.print("Enter quiz title: ");
			title = System.console().readLine();
		} while (title.equals(""));
		return title;
	}

	/**
	 * Add a new quiz
	 * 
	 * @return the id of the quiz if successfully created otherwise 0
	 */
	public int addQuiz() {
		Quiz newQuiz;
		try {
			String title = this.getQuizDetails();
			newQuiz = new QuizImpl(this.quizService.getNextQuizId(), title);
			newQuiz.addQuestions();
			this.quizService.addQuiz(newQuiz);
			this.quizService.printMessage("Quiz added.");
			return newQuiz.getId();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Show a list of all players
	 * @throws RemoteException 
	 */
	public void showPlayers() throws RemoteException {
		if (this.quizService.getPlayers().isEmpty()) {
			System.out.println("No players yet.\n");
		} else {
			System.out.println("");
			System.out.println("All players");
			System.out.println("ID\tName");
			for (Player p : this.quizService.getPlayers()) {
				StringBuilder sb = new StringBuilder();
				sb.append(p.getId());
				sb.append("\t");
				sb.append(p.getName());
				System.out.println(sb.toString());
			}
			System.out.println("");
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
			System.out.println("No quizzes yet.\n");
		} else {
			System.out.println("All quizzes");
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
