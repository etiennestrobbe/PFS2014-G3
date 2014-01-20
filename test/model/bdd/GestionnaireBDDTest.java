package model.bdd;
/**
 * author Strobbe Etienne
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import personne.*;
import model.emprunt.Emprunt;
import model.stock.*;

import java.util.Calendar;

import model.temps.*;
public class GestionnaireBDDTest {
	
	GestionnaireStock bdd;
	Personne p1,p2,p3;
	MaterielStock ms;
	Calendar c1,c2;
	DateAbsolue d1,d2;
	
	public GestionnaireBDDTest(){}
	
	@Before
	public void setUp(){
		String chemin = "./BDD/";
		GestionBDD.setBDD(chemin);
		bdd = new GestionnaireStock(chemin);
		p1 = new Personne("Jean","Dupont",Statut.Chercheur);
		p2 = new Personne("Jacques","Durant",Statut.Chercheur);
		p3 = new Personne("Pierre","Leclercq",Statut.Chercheur);
		ms = bdd.getStock().get(0);
		c1 = Calendar.getInstance();
		c1.set(2013, 11, 20);
		c2 = Calendar.getInstance();
		c2.set(2013, 11, 22);
		d1 = new DateAbsolue(c1);
		d2 = new DateAbsolue(c2);

		

	}

	@Test
	public void testCreerCompte() {
		assertTrue(bdd.creerCompte(p1));
		assertTrue(bdd.getCompte().get(0).equals(p1));
		assertFalse(bdd.creerCompte(p1));
		assertTrue(bdd.creerCompte(p2));
		assertTrue(bdd.getCompte().get(1).equals(p2));
		assertFalse(bdd.creerCompte(p2));
		assertTrue(bdd.creerCompte(p3));
		assertTrue(bdd.getCompte().get(2).equals(p3));
		assertFalse(bdd.creerCompte(p3));
	}


	@Test
	public void testSupprimerCompte() {
		bdd.creerCompte(p1);
		bdd.creerCompte(p2);
		bdd.creerCompte(p3);
		assertTrue(bdd.supprimerCompte(p1));
		assertFalse(bdd.supprimerCompte(p1));
		assertTrue(bdd.supprimerCompte(p2));
		assertFalse(bdd.supprimerCompte(p2));
		assertTrue(bdd.supprimerCompte(p3));
		assertFalse(bdd.supprimerCompte(p3));
	}

	@Test
	public void testAjouterEmprunt() {
		bdd.creerCompte(p1);
		bdd.creerCompte(p2);
		bdd.creerCompte(p3);
		Emprunt emp1 = new Emprunt(p2,d1,d2,ms);
		assertTrue(bdd.ajouterEmprunt(emp1));
		
	}

	@Test
	public void testAnnulerEmprunt() {
		//
	}

}
