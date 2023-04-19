package fr.gsb.rv.dr.entites;

import java.time.LocalDate;

public class Praticien {

	private int numero;
	private String nom;
	private String ville;
	private double coefNotoriete;
	
	private LocalDate dateDernireVisite;
	private int dernierCoefConfiance;

	public Praticien(int numero, String nom, String ville, double coefNotoriete, LocalDate dateDernireVisite, int dernierCoefConfiance) {
		super();
		this.numero = numero;
		this.nom = nom;
		this.ville = ville;
		this.coefNotoriete = coefNotoriete;
		this.dateDernireVisite = dateDernireVisite;
		this.dernierCoefConfiance = dernierCoefConfiance;
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public double getCoefNotoriete() {
		return coefNotoriete;
	}
	public void setCoefNotoriete(double coefNotoriete) {
		this.coefNotoriete = coefNotoriete;
	}
	public LocalDate getDateDernireVisite() {
		return dateDernireVisite;
	}
	public void setDateDernireVisite(LocalDate dateDernireVisite) {
		this.dateDernireVisite = dateDernireVisite;
	}
	public int getDernierCoefConfiance() {
		return dernierCoefConfiance;
	}
	public void setDernierCoefConfiance(int dernierCoefConfiance) {
		this.dernierCoefConfiance = dernierCoefConfiance;
	}
	
	@Override
	public String toString() {
		return "Praticien [numero=" + numero 
				+ ", nom=" + nom
				+ ", ville=" + ville
				+ ", coefNotoriete=" + coefNotoriete
				+ ", dateDernireVisite=" + dateDernireVisite 
				+ ", dernierCoefConfiance=" + dernierCoefConfiance 
			+ "]";
	}
}
