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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	// Map used for serializing and deserializing IDs
	private HashMap<String, Integer> idMap;

	public QuizServer() throws RemoteException {
		
		// get the saved IDs or enter default values if no previous data
		this.idMap = this.loadIdData("ids.ser");
		
		this.players = this.loadPlayerData("players.ser");
		this.playerId = this.idMap.get("playerId");
		
		this.quizzes = this.loadQuizData("quizzes.ser");
		this.quizId = this.idMap.get("quizId");

		this.history = this.loadHistory("history.ser");
		this.recordId = this.idMap.get("recordId");
	}

	
	/**
	 * Creates a new quiz
	 * 
	 * Note: Functionality now in QuizServer.addQuiz(Quiz)
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
		this.flush("quizzes");
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
	 * @param id the id of the quiz to close
	 * @return List of recent records for the quoted quiz
	 * or null if the quiz does not exist
	 */
	public synchronized List<Record> closeQuiz(int id) throws RemoteException  {
		List<Record> closeOutHistory = null;
		Quiz quiz;
		if (this.quizWithIdExists(id)) {
			closeOutHistory = new ArrayList<>();
			quiz = this.getQuizFromId(id);
			closeOutHistory = this.getRecentHistory(id);
			quiz.close();
		}
		this.flush("quizzes");
		return closeOutHistory;
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
	public synchronized void addPlayer(Player player) throws RemoteException {
		this.players.add(player);
		this.flush("players");
	}

	/**
	 * List all available quizzes
	 * 
	 * Note: This functionality is now in QuizSetupClient.showQuizzes()
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
	 * Get a list of all quizzes that are live
	 *
	 * @return players a List<Quiz> of players
	 */
	public List<Quiz> getLiveQuizzes() throws RemoteException {
		List<Quiz> liveQuizzes = new ArrayList<>();
		for (Quiz q : this.quizzes) {
			if (q.isLive()) {
				liveQuizzes.add(q);
			}
		}
		return liveQuizzes;
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
	 * Check if a player with a given ID exists
	 * 
	 * @param id the ID of the player
	 * @return true if player exists otherwise false
	 */
	public boolean playerWithIdExists(int id) throws RemoteException {
		for (Player player : this.players) {
			if (player.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the Quiz object associated with an ID
	 * 
	 * @param id the ID of a player
	 * @return player the Player object with ID id
	 * or null if there is no matching player
	 */
	public Player getPlayerFromId(int id) throws RemoteException {
		for (Player player : this.players) {
			if (player.getId() == id) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Get the title of the Quiz object associated with an ID
	 * 
	 * @param id the ID of a quiz
	 * @return the title of the Quiz object with ID
	 */
	public String getQuizTitleFromId(int id) throws RemoteException {
		for (Quiz quiz : this.quizzes) {
			if (quiz.getId() == id) {
				return quiz.getTitle();
			}
		}
		return "";
	}

	/**
	 * Add questions to a specific quiz
	 * 
	 * Note: This method is now deprecated and the functionality is in QuizSetupClient
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
	public synchronized void addRecord(Record record) throws RemoteException {
		this.history.add(record);
		this.flush("history");
	}
	
	/**
	 * Create a list of the most recent quiz play records
	 */
	public List<Record> getRecentHistory() throws RemoteException {
		if (this.history.size() < 11) {
			return this.history;
		} else {
			List<Record> recentHistory = new ArrayList<>(this.history.subList(this.history.size() - 10, this.history.size()));
			return recentHistory;
		}
	}
	
	/**
	 * Create a list of the most recent quiz play records for a specific quiz
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
			System.out.println("Object data saved.");
		} catch (FileNotFoundException fex) {
			System.out.println("Could not open file for writing.");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		/*
		 * flush all IDs to disk
		 */
		this.flushIds();
	}
	
	/**
	 * Save data to disk
	 */
	private synchronized void flushIds() {
		// Note: this is creating a new Map each time data is saved potentially wasting space and GC time
		idMap = new HashMap<>();
		idMap.put("playerId", this.playerId);
		idMap.put("quizId", this.quizId);
		idMap.put("recordId", this.recordId);
		
		try {
			File file = new File("ids.ser");
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(idMap);
			out.close();
			fout.close();
			System.out.println("ID data saved.");
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
				System.out.print("..loaded players.");
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("No player list found. Using defaults.");
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
				System.out.print("..loaded quizzes.");
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("No quiz list found. Using defaults.");
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
				System.out.print("..loaded history.");
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("No history found.");
				c.printStackTrace();
			}
		}
		return history;
	}
	
	/**
	 * Load IDs from disk
	 * 
	 * @param filename the name of the file to load
	 * @return a HashMap of the three types of IDs tracked by the server
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, Integer> loadIdData(String filename) throws RemoteException {
		HashMap<String, Integer> map = new HashMap<>();
		
		/*
		 * First check to see if there if the file filename exists and
		 * if it does, import the HashMap containing the three ID ints
		 */
		File file = new File(filename);
		if (file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fin);
				map = (HashMap<String, Integer>) in.readObject();
				in.close();
				fin.close();
				System.out.print("..loaded IDs.");
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("IDs not found");
				c.printStackTrace();
			}
		} else {
			/*
			 * If the file does not exist, enter the default values to start a new session
			 */
			//System.out.println("No previous IDs found. Starting with default values.");
			map.put("playerId", 1);
			map.put("quizId", 1);
			map.put("recordId", 1);
		}
		return map;
	}

	/**
	 * Get the top score and associated players for a quiz identified by ID
	 * 
	 * @param id the ID of the quiz
	 * @return a list of players who got the top score
	 */
	public List<Player> getQuizTopPlayers(int id) throws RemoteException {
		Set<Player> topPlayerSet = new HashSet<>();
		int topScore = this.getQuizTopScore(id);
		
		for (Record record : this.history) {
			if (record.getQuizID() == id && record.getQuizScore() == topScore) {
				topPlayerSet.add(this.getPlayerFromId(record.getPlayerId()));
			}
		}
		
		List<Player> topPlayers = new ArrayList<>(topPlayerSet); 
		
		return topPlayers;
	}
	
	/**
	 * Get the top score for a quiz identified by ID
	 * 
	 * Note: during refactoring, it would be a good idea to put the top score for each
	 * quiz into a Map right when quizzes are completed to avoid having to scan the 
	 * history each time 
	 * 
	 * @param id the ID of the quiz
	 * @return the top score
	 */
	public int getQuizTopScore(int id) throws RemoteException {
		int topScore = 0;
		for (Record record : this.history) {
			if (record.getQuizID() == id && record.getQuizScore() > topScore) {
				topScore = record.getQuizScore();
			}
		}
		
		return topScore;
	}

}