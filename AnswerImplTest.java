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

		a = new AnswerImpl(text, score);
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
}
