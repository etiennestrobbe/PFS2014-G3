package personne;

/**
 * 
 * @author Sebastien Petillon
 *
 */

public enum Statut {
	Etudiant(0), Professeur(1), Chercheur(2), Gestionnaire(-1);

	private int value;

	private Statut(int value) {
		this.value = value;
	}
	
	public static Statut getInstance(int value){
		switch(value){
		case 0:return Statut.Etudiant;
		case 1:return Statut.Professeur;
		case 2:return Statut.Chercheur;
		default:return Statut.Etudiant;
		}
	}

	public boolean equals(Statut s) {
		return this.value == s.value;
	}
	
	public boolean suffisant(Statut s){
		return s.value - this.value <= 0;
	}
	
}