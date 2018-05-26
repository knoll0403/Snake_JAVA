package game;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * <h1>Snake</h1>
 * A kigyót reprezentáló osztály.
 * <p>
 * ide még kell írni valamit
 * 
 * @author Knoll Zsolt
 */
public class Snake
{
	public enum Direction
	{
		UP, DOWN, LEFT, RIGHT
	}

	private ArrayList<Unit> body = new ArrayList<Unit>();
	private Direction direction, nextdirection;
	private int score;
	private boolean isAlive;
	private Color color;
	
	/**
	 * Az osztály konstruktora.
	 * @param gc GraphicsContext osztály egy példánya, erre helyezi el a kígyó testét.
	 * @param startx A kígyó startpozíciójának x koordinátája.
	 * @param starty A kígyó startpozíciójának y koordinátája.
	 * @param c A kígyó színe.
	 */
	public Snake(GraphicsContext gc, int startx, int starty, Color c)
	{
		direction = Snake.Direction.RIGHT;
		nextdirection = Snake.Direction.RIGHT;
		score = 0;
		isAlive = true;
		color = c;
		
		for (int i = 0; i < 5; i++)
		{
			Unit head = new Unit(gc, startx - i * Unit.BLOCK_SIZE, starty, color);
			body.add(head);
		}
	}
	
	/**
	 * A kígyót mozgatja.
	 * A kígyó fejét határozza meg, a kígyó mozgásának irányából.
	 * @return true minden esetben, mert a kígyó mozgott
	 */
	public void move()
	{
		Unit tail = body.remove(body.size()-1);
		
		switch (direction)
		{
		case UP:
			tail.setX(body.get(0).getX());
			tail.setY(body.get(0).getY() - Unit.BLOCK_SIZE);
			break;
		case DOWN:
			tail.setX(body.get(0).getX());
			tail.setY(body.get(0).getY() + Unit.BLOCK_SIZE);
			break;
		case LEFT:
			tail.setX(body.get(0).getX() - Unit.BLOCK_SIZE);
			tail.setY(body.get(0).getY());
			break;
		case RIGHT:
			tail.setX(body.get(0).getX() + Unit.BLOCK_SIZE);
			tail.setY(body.get(0).getY());
			break;
		}
		
		body.add(0, tail);
		isAlive = this.legalStep();
	}
	
	/**
	 * Megvizsgálja, hogy a paraméterként kapott u Unit benne-e van a-e a kígyóban.
	 * @param u a Unit melyet megvizsgál.
	 * @return true, ha u benne van a kígyóban 
	 */
	public boolean inSnake(Unit u)
	{
		boolean same = false;
		for (Unit un: body)
		{
			if(un != u && u.getX() == un.getX() && u.getY() == un.getY())
				same = true;
		}
		return same;
	}
	
	public void setNextDir(Direction dir)
	{
		nextdirection = dir;
	}
	
	public Direction getNextDir()
	{
		return nextdirection;
	}
	
	/**
	 * Megnézi, hogy a kígyó lépése érvényes-e. Megvizsgálja, hogy nem-e ütközik magával és a pályán belül marad-e.
	 * @return true, ha a lépés érvényes.
	 */
	public boolean legalStep()
	{
		//Nem ütközik magával
		Unit head = body.get(0);
		isAlive = !this.inSnake(head);
		
		//Pályán belül marad
		if (head.getX() < Unit.BLOCK_SIZE || head.getX() >= 31 * Unit.BLOCK_SIZE ||
				head.getY() < 3 * Unit.BLOCK_SIZE || head.getY() >= 22 * Unit.BLOCK_SIZE)
		{
			isAlive = false;
		}
		
		return isAlive;
	}
	
	/**
	 * Kirajzolja a kígyót.
	 */
	public void draw()
	{
		for (Unit un: body)
		{
			un.draw();
		}
	}

	/**
	 * Visszadja, hogy a kígyó életben van-e.
	 * @return true, ha a kígyó él
	 */
	public boolean isAlive()
	{
		return isAlive;
	}
	
	/**
	 * A kígyó testének getter függvénye.
	 * @return a kígyó testét tartalmazó ArrayList
	 */
	public ArrayList<Unit> getBody()
	{
		return body;		
	}
	
	/**
	 * A kígyó fejének a getter függvénye.
	 * @return a kígyó feje
	 */
	public Unit getHead()
	{
		return body.get(0);
	}
	
	/**
	 * A kígyó irányának getter függvénye.
	 * @return a kígyó iránya
	 */
	public Direction getDirection()
	{
		return direction;
	}
	
	/**
	 * A kígyó irányának setter függvénye
	 * @param dir ez lesz a kígyó mozgásának új iránya
	 */
	public void setDirection(Direction dir)
	{
		direction = dir;
	}

	/**
	 * A kígyó pontjának getter függvénye.
	 * @return a kígyó pontszáma
	 */
	public Integer getScore()
	{
		return score;
	}
	
	/**
	 * A kígyó pontszámát 10-zel megnöveli.
	 */
	public void addScore()
	{
		score += 10;
	}
	
	/**
	 * A kígyó pontszámát 10-el csökkenti.
	 */
	public void decScore()
	{
		score -= 10;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * A kígyóhoz hozzáadja a paraméterként kapott u Unit-ot, és ez lesz az új feje. 
	 * @param un Unit, ami kígyó feje lesz
	 */
	public void add(Unit un)
	{
		body.add(0, un);
	}
}
