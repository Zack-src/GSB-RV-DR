package application.panneaux;



import java.util.List;

import fr.gsb.rv.dr.connector.Database;
import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.technique.Session;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PanneauPracticiens {

	public static void rafraichir()
	{
		
	}

	
	public static void show(BorderPane panel)
	{
		
        ToggleGroup tg = new ToggleGroup();
        RadioButton radioConf = new RadioButton("Confiance");
        RadioButton radioNoto = new RadioButton("Notoriété");
        RadioButton radioDate = new RadioButton("Date Visite");
        
        tg.getToggles().addAll(radioConf, radioNoto, radioDate);
        
        radioNoto.setSelected(true);
        
        tg.getToggles().get(Session.getSession().getCritereTri()).setSelected(true);
        
        
        HBox hbox = new HBox(radioConf, radioNoto, radioDate);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
		
        TableView<Praticien> tabPraticien = new TableView<>();
		
		TableColumn<Praticien, Integer> colNum = new TableColumn<>("Numéro");
	    TableColumn<Praticien, String> colNom = new TableColumn<>("Nom");
	    TableColumn<Praticien, String> colVille = new TableColumn<>("Ville");
	    
        colNum.setCellValueFactory( new PropertyValueFactory<>("numero") );
        colNom.setCellValueFactory( new PropertyValueFactory<>("nom") );
        colVille.setCellValueFactory( new PropertyValueFactory<>("ville") );
        
      	tabPraticien.getColumns().addAll(colNum, colNom, colVille);

	    List<Praticien> praticiens = Database.getPraticiensHesitant();
      	tabPraticien.getItems().addAll(praticiens);
      	
      	tabPraticien.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      	tabPraticien.setBackground(Background.EMPTY);
      	
	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(hbox, tabPraticien);

	    tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
	        if (tg.getSelectedToggle() != null) {
	            int selectedIndex = tg.getToggles().indexOf(tg.getSelectedToggle());
	            Session.getSession().setCritereTri(selectedIndex);
	        }
	    });

		panel.setCenter(vbox);
	}	
}
