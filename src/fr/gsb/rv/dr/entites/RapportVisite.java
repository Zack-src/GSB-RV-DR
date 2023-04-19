package fr.gsb.rv.dr.entites;

import java.time.LocalDate;

public class RapportVisite {

	private Visiteur leVisiteur;
	private Praticien lePraticien;

	private int numero;
	private LocalDate date;
	private String bilan;
	private String motif;

	private int coef_confiance;

	public RapportVisite(Visiteur leVisiteur, Praticien lePraticien, int numero, LocalDate date, String bilan, String motif, int coef_confiance) {
		super();
		this.leVisiteur = leVisiteur;
		this.lePraticien = lePraticien;
		this.numero = numero;
		this.date = date;
		this.bilan = bilan;
		this.motif = motif;
		this.coef_confiance = coef_confiance;
	}
	
	
	public Visiteur getLeVisiteur() {
		return leVisiteur;
	}

	public void setLeVisiteur(Visiteur leVisiteur) {
		this.leVisiteur = leVisiteur;
	}

	public Praticien getLePraticien() {
		return lePraticien;
	}

	public void setLePraticien(Praticien lePraticien) {
		this.lePraticien = lePraticien;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getBilan() {
		return bilan;
	}

	public void setBilan(String bilan) {
		this.bilan = bilan;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public int getCoef_confiance() {
		return coef_confiance;
	}

	public void setCoef_confiance(int coef_confiance) {
		this.coef_confiance = coef_confiance;
	}	
	
	@Override
	public String toString() {
		return "RapportVisite [leVisiteur=" + leVisiteur.toString() + 
				", lePraticien=" + lePraticien.toString() + 
				", numero=" + numero + 
				", date=" + date + 
				", bilan=" + bilan + 
				", motif=" + motif + 
				", coef_confiance=" + coef_confiance + 
			"]";
	}
}
