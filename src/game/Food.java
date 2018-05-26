package game;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
* <h1>Food</h1>
* A Food oszt�ly az �tel a j�t�kban. A k�gy� ennek felv�tel�vel n�het �s szerezhet pontot. Sz�ne s�rga.
*
* @author  Knoll Zsolt
* 
*/
public class Food
{
	private Unit body;
	private static final Color c = Color.YELLOW;
	
	/**
	 * Az oszt�ly konstruktora
	 * <p>
	 * A param�ter�l kapott GraphicsContext oszt�lyra l�trehoz egy s�rga sz�n� Unit-ot v�letlen poz�ci�ba.
	 * @param gc a GraphicsContext oszt�ly egy p�ld�nya, ahova a Food kirajzol�sra ker�l
	 */
	public Food(GraphicsContext gc)
	{
		Random rand = new Random();

		int x = rand.nextInt(30) * Unit.BLOCK_SIZE + 20,
			y = rand.nextInt(19) * Unit.BLOCK_SIZE + 60;
		
		body = new Unit(gc, x, y, c);
	}
	
	/**
	 * Kirajzolja a Food-ot a k�perny�re.
	 */
	public void draw()
	{
		body.draw();
	}
	
	/**
	 * Visszaadja az oszt�lyban tal�lhat� Unit-ot.
	 * @return Az oszt�lyban l�v� Unit
	 */
	public Unit getUnit()
	{
		return body;
	}
	
	/**
	 * T�rli az �telt a k�perny�r�l.
	 */
	public void delete()
	{
		body.delete();
	}
}
