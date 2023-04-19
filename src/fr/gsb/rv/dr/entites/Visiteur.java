package fr.gsb.rv.dr.entites;

import javafx.scene.control.RadioButton;

public class Visiteur {

	@Override
	public String toString() {
		return "Visiteur [marticule=" + marticule + 
				", nom=" + nom + 
				", prenom=" + prenom + "]";
	}

	private String marticule;
	private String nom;
	private String prenom;
		
	public Visiteur(String marticule, String nom, String prenom) {
		this.marticule = marticule;
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getMarticule() {
		return this.marticule;
	}

	public void setMarticule(String marticule) {
		this.marticule = marticule;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
}
