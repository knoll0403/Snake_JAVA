package game;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * <h1>Poison</h1>
 * A Poison osztály megmérgezi a kígyót, ha a kígyó megeszik egy mérget a játékos pontszáma csökken.
 * Zöld színû, és 50 ciklus után autómatikusan eltûnik a képernyõrõl.
 * 
 * @author Knoll Zsolt
 *
 */
public class Poison
{
	private Unit body;
	private static final Color c = Color.GREEN;
	int life = 50;
	
	/**
	 * Az osztály konstruktora
	 * <p>
	 * A paraméterül kapott GraphicsContext osztályra létrehoz egy zöld színû Unit-ot véletlen pozícióba.
	 * @param gc a GraphicsContext osztály egy példánya, ahova a Poison kirajzolásra kerül
	 */
	public Poison(GraphicsContext gc)
	{
		Random rand = new Random();

		int x = rand.nextInt(30) * Unit.BLOCK_SIZE + 20,
			y = rand.nextInt(19) * Unit.BLOCK_SIZE + 60;
		
		body = new Unit(gc, x, y, c);
	}
	
	/**
	 * Kirajzolja a Poison-t a képernyõre.
	 */
	public void draw()
	{
		body.draw();
	}
	
	/**
	 * Visszaadja az osztályban található Unit-ot.
	 * @return Az osztályban lévõ Unit
	 */
	public Unit getUnit()
	{
		return body;
	}
	
	/**
	 * Törli a Poison-t a képernyõrõl.
	 */
	public void delete()
	{
		body.delete();
	}
	
	/**
	 * A méreg életét csökkenti.
	 */
	public void decLife()
	{
		life--;
	}
	
	/**
	 * Megnézi, hogy van-e még élete a méregnek.
	 * @return true, ha a méreg élete 0.
	 */
	public boolean noLife()
	{
		return life == 0;
	}
}