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
		setMateriel(chemin);
		setComptes(chemin);
		setEmprunts(chemin);
	}

	private static void setEmprunts(String chemin) {
		HashMap<Personne, LinkedList<String>> emprunts = new HashMap<Personne, LinkedList<String>>();
		ConfigXML.store(emprunts, "emprunts");
	}

	private static void setComptes(String chemin) {
		LinkedList<Personne> comptes = new LinkedList<Personne>();
		ConfigXML.store(comptes, "comptes");
	}

	private static void setMateriel(String chemin) {

		LinkedList<String> ret = (LinkedList<String>) ConfigXML.load("listeMateriel");

		LinkedList<MaterielStock> stock = new LinkedList<MaterielStock>();
		String nameAndProps[];
		MaterielStock m;
		int i = 1;

		for (String s : ret) {
			nameAndProps = s.split("-");

			m = new MaterielStock(nameAndProps[0], 4, Statut.Etudiant);

			while (i < nameAndProps.length) {
				System.out.println("Prop : " + nameAndProps[i]);
				m.addPropriete(nameAndProps[i]);
				i++;
			}
			stock.add(m);
			i = 1;
		}

		ConfigXML.store(stock, "materiel");
	}
	
	public static void addMateriel(LinkedList<String> materiel){
		boolean nameGiven=false;
		for(String s : materiel){
//			MaterielStock m = new MaterielStock(materiel.get(0),materiel.get(Statut((int)materiel.size()-1)));
			if(s.equals("-1")){
				return;
			}
			if(!nameGiven){
				
			}
			
		}
	}
	
	public static void removeMateriel(){
		// TODO
	}

}
