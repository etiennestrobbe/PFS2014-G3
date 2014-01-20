package model.stock;
/**Classe designant un element du stock ayant un nom et des proprietes definies
 * author Petillon Sebastien & Etienne STROBBE
 */
import java.util.LinkedList;

import model.temps.DateAbsolue;
import model.temps.PeriodeAbsolue;
import personne.Statut;

public class MaterielStock {

	private Statut statutNecessaire;
	private String name;
	private LinkedList<PeriodeAbsolue> quantitePeriodes;
	private LinkedList<String> proprietes;
	private int disponibilite;
	private int nbFoisEmp;
	private int nbFoisPanne;

	public MaterielStock(String name, int disponibilite, Statut statut) {
		this.statutNecessaire = statut;
		this.name = name;
		this.disponibilite = disponibilite;
		quantitePeriodes = new LinkedList<PeriodeAbsolue>();
		proprietes = new LinkedList<String>();
		quantitePeriodes.add(new PeriodeAbsolue());
		nbFoisEmp = 0;
		nbFoisPanne = 0;
	}

	public void addPropriete(String propriete) {
		proprietes.add(propriete);
	}
	

	public void setStatutNecessaire(Statut statutNecessaire) {
		this.statutNecessaire = statutNecessaire;
	}

	public Statut getStatutNecessaire() {
		return statutNecessaire;
	}

	@Override
	public String toString() {

		// Creer la chaine de caractere a retourner
		String res = affichage();

		// Y renseigner la disponibilite
		res += "|\nQuantité maximale : " + disponibilite
				+ "\nRéservations : \n";

		// Enumerer toutes les periodes et disponibilit�s associees
		for (PeriodeAbsolue p : quantitePeriodes) {
			res += "\t" + p.toString() + "\n\n";
		}
		return res;
	}

	public String affichage() {

		// Creer la chaine de caractere a retourner
		String res = "|" + name;

		// Ajouter les proprietes
		for (String p : proprietes) {
			res += p + " - ";
		}

		// Informer du statut necessaire
		res += "Accessible au maximum au statut : " + getStatutNecessaire();
		return res;
	}

	/**
	 * Methode permettant de savoir si une instance de l'objet est disponible
	 * sur la periode formee par les deux dates donnees.
	 * 
	 * @param debut
	 *            Date de debut de la recherche
	 * @param fin
	 *            Date de fin de la recherche
	 * @return true si il reste au moins une disponibilite pour chaque periode
	 *         concernee false sinon
	 */
	public boolean disponible(DateAbsolue debut, DateAbsolue fin) {

		// Parcours des periodes
		for (PeriodeAbsolue pa : quantitePeriodes) {

			// Test de la presence et de la superiorit� de la quantite aux
			// emprunts
			if (pa.presenteEntre(debut, fin)
					&& pa.getQuantite() >= disponibilite) {
				return false;
			}
		}
		return true;
	}

	public int getDisponible(){
		return this.disponibilite;
	}
	/**
	 * Methode permettant de recuperer la liste des periodes inclues entre les
	 * dates donnees ou contenant une de ces dates
	 * 
	 * @param debut
	 *            Date de debut de la recherche
	 * @param fin
	 *            Date de fin de la recherche
	 * @return liste des periodes concernees
	 */
	public LinkedList<PeriodeAbsolue> periodesConcernees(DateAbsolue debut,
			DateAbsolue fin) {

		// Creation de la liste de retour
		LinkedList<PeriodeAbsolue> res = new LinkedList<PeriodeAbsolue>();

		// Parcours des periode
		for (PeriodeAbsolue pa : quantitePeriodes) {

			// Ajout seulement si concernee
			if (pa.presenteEntre(debut, fin)) {
				res.add(pa);
			}
		}
		return res;
	}

	/**
	 * Methode permettant de retirer une reservation entre deux dates donnees.
	 * Attention : aucune verification n'est effectuee, chaque periode concernee
	 * se verra retirer une reservation, les dates ne sont pas verifiees comme
	 * etant valides et/ou correspondant a un emprunt.
	 * 
	 * @param debut
	 *            Date de debut de la recherche
	 * @param fin
	 *            Date de fin de la recherche
	 */
	public void retirer(DateAbsolue debut, DateAbsolue fin) {

		// Creation de la liste des periodes a modifier
		LinkedList<PeriodeAbsolue> aTraiter = periodesConcernees(debut, fin);

		// Traiter chaque periode
		for (PeriodeAbsolue p : aTraiter) {
			p.decQuantite();
		}

		// Fusionner les periodes de meme quantites
		reorganiser();
	}

