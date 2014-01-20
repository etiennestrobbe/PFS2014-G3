package model.temps;

/**
 * @author Etienne Strobbe & Sebastien Petillon 
 */

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class PeriodeAbsolueTest {

	PeriodeAbsolue p1;
	PeriodeAbsolue p2;
	PeriodeAbsolue p3;
	Calendar c1;
	Calendar c2;
	Calendar c3;
	Calendar c4;
	Calendar c5;
	Calendar c6;
	Calendar c7;
	Calendar c8;
	
	@Before
	public void setUp() throws Exception {
		
		c1 = Calendar.getInstance();
		c1.set(2013, 11, 21);
		c2 = Calendar.getInstance();
		c2.set(2013, 11, 23);
		c3 = Calendar.getInstance();
		c3.set(2013, 11, 20);
		c4 = Calendar.getInstance();
		c4.set(2013, 11, 28);
		c5 = Calendar.getInstance();
		c5.set(2013, 10, 11);
		c6 = Calendar.getInstance();
		c6.set(2013, 12, 13);
		c7 = Calendar.getInstance();
		c7.set(2013, 11, 24);
		c8 = Calendar.getInstance();
		c8.set(2013, 11, 27);
		
		p1 = new PeriodeAbsolue();
		p2 = new PeriodeAbsolue(new DateAbsolue(c1), new DateAbsolue(c2));
		p3 = new PeriodeAbsolue(new DateAbsolue(c3), new DateAbsolue(c4), 2);
	}

	@Test
	public void testClone() {
		PeriodeAbsolue p1b = p1.clone();
		PeriodeAbsolue p2b = p2.clone();
		PeriodeAbsolue p3b = p3.clone();

		assertTrue(p1.equals(p1b));
		assertFalse(p1 == p1b);
		assertTrue(p2.equals(p2b));
		assertFalse(p2 == p2b);
		assertTrue(p3.equals(p3b));
		assertFalse(p3 == p3b);
	}
	
	@Test
	public void testEquals() {
		assertTrue(p1.equals(p1));
		assertTrue(p2.equals(p2));
		assertTrue(p3.equals(p3));
		assertFalse(p1.equals(p2));
		assertFalse(p1.equals(p3));
		assertFalse(p2.equals(p3));

	}

	@Test
	public void testPresenteEntre() {
		assertTrue(p1.presenteEntre(new DateAbsolue(c5), new DateAbsolue(c6)));
		assertTrue(p2.presenteEntre(new DateAbsolue(c5), new DateAbsolue(c6)));
		assertTrue(p3.presenteEntre(new DateAbsolue(c5), new DateAbsolue(c6)));
		assertTrue(p2.presenteEntre(new DateAbsolue(Infini.negatif), new DateAbsolue(c6)));
		assertTrue(p3.presenteEntre(new DateAbsolue(Infini.negatif), new DateAbsolue(c6)));
		assertTrue(p2.presenteEntre(new DateAbsolue(c5), new DateAbsolue(Infini.positif)));
		assertTrue(p3.presenteEntre(new DateAbsolue(c5), new DateAbsolue(Infini.positif)));
		assertTrue(p3.presenteEntre(new DateAbsolue(c7), new DateAbsolue(c8)));
		
		assertFalse(p2.presenteEntre(new DateAbsolue(c7), new DateAbsolue(c8)));
		assertFalse(p3.presenteEntre(new DateAbsolue(Infini.negatif), new DateAbsolue(c5)));
	}

	@Test
	public void testAjouterFin() {
		PeriodeAbsolue actualFirst = p3.clone(); // p3 -> du 20/11/2013 au 28/11/2013 avec quantite 2
		Calendar tmp = (Calendar) c1.clone(); // c1 -> 21/11/2013
		tmp.add(Calendar.DATE, -1);	
		
		PeriodeAbsolue actualSecond = actualFirst.ajouterFin(new DateAbsolue(c1));
		PeriodeAbsolue expectedSecond = new PeriodeAbsolue(p3.getDebut(),new DateAbsolue(tmp),p3.getQuantite());
		PeriodeAbsolue expectedFirst = new PeriodeAbsolue(new DateAbsolue(c1),p3.getFin(),p3.getQuantite());
		assertTrue(expectedFirst.equals(actualFirst));
		assertTrue(expectedSecond.equals(actualSecond));
		
		
	}

	@Test
	public void testAjouterDebut() {
		PeriodeAbsolue actualFirst = p3.clone(); // p3 -> du 20/11/2013 au 28/11/2013 avec quantite 2
		Calendar tmp = (Calendar) c1.clone(); // c1 -> 21/11/2013
		tmp.add(Calendar.DATE, +1);	
		
		PeriodeAbsolue actualSecond = actualFirst.ajouterDebut(new DateAbsolue(c1));
		PeriodeAbsolue expectedFirst = new PeriodeAbsolue(p3.getDebut(),new DateAbsolue(c1),p3.getQuantite());
		PeriodeAbsolue expectedSecond = new PeriodeAbsolue(new DateAbsolue(tmp),p3.getFin(),p3.getQuantite());
		
		assertTrue(actualFirst.equals(expectedFirst));
		assertTrue(actualSecond.equals(expectedSecond));
	}

}
