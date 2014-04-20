Quiz
====

Quiz is a command line quiz app built in Java and using Remote Method Invocation for network communication.

### Instructions

To start the server, run the following command from the command line:
```sh
java -Djava.security.policy=server.policy QuizServerLauncher
```

Quiz comes with two clients: QuizSetupClient, for creating quizzes and adding players, and QuizPlayerClient for attempting quizzes and checking scores.

To start QuizSetupClient, run the following command from the command line:
```sh
java -Djava.security.policy=client.policy QuizSetupClient
```

To start QuizPlayerClient, run the following command from the command line:
```sh
java -Djava.security.policy=client.policy QuizPlayerClient
```

Once the client is started, follow the onscreen instructions to enter players and enter and play quizzes and see the recent quiz play history.

There is currently no elegant way to quit the server. Instead, to quit, hit `Ctrl+c`. Both clients can be quit from their respective menus.

### Notes
Quiz was written and tested under Mac OS X Lion (10.7.5). The code was written in Eclipse (Kepler Service Release 2) with the apps tested in Terminal.app.

Server data is saved in a series of `.ser` files. These can be deleted to reset the system to its initial state. The files provided include a Guest player and a sample quiz.

The majority of testing was done by using the quiz system directly. There is currently little in the way of unit testing. This was a necessary concession due to time constraints. Sorry about that.
