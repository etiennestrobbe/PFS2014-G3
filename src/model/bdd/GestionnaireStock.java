package model.bdd;
/**
 * author Petillon Sebastien & Strobbe Etienne
 */
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import model.emprunt.Emprunt;
import model.stock.MaterielStock;
import model.temps.DateAbsolue;
import personne.Personne;
import personne.Statut;

// TODO : gestion des exceptions
public class GestionnaireStock {

	private static LinkedList<MaterielStock> stocks;
	private LinkedList<Personne> comptes;
	private HashMap<Personne, LinkedList<Emprunt>> emprunts;

	public GestionnaireStock(String chemin) {
		ConfigXML.definirDossier(chemin);
		loadAll();
	}

	private void loadAll() {
		stocks = (LinkedList<MaterielStock>) ConfigXML.load("materiel");
		Object cmpt = ConfigXML.load("comptes");
		if (cmpt instanceof LinkedList) {
			comptes = (LinkedList<Personne>) cmpt;
		} else {
			comptes = new LinkedList<Personne>();
		}
		Object emp = ConfigXML.load("emprunts");
		if (emp instanceof HashMap) {
			emprunts = (HashMap<Personne, LinkedList<Emprunt>>) emp;
		} else {
			emprunts = new HashMap<Personne, LinkedList<Emprunt>>();
		}
	}

	/**
	 * Méthode qui va vérifier si le compte n'existe pas encore et l'ajoute à
	 * la liste des comptes
	 * 
	 * @param p
	 * @return true si la création à réussi et false sinon
	 */
	public boolean creerCompte(Personne p) {
		if (!(comptes.contains(p))) {
			comptes.add(p);
			ConfigXML.store(comptes, "comptes");
			return true;
		}
		return false;
	}
	
	public Statut chercherStatut(Personne pers){
		for(Personne p : comptes){
			if(pers.equals(p)){
				return p.getStatut();
			}
		}
		return null;
	}

	public LinkedList<Emprunt> getEmprunts(Personne p) {
		return emprunts.get(p);
	}

	/**
	 * Méthode qui supprime un compte (s'il existe dans la liste des comptes)
	 * Cette méthode supprimera et annulera egalement la liste des emprunt
	 * effectués avec ce compte.
	 * 
	 * @param p
	 * @return true si le compte est supprimé et false sinon
	 */
	public boolean supprimerCompte(Personne p) {
		if(comptes.contains(p)){
			if(emprunts.get(p) != null){
				for (Emprunt emp : emprunts.get(p)) {
					annulerEmprunt(emp);
				}
				emprunts.remove(p);
			}
			comptes.remove(p);
			ConfigXML.store(comptes, "comptes");
			ConfigXML.store(emprunts, "emprunts");
			return true;
		}
		return false;
	}

	public void afficherComptes() {
		int i = 0;
		for (Personne p : comptes) {
			System.out.println("id : " + i + p);
			i++;
		}
	}

	public void afficherEmprunts(Personne p) {
		int i = 0;
		if(emprunts.containsKey(p)){
			for (Emprunt emp : emprunts.get(p)) {
				System.out.println("id : " + i + p);
				i++;
			}
		}
		else{
			System.out.println("Aucun emprunt en cours...");
		}
	}
	
	public void afficherStock(){
		int i=0;
		for(MaterielStock ms : stocks){
			System.out.println("id : "+i+ms);
			i++;
		}
	}

	public LinkedList<Personne> getCompte() {
		return comptes;
	}
	
	public LinkedList<MaterielStock> getStock(){
		return stocks;
	}

	public int nombreCompte() {
		return comptes.size();
	}

	/**
	 * Méthode qui s'occupe d'ajouter un emprunt dans la BDD
	 * 
	 * @param emp
	 * @return true si l'emprunt est ajouté et false sinon
	 */
	public boolean ajouterEmprunt(Emprunt emp) {

		if (!emp.valide()) {
			return false;
		}

		if (stocks.contains(emp.getMaterielEmprunte())) {
			if (incQuantiteStock(
					stocks.get(stocks.indexOf(emp.getMaterielEmprunte())), emp)) {
				ConfigXML.store(stocks, "materiel");

				LinkedList<Emprunt> empruntsPersonne = emprunts.get(emp
						.getPersonne());
				if (empruntsPersonne == null) {
					empruntsPersonne = new LinkedList<Emprunt>();
				}
				emprunts.put(emp.getPersonne(), empruntsPersonne);
				empruntsPersonne.add(emp);
				ConfigXML.store(emprunts, "emprunts");

				return true;
			}
			return false;

		}
		return false;
	}

