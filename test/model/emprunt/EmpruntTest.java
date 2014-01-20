package model.emprunt;

/**
 * @author Etienne Strobbe
 */

import static org.junit.Assert.*;

import java.util.Calendar;

import model.stock.*;
import model.temps.DateAbsolue;

import org.junit.Before;
import org.junit.Test;

import personne.*;

public class EmpruntTest {

	Emprunt emp, empBis;
	MaterielStock ms;
	Calendar c1, c2;
	DateAbsolue d1, d2;

	@Before
	public void setup() {
		ms = new MaterielStock("nom", 10, Statut.Chercheur);
		c1 = Calendar.getInstance();
		c2 = Calendar.getInstance();
	}
	
	public void setDates(int jour, int duree) {
		Calendar c0 = Calendar.getInstance();

		c1 = (Calendar) c0.clone();
		c1.add(Calendar.DATE, jour);
		c2 = (Calendar) c0.clone();
		c2.add(Calendar.DATE, jour + duree);
		d1 = new DateAbsolue(c1);
		d2 = new DateAbsolue(c2);
	}

	public void creerEmprunt(Statut statut) {
		emp = new Emprunt(new Personne("prenom", "nom", statut), d1, d2, ms);
		empBis = new Emprunt(new Personne("prenom2", "nom", statut), d1, d2, ms);

	}

	@Test
	public void testValide() {
		setDates(4, 360);
		creerEmprunt(Statut.Chercheur);
		assertTrue(emp.valide());
		setDates(4, 412);
		creerEmprunt(Statut.Chercheur);
		assertFalse(emp.valide());
		setDates(80, 85);
		creerEmprunt(Statut.Chercheur);
		assertFalse(emp.valide());
		setDates(2, 7);
		creerEmprunt(Statut.Professeur);
		assertFalse(emp.valide());
		ms = new MaterielStock("nom", 10, Statut.Professeur);
		setDates(10, 8);
		creerEmprunt(Statut.Professeur);
		assertTrue(emp.valide());
		creerEmprunt(Statut.Etudiant);
		assertFalse(emp.valide());
		ms = new MaterielStock("nom", 10, Statut.Etudiant);
		setDates(5, 3);
		creerEmprunt(Statut.Etudiant);
		assertTrue(emp.valide());

	}

	@Test
	public void testEquals() {
		setDates(2,4);
		creerEmprunt(Statut.Chercheur);
		assertTrue(emp.equals(emp));
		assertFalse(emp.equals(empBis));
	}

}
