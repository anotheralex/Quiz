//package quiz;

import org.junit.*;
import static org.junit.Assert.*;

public class QuestionImplTest {

	private Question q;
	private int id;
	private String text;
	private Answer a;

	@Before
	public void setup() {

		text = "Test question";
		id = 0;
		String atext = "Test answer";
		int score = 1;
		
		q = new QuestionImpl(id, text);
		a = new AnswerImpl(atext, score);
	}

	@Test
	public void getIdTest() {
		int expected = id;
		int actual = q.getId();
		assertEquals(expected, actual);
	}


	@Test
	public void getTextTest() {
		String expected = text;
		String actual = q.getText();
		assertEquals(expected, actual);
	}

	@Test
	public void setTextTest() {
		String altText = "Alt text";
		q.setText(altText);

		String expected = altText;
		String actual = q.getText();

		assertEquals(expected, actual);
	}

	public void getAnswers() {
	}
}
