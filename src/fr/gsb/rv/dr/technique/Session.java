package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.Visiteur;

// Design Patern Singleton

public class Session {

	private static Session session = null;
	private Visiteur leVisiteur;
	private int critereTri;
	
	private Session(Visiteur visiteur)
	{
		this.leVisiteur = visiteur;
	}
	
	public static void ouvrir(Visiteur visiteur)
	{
		Session.session = new Session(visiteur);
	}
	
	public static void fermer()
	{
		Session.session = null;
	}
	
	public static Session getSession()
	{
		return Session.session;
	}
	
	public Visiteur getLeVisiteur()
	{
		return this.leVisiteur;
	}
	
	public static boolean estOuverte()
	{
		return getSession() != null;
	}

	public int getCritereTri() {
		return critereTri;
	}

	public void setCritereTri(int critereTri) {
		this.critereTri = critereTri;
	}
}
