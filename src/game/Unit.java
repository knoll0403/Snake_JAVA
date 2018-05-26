package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
* <h1>Unit</h1>
* A program alapegys�ge. Enn�l kisebb objektumot nem rajzol ki.
* <p>
* A Unit oszt�ly egy x,y koordin�ta p�rt t�rol, amelyet a gc oszt�lyon c sz�nnel ki tud rajzolni. 
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
	 * Az oszt�ly konstruktora.
	 * <p>
	 * A param�ter�l kapott �rt�kekkel l�trehoz egy p�ld�nyt az oszt�lyb�l. 
	 *
	 * @param  g a GraphicsContext oszt�ly egy p�ld�nya, ahova a Unit kirajzol�sra ker�l
	 * @param  a a pont balfels� sark�nak x koordin�t�ja
	 * @param  b a pont balfels� sark�nak y koordin�t�ja
	 * @param  d a pont sz�ne
	 */
	public Unit(GraphicsContext g, int a, int b, Color d)
	{
		gc = g;
		x = a;
		y = b;
		c = d;
	}
	
	/**
	 * Az x param�ter getter f�ggv�nye.
	 * @return Unit p�ld�ny x param�ter�nek �rt�ke.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Az y param�ter getter f�ggv�nye.
	 * @return Unit p�ld�ny y param�ter�nek �rt�ke.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Az x param�ter setter f�ggv�nye.
	 * T�rli a pontot a kijelz�r�l �s az x param�ter �rt�k�t �t�ll�tja.
	 * @param a az x v�ltoz� �rt�k�t a-ra �ll�tja.
	 */
	public void setX(int a)
	{
		this.delete();
		
		x = a;
	}

	/**
	 * Az y param�ter setter f�ggv�nye.
	 * T�rli a pontot a kijelz�r�l �s az y param�ter �rt�k�t �t�ll�tja.
	 * @param a az y v�ltoz� �rt�k�t a-ra �ll�tja.
	 */
	public void setY(int a)
	{
		this.delete();
		
		y = a;
	}
	
	/**
	 * Kirajzolja a Unit-ot a k�perny�re.
	 */
	public void draw()
	{
		gc.setFill(c);
		gc.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}
	
	/**
	 * T�rli a Unit-ot a k�perny�r�l.
	 */
	public void delete()
	{
		gc.setFill(Color.WHITE);
		gc.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
	}
}