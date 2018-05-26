package toplist;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ToplistTest
{
	Toplist toplist;
	Result r1, r2, r3, r4, r5;
	
	@Before
	public void setUp()
	{
		toplist = new Toplist();
		r1 = new Result("Anna", 1000);
		r2 = new Result("Bea", 2000);
		r3 = new Result("Cili", 3000);
		r4 = new Result("Dóri", 4000);
		r5 = new Result("Emese", 5000);
		
		toplist.add(r1);
		toplist.add(r2);
		toplist.add(r3);
		toplist.add(r4);
		toplist.add(r5);
	}
	
	@Test
	public void getLowestTest()
	{
		boolean same = r1.getName() == toplist.getLowest().getName() &&
				r1.getScore() == toplist.getLowest().getScore();
		assertTrue(same);
	}
	
	@Test
	public void lowestScoreTest()
	{
		boolean same = r1.getScore() == toplist.lowestScore();
		assertTrue(same);
	}
	
	@Test
	public void addTest()
	{
		Result r = new Result("Me", 1001);
		toplist.add(r);
		
		boolean added = false;
		for(int i = 0; i < 5; i++)
			if(toplist.get(i).getName() == r.getName() &&
			   toplist.get(i).getScore() == r.getScore())
				added = true;
		assertTrue(added);
	}
}
