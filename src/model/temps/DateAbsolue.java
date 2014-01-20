package model.temps;

/**
 * @author Sebastien Petillon
 */

import java.util.Calendar;

public class DateAbsolue {

	// Attributs

	private Calendar calendar;
	private Infini infini;

	public DateAbsolue(final Calendar date) {
		this.calendar = (Calendar) date.clone();
		this.infini = null;
	}

	public DateAbsolue(Infini date) {
		this.calendar = null;
		this.infini = date;
	}

	public DateAbsolue(int annee, int mois, int jour) {
		this.calendar = Calendar.getInstance();
		this.calendar.set(annee, mois, jour);
		this.infini = null;
	}

	// Getters / Setters

	public Object get() {
		if (this.calendar != null) {
			return this.calendar;
		}
		return infini;
	}

	public DateAbsolue clone() {
		if (this.calendar != null) {
			return new DateAbsolue((Calendar) this.calendar.clone());
		}
		return new DateAbsolue(this.infini);
	}

	public boolean equals(DateAbsolue d) {
		if (this.calendar == null) {
			return d.calendar == null && this.infini.equals(d.infini);
		}
		return this.infini == null && d.infini == null
				&& this.calendar.equals(d.calendar);
	}

	@Override
	public String toString() {
		if (infini != null) {
			if (infini.equals(Infini.negatif)) {
				return "[-I]";
			} else {
				return "[+I]";
			}
		}
		return "[" + calendar.get(Calendar.YEAR) + "/"
				+ calendar.get(Calendar.MONTH) + "/"
				+ calendar.get(Calendar.DATE) + "]";
	}

	// Avant / Apres

	public boolean avant(DateAbsolue date) {

		Boolean inf = avantInfini(date);
		if (inf != null) {
			return inf;
		}

		return avantDate(date);
	}

	public boolean apres(DateAbsolue date) {

		Boolean inf = apresInfini(date);
		if (inf != null) {
			return inf;
		}

		return this.apresDate(date);
	}

	private boolean avantDate(DateAbsolue date) {
		if (this.get() instanceof Calendar) {
			if (date.get().equals(Infini.positif)) {
				return true;
			}
			if (date.get().equals(Infini.negatif)) {
				return false;
			}
			return ((Calendar) this.get()).before((Calendar) date.get());
		}
		return false;
	}

	private boolean apresDate(DateAbsolue date) {
		if (this.get() instanceof Calendar) {
			if (date.get().equals(Infini.positif)) {
				return false;
			}
			if (date.get().equals(Infini.negatif)) {
				return true;
			}
			return ((Calendar) this.get()).after((Calendar) date.get());
		}
		return false;
	}

	private Boolean avantInfini(DateAbsolue date) {

		if (this.get().equals(Infini.negatif)) {
			return !(date.get().equals(Infini.negatif));
		}
		if (this.get().equals(Infini.positif)) {
			return !(date.get().equals(Infini.positif));
		}

		return null;
	}

	private Boolean apresInfini(DateAbsolue date) {

		if (this.get().equals(Infini.negatif)) {
			return false;
		}
		if (this.get().equals(Infini.positif)) {
			return !(date.get().equals(Infini.positif));
		}

		return null;
	}
}