	/**
	 * Méthode qui annule un emprunt si celui existe
	 * 
	 * @param emp
	 * @return true si l'emprunt est annulé et false sinon
	 */
	public boolean annulerEmprunt(Emprunt emp) {

		Personne p = emp.getPersonne();
		LinkedList<Emprunt> listeEmprunts = emprunts.get(p);
		if (listeEmprunts == null) {
			return false;
		}

		for (Emprunt e : listeEmprunts) {
			if (emp.equals(e)) {
				if (stocks.contains(emp.getMaterielEmprunte())) {
					decQuantiteStock(stocks.get(stocks.indexOf(emp
							.getMaterielEmprunte())), emp);
					ConfigXML.store(stocks, "materiel");
					emprunts.remove(listeEmprunts.indexOf(emp));
					ConfigXML.store(emprunts, "emprunts");
				}
			}
		}
		return true;
	}

	/**
	 * Méthode qui incrémente la quantité d'un objet MaterielStock selon
	 * l'emprunt passé en parametre
	 * 
	 * @param ms
	 * @param emp
	 * @return true si l'incrémentation est possible et false sinon
	 */
	private boolean incQuantiteStock(MaterielStock ms, Emprunt emp) {
		if (ms.disponible(emp.getDebut(), emp.getFin())) {
			ms.inserer(emp.getDebut(), emp.getFin());
			return true;
		}
		return false;
	}

	/**
	 * Méthode qui decremente la quantité d'un objet MaterielStock selon
	 * l'emprunt passé en parametre
	 * 
	 * @param ms
	 * @param emp
	 */
	private void decQuantiteStock(MaterielStock ms, Emprunt emp) {
		ms.retirer(emp.getDebut(), emp.getFin());
	}
	
	private void majStatistique(){}

	// TMP pour tests
	public static void main(String args[]) {

		Calendar c1 = Calendar.getInstance();
		c1.set(2013, 11, 14);
		Calendar c2 = Calendar.getInstance();
		c2.set(2013, 11, 19);
		Calendar c3 = Calendar.getInstance();
		c3.set(2013, 11, 18);
		Calendar c4 = Calendar.getInstance();
		c4.set(2013, 11, 25);
		String chemin = "./BDD/";
		GestionBDD.setBDD(chemin);
		GestionnaireStock gbdd = new GestionnaireStock(chemin);
		MaterielStock ms = gbdd.stocks.get(0);
		MaterielStock ms2 = gbdd.stocks.get(1);
		Personne p1 = new Personne("Nom1", "Prenom", Statut.Professeur);
		Personne p2 = new Personne("Nom2", "Prenom", Statut.Professeur);
		Personne p3 = new Personne("Nom3", "Prenom", Statut.Professeur);

		gbdd.creerCompte(p1);
		gbdd.creerCompte(p2);
		gbdd.creerCompte(p3);

		Emprunt emp1 = new Emprunt(gbdd.comptes.get(0), new DateAbsolue(c1),
				new DateAbsolue(c2), ms2);
		Emprunt emp1_bis = new Emprunt(gbdd.comptes.get(0),
				new DateAbsolue(c3), new DateAbsolue(c4), ms2);
		Emprunt emp2 = new Emprunt(gbdd.comptes.get(0), new DateAbsolue(c3),
				new DateAbsolue(c4), ms2);

		gbdd.ajouterEmprunt(emp1);
		gbdd.ajouterEmprunt(emp1_bis);
		gbdd.ajouterEmprunt(emp2);
//		gbdd.supprimerCompte(p1);
		 gbdd.annulerEmprunt(emp2);
		 
//		gbdd.annulerEmprunt(emp2);
		LinkedList<String> lkl = new LinkedList<String>();
		lkl.add("Tartine");
		lkl.add("beurre");
		lkl.add("confiture");
		lkl.add("-1");
		GestionBDD.addMateriel(125, lkl, stocks);
		GestionBDD.addMateriel(125, lkl, stocks);

		for (MaterielStock a : gbdd.stocks) {
			System.out.println(a);
		}
		

	}
}
