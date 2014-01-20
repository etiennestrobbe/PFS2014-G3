package config;

/**
 * @author Sebastien Petillon
 */

import personne.Statut;

public class Configurations {

	public static int AVANCE_EMPRUNTS_ETUDIANT = 7;
	public static int TEMPS_EMPRUNTS_ETUDIANT = 7;
	public static int AVANCE_EMPRUNTS_PROFESSEUR = 30;
	public static int TEMPS_EMPRUNTS_PROFESSEUR = 30;
	public static int AVANCE_EMPRUNTS_CHERCHEUR = 60;
	public static int TEMPS_EMPRUNTS_CHERCHEUR = 365;
	
	public static int avanceEmprunt(Statut s){
		switch(s){
		case Etudiant : return AVANCE_EMPRUNTS_ETUDIANT;
		case Professeur : return AVANCE_EMPRUNTS_PROFESSEUR;
		case Chercheur : return AVANCE_EMPRUNTS_CHERCHEUR;
		}
		return 0;
	}
	
	public static int tempsEmprunt(Statut s){
		switch(s){
		case Etudiant : return TEMPS_EMPRUNTS_ETUDIANT;
		case Professeur : return TEMPS_EMPRUNTS_PROFESSEUR;
		case Chercheur : return TEMPS_EMPRUNTS_CHERCHEUR;
		}
		return 0;
	}
}
