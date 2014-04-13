import java.util.Calendar;

/**
 * Object that stores the record of a quiz game
 */
public class Record {
	
	/*
	 * use final since records should be immutable
	 */
	private final int recordId;
	private final Calendar date;
	private final int playerId;
	private final int quizId;
	private final int quizScore;

	/**
	 * Constructor
	 * Records can only be created at construction
	 * Note that the date is added at construction time
	 * 
	 * @param recordId the record ID
	 * @param playerId the player ID
	 * @param quizId the quiz ID
	 * @param quizScore the score of the quiz
	 */
	public Record(int recordId, int playerId, int quizId, int quizScore) {
		this.recordId = recordId;
		this.date = Calendar.getInstance();
		this.playerId = playerId;
		this.quizId = quizId;
		this.quizScore = quizScore;
	}
	
	/**
	 * Getter for recordId
	 * @return the recordId
	 */
	public int getRecordId() {
		return this.recordId;
	}

	/**
	 * Getter for date
	 * @return the date
	 */
	public Calendar getDate() {
		return this.date;
	}

	/**
	 * Getter for playerId
	 * @return the player ID
	 */
	public int getPlayerId() {
		return this.playerId;
	}
	
	/**
	 * Getter for quizId
	 * @return the quiz ID
	 */
	public int getQuizID() {
		return this.quizId;
	}

	/**
	 * Getter for quizScore
	 * @return the quiz score
	 */
	public int getQuizScore() {
		return this.quizScore;
	}
}