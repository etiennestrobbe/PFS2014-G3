package model.emprunt;
/**
 * author Petillon Sebastien
 */
import java.util.Calendar;

import config.Configurations;

import personne.Personne;
import model.stock.MaterielStock;
import model.temps.DateAbsolue;

public class Emprunt {

	private static int nextId = 0;
	private int id;
	private Personne personne;
	private DateAbsolue debut;
	private DateAbsolue fin;
	private MaterielStock materielEmprunte;

	public Emprunt(Personne personne, DateAbsolue debut, DateAbsolue fin,
			MaterielStock materielEmprunte) {
		id = nextId;
		nextId++;
		this.setPersonne(personne);
		this.debut = debut;
		this.fin = fin;
		this.materielEmprunte = materielEmprunte;
	}

	/**
	 * Methode permettant de savoir si un emprunt est autorise (valide) ou pas.
	 * @return true si l'emprunt est valide, false sinon
	 */
	public boolean valide() {

		if (!personne.getStatut().suffisant(
				materielEmprunte.getStatutNecessaire())) {
			System.out.println("probleme statut");
			return false;
		}

		if (debut.apres(new DateAbsolue(Calendar.getInstance()))
				|| fin.apres(new DateAbsolue(Calendar.getInstance())));
			
		int miliDay = 60*60*1000*24;
		int inow = (int) (Calendar.getInstance().getTimeInMillis() / miliDay);
		int idebut = (int) (((Calendar) debut.get()).getTimeInMillis() / miliDay);
		int ifin = (int) (((Calendar) fin.get()).getTimeInMillis() / miliDay);

		if(idebut<inow){System.out.println("Pas d'emprunt dans le passe");return false;}
		if(idebut>ifin){System.out.println("Duree negative ?!?");return false;}

		if((idebut - inow) > Configurations.avanceEmprunt(personne.getStatut())){System.out.println("trop a l'avance");return false;}
		if((ifin - idebut) > Configurations.tempsEmprunt(personne.getStatut())){System.out.println("temps emprunt trop long");return false;}
		
		return true;
	}

	public MaterielStock getMaterielEmprunte() {
		return materielEmprunte;
	}

	public DateAbsolue getDebut() {
		return debut;
	}

	public DateAbsolue getFin() {
		return fin;
	}

	public boolean equals(Emprunt e) {
		return this.getPersonne().equals(e.getPersonne())
				&& this.getDebut().equals(e.getDebut())
				&& this.getFin().equals(e.getFin())
				&& this.getMaterielEmprunte().equals(e.getMaterielEmprunte());
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Personne getPersonne() {
		return personne;
	}

}
