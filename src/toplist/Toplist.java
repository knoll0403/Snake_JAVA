package toplist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <h1>Toplist</h1>
 * A toplista oszt�lya
 * <p>
 * Egy t�mben elt�rolja az 5 legnagyobb pontsz�mot el�rt j�t�kos nev�t �s eredm�ny�t.
 * 
 * @author Knoll Zsolt
 */
public class Toplist
{
	private Result[] list = new Result[5];
	
	/**
	 * Az oszt�ly konstruktora.
	 * A toplist.dat f�jlb�l beolvassa az eddigi toplist�t, ha l�tezik, k�l�nben l�trehoz egy �j "none"-0 elemeket tartalmaz� t�mb�t.
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
	 * A legkisebb pontsz�m� eredm�ny getter f�ggv�nye.
	 * @return a legkisebb pontsz�m� eredm�ny
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
	 * A toplist�ban szerepl� legkisebb pontsz�m getterf�ggv�nye.
	 * @return a legkisebb pontsz�m
	 */
	public int lowestScore()
	{
		return getLowest().getScore();
	}

	/**
	 * Hozz�aadja a toplist�hoz a param�ter�l kapott eredm�nyt.
	 * A legkisebb eredm�nyt fel�l�rja az �j eredm�nnyel.
	 * @param r az �j eredm�ny
	 */
	public void add(Result r)
	{
		Result r2 = getLowest();
		r2.setName(r.getName());
		r2.setScore(r.getScore());
	}
	
	/**
	 * Az i-edik eredm�ny getterf�ggv�nye, t�lindexel�sn�l NullPointException-t dob
	 * @param i 0-4 k�z�tti eg�sz sz�m, ah�nyadik eredm�nyre k�v�ncsiak vagyunk
	 * @return az i-edik eredm�ny
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
	 * A toplist.dat f�jlba elmenti a toplist�t.
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
