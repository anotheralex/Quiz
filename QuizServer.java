import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	// list of Record objects including the history of all quiz plays
	private int recordId;
	private List<Record> history;

	public QuizServer() throws RemoteException {
		
		//this.players = new ArrayList<>();
		this.players = this.loadPlayerData("players.ser");
		this.playerId = 1;
		
		this.quizzes = this.loadQuizData("quizzes.ser");
		this.quizId = 1;

		this.history = this.loadHistory("history.ser");
		this.recordId = 1;
	}

	// TODO Functionality now in addQuiz(Quiz)
	/**
	 * Creates a new quiz
	 */
	public synchronized Quiz addQuiz(String title) throws RemoteException {
		Quiz newQuiz = new QuizImpl(this.quizId, title);
		this.quizzes.add(newQuiz);
		this.quizId++;
		return newQuiz;
	}

	/**
	 * Adds a new Quiz to the list
	 *
	 * @param quiz the Quiz object to add
	 */
	public synchronized void addQuiz(Quiz quiz) throws RemoteException {
		this.quizzes.add(quiz);
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
	 * Add a new player and add it to the list of players
	 *
	 * @param player the Player object to add
	 */
	public synchronized void addPlayer(Player player) {
		this.players.add(player);
	}

	// TODO This functionality is now in QuizSetupClient.showQUixxes()
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

	// TODO This method is now deprecated and the functionality is in QuizSetupClient
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

	/**
	 * Get the next available quiz ID
	 * 
	 * @return the next ID available and assumes it will be used
	 */
	public synchronized int getNextQuizId() throws RemoteException {
		int result = this.quizId;
		this.quizId++;
		return result;
	}
	
	/**
	 * Get the next available record ID
	 * 
	 * @return the next ID available and assumes it will be used
	 */
	public synchronized int getNextRecordId() throws RemoteException {
		int result = this.recordId;
		this.recordId++;
		return result;
	}

	/**
	 * Get the next available player ID
	 * 
	 * @return the next ID available and assumes it will be used
	 */
	public synchronized int getNextPlayerId() throws RemoteException {
		int nextPlayerId = this.playerId;
		this.playerId++;
		return nextPlayerId; 
	}

	/**
	 * Create a record from a quiz play
	 * @param record the Record object to store
	 */
	public synchronized void addRecord(Record record) {
		this.history.add(record);
	}
	
	/**
	 * Create a list of the most recent quiz play records
	 * List is size 10 (or less if there are fewer than 10 records)
	 */
	public List<Record> getRecentHistory() throws RemoteException {
		if (this.history.size() < 10) {
			return this.history;
		} else {
			return this.history.subList(this.history.size() - 10, this.history.size());
		}
	}
	
	/**
	 * Create a list of the most recent quiz play records for a specific quiz
	 * List is size 10 (or less if there are fewer than 10 records)
	 * 
	 * @param quizId the ID of the quiz
	 */
	public List<Record> getRecentHistory(int quizId) throws RemoteException {
		List<Record> quizHistory = new ArrayList<>();
		for (Record r : this.history) {
			if (r.getQuizID() == quizId) {
				quizHistory.add(r);
			}
		}
		return quizHistory;
	}

	/**
	 * Save data to disk
	 */
	public synchronized void flush(String data) throws RemoteException {
		File file = null;
		Object obj = null;
		
		/*
		 * Use a String passed from the client to determine what data to save
		 * Initialize the variables as required to save the appropriate data
		 */
		switch (data) {
			case "players":
				file = new File("players.ser");
				obj = this.players;
				break;
			case "quizzes":
				file = new File("quizzes.ser");
				obj = this.quizzes;
				break;
			case "history":
				file = new File("history.ser");
				obj = this.history;
				break;
			default:
				System.out.println("Unknown data source.");
		}

		try {
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(obj);
			out.close();
			fout.close();
			System.out.println("Data saved.");
		} catch (FileNotFoundException fex) {
			System.out.println("Could not open file for writing.");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Load player data from disk
	 * 
	 * @param filename the name of the file to load
	 * @return players a List of Player objects
	 */
	@SuppressWarnings("unchecked")
	public List<Player> loadPlayerData(String filename) throws RemoteException {
		List<Player> players = new ArrayList<>();
		
		File file = new File(filename);
		if (file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fin);
				players = (List<Player>) in.readObject();
				in.close();
				fin.close();
				System.out.println("Loaded players.");
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("Player list not found");
				c.printStackTrace();
			}
		}
		return players;					
	}

	/**
	 * Load player data from disk
	 * 
	 * @param filename the name of the file to load
	 * @return players a List of Player objects
	 */
	@SuppressWarnings("unchecked")
	public List<Quiz> loadQuizData(String filename) throws RemoteException {
		List<Quiz> quizzes = new ArrayList<>();
		
		File file = new File(filename);
		if (file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fin);
				quizzes = (List<Quiz>) in.readObject();
				in.close();
				fin.close();
				System.out.println("Loaded quizzes.");
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("Quiz list not found");
				c.printStackTrace();
			}
		}
		return quizzes;
	}

	/**
	 * Load player data from disk
	 * 
	 * @param filename the name of the file to load
	 * @return players a List of Player objects
	 */
	@SuppressWarnings("unchecked")
	public List<Record> loadHistory(String filename) throws RemoteException {
		List<Record> history = new ArrayList<>();
		
		File file = new File(filename);
		if (file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fin);
				history = (List<Record>) in.readObject();
				in.close();
				fin.close();
				System.out.println("Loaded history.");
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("History not found");
				c.printStackTrace();
			}
		}
		return history;
	}

}