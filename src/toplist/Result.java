package toplist;

import java.io.Serializable;

/**
 * <h1>Result</h1>
 * A j�t�kosok eredm�nyeit tartalmaz� oszt�ly.
 * <p>
 * A j�t�kosok eredm�nyeit t�rolja, egy name �s egy score attrib�tumban.
 * Megval�s�tja a Serializable interface-t, hogy az eredm�nyeket f�jlba lehessen menteni.
 * 
 * @author Knoll Zsolt
 */
public class Result implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer score;
	
	/**
	 * Az oszt�ly konstruktora.
	 * @param n a j�t�kos neve
	 * @param s a j�t�kos pontsz�ma
	 */
	public Result(String n, int s)
	{
		this.name = new String(n);
		this.score = new Integer(s);
	}
	
	/**
	 * A n�v getter f�ggv�nye.
	 * @return a j�t�kos neve
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * A n�v setter f�ggv�nye.
	 * @param n a j�t�kos �j neve
	 */
	public void setName(String n)
	{
		name = n;
	}
	
	/**
	 * A pontsz�m getter f�ggv�nye.
	 * @return a j�t�kos pontsz�ma
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * A pontsz�m setter f�gg�v�nye.
	 * @param i az �j pontsz�m
	 */
	public void setScore(Integer i)
	{
		score = i;
	}
}
