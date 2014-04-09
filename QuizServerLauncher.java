import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;

public class QuizServerLauncher {

	public static void main(String[] args) {
		QuizServerLauncher app = new QuizServerLauncher();
		app.launch();
	}

	private void launch() {
		//1. If there is no security manager, start one
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			// 2. Create the registry if there is not one
			LocateRegistry.createRegistry(1099);
			// 3. Create the server object
			QuizService service = new QuizServer();
			// 4. Register (bind) the server object on the registry
			// The registry may be on a different machine
			String registryHost = "//localhost/";
			String serviceName = "quizserver";
			Naming.rebind(registryHost + serviceName, service);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}
}