	/**
	 * Methode permettant de fusionner les periodes comportant les memes valeurs
	 * de reservation. Utile pour la methode retirer.
	 */
	private void reorganiser() {

		PeriodeAbsolue courante;
		PeriodeAbsolue precedente;

		// Parcours de toutes les periodes
		for (int i = 1; i < quantitePeriodes.size(); i++) {

			// Recuperation de deux periodes consecutives
			courante = quantitePeriodes.get(i);
			precedente = quantitePeriodes.get(i - 1);

			// Si les valeurs de reservation sont les memes
			if (courante.getQuantite() == precedente.getQuantite()) {

				// Fusion et supression
				precedente.setFin(courante.getFin());
				quantitePeriodes.remove(courante);
				// Retour a la premiere des deux
				i--;
			}
		}
	}

	/**
	 * Methode permettant d'ajouter une reservation dans la liste,
	 * eventuellement en redecoupant les periodes.
	 * 
	 * @param debut
	 *            Date de debut de la reservation
	 * @param fin
	 *            Date de fin de la reservation
	 */
	public void inserer(DateAbsolue debut, DateAbsolue fin) {

		// Creation de la liste des periodes a modifier
		LinkedList<PeriodeAbsolue> aTraiter = periodesConcernees(debut, fin);

		// Cas special d'une seule periode exactement semblable
		if (aTraiter.size() == 1 && aTraiter.get(0).getDebut().equals(debut)
				&& aTraiter.get(0).getFin().equals(fin)) {
			aTraiter.get(0).incQuantite();
			return;
		}
		
		// Recuperation des index de la premiere et la derniere periode a
		// traiter
		// (extremites -> cas particuliers)
		System.out.println("EMPRUNT : " + this);
		int indexPremier = quantitePeriodes.indexOf(aTraiter.getFirst());
		int indexDernier = quantitePeriodes.indexOf(aTraiter.getLast());

		// Creation des nouvelles periodes et modification des extremites
		PeriodeAbsolue insertionDebut = aTraiter.getFirst().ajouterFin(debut);
		PeriodeAbsolue insertionFin = aTraiter.getLast().ajouterDebut(fin);

		// Affectation de la nouvelle liste de periodes
		quantitePeriodes = insertTraitement(aTraiter, indexPremier,
				indexDernier, insertionDebut, insertionFin);
	}

	/**
	 * Methode intermediaire permettant une partie du traitement d'insertion
	 * 
	 * @param aTraiter
	 *            liste des periodes a modifier
	 * @param indexPremier
	 *            index de la premiere insertion
	 * @param indexDernier
	 *            index de la derniere insertion
	 * @param insertionDebut
	 *            premiere periode a inserer
	 * @param insertionFin
	 *            derniere periode a inserer
	 * @return liste traitee
	 */
	private LinkedList<PeriodeAbsolue> insertTraitement(
			LinkedList<PeriodeAbsolue> aTraiter, int indexPremier,
			int indexDernier, PeriodeAbsolue insertionDebut,
			PeriodeAbsolue insertionFin) {

		LinkedList<PeriodeAbsolue> finale = new LinkedList<PeriodeAbsolue>();

		// Traitement des periodes concernees
		for (PeriodeAbsolue p : aTraiter) {
			p.incQuantite();
		}

		// Ajout des periodes non concernees precedantes
		for (int i = 0; i < indexPremier; i++) {
			finale.add(quantitePeriodes.get(i));
		}

		// Ajout de la premiere nouvelle periode
		if (insertionDebut != null) {
			finale.add(insertionDebut);
		}

		// Ajout des periodes traitees
		finale.addAll(aTraiter);

		// Ajout de la derniere nouvelle periode
		if (insertionFin != null) {
			finale.add(insertionFin);
		}

		// Ajout des periodes non concernees suivantes
		for (int i = (indexDernier + 1); i < quantitePeriodes.size(); i++) {
			finale.add(quantitePeriodes.get(i));
		}

		return finale;
	}

	public String getName() {
		return name;
	}

	public LinkedList<String> getProprietes() {
		return proprietes;
	}

	@Override
	public boolean equals(Object o) {
		MaterielStock ms = (MaterielStock) o;
		return this.getStatutNecessaire().equals(ms.getStatutNecessaire())
				&& this.name.equals(ms.name)
				&& this.quantitePeriodes.equals(ms.quantitePeriodes)
				&& this.proprietes.equals(ms.proprietes)
				&& (this.disponibilite == ms.disponibilite);
	}
	
	public boolean estLeMemeMaterielQue(MaterielStock m){
		return this.getStatutNecessaire()==m.getStatutNecessaire() && this.name.equals(m.name) && this.proprietes.equals(m.proprietes);
	}

	public int getPanne(){
		return nbFoisPanne;
	}

	public int getEmprunt(){
		return nbFoisEmp;
	}

	public void setNbPanne(int nb){
		nbFoisPanne += nb;
	}

	public void setNbEmprunt(int nb){
		nbFoisEmp += nb;
	}
}
