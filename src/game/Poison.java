package game;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * <h1>Poison</h1>
 * A Poison oszt�ly megm�rgezi a k�gy�t, ha a k�gy� megeszik egy m�rget a j�t�kos pontsz�ma cs�kken.
 * Z�ld sz�n�, �s 50 ciklus ut�n aut�matikusan elt�nik a k�perny�r�l.
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
	 * Az oszt�ly konstruktora
	 * <p>
	 * A param�ter�l kapott GraphicsContext oszt�lyra l�trehoz egy z�ld sz�n� Unit-ot v�letlen poz�ci�ba.
	 * @param gc a GraphicsContext oszt�ly egy p�ld�nya, ahova a Poison kirajzol�sra ker�l
	 */
	public Poison(GraphicsContext gc)
	{
		Random rand = new Random();

		int x = rand.nextInt(30) * Unit.BLOCK_SIZE + 20,
			y = rand.nextInt(19) * Unit.BLOCK_SIZE + 60;
		
		body = new Unit(gc, x, y, c);
	}
	
	/**
	 * Kirajzolja a Poison-t a k�perny�re.
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
	 * T�rli a Poison-t a k�perny�r�l.
	 */
	public void delete()
	{
		body.delete();
	}
	
	/**
	 * A m�reg �let�t cs�kkenti.
	 */
	public void decLife()
	{
		life--;
	}
	
	/**
	 * Megn�zi, hogy van-e m�g �lete a m�regnek.
	 * @return true, ha a m�reg �lete 0.
	 */
	public boolean noLife()
	{
		return life == 0;
	}
}