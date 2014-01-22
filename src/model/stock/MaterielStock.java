package model.stock;
/**Classe designant un element du stock ayant un nom et des proprietes definies
 * author Petillon Sebastien & Etienne STROBBE
 * Améliorer le 22 Janvier 2014 Guillaume BORG : ajout des validation et instanciations pour plusieurs emprunts
 * Ainsi que la satsifaction au maximum de personne
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
	private int panne;
	private int nbFoisEmp;
	private int nbFoisPanne;



	/**
	 * Constructeur de MaterielStock
	 * @param name
	 * 			String nom du matériel
	 * @param disponibilite
	 * 			Donne le nombre de matériel disponible
	 * @param statut
	 * 			Statut fiche le statut minimum pour interargir avec le materiel
	 * @return void
	 */
	public MaterielStock(String name, int disponibilite, Statut statut) {
		this.statutNecessaire = statut;
		this.name = name;
		this.disponibilite = disponibilite;
		quantitePeriodes = new LinkedList<PeriodeAbsolue>();
		proprietes = new LinkedList<String>();
		quantitePeriodes.add(new PeriodeAbsolue());
		nbFoisEmp = 0;
		panne = 0;
		nbFoisPanne = 0;
	}
	
	/**
	 * 
	 * @param propriete 
	 * 				String ajouter une propriété appliqué au materiel
	 * @return void
	 */
	public void addPropriete(String propriete) {
		proprietes.add(propriete);
	}
	
	/**
	 * 
	 * @param statutNecessaire
	 * 				Statut fixe le statut pour interargir avec materiel
	 * @return void
	 */
	public void setStatutNecessaire(Statut statutNecessaire) {
		this.statutNecessaire = statutNecessaire;
	}
	
	/**
	 * 
	 * @return Statut 
	 * 				Getter du Statut minimum pour interargir avec ce materiel
	 */
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
	
	/**
	 * Affichage des données du materiel 
	 * @return String
	 */
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
	 * @param quantiteDemandee
	 * 			  La quantité demander par l'emprunt
	 * @return int Le nombre de disponibilite possible
	 *         0 si auncune disponiblité
	 */
	public int disponible(DateAbsolue debut, DateAbsolue fin, int quantiteDemandee) {
		// Parcours des periodes
		for (PeriodeAbsolue pa : quantitePeriodes) {
		
			// Test de la presence et de la superiorité de la quantite aux
			// emprunts
			if (pa.presenteEntre(debut, fin)
			&& ((pa.getQuantite() + quantiteDemandee) > disponibilite) ) {
			
				int quantiteArrondie = quantiteDemandee/2;
				System.out.println("quantiteDemandee : " + quantiteDemandee +
				" quantiteDemandee/2 : " + (quantiteDemandee/2) +
				" quantiteArrondie : " + quantiteArrondie +
				" pa.getQuantite() : " + pa.getQuantite());
				if (pa.presenteEntre(debut, fin)
				&& ((pa.getQuantite() + quantiteArrondie) <= disponibilite) ) {
					System.out.println("JUSTE demandé : " + (pa.getQuantite() + quantiteArrondie) + " disponibilite : " + disponibilite);
					return quantiteArrondie;
				}
				else {
					System.out.println("demandé : " + (pa.getQuantite() + quantiteArrondie) + " disponibilite : " + disponibilite);
						return 0;
				}
			
			}
		}
		System.out.println("fonction disponible OK");
		return quantiteDemandee;
	}
	
	public int getDisponibilite() {
		return disponibilite;
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
	public void retirer(DateAbsolue debut, DateAbsolue fin, int quantiteDemandee) {

		// Creation de la liste des periodes a modifier
		LinkedList<PeriodeAbsolue> aTraiter = periodesConcernees(debut, fin);

		// Traiter chaque periode
		for (PeriodeAbsolue p : aTraiter) {
			p.setQuantite(p.getQuantite() - quantiteDemandee); // TODO - quantite fait
			System.out.println("p.getQuantite : " + p.getQuantite() + " quantiteDemandee : " + quantiteDemandee);
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
;
				precedente.setQuantite(precedente.getQuantite() + courante.getQuantite());
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
	public void inserer(DateAbsolue debut, DateAbsolue fin, int quantite) {

		// Creation de la liste des periodes a modifier
		LinkedList<PeriodeAbsolue> aTraiter = periodesConcernees(debut, fin);

		// Cas special d'une seule periode exactement semblable
		if (aTraiter.size() == 1 && aTraiter.get(0).getDebut().equals(debut)
				&& aTraiter.get(0).getFin().equals(fin)) {
			aTraiter.get(0).setQuantite(aTraiter.get(0).getQuantite() + quantite);
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
				indexDernier, insertionDebut, insertionFin, quantite);
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
			PeriodeAbsolue insertionFin, int quantite) {

		LinkedList<PeriodeAbsolue> finale = new LinkedList<PeriodeAbsolue>();

		// Traitement des periodes concernees
		for (PeriodeAbsolue p : aTraiter) {
			p.setQuantite(p.getQuantite() + quantite);
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

	public int getNbPanne(){
		return nbFoisPanne;
	}

	public int getNbEmprunt(){
		return nbFoisEmp;
	}

	public void incNbPanne(int nb){
		nbFoisPanne += nb;
	}

	public void incNbEmprunt(int nb){
		nbFoisEmp += nb;
	}
	
	
	/**
	 * Fonction permettant de supprimer un matériel
	 * @param quantite la quantite a supprimer
	 */
	public void supprimerMateriel (int quantite) {
		if(disponibilite >= quantite)
			disponibilite-=quantite;
	}
	
	/**
	 * Fonction permettant d'ajouter un matériel
	 * @param quantite la quantite a ajouter
	 */
	public void ajouterMateriel (int quantite) {
		disponibilite+=quantite;
	}
	
	/**
	 * Fonction permettant de mettre en reparation un matériel
	 * @return void
	 */
	public void reparerMateriel () {
		if(disponibilite == 0) {
			disponibilite--;
			++panne;
			++nbFoisPanne;
		}	
	}
	
	/**
	 * Fonction permettant de remettre le matériel en fonction
	 * @return void
	 */
	public void finReparerMateriel () {
			++disponibilite;
			--panne;
	}	
	

}
