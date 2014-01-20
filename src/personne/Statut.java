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
	
	public Statut getInstance(int value){
		return new Statut(value);
	}

	public boolean equals(Statut s) {
		return this.value == s.value;
	}
	
	public boolean suffisant(Statut s){
		return s.value - this.value <= 0;
	}
	
}