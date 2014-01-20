package control;
/**
 * author Strobbe Etienne
 */
import java.util.Calendar;
import java.util.LinkedList;

import model.stock.*;
import model.temps.DateAbsolue;

import java.util.Scanner;

import model.bdd.*;
import model.emprunt.*;
import personne.*;

public class Main {

	private static GestionnaireBDD bdd = new GestionnaireBDD("./BDD/");
	private static Scanner scan;
	private static String commande;
	private static Personne personne;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String chemin = "./BDD/";
//		CreerBDD.setBDD(chemin);
//		bdd.creerCompte(new Personne("Peter","Sander",Statut.Gestionnaire));
		scan = new Scanner(System.in);
		while (!connexion()) {
			
		}

		commande = "";

		while (!commande.equals("quitter")) {
			
			
			gestionCommande();
		}
		System.out.println("Gestion quitt�e");

	}

	private static boolean connexion() {

		String nom = "";
		String prenom = "";

		while (personne == null) {
			while (nom.equals("")) {
				System.out.println("Veuillez indiquer votre nom");
				nom = scan.nextLine();
			}
			while (prenom.equals("")) {
				System.out.println("Veuillez indiquer votre prenom");
				prenom = scan.nextLine();
			}
			personne = new Personne(prenom, nom, Statut.Etudiant);
			if (bdd.chercherStatut(personne) != null) {
				personne.setStatut(bdd.chercherStatut(personne));
				return true;
			}
			nom = "";
			prenom = "";
			personne = null;
			System.out.println("Erreur, compte inconnu");
		}
		return false;

	}

	private static void afficherMenu(boolean gestionnaire) {
		String commandesEmprunteur = "## Commandes :\n"
				+ "-ajouter un emprunt :			ajouterE\n"
				+ "-annuler un emprunt :			annulerE\n";

		String menu = ""
				+ "#################################################\n"
				+ "##                                             ##\n"
				+ "##            GESTION DE STOCK/EMPRUNT         ##\n"
				+ "##                                             ##\n"
				+ "#################################################\n" + "\n";
		String commandeGestionnaire = (gestionnaire) ? "-ajouter un nouveau compte :		ajouterC\n-supprimer un compte existant :		supprimerC\n"
				: "-supprimer le compte :			supprimerC\n";
		String quitter = "-quitter la gestion du stock:		quitter\n";
		System.out.println(menu + commandesEmprunteur + commandeGestionnaire
				+ quitter);

	}

	private static void gestionCommande() {
		if (personne.getStatut().equals(Statut.Gestionnaire)) {
			afficherMenu(true);
		} else {
			afficherMenu(false);
		}
		String pattern = "[^ ]+";

		commande = scan.next(pattern);
		if (commande.equals("ajouterE")) {
			ajouterEmprunt();
		} else if (commande.equals("annulerE")) {
			annulerEmprunt();
		} else if (commande.equals("ajouterC")) {
			ajouterCompte();
		} else if (commande.equals("supprimerC")) {
			supprimerCompte();
		} else {}
		// System.out.println("Good Bye !");
		return;
	}

	private static int choisirElement() {
		int idCompte;
		bdd.afficherComptes();
		System.out.println("Choisissez l'id associé à l'élément:");
		idCompte = scan.nextInt();
		while (idCompte > bdd.nombreCompte() - 1) {
			System.out.println("L'id est invalide, veuillez réessayer :");
			idCompte = scan.nextInt();
		}
		return idCompte;
	}
	
	private static DateAbsolue calculerDate(int nbjour){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Calendar.getInstance().getTimeInMillis()+(60*60*1000*24*(nbjour)));
		return new DateAbsolue(c);
		
	}

	private static void ajouterEmprunt() {
		// devrait fonctionner (si methode valide() de emprunt fonctionne)
		Personne emprunteur;
		if(personne.getStatut().equals(Statut.Gestionnaire)){
			int idPersonne = choisirElement();
			emprunteur = bdd.getCompte().get(idPersonne);
		}
		emprunteur = personne;
		bdd.afficherStock();
		int idMateriel = -1;
		int dateD = -1;
		int temps = -1;
		while(idMateriel < 0 || idMateriel > bdd.getStock().size()-1){
			System.out.println("Choisir l'id du materiel � emprunter : ");
			idMateriel = scan.nextInt();
		}

		while(dateD < 0){
		System.out.println("Dans combien de jours voulez vous emprunter ?");
			dateD = scan.nextInt();
		}
		while(temps < 0){
			System.out.println("DPour combien de jours voulez vous emprunter ?");
			temps = scan.nextInt();
		}
		DateAbsolue d1 = calculerDate(dateD);
		System.out.println(d1);
		DateAbsolue d2 = calculerDate(dateD+temps);
		System.out.println(d2);
		Emprunt e = new Emprunt(emprunteur,d1,d2,bdd.getStock().get(idMateriel));
		boolean a = bdd.ajouterEmprunt(e);
		if(!a){System.out.println("Cet emprunt n'est pas valide : vous n'avez pas le statut necessaire " +
				", vous avez choisi une periode qui ne vous est pas permise, ou l'objet n'est pas disponible.");}
		
		return;
	}

	private static void annulerEmprunt() {
		
		bdd.afficherComptes();
		int idPersonne=-1;
		while(!(idPersonne>=0 && idPersonne<bdd.getCompte().size())){
			System.out.println("Choisir un id de personne");
			idPersonne = scan.nextInt();
		}
		
		bdd.afficherEmprunts(bdd.getCompte().get(idPersonne));

		return;
	}

	private static void ajouterCompte() {

		String nom = "", prenom = "", statut = "";
		nom = scan.nextLine();
		while (nom.equals("")) {
			System.out.println("Veuillez taper le nom de la personne :");
			nom = scan.nextLine();
		}
		while (prenom.equals("")) {
			System.out.println("Veuillez taper le prénom de la personne :");
			prenom = scan.nextLine();
		}
		System.out
				.println("Veuillez taper le statut du compte (etudiant,professeur,chercheur ou gestionnaire) :");
		statut = scan.nextLine();
		while (!(statut.equals("etudiant") || statut.equals("professeur")
				|| statut.equals("chercheur") || statut.equals("gestionnaire"))) {
			System.out.println("statut invalide, veuillez réessayer :");
			statut = scan.nextLine();

		}
		Personne p;
		if (statut.equals("etudiant")) {
			p = new Personne(prenom, nom, Statut.Etudiant);
		} else if (statut.equals("professeur")) {
			p = new Personne(prenom, nom, Statut.Professeur);
		} else if (statut.equals("chercheur")) {
			p = new Personne(prenom, nom, Statut.Professeur);
		} else if (statut.equals("professeur")) {
			p = new Personne(prenom, nom, Statut.Chercheur);
		} else if (statut.equals("gestionnaire")) {
			p = new Personne(prenom, nom, Statut.Gestionnaire);
		} else
			return;

		if (bdd.creerCompte(p)) {
			System.out.println("Le compte a bien été ajouté");
		} else {
			System.out.println("Le compte existe déjà !");
		}

		return;
	}

	private static void supprimerCompte() {
		if (!personne.getStatut().equals(Statut.Gestionnaire)) {
			bdd.supprimerCompte(personne);
			commande = "quitter";
			return;
		}

		int idASupprimer = choisirElement();
		bdd.supprimerCompte(bdd.getCompte().get(idASupprimer));
		System.out.println("Le compte à bien été supprimé");

		return;
	}

}
