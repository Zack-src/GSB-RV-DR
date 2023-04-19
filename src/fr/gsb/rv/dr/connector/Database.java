package fr.gsb.rv.dr.connector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.panneaux.PanneauRapports;
import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;

public class Database {
		
	private static Connection connection = null;
	private static int error;

	
	private static int ConnexionDB(String dbName)
	{
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			return -1;
		}
		
		String url = "jdbc:mariadb://localhost:3306/" + dbName;
		String user = "root";
		String pssd = "devadmin";
		
		try {
			connection = DriverManager.getConnection(url, user, pssd);
		} catch (SQLException e) {
			return -2;
		}
		
		return 0;
	}

	
	private static void setError(int error)
	{
		Database.error = error;
	}
	
	public static int getError()
	{
		return Database.error;
	}
	
	public static String getErrorBD()
	{
		int err = Database.getError();
		String error;
		switch (err) {

		case 0:
			error = "OK";
			break;
		case 1:
			error = "Aucun element ne correspond aux elements selectionnée";
			break;
		case -1:
			error = "Impossible de charger le driver";
			break;
		case -2:
			error = "Impossible de se connecter à la bdd";
			break;
		case -3:
			error = "Erreur lors de l'initialisation de la requette";
			break;
		case -4:
			error = "Erreur lors du chargement des filtres";
			break;
		case -5:
			error = "Erreur lors de l'execution de la requette";
			break;
		case -6:
			error = "Erreur lors de la recupération des éléments";
			break;			
		case -7:
			error = "Erreur inatendu";
			break;
		default:
			error = "Code d'erreur inatendu";
			break;
		}
		return error;
	}
	
	
	public static Visiteur loginUser(String name, String password)
	{
		
		if(connection == null)
		{
			int result = ConnexionDB("gsbrv");
			if(result != 0) 
			{
				setError(result);
				return null;
			}
		}
		
		String req = "SELECT vis_matricule, vis_nom, vis_prenom FROM Visiteur WHERE vis_matricule = ? AND vis_mdp = ?";
		
		PreparedStatement pstmt = null;
		ResultSet response = null;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(req);
		} catch (SQLException e) {
			setError(-4);
			return null;
		}
		
		try {
			pstmt.setString(1, name);
			pstmt.setString(2, password);
		} catch (SQLException e) {
			setError(-5);
			return null;
		}

		try {
			response = pstmt.executeQuery();
		} catch (SQLException e) {
			setError(-6);
			return null;
		}

		try {
			while(response.next())
			{
				String id = response.getString("vis_matricule");	
				String nom = response.getString("vis_nom");
				String prenom = response.getString("vis_prenom");
				Visiteur visiteur = new Visiteur(id, nom, prenom);

				setError(0);
				return visiteur;
			}
		} catch (SQLException e) {
			setError(-7);
			return null;
		}

		setError(-1);
		return null;
	}
	
	public static List<Praticien> getPraticiensHesitant()
	{
		
		if(connection == null)
		{
			int result = ConnexionDB("gsbrv");
			if(result != 0) 
			{
				setError(result);
				return null;
			}
		}

		String req = "SELECT P.pra_num, P.pra_nom, P.pra_ville, P.pra_coefnotoriete, "
				+ "RV.rap_date_visite, RV.rap_coef_confiance "
				+ "FROM Praticien P "
				+ "JOIN RapportVisite RV on P.pra_num = RV.pra_num "
				+ "WHERE RV.rap_date_visite = ( "
				+ "SELECT max(RV2.rap_date_visite) "
				+ "FROM RapportVisite RV2 "
				+ "JOIN RapportVisite rv on P.pra_num = RV2.pra_num) "
				;
		
		PreparedStatement pstmt = null;
		ResultSet response = null;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(req);
		} catch (SQLException e) {
			setError(-4);
			return null;
		}

		try {
			response = pstmt.executeQuery();
		} catch (SQLException e) {
			setError(-5);
			return null;
		}

		List<Praticien> praticiens = new ArrayList<>();

		try {
			while(response.next())
			{
				int id = response.getInt("P.pra_num");
				String nom = response.getString("P.pra_nom");
				String ville = response.getString("P.pra_ville");
				
				double coefNotoriete = response.getDouble("P.pra_coefnotoriete");
				int coefConfiance = response.getInt("RV.rap_coef_confiance");
				
				LocalDate date = LocalDate.parse(response.getString("rap_date_visite"));
				
				Praticien pr = new Praticien(id, nom, ville, coefNotoriete, date, coefConfiance);
				
				praticiens.add(pr);
			}

			if(!praticiens.isEmpty())
			{
				setError(0);
				return praticiens;
			}
		} catch (SQLException e) {
			setError(-6);
			return null;
		}

		
		setError(-1);
		return null;
	}
	
	private static List<Visiteur> getVisiteurs(){
		if(connection == null)
		{
			int result = ConnexionDB("gsbrv");
			if(result != 0) 
			{
				setError(result);
				return null;
			}
		}

		String req = "SELECT vis_matricule, vis_nom, vis_prenom "
					+"FROM Visiteur";
		
		PreparedStatement pstmt = null;
		ResultSet response = null;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(req);
		} catch (SQLException e) {
			setError(-4);
			return null;
		}

		try {
			response = pstmt.executeQuery();
		} catch (SQLException e) {
			setError(-5);
			return null;
		}

		List<Visiteur> visiteurs = new ArrayList<>();

		try {
			while(response.next())
			{
				String id = response.getString("vis_matricule");
				String nom = response.getString("vis_nom");
				String prenom = response.getString("vis_prenom");
				
				Visiteur vis = new Visiteur(id, nom, prenom);
								
				visiteurs.add(vis);
			}

			if(!visiteurs.isEmpty())
			{
				setError(0);
				return visiteurs;
			}
		} catch (SQLException e) {
			setError(-6);
			return null;
		}

		
		setError(-1);
		return null;
	}
	
	public static List<RapportVisite> getRapportVisite(String matricule, int mois, int annee)
	{
		if(connection == null)
		{
			int result = ConnexionDB("gsbrv");
			if(result != 0) 
			{
				setError(result);
				return null;
			}
		}

		String req = "SELECT"
						+ "	RapportVisite.vis_matricule,"
						+ "	RapportVisite.rap_num,"
						+ "	RapportVisite.rap_date_visite,"
						+ "	RapportVisite.rap_bilan,"
						+ "	RapportVisite.pra_num,"
						+ "	Motif.mot_libelle,"
						+ "	RapportVisite.rap_coef_confiance,"
		
						+ "	Visiteur.vis_nom,"
						+ "	Visiteur.vis_prenom,"
		
						+ "	Praticien.pra_nom,"
						+ "	Praticien.pra_ville,"
						+ "	Praticien.pra_coefnotoriete"

					+ " FROM"
						+ "	RapportVisite,"
						+ "	Visiteur,"
						+ "	Praticien,"
						+ "	Motif"
					
					+ " WHERE"
						+ "	RapportVisite.vis_matricule = Visiteur.vis_matricule"
						+ " AND"
						+ "	RapportVisite.pra_num = Praticien.pra_num"
						+ " AND"
						+ "	RapportVisite.mot_num = Motif.mot_num"
						+ " AND"
						+ "	RapportVisite.vis_matricule = ?"
						+ " AND"
						+ "	month(rap_date_visite) = ?"
						+ " AND"
						+ "	year(rap_date_visite) = ?";
		
		PreparedStatement pstmt = null;
		ResultSet response = null;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(req);
		} catch (SQLException e) {
			setError(-3);
			return null;
		}

		try {
			pstmt.setString(1, matricule);
			pstmt.setInt(2, mois);
			pstmt.setInt(3, annee);
		} catch (SQLException e) {
			setError(-4);
			return null;
		}

		try {
			response = pstmt.executeQuery();
		} catch (SQLException e) {
			setError(-5);
			return null;
		}

		List<RapportVisite> rapport = new ArrayList<>();
		
		try {
			
			if(response.next() == false)
			{
				setError(1);
				return null;
			}
			
			while(response.next())
			{
				int rap_num = response.getInt("rap_num");
				
				LocalDate rap_date_visite = LocalDate.parse(response.getString("rap_date_visite"));

				String rap_bilan = response.getString("rap_bilan");
				String mot_libelle = response.getString("mot_libelle");
				int rap_coef_confiance = response.getInt("rap_coef_confiance");
				
				String vis_matricule = response.getString("vis_matricule");
				String vis_nom = response.getString("vis_nom");
				String vis_prenom = response.getString("vis_prenom");
				
				int pra_num = response.getInt("pra_num");
				String pra_nom = response.getString("pra_nom");
				String pra_ville = response.getString("pra_ville");
				
				int pra_coef = response.getInt("pra_coefnotoriete");
				
				RapportVisite rap = new RapportVisite(
							new Visiteur(vis_matricule, vis_nom, vis_prenom),
							new Praticien(pra_num, pra_nom, pra_ville, pra_coef, rap_date_visite, pra_coef),
							rap_num,
							rap_date_visite,
							rap_bilan,
							mot_libelle,
							rap_coef_confiance
						);

				rapport.add(rap);
			}

			if(!rapport.isEmpty())
			{
				setError(0);
				return rapport;
			}
		} catch (SQLException e) {
			setError(-6);
			return null;
		}

		
		setError(-7);
		return null;
	}
	
	
	public static void setRapportVisiteLu(String matricule, int numRapport)
	{
		
		return;
		
	}
	
	
	
}
