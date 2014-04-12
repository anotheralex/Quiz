import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public class QuizPlayerClientImpl implements QuizPlayerClient {

	private QuizService quizService;
	
	public static void main(String[] args) throws MalformedURLException, NotBoundException {
		QuizPlayerClient qp = new QuizPlayerClientImpl();
		try {
			qp.launch();
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
		System.out.println("QUIZ PLAYER CLIENT MAIN MENU");
		System.out.println("");
		System.out.println("Select one of the following options:");
		System.out.println("1. Add new player");
		System.out.println("2. Show current players");
		System.out.println("3. Show current quizzes");
		System.out.println("4. Play quiz");
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
			this.showMenu();
		
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
						Player newPlayer = this.quizService.addPlayer(name);
						System.out.println("Player added.");
						System.out.println("");
						quizService.printMessage("Player added.");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					this.showPlayers();
					break;
				case 3:
					this.showQuizzes();
					break;
				case 4:
					this.showQuizzes();
					this.playQuiz();
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
	 * Show a list of all players
	 * @throws RemoteException 
	 */
	public void showPlayers() throws RemoteException {
		if (this.quizService.getPlayers().isEmpty()) {
			System.out.println("No players yet.");
			System.out.println("");
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
	 * Show a list of all quizzes
	 * @throws RemoteException 
	 */
	public void showQuizzes() throws RemoteException {
		if (this.quizService.getQuizzes().isEmpty()) {
			System.out.println("No quizzes yet.");
			System.out.println("");
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

	public void showQuizQuestions(int id) throws RemoteException {
		Quiz quiz = this.quizService.getQuizFromId(id);
		for (Question q : quiz.getQuestions()) {
			System.out.println(q.getText());
		}
	}

	/**
	 * Ask for and play a particular quiz, identified by its ID
	 * 
	 * @throws RemoteException
	 */
	public void playQuiz() throws RemoteException {
		System.out.print("Enter the id of the quiz you want to play: ");
		int id = Integer.parseInt(System.console().readLine());
		if (this.quizService.quizWithIdExists(id)) {
			Quiz quiz = this.quizService.getQuizFromId(id);
			
			/*
			 * Debugging methods to check that questions are being retrieved 
			 */
			System.out.println("Quiz with ID " + quiz.getId() + " exists.");
			quiz.printQuestions();
			System.out.println("Number of questions: " + quiz.getQuestions().size());
			
			for (Question q : quiz.getQuestions()) {
				System.out.println(q.getText());
			}
			this.switcher();
		} else {
			System.out.println("Sorry, no quiz with ID \"" + id + "\" exists.\n");
			this.switcher();
		}
	}

	public void getTopScores() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
