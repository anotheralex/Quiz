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
					try {
						String name = this.getPlayerDetails();
						this.quizService.addPlayer(name);
						System.out.println("Player added.");
						System.out.println("");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						String title = this.getQuizDetails();
						Quiz newQuiz = this.quizService.addQuiz(title);
						newQuiz.addQuestions();
						quizService.printMessage("Quiz added.");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				case 3:
					this.showPlayers();
					break;
				case 4:
					showQuizzes();
					break;
				case 5:
					run = false;
					break;
				default:
					System.out.println("Invalid option. PLease try again.");
					System.out.println("");
			}
		}
	}

	/**
	 * Get the name of a new player
	 *
	 * @return the name of the player
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
	 * Get the name of a new player
	 *
	 * @return the name of the player
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
	 */
	public void addQuiz() {
	}

	/**
	 * Add a new question
	 * 
	 * Private method to be used by addQuiz()
	 */
	private void addQuestion() {
	}

	/**
	 * Add a new answer
	 * 
	 * Private method to be used by addQuestion()
	 */
	private void addAnswer() {
	}

	/**
	 * Show the details of a specified player
	 *
	 * @param id the ID of the player
	 */
	public void showPlayer(int id) {
	}
	
	/**
	 * Show a list of all players
	 * @throws RemoteException 
	 */
	public void showPlayers() throws RemoteException {
		if (this.quizService.getPlayers().isEmpty()) {
			System.out.println("No players added.");
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
