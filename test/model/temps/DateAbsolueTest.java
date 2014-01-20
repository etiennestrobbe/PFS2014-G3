package model.temps;

/**
 * @author Sebastien Petillon
 */

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class DateAbsolueTest {

	DateAbsolue d1;
	DateAbsolue d2;
	DateAbsolue d3;
	DateAbsolue d4;
	Calendar c1;
	Calendar c2;
	
	@Before
	public void setUp() throws Exception {
		c1 = Calendar.getInstance();
		c1.set(2013, 11, 22);
		c2 = Calendar.getInstance();
		c2.set(2013, 11, 20);
		d1 = new DateAbsolue(c1);
		d2 = new DateAbsolue(c2);
		d3 = new DateAbsolue(Infini.negatif);
		d4 = new DateAbsolue(Infini.positif);
	}
	
	@Test
	public void testEquals() {
		assertTrue(d1.equals(d1));
		assertTrue(d2.equals(d2));
		assertTrue(d3.equals(d3));
		assertTrue(d4.equals(d4));
		assertFalse(d1.equals(d2));
		assertFalse(d1.equals(d3));
		assertFalse(d1.equals(d4));
		assertFalse(d2.equals(d3));
		assertFalse(d2.equals(d4));
		assertFalse(d3.equals(d4));
	}

	@Test
	public void testClone() {
		DateAbsolue d1b = d1.clone();
		DateAbsolue d2b = d2.clone();
		DateAbsolue d3b = d3.clone();
		DateAbsolue d4b = d4.clone();
		
		assertTrue(d1b.equals(d1));
		assertTrue(d2b.equals(d2));
		assertTrue(d3b.equals(d3));
		assertTrue(d4b.equals(d4));
		assertFalse(d1b == d1);
		assertFalse(d2b == d2);
		assertFalse(d3b == d3);
		assertFalse(d4b == d4);
		
	}

	@Test
	public void testGet() {
		assertEquals(c1, d1.get());
		assertEquals(c2, d2.get());
		assertEquals(Infini.negatif, d3.get());
		assertEquals(Infini.positif, d4.get());
	}

	@Test
	public void testAvant() {

		assertFalse(d1.avant(d1));
		assertFalse(d1.avant(d2));
		assertFalse(d1.avant(d3));
		assertTrue(d1.avant(d4));

		assertTrue(d2.avant(d1));
		assertFalse(d2.avant(d2));
		assertFalse(d2.avant(d3));
		assertTrue(d2.avant(d4));
		
		assertTrue(d3.avant(d1));
		assertTrue(d3.avant(d2));
		assertFalse(d3.avant(d3));
		assertTrue(d3.avant(d4));

		assertTrue(d4.avant(d1));
		assertTrue(d4.avant(d2));
		assertTrue(d4.avant(d3));
		assertFalse(d4.avant(d4));

	}

	@Test
	public void testApres() {
		
		assertFalse(d1.apres(d1));
		assertTrue(d1.apres(d2));
		assertTrue(d1.apres(d3));
		assertFalse(d1.apres(d4));

		assertFalse(d2.apres(d1));
		assertFalse(d2.apres(d2));
		assertTrue(d2.apres(d3));
		assertFalse(d2.apres(d4));
		
		assertFalse(d3.apres(d1));
		assertFalse(d3.apres(d2));
		assertFalse(d3.apres(d3));
		assertFalse(d3.apres(d4));

		assertTrue(d4.apres(d1));
		assertTrue(d4.apres(d2));
		assertTrue(d4.apres(d3));
		assertFalse(d4.apres(d4));
	}

}
