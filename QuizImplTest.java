//package quiz;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class QuizImplTest {

	private Quiz z;
	private int id;
	private String title;

	@Before
	public void setup() {
		id = 0;
		title = "Test";
		z = new QuizImpl(id, title);
	}

	@Test
	public void getIdTest() {
		int expected = id;
		int actual = z.getId();
		assertEquals(expected, actual);
	}

	@Test
	public void getTitleTest() {
		String expected = title;
		String actual = z.getTitle();
		assertEquals(expected, actual);
	}

	@Test
	public void setNameTest() {
		String altTitle = "Alt name";
		z.setTitle(altTitle);

		String expected = altTitle;
		String actual = z.getTitle();

		assertEquals(expected, actual);
	}
}
