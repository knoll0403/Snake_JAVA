package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PoisonTest
{
	Poison poison;
	
	@Before
	public void setUp()
	{
		poison = new Poison(null);
	}
	
	@Test
	public void decLife()
	{
		poison.decLife();
		assertEquals(49, poison.life);
	}
	
	@Test
	public void noLife()
	{
		for (int i = 0; i < 50; i++)
		{
			poison.decLife();
		}
		assertTrue(poison.noLife());
	}

}
