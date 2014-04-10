//package quiz;

import java.util.*;

/**
 * A question is a String with a collection of Answers, each of which is 
 * correct or incorrect
 *
 * A question has an ID, content, and a list of answers, only one of which 
 * is correct
 *
 */
public class QuestionImpl implements Question {

	private int id;
	private String text;
	private List<Answer> answers;
	private int answerId;

	public QuestionImpl(int id, String text) {
		this.id = id;
		this.text = text;
		this.answers = new ArrayList<>();
		this.answerId = 0;
	}

	/**
	 * Return the question ID
	 *
	 * @return the id of the question
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Return the text of the question
	 *
	 * @return the text of the question
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Set the text of a question
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Add an answer to a question
	 *
	 * @param answer the answer to be added;
	 * the answer includes a score specifying if it is correct
	 */
	public void addAnswers() {
		boolean addNewAnswer = true;
		String text;
		int score = 0;
		Answer a;
		String promptResponse = "";
		
		while (addNewAnswer) {
			System.out.print("Enter answer: ");
			text = System.console().readLine();
			
			do {
				System.out.print("Answer correct (y/n): ");
				promptResponse = System.console().readLine();
				if (promptResponse.equals("y")) {
					score = 1;
				} else if (promptResponse.equals("y")) {
					score = 0;
				}
			} while ( !(promptResponse.equals("y") || promptResponse.equals("n")) );

			answerId++;
			promptResponse = "";
			
			a = new AnswerImpl(answerId, text, score);
			this.answers.add(a);

			System.out.print("Hit Return to add another answer or n to enter the next question: ");
			promptResponse = System.console().readLine();
			if (promptResponse.equals("n") || promptResponse.equals("N")) {
				addNewAnswer = false;
			}
		}

	}

	/**
	 * Return a list of answers for the question
	 *
	 * @return a list of all answers associated with this question
	 * or null if there are none
	 */
	public List<Answer> getAnswers() {
		return this.answers;
	}
}
