package model.bdd;
/**
 * @author Petillon Sebastien
 */
import java.util.HashMap;
import java.util.LinkedList;

import model.stock.MaterielStock;
import personne.Personne;
import personne.Statut;

public class CreerBDD {

	public static void setBDD(String chemin) {

		ConfigXML.definirDossier(chemin);
		setMateriel();
		setComptes();
		setEmprunts();
	}

	private static void setEmprunts() {
		HashMap<Personne, LinkedList<String>> emprunts = new HashMap<Personne, LinkedList<String>>();
		ConfigXML.store(emprunts, "emprunts");
	}

	private static void setComptes() {
		LinkedList<Personne> comptes = new LinkedList<Personne>();
		ConfigXML.store(comptes, "comptes");
	}

	/**
	 * Fonction qui cree le stock a partir du fichier XML listeMateriel.xml qui est la base du stock
	 */
	private static void setMateriel() {

		LinkedList<String> ret = (LinkedList<String>) ConfigXML.load("listeMateriel");

		LinkedList<MaterielStock> stock = new LinkedList<MaterielStock>();
		String nameAndProps[];
		MaterielStock m;
		int i = 2;

		for (String s : ret) {
			nameAndProps = s.split("-");

			m = new MaterielStock(nameAndProps[1], Integer.parseInt(nameAndProps[0]), Statut.Etudiant);

			while (i < nameAndProps.length) {
				System.out.println("Prop : " + nameAndProps[i]);
				m.addPropriete(nameAndProps[i]);
				i++;
			}
			stock.add(m);
			i = 2;
		}

		ConfigXML.store(stock, "materiel");
	}
	
	/**
	 * Fonction qui permet d'ajouter un nouveau materiel avec des proprietes definies dans le stock.
	 * Si le materiel existe deja, sa quantite est augmente
	 * @param quantite la quantite de materiel a ajouter
	 * @param listeNameAndProp le nom et la liste des propriete du materiel, se termine par -1
	 * @param stock le stock dans lequel on ajoute le materiel
	 */
	public static void addMateriel(int quantite , LinkedList<String> listeNameAndProp , LinkedList<MaterielStock> stock){
		boolean alreadyExisting = false;
		MaterielStock m = new MaterielStock(listeNameAndProp.get(0),quantite,Statut.getInstance(Integer.parseInt(listeNameAndProp.get(listeNameAndProp.size()-1))));
		for(int i=1;i<listeNameAndProp.size();i++){
			if(listeNameAndProp.get(i).equals("-1")){
				break;
			}
			m.addPropriete(listeNameAndProp.get(i));
		}
		for(MaterielStock ms : stock){
			if(m.estLeMemeMaterielQue(ms)){
				// TODO incrementer la quantite
				alreadyExisting = true;
				break;
			}
		}
		if(!alreadyExisting){
			stock.add(m);
		}
		
		ConfigXML.store(stock, "materiel");
		
		
	}
	
	/**
	 * Fonction permettant de supprimer un materiel du stock
	 * @param stock le stock dans lequel on travail
	 * @param materiel le materiel a supprimer
	 * @param quantite la quantite du materiel a supprimer
	 */
	public static void removeMateriel(LinkedList<MaterielStock> stock , MaterielStock materiel ,int quantite){
		for(MaterielStock ms : stock){
			if(ms.equals(materiel)){
				// si la quantite a enlever est plus grande ou egale que la quantite disponible, alors on supprime completement le materiel
				if(ms.getDisponible()<= quantite){
					stock.remove(ms);
					break;
				}
				else{
					// TODO decrementer la quantite (gerer avec les reparations ...)
					System.out.println("decrementation du stock");
					break;
				}
			}
		}
		ConfigXML.store(stock, "materiel");
	}

}
