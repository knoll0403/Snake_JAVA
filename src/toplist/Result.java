package toplist;

import java.io.Serializable;

/**
 * <h1>Result</h1>
 * A játékosok eredményeit tartalmazó osztály.
 * <p>
 * A játékosok eredményeit tárolja, egy name és egy score attribútumban.
 * Megvalósítja a Serializable interface-t, hogy az eredményeket fájlba lehessen menteni.
 * 
 * @author Knoll Zsolt
 */
public class Result implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer score;
	
	/**
	 * Az osztály konstruktora.
	 * @param n a játékos neve
	 * @param s a játékos pontszáma
	 */
	public Result(String n, int s)
	{
		this.name = new String(n);
		this.score = new Integer(s);
	}
	
	/**
	 * A név getter függvénye.
	 * @return a játékos neve
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * A név setter függvénye.
	 * @param n a játékos új neve
	 */
	public void setName(String n)
	{
		name = n;
	}
	
	/**
	 * A pontszám getter függvénye.
	 * @return a játékos pontszáma
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * A pontszám setter függévénye.
	 * @param i az új pontszám
	 */
	public void setScore(Integer i)
	{
		score = i;
	}
}
