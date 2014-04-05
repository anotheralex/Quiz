//package quiz;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class QuizImplTest {

	private Quiz z;
	private int id;
	private String name;

	@Before
	public void setup() {
		id = 0;
		name = "Test";
		z = new QuizImpl(id, name);
	}

	@Test
	public void getIdTest() {
		int expected = id;
		int actual = z.getId();
		assertEquals(expected, actual);
	}


	@Test
	public void getNameTest() {
		String expected = name;
		String actual = z.getName();
		assertEquals(expected, actual);
	}

	@Test
	public void setNameTest() {
		String altName = "Alt name";
		z.setText(altName);

		String expected = altText;
		String actual = q.getText();

		assertEquals(expected, actual);
	}
}
