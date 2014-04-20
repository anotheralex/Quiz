import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuizPlayerClientImpl implements QuizPlayerClient {

	private QuizService quizService;
	private Player player;
	
	public static void main(String[] args) throws MalformedURLException, NotBoundException {
		QuizPlayerClient qp = new QuizPlayerClientImpl();
		try {
			qp.launch();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void launch() throws RemoteException, MalformedURLException, NotBoundException {
		try {
			System.out.print("Connecting to server...");
			this.connect();
			System.out.println("connected.");
			this.switcher();
		} catch (ConnectException c) {
			System.out.println("Server is not running.");
		}
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
		System.out.println("6. Select player");
		System.out.println("7. Quit\n");
		System.out.print("Option (1-7): ");
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
					this.playQuiz();
					break;
				case 5:
					this.showRecentHistory();
					break;
				case 6:
					this.setPlayer();
					break;
				case 7:
					run = false;
					break;
				default:
					System.out.println("Invalid option. Please try again.\n");
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
		
		System.out.println("Player added.");
		this.quizService.printMessage("New player added (ID: " + newPlayer.getId() + "; Username: " + newPlayer.getName() + ")");

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

	/**
	 * Show the questions included in a specified quiz
	 * 
	 * @param id the ID of the quiz
	 */
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
		int answerId = 0;
		Answer answer;

		// make sure that there is a valid Player playing
		System.out.println("Please choose a player.");
		this.setPlayer();
		
		// quizAnswers holds hold history of the questions and the answers given to each question
		Map<Integer, Integer> quizAnswers = new HashMap<>();
		
		// prompt for an integer ID of the quiz to be played
		this.showQuizzes();
		System.out.print("\nEnter the id of the quiz you want to play: ");
		int quizId = Integer.parseInt(System.console().readLine());
		
		/*
		 * This loop contains the main functionality for checking whether offered answers
		 * and also for keeping a record of all answers to questions.
		 * 
		 * Start by checking that there is a quiz with the given ID
		 */
		if (this.quizService.quizWithIdExists(quizId)) {
			
			// There is a matching quiz, so get the questions and then loop through them
			Quiz quiz = this.quizService.getQuizFromId(quizId);
			for (Question question : quiz.getQuestions()) {
				// print out the text of the question
				System.out.println(question.getText());
				
				// Prompt for a numeric answer to the question
				answerId = this.getQuizQuestionAnswer(question);

				// get the Answer object that matches the answerId
				answer = question.getAnswerFromId(answerId);
				
				// answers have an score of 0 if wrong or 1 if correct
				quizScore += answer.getScore();
				
				// add a questionId and the given answerId to the history
				quizAnswers.put(question.getId(), answerId);
			}
			System.out.println("\nFinal Score: " + quizScore);
			Record record = new Record(this.quizService.getNextRecordId(), this.player.getId(), quiz.getId(), quizAnswers, quizScore);
			this.quizService.addRecord(record);
			this.quizService.flush("history");
			this.quizService.printMessage("New record added (ID: " + record.getRecordId() + ")");
			//this.switcher();
		} else {
			System.out.println("Sorry, no quiz with ID \"" + quizId + "\" exists.\n");
			//this.switcher();
		}
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
		int quizQuestionAnswerId = Integer.parseInt(System.console().readLine());
		// return question.getAnswerFromId(response).getScore();
		return quizQuestionAnswerId;
	}
	
	public void getTopScores() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Show a list of recent play records
	 */
	public void showRecentHistory() throws RemoteException {
		List<Record> recent = this.quizService.getRecentHistory();
		
		if (recent.size() == 0) {
			System.out.println("Sorry, no history to display.");
		} else {
			System.out.println("Quiz ID\tQuiz Title\tPlayer\tQuiz Score");
			for (Record r : recent) {
				StringBuilder sb = new StringBuilder();
				sb.append(r.getQuizID());
				sb.append("\t");
				sb.append(this.quizService.getQuizTitleFromId(r.getQuizID()));
				sb.append("\t");
				sb.append(this.quizService.getPlayerFromId(r.getQuizID()).getName());
				sb.append("\t");
				sb.append(r.getQuizScore());
				System.out.println(sb.toString());
			}
		}
		
	}

	/**
	 * Select a player to play as
	 * This is stored in a member field but can be changed at any time
	 * 
	 * @return the Player object of the selected player
	 * @throws RemoteException 
	 */
	public Player selectExistingPlayer() throws RemoteException {
		int id;
		
		do {
			this.showPlayers();
			System.out.print("\nEnter player ID: ");
			id = Integer.parseInt(System.console().readLine());
		} while (!this.quizService.playerWithIdExists(id));
		
		return this.quizService.getPlayerFromId(id);
	}
	
	/**
	 * Set the Player for the session
	 */
	public void setPlayer() throws RemoteException {
		String response;
		
		do {
			System.out.print("\nHit n to create a new player or e to choose from the player list: ");
			response = System.console().readLine();

			switch (response) {
			case "n":
				int newPlayerId = this.addPlayer();
				this.player = this.quizService.getPlayerFromId(newPlayerId);
				break;
			case "e":
				this.player = this.selectExistingPlayer();
				break;
			default:
				System.out.print("Invalid option. Please try again.");
			}

		} while (!(response.equals("n") || response.equals("e")));
		
		System.out.println("You are playing as " + this.player.getName() + " (ID: " + this.player.getId() + ")");
	}
}
