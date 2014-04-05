//package quiz;

import org.junit.*;
import static org.junit.Assert.*;

public class PlayerImplTest {

	private Player p;
	private String name;
	private int id;

	@Before
	public void setup() {
		id = 1;
		name = "Test";

		p = new PlayerImpl(id, name);
	}

	@Test
	public void getIdTest() {
		int expected = id;
		int actual = p.getId();
		assertEquals(expected, actual);
	}

	@Test
	public void getNameTest() {
		String expected = name;
		String actual = p.getName();
		assertEquals(expected, actual);
	}
}
