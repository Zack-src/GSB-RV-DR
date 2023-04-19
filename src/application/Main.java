package application;

import java.util.Optional;

import application.panneaux.PanneauAccueil;
import application.panneaux.PanneauPracticiens;
import application.panneaux.PanneauRapports;
import fr.gsb.rv.dr.connector.Database;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.Session;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


			MenuBar barreMenus = new MenuBar();

			Menu menuFichier = new Menu("Fichier");
			Menu menuRapports = new Menu("Rapports");
			Menu menuPracticiens = new Menu("Practiciens");
			
			MenuItem itemSeConnecter = new MenuItem("Se connecter");
			MenuItem itemSeDeconnecter = new MenuItem("Se deconnecter");
			MenuItem itemQuitter = new MenuItem("Quitter");

			MenuItem itemConsulter = new MenuItem("Consulter");

			MenuItem itemHesitants = new MenuItem("Hésitants");
			
			
			itemSeDeconnecter.setDisable(true);
			menuRapports.setDisable(true);
			menuPracticiens.setDisable(true);

			itemSeConnecter.setOnAction(e -> {
				
				Optional<Pair<String, String>> result = Modal.login();
				if(result.isPresent())
				{	
					Visiteur visiteur = Database.loginUser(result.get().getKey(), result.get().getValue());
					
					if(visiteur != null)
					{
						Session.ouvrir(visiteur);
						
						itemSeDeconnecter.setDisable(false);
						itemSeConnecter.setDisable(true);

						menuRapports.setDisable(false);
						menuPracticiens.setDisable(false);	
					}
					else
					{
						System.out.println(Database.getError());
					}
				}
				
			});

			itemSeDeconnecter.setOnAction(e -> {

				Session.fermer();
				
				itemSeDeconnecter.setDisable(true);
				itemSeConnecter.setDisable(false);
				
				menuRapports.setDisable(true);
				menuPracticiens.setDisable(true);

				PanneauAccueil.show(root);
			});
			
			itemQuitter.setOnAction(e -> {
				if(Modal.confirmation("Quitter", "Voulez-vous quitter l'application?"))
					Platform.exit();
			});

			itemConsulter.setOnAction(e ->{
				PanneauRapports.show(root);
			});
			
			itemHesitants.setOnAction(e ->{
				PanneauPracticiens.show(root);
			});
			
			
			menuFichier.getItems().add(itemSeConnecter);
			menuFichier.getItems().add(itemSeDeconnecter);
			menuFichier.getItems().add(itemQuitter);
			
			menuFichier.getItems().add(2,new SeparatorMenuItem());
			
			menuRapports.getItems().add(itemConsulter);
			menuPracticiens.getItems().add(itemHesitants);
			
			
			barreMenus.getMenus().add(menuFichier);
			barreMenus.getMenus().add(menuRapports);
			barreMenus.getMenus().add(menuPracticiens);
			
			
			root.setTop(barreMenus);

			PanneauAccueil.show(root);
						
			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
