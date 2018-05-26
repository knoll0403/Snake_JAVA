package game;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * <h1>Snake</h1>
 * A kigy�t reprezent�l� oszt�ly.
 * <p>
 * ide m�g kell �rni valamit
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
	 * Az oszt�ly konstruktora.
	 * @param gc GraphicsContext oszt�ly egy p�ld�nya, erre helyezi el a k�gy� test�t.
	 * @param startx A k�gy� startpoz�ci�j�nak x koordin�t�ja.
	 * @param starty A k�gy� startpoz�ci�j�nak y koordin�t�ja.
	 * @param c A k�gy� sz�ne.
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
	 * A k�gy�t mozgatja.
	 * A k�gy� fej�t hat�rozza meg, a k�gy� mozg�s�nak ir�ny�b�l.
	 * @return true minden esetben, mert a k�gy� mozgott
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
	 * Megvizsg�lja, hogy a param�terk�nt kapott u Unit benne-e van a-e a k�gy�ban.
	 * @param u a Unit melyet megvizsg�l.
	 * @return true, ha u benne van a k�gy�ban 
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
	 * Megn�zi, hogy a k�gy� l�p�se �rv�nyes-e. Megvizsg�lja, hogy nem-e �tk�zik mag�val �s a p�ly�n bel�l marad-e.
	 * @return true, ha a l�p�s �rv�nyes.
	 */
	public boolean legalStep()
	{
		//Nem �tk�zik mag�val
		Unit head = body.get(0);
		isAlive = !this.inSnake(head);
		
		//P�ly�n bel�l marad
		if (head.getX() < Unit.BLOCK_SIZE || head.getX() >= 31 * Unit.BLOCK_SIZE ||
				head.getY() < 3 * Unit.BLOCK_SIZE || head.getY() >= 22 * Unit.BLOCK_SIZE)
		{
			isAlive = false;
		}
		
		return isAlive;
	}
	
	/**
	 * Kirajzolja a k�gy�t.
	 */
	public void draw()
	{
		for (Unit un: body)
		{
			un.draw();
		}
	}

	/**
	 * Visszadja, hogy a k�gy� �letben van-e.
	 * @return true, ha a k�gy� �l
	 */
	public boolean isAlive()
	{
		return isAlive;
	}
	
	/**
	 * A k�gy� test�nek getter f�ggv�nye.
	 * @return a k�gy� test�t tartalmaz� ArrayList
	 */
	public ArrayList<Unit> getBody()
	{
		return body;		
	}
	
	/**
	 * A k�gy� fej�nek a getter f�ggv�nye.
	 * @return a k�gy� feje
	 */
	public Unit getHead()
	{
		return body.get(0);
	}
	
	/**
	 * A k�gy� ir�ny�nak getter f�ggv�nye.
	 * @return a k�gy� ir�nya
	 */
	public Direction getDirection()
	{
		return direction;
	}
	
	/**
	 * A k�gy� ir�ny�nak setter f�ggv�nye
	 * @param dir ez lesz a k�gy� mozg�s�nak �j ir�nya
	 */
	public void setDirection(Direction dir)
	{
		direction = dir;
	}

	/**
	 * A k�gy� pontj�nak getter f�ggv�nye.
	 * @return a k�gy� pontsz�ma
	 */
	public Integer getScore()
	{
		return score;
	}
	
	/**
	 * A k�gy� pontsz�m�t 10-zel megn�veli.
	 */
	public void addScore()
	{
		score += 10;
	}
	
	/**
	 * A k�gy� pontsz�m�t 10-el cs�kkenti.
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
	 * A k�gy�hoz hozz�adja a param�terk�nt kapott u Unit-ot, �s ez lesz az �j feje. 
	 * @param un Unit, ami k�gy� feje lesz
	 */
	public void add(Unit un)
	{
		body.add(0, un);
	}
}
