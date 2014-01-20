package model.stock;

import static org.junit.Assert.*;

import java.util.Calendar;

import model.temps.DateAbsolue;

import org.junit.Before;
import org.junit.Test;

import personne.Statut;

public class MaterielStockTest {

	Calendar c1;
	Calendar c2;
	Calendar c3;
	Calendar c4;
	Calendar c5;
	Calendar c6;
	Calendar c7;
	Calendar c8;
	Calendar c9;
	Calendar c10;
	MaterielStock stock;

	@Before
	public void setUp() throws Exception {
		c1 = Calendar.getInstance();
		c1.set(2013, 1, 20);
		c2 = Calendar.getInstance();
		c2.set(2013, 3, 12);
		c3 = Calendar.getInstance();
		c3.set(2013, 3, 21);
		c4 = Calendar.getInstance();
		c4.set(2013, 4, 5);
		c5 = Calendar.getInstance();
		c5.set(2013, 5, 12);
		c6 = Calendar.getInstance();
		c6.set(2013, 9, 17);
		c7 = Calendar.getInstance();
		c7.set(2013, 9, 19);
		c8 = Calendar.getInstance();
		c8.set(2013, 9, 20);
		c9 = Calendar.getInstance();
		c9.set(2013, 11, 23);
		c10 = Calendar.getInstance();
		c10.set(2013, 11, 30);
		stock = new MaterielStock("test", 8, Statut.Etudiant);
	}

	@Test
	public void testDisponible() {

		assertTrue(stock.disponible(new DateAbsolue(c1), new DateAbsolue(c2)));
		for (int i = 0; i < 8; i++) {
			stock.inserer(new DateAbsolue(c1), new DateAbsolue(c9));
		}
		assertFalse(stock.disponible(new DateAbsolue(c1), new DateAbsolue(c2)));
	}

}
