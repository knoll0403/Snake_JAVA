package game;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
* <h1>Food</h1>
* A Food osztály az étel a játékban. A kígyó ennek felvételével nõhet és szerezhet pontot. Színe sárga.
*
* @author  Knoll Zsolt
* 
*/
public class Food
{
	private Unit body;
	private static final Color c = Color.YELLOW;
	
	/**
	 * Az osztály konstruktora
	 * <p>
	 * A paraméterül kapott GraphicsContext osztályra létrehoz egy sárga színû Unit-ot véletlen pozícióba.
	 * @param gc a GraphicsContext osztály egy példánya, ahova a Food kirajzolásra kerül
	 */
	public Food(GraphicsContext gc)
	{
		Random rand = new Random();

		int x = rand.nextInt(30) * Unit.BLOCK_SIZE + 20,
			y = rand.nextInt(19) * Unit.BLOCK_SIZE + 60;
		
		body = new Unit(gc, x, y, c);
	}
	
	/**
	 * Kirajzolja a Food-ot a képernyõre.
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
	 * Törli az ételt a képernyõrõl.
	 */
	public void delete()
	{
		body.delete();
	}
}
