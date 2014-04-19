Quiz
====

Quiz is a command line quiz system built in Java that uses Java's RMI protocol for network communication.

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

### Notes
Quiz has been tested on OS X 10.7.5 with apps running successfully in Terminal.app.

Server data is saved in a series of .ser files. These can be deleted to reset the system to its initial state. The files provided include a Guest player and a sample quiz.
