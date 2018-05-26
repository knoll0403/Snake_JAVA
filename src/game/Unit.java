package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
* <h1>Unit</h1>
* A program alapegysége. Ennél kisebb objektumot nem rajzol ki.
* <p>
* A Unit osztály egy x,y koordináta párt tárol, amelyet a gc osztályon c színnel ki tud rajzolni. 
*
* @author  Knoll Zsolt
*/
public class Unit
{
	public static final int BLOCK_SIZE = 20;
	
	private int x, y;
	private Color c;
	private GraphicsContext gc;
	
	
	/**
	 * Az osztály konstruktora.
	 * <p>
	 * A paraméterül kapott értékekkel létrehoz egy példányt az osztályból. 
	 *
	 * @param  g a GraphicsContext osztály egy példánya, ahova a Unit kirajzolásra kerül
	 * @param  a a pont balfelsõ sarkának x koordinátája
	 * @param  b a pont balfelsõ sarkának y koordinátája
	 * @param  d a pont színe
	 */
	public Unit(GraphicsContext g, int a, int b, Color d)
	{
		gc = g;
		x = a;
		y = b;
		c = d;
	}
	
	/**
	 * Az x paraméter getter függvénye.
	 * @return Unit példány x paraméterének értéke.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Az y paraméter getter függvénye.
	 * @return Unit példány y paraméterének értéke.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Az x paraméter setter függvénye.
	 * Törli a pontot a kijelzõrõl és az x paraméter értékét átállítja.
	 * @param a az x változó értékét a-ra állítja.
	 */
	public void setX(int a)
	{
		this.delete();
		
		x = a;
	}

	/**
	 * Az y paraméter setter függvénye.
	 * Törli a pontot a kijelzõrõl és az y paraméter értékét átállítja.
	 * @param a az y változó értékét a-ra állítja.
	 */
	public void setY(int a)
	{
		this.delete();
		
		y = a;
	}
	
	/**
	 * Kirajzolja a Unit-ot a képernyõre.
	 */
	public void draw()
	{
		gc.setFill(c);
		gc.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}
	
	/**
	 * Törli a Unit-ot a képernyõrõl.
	 */
	public void delete()
	{
		gc.setFill(Color.WHITE);
		gc.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}
}