import java.util.ArrayList;
import java.util.List;

/**
 * A quiz is a collection of questions, each with multiple answers, only one
 * of which is correct
 *
 * A quiz has an ID, a name, a list of questions, with answers for each
 * question, and a boolean indicating if the quiz is running.
 *
 */

public class QuizImpl implements Quiz {

	private int id;
	private String title;
	private List<Question> questions;
	private int questionId;
	private boolean isActive;

	/**
	 * Constructor
	 *
	 * @param id the id for the quiz
	 * @param title the title of the quiz
	 */
	public QuizImpl(int id, String title) {
		this.id = id;
		this.title = title;
		this.questions = new ArrayList<>();
		this.isActive = false;
		this.questionId = 0;
	}

	/**
	 * Main for testing purposes
	 */
	public static void main(String[] args) {
		System.out.println("Testing basic functions...");
		Quiz q = new QuizImpl(1, "Test quiz");
		System.out.println("Quiz ID: " + q.getId());
		System.out.println("Quiz name: " + q.getTitle());
		System.out.println("Adding questions...");
		q.addQuestions();
		System.out.println("Printing questions...");
		q.printQuestions();
	}
	
	
	/**
	 * Set the quiz ID
	 *
	 * @param id the id of the quiz
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return the quiz ID
	 *
	 * @return id the id of the quiz
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set the quiz name
	 *
	 * @param name the name of the quiz
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return the name of the quiz
	 *
	 * @return name the name of the quiz
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Add a new question (and associated potential answers) to a quiz
	 */
	public void addQuestions() {
		boolean addNewQuestion = true;
		String text;
		Question q;
		String promptResponse;
		
		while (addNewQuestion) {
			System.out.print("Enter question: ");
			text = System.console().readLine();
			questionId++;
			q = new QuestionImpl(questionId, text);
			this.questions.add(q);

			System.out.println("");
			System.out.print("Hit Return to continue or q to quit: ");
			promptResponse = System.console().readLine();
			if (promptResponse.equals("q") ||
					promptResponse.equals("Q") ||
					promptResponse.equals("quit") ||
					promptResponse.equals("Quit")) {
				addNewQuestion = false;
			}
		}
	}

	/**
	 * Get a list of the questions in a quiz
	 *
	 * @return a list of Questions
	 */
	public List<Question> getQuestions() {
		return this.questions;
	}

	/**
	 * Print the questions in the current quiz
	 */
	public void printQuestions() {
		for (Question q : questions) {
			System.out.println(q.getText());
		}
	}

	/**
	 * Close a quiz
	 */
	public void close() {
		if (isActive) {
			isActive = false;
		} else {
			System.out.println("Quiz " + id + " is not running.");
		}
	}
}
