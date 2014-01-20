package statistiques;


import model.bdd.GestionnaireStock;
import personne.Personne;
import model.stock.MaterielStock;

public class Statistiques{
	private GestionnaireStock stock;
	private Personne plusGrosEmp;
	private MaterielStock plusDeFoisEmp;
	private MaterielStock plusDeFoisPanne;

	public Statistiques(GestionnaireStock stock){
		this.stock = stock;
		plusGrosEmp = null;
		plusDeFoisEmp = null;
		plusDeFoisPanne = null;
	}

	public void majStat(){
		majStatEmprunteur();
		majStatMateriel();
	}

	/**
	 * Methode qui met a jour les statistique sur le plus gros emprunteur
	 */
	private void majStatEmprunteur(){
		int max = 0;		
		for (Personne p : stock.getCompte()){
			if (p.getNbEmprunt()>max){
				max = p.getNbEmprunt();
				plusGrosEmp = p;
			}
		} 
	}

	/**
	 * Methode qui met a jour les statistiques sur le materiel le plus emprunte
	 * et le materiel le plus de fois tombe en panne.
	 */
	private void majStatMateriel(){
		int maxEmprunt = 0;
		int maxPanne = 0;
		for (MaterielStock ms : stock.getStock()){
			if (ms.getNbEmprunt()>maxEmprunt){
				maxEmprunt = ms.getNbEmprunt();
				plusDeFoisEmp = ms;
			}
			if (ms.getNbPanne()>maxPanne){
				maxPanne = ms.getNbPanne();
				plusDeFoisPanne = ms;
			}
		}
	}

	public String toString(){
		String texte = "";
		if (plusGrosEmp == null){
			texte += "Aucun utilisateur n'a encore emprunté de matériel\n";
		}
		else texte += plusGrosEmp();
		if (plusDeFoisEmp == null){
			texte += "Aucun matériel n'a encore été emprunté\n";
		}
		else texte += plusDeFoisEmp();
		if (plusDeFoisPanne == null){
			texte += "Aucun matériel n'a encore été en panne\n";
		}
		else texte += plusDeFoisPanne();
		return texte;
	}

	private String plusGrosEmp(){
		String texte = "";
		if (plusGrosEmp == null){
			texte += "Aucun utilisateur n'a encore emprunté de matériel\n";
		}
		else texte += "Le plus gros emprunteur est : "+plusGrosEmp+" avec "+plusGrosEmp.getNbEmprunt()+" emprunt(s)\n";
		return texte;
	}

	private String plusDeFoisEmp(){
		String texte = "";
		if (plusDeFoisEmp == null){
			texte += "Aucun matériel n'a encore été emprunté\n";
		}
		else texte += "Le matériel le plus emprunté est : "+plusDeFoisEmp+" avec "+plusDeFoisEmp.getNbEmprunt()+" emprunt(s)\n";
		return texte;
	}

	private String plusDeFoisPanne(){
		String texte = "";
		if (plusDeFoisPanne == null){
			texte += "Aucun matériel n'a encore été en panne\n";
		}
		else texte += "Le matériel le plus souvent en panne est : "+plusDeFoisPanne+" avec "+plusDeFoisPanne.getNbPanne()+" panne(s)\n";
		return texte;
	}
}