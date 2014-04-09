import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;

public class QuizServerLauncher {

	public static void main(String[] args) {
		QuizServerLauncher app = new QuizServerLauncher();
		app.launch();
		System.out.println("Setting up the Quiz Service...");
	}

	private void launch() {
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
			
			QuizService service = new QuizServer();

			String registryHost = "//localhost/";
			String serviceName = "quizservice";
			
			Naming.rebind(registryHost + serviceName, service);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}
}