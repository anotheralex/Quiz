import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


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
		System.out.println("\nQUIZ PLAYER CLIENT MAIN MENU\n");
		System.out.println("Select one of the following options:");
		System.out.println("1. Add new player");
		System.out.println("2. Show current players");
		System.out.println("3. Show current quizzes");
		System.out.println("4. Play quiz");
		System.out.println("5. Show recent history");
		System.out.println("6. Quit\n");
		System.out.print("Option (1-6): ");
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
					this.addPlayer();
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
					this.showRecentHistory();
					break;
				case 6:
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
	 * Add a new player
	 * Players have a unique username and an ID
	 * 
	 * @return playerId the ID of the newly created player
	 * @throws RemoteException 
	 */
	public int addPlayer() throws RemoteException {
		int playerId = this.quizService.getNextPlayerId();
		String name = this.getPlayerDetails();
		
		Player newPlayer = new PlayerImpl(playerId, name);
		this.quizService.addPlayer(newPlayer);
		
		System.out.println("Player added.");
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
		int quizScore = 0;
		System.out.print("Enter the id of the quiz you want to play: ");
		int id = Integer.parseInt(System.console().readLine());
		if (this.quizService.quizWithIdExists(id)) {
			Quiz quiz = this.quizService.getQuizFromId(id);
			for (Question q : quiz.getQuestions()) {
				System.out.println(q.getText());
				quizScore += this.getQuizQuestionAnswer(q);
			}
			System.out.println("Score: " + quizScore);
			Record record = new Record(this.quizService.getNextRecordId(), 0, quiz.getId(), quizScore);
			this.quizService.addRecord(record);
			this.switcher();
		} else {
			System.out.println("Sorry, no quiz with ID \"" + id + "\" exists.\n");
			this.switcher();
		}
	}

	public void getTopScores() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Show the answers for a quiz question
	 * get a response and generate a score
	 * 
	 * @param question the Question object
	 * @return the score for the question
	 */
	public int getQuizQuestionAnswer(Question question) {
		for (Answer a : question.getAnswers()) {
			System.out.println(a.getId() + ". " + a.getText());
		}
		System.out.print("Answer: ");
		int response = Integer.parseInt(System.console().readLine());
		return question.getAnswerFromId(response).getScore();
	}
	
	/**
	 * Show a list of recent play records
	 */
	public void showRecentHistory() {
		List<Record> recent = null;
		try {
			recent = this.quizService.getRecentHistory();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (recent.size() == 0) {
			System.out.println("Sorry, no history to display.");
		} else {
			System.out.println("Quiz ID\tPlayer ID\tQuiz Score");
			for (Record r : recent) {
				System.out.println(r.getQuizID() + "\t" + r.getPlayerId() + "\t" + r.getQuizScore());
			}
		}
		
	}

}
