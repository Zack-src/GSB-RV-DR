package application.panneaux;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.dr.connector.Database;
import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PanneauRapports {

	private static List<RapportVisite> rapports = new ArrayList<RapportVisite>();
	private static String erreur;
	
	private enum Mois { 
		Janvier("Janvier"), 
		Fevrier("Février"), 
		Mars("Mars"), 
		Avril("Avril"), 
		Mai("Mai"), 
		Juin("Juin"), 
		Juillet("Juillet"), 
		Aout("Aout"), 
		Septembre("Septembre"), 
		Octobre("Octobre"), 
		Novembre("Novembre"), 
		Decembre("Decembre");

		private String string;
		
		Mois(String string) {
			this.string = string;
		} 
		
		public String toString()
		{
			return string;
		}
	};
		
	public static void show(BorderPane panel)
	{
		
        ComboBox<Mois> cmbMoi = new ComboBox<>();
        
        cmbMoi.setItems( FXCollections.observableArrayList( Mois.values()));
        cmbMoi.getSelectionModel().selectFirst();
        
        int year = LocalDate.now().getYear();
        ComboBox<String> cmbDate = new ComboBox<String>();
        cmbDate.getItems().addAll(
        		Integer.toString(year), 
        		Integer.toString(year-=1), 
        		Integer.toString(year-=1), 
        		Integer.toString(year-=1),
        		Integer.toString(year-=1),
        		Integer.toString(year-=1)
        	);
        cmbDate.getSelectionModel().selectFirst();

        
        Button valider = new Button("Valider");

        Text error_msg = new Text(10,20,erreur);
		
        
        // Show elements
        HBox hbox = new HBox(cmbMoi, cmbDate, valider, error_msg);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        
        TableView<RapportVisite> tabRapports = new TableView<>();
		
		TableColumn<RapportVisite, Integer> colNum = new TableColumn<>("Numéro");
	    TableColumn<RapportVisite, String> colBilan = new TableColumn<>("Bilan");
	    TableColumn<RapportVisite, String> colMotif = new TableColumn<>("Motif");
	    TableColumn<RapportVisite, String> colDate = new TableColumn<>("Date");
	    
	    
        colNum.setCellValueFactory( new PropertyValueFactory<>("numero") );
        colBilan.setCellValueFactory( new PropertyValueFactory<>("bilan") );
        colMotif.setCellValueFactory( new PropertyValueFactory<>("motif") );
        colDate.setCellValueFactory( new PropertyValueFactory<>("date") );
        
        tabRapports.getColumns().addAll(colNum, colBilan, colMotif);

        if(PanneauRapports.rapports != null)
        {
        	tabRapports.getItems().addAll(PanneauRapports.rapports);	
        }
        
	    tabRapports.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    tabRapports.setBackground(Background.EMPTY);
        
        
	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(hbox, tabRapports);
 
	    panel.setCenter(vbox);
	    
		valider.setOnAction(e -> {
			PanneauRapports.rapports = Database.getRapportVisite("b4", cmbMoi.getValue().ordinal()+1, Integer.parseInt(cmbDate.getValue()));
			
			PanneauRapports.erreur = Database.getErrorBD();
			
			PanneauRapports.show(panel);
		});
	    
	}	
}
