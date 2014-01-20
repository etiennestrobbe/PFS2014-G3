package personne;

/**
 * Enum designant le statut d'une personne
 * @author Sebastien Petillon
 *
 */

public enum Statut {
	Etudiant(0), Professeur(1), Chercheur(2), Gestionnaire(-1);

	private int value;

	private Statut(int value) {
		this.value = value;
	}
	
	/**
	 * Permet de recuperer un enum avec un parametre donne
	 * @param value 
	 * @return 
	 */
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
	
	/**
	 * Methode permettant de savoir si un statut est suffisant selon le statut requis 
	 * @param s le statut requis
	 * @return true ou false
	 */
	public boolean suffisant(Statut s){
		return s.value - this.value <= 0;
	}
	
}