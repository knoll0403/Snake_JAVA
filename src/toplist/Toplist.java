package toplist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <h1>Toplist</h1>
 * A toplista osztálya
 * <p>
 * Egy tömben eltárolja az 5 legnagyobb pontszámot elért játékos nevét és eredményét.
 * 
 * @author Knoll Zsolt
 */
public class Toplist
{
	private Result[] list = new Result[5];
	
	/**
	 * Az osztály konstruktora.
	 * A toplist.dat fájlból beolvassa az eddigi toplistát, ha létezik, különben létrehoz egy új "none"-0 elemeket tartalmazó tömböt.
	 */
	public Toplist()
	{
		File f = new File("toplist.dat");
		if(f.exists())
		{
			try
			{
				FileInputStream fin = new FileInputStream(f);
				ObjectInputStream in = new ObjectInputStream(fin);
				for (int i = 0; i < 5; i++)
					list[i] = (Result) in.readObject();
				in.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			for(int i = 0; i < 5; i++)
				list[i] = new Result("none", 0);
		}
	}
	
	/**
	 * A legkisebb pontszámú eredmény getter függvénye.
	 * @return a legkisebb pontszámú eredmény
	 */
	public Result getLowest()
	{
		int min = list[0].getScore(),
			i = 0;
		
		for (int j = 1; j < 5; j++)
			if(list[j].getScore() < min)
			{
				min = list[j].getScore();
				i = j;
			}
		
		return list[i];
	}
	
	/**
	 * A toplistában szereplõ legkisebb pontszám getterfüggvénye.
	 * @return a legkisebb pontszám
	 */
	public int lowestScore()
	{
		return getLowest().getScore();
	}

	/**
	 * Hozzáaadja a toplistához a paraméterül kapott eredményt.
	 * A legkisebb eredményt felülírja az új eredménnyel.
	 * @param r az új eredmény
	 */
	public void add(Result r)
	{
		Result r2 = getLowest();
		r2.setName(r.getName());
		r2.setScore(r.getScore());
	}
	
	/**
	 * Az i-edik eredmény getterfüggvénye, túlindexelésnél NullPointException-t dob
	 * @param i 0-4 közötti egész szám, ahányadik eredményre kíváncsiak vagyunk
	 * @return az i-edik eredmény
	 */
	public Result get(int i)
	{
		if (i >= 0 && i < 5)
		{
			return list[i];
		}
		throw new NullPointerException();
	}
	
	/**
	 * A toplist.dat fájlba elmenti a toplistát.
	 */
	public void save()
	{
		try
		{
			FileOutputStream fout = new FileOutputStream("toplist.dat");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			for (Result r: list)
			{
				out.writeObject(r);
			}
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
