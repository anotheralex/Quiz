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
					break;
				case 2:
					break;
				default:
					System.out.println("Invalid option. PLease try again.");
					System.out.println("");
			}
		}
	}
	
	
	@Override
	public void showPlayers() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showQuizzes() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showQuizQuestions(int id) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playQuiz(int id) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTopScores() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
