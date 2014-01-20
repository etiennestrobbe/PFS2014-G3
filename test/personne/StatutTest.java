package personne;

/**
 * @autor Etienne Strobbe
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class StatutTest {
	
	Statut statut1;
	Statut statut2;
	Statut statut3;
	Statut statut4;

	
	public StatutTest(){
		statut1 = Statut.Etudiant;
		statut2 = Statut.Professeur;
		statut3 = Statut.Chercheur;
		statut4 = Statut.Gestionnaire;
		
	}

	@Test
	public void testEquals() {
		assertTrue(statut1.equals(Statut.Etudiant));
		assertTrue(statut2.equals(Statut.Professeur));
		assertTrue(statut3.equals(Statut.Chercheur));
		assertTrue(statut4.equals(Statut.Gestionnaire));
		assertFalse(statut1.equals(Statut.Professeur));
		assertFalse(statut1.equals(Statut.Chercheur));
		assertFalse(statut1.equals(Statut.Gestionnaire));
		assertFalse(statut2.equals(Statut.Etudiant));
		assertFalse(statut2.equals(Statut.Chercheur));
		assertFalse(statut2.equals(Statut.Gestionnaire));
		assertFalse(statut3.equals(Statut.Etudiant));
		assertFalse(statut3.equals(Statut.Professeur));
		assertFalse(statut3.equals(Statut.Gestionnaire));
		assertFalse(statut4.equals(Statut.Etudiant));
		assertFalse(statut4.equals(Statut.Professeur));
		assertFalse(statut4.equals(Statut.Chercheur));
	}

	@Test
	public void testSuffisant() {
		assertTrue(statut3.suffisant(Statut.Chercheur));
		assertTrue(statut3.suffisant(Statut.Professeur));
		assertTrue(statut3.suffisant(Statut.Etudiant));
		
		assertFalse(statut2.suffisant(Statut.Chercheur));
		assertTrue(statut2.suffisant(Statut.Professeur));
		assertTrue(statut2.suffisant(Statut.Etudiant));
		
		assertFalse(statut1.suffisant(Statut.Chercheur));
		assertFalse(statut1.suffisant(Statut.Professeur));
		assertTrue(statut1.suffisant(Statut.Etudiant));
		
		assertFalse(statut4.suffisant(Statut.Chercheur));
		assertFalse(statut4.suffisant(Statut.Professeur));
		assertFalse(statut4.suffisant(Statut.Etudiant));

	}

}
