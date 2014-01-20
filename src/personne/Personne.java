package personne;

public class Personne {

	private String prenom;
	private String nom;
	private Statut statut;

	public Personne(String prenom, String nom, Statut statut) {
		this.prenom = prenom;
		this.nom = nom;
		this.setStatut(statut);
	}

	@Override
	public boolean equals(Object o) {
		Personne e = (Personne) o;
		return this.prenom.equals(e.prenom) && this.nom.equals(e.nom);
	}
	
	public String toString(){
		return "Nom : "+nom+" | Prénom : "+prenom+" | Statut : "+getStatut();
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public Statut getStatut() {
		return statut;
	}

}
