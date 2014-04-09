import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;

public class QuizServerLauncher {

	public static void main(String[] args) {
		System.out.print("Setting up the Quiz Service.");
		QuizServerLauncher app = new QuizServerLauncher();
		System.out.print(".");
		app.launch();
	}

	private void launch() {
		System.out.print(".");
		/*
		 * Check if there is a  security manager running
		 * If there is not, start one
		 */
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		try {
			/*
			 * Register the service with the registry
			 * For this assignment the registry is assumed to be on
			 * the same machine as the client and the server
			 */
			LocateRegistry.createRegistry(1099);
			
			System.out.print(".");	
			QuizService service = new QuizServer();

			String registryHost = "//localhost/";
			String serviceName = "quizservice";
			
			System.out.print(".");
			Naming.rebind(registryHost + serviceName, service);
			
			System.out.println("done.");
			System.out.println("");
			System.out.println("Activity summary:");

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}
}