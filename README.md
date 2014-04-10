Quiz
====

Quiz is a command line quiz system built in Java that uses Java's RMI protocol for network communication.

### Instructions

To start the server, run the following command from the command line:
- java -Djava.security.policy=server.policy QuizServerLauncher

Quiz comes with two clients: QuizSetupClient, for creating quizzes and adding players, and QuizPlayerClient for attempting quizzes and checking scores.

To start QuizSetupClient, run the following command from the command line:
- java -Djava.security.policy=client.policy QuizSetupClient
