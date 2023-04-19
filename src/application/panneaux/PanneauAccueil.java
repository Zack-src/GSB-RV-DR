package application.panneaux;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PanneauAccueil {

	
	public static void show(BorderPane panel)
	{
		
		Text t = new Text(10,20,"Bienvenue dans l'acceuil");
		
		Image img = new Image("http://hroche.com/wp-content/uploads/2019/02/image-GSB-1.png");
		ImageView imageView = new ImageView(img);


	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(t, imageView);
 

		panel.setCenter(vbox);
	}	
}
