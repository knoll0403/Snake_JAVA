package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.Snake.Direction;
import javafx.scene.paint.Color;

public class SnakeTest
{
	Snake snake;
	
	@Before
	public void setUp()
	{
		snake = new Snake(null, 100*Unit.BLOCK_SIZE, 100*Unit.BLOCK_SIZE, Color.BLUE);
	}
	
	@Test
	public void isAliveTest()
	{
		assertTrue(snake.isAlive());
	}
	
	@Test
	public void inSnakeTest()
	{
		Unit unit = new Unit(null, 98*Unit.BLOCK_SIZE, 100*Unit.BLOCK_SIZE, Color.ALICEBLUE);
		assertTrue(snake.inSnake(unit));
	}
	
	@Test
	public void legalStepTest()
	{
		assertFalse(snake.legalStep());
	}

	@Test
	public void addScoreTest()
	{
		snake.addScore();
		assertEquals(10, (int) snake.getScore());
	}
	
	@Test
	public void desScoreTest()
	{
		snake.decScore();
		assertEquals(-10, (int) snake.getScore());
	}
}
