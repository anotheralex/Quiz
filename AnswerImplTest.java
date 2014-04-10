//package quiz;

import org.junit.*;
import static org.junit.Assert.*;

public class AnswerImplTest {

	private Answer a;
	private String text;
	private int score;

	@Before
	public void setup() {
		text = "Test";
		score = 1;

		a = new AnswerImpl(1, text, score);
	}

	@Test
	public void getTextTest() {
		String expected = text;
		String actual = a.getText();
		assertEquals(expected, actual);
	}

	@Test
	public void getScoreTest() {
		int expected = score;
		int actual = a.getScore();
		assertEquals(expected, actual);
	}

	@Test
	public void setTextTest() {
		Answer b = new AnswerImpl(1, text, score);
		String altText = "Alt Text";
		b.setText(altText);

		String expected = altText;
		String actual = b.getText();

		assertEquals(expected, actual);
	}
	
	@Test
	public void setScoreTest() {
		Answer b = new AnswerImpl(1, text, score);
		int altScore = 0;
		b.setScore(altScore);

		int expected = altScore;
		int actual = b.getScore();

		assertEquals(expected, actual);
	}
}
