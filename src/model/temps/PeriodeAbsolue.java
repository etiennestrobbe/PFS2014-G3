package model.temps;
/**Classe designant une periode, celle ci est compose de 2 dates de type DateAbsolue et d'une quantite
 * representant la quantite emprunte durant cette periode
 * @see DateAbsolue
 * author Petillon Sebastien
 */
import java.util.Calendar;

public class PeriodeAbsolue {

	// Attributs

	private DateAbsolue debut;
	private DateAbsolue fin;
	private int quantite;

	// Constructeurs

	public PeriodeAbsolue() {
		this.debut = new DateAbsolue(Infini.negatif);
		this.fin = new DateAbsolue(Infini.positif);
		this.quantite = 0;
	}

	public PeriodeAbsolue(DateAbsolue debut, DateAbsolue fin) {
		if (debut.avant(fin)) {
			this.debut = debut;
			this.fin = fin;
		} else {
			this.debut = fin;
			this.fin = debut;
		}
		this.quantite = 0;
	}

	public PeriodeAbsolue(DateAbsolue debut, DateAbsolue fin, int quantite) {
		this(debut, fin);
		this.quantite = quantite;
	}

	// Getters / Setters

	public boolean equals(PeriodeAbsolue periode) {
		if (!debut.equals(periode.debut)) {
			return false;
		}
		if (!fin.equals(periode.fin)) {
			return false;
		}
		return quantite == periode.quantite;
	}

	public PeriodeAbsolue clone() {
		return new PeriodeAbsolue(debut.clone(), fin.clone(), quantite);
	}

	public DateAbsolue getDebut() {
		return debut;
	}

	public void setDebut(DateAbsolue debut) {
		this.debut = debut;
	}

	public DateAbsolue getFin() {
		return fin;
	}

	public void setFin(DateAbsolue fin) {
		this.fin = fin;
	}

	public int getQuantite() {
		return quantite;
	}

	public void incQuantite() {
		this.quantite++;
	}

	public void decQuantite() {
		this.quantite--;
	}

	@Override
	public String toString() {
		return "{" + debut + ",\t" + fin + ",\t" + quantite + "}";
	}

	// Methodes

	public boolean presenteEntre(DateAbsolue debut, DateAbsolue fin) {
		if ((!debut.apres(this.debut) && !fin.apres(this.debut))
				|| (!debut.avant(this.fin) && !fin.avant(this.fin))) {
			return false;
		}
		return true;
	}

	public PeriodeAbsolue ajouterFin(DateAbsolue date) {
		
		if(date.equals(debut)){
			return null;
		}

		// On cree le retour avec la date de debut et la date donnee
		PeriodeAbsolue d = new PeriodeAbsolue(debut, date, this.quantite);

		// On modifie la date de debut de l'actuelle periode a la date donnee
		this.debut = date.clone();

		// On decremente dans le retour la date de fin
		Calendar c = (Calendar) date.get();
		c.add(Calendar.DATE, -1);

		return d;

	}

	public PeriodeAbsolue ajouterDebut(DateAbsolue date) {
		
		if(date.equals(fin)){
			return null;
		}

		// On cree le retour avec la date donnee et la date de fin
		PeriodeAbsolue d = new PeriodeAbsolue(date, fin, this.quantite);

		// On modifie la date de fin de l'actuelle periode a la date donnee
		this.fin = date.clone();

		// On incremente dans le retour la date de debut
		Calendar c = (Calendar) date.get();
		c.add(Calendar.DATE, +1);

		return d;

	}

}
