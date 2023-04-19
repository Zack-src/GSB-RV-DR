module Test_FX {
	requires javafx.controls;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;
	requires javafx.graphics;
    requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	opens fr.gsb.rv.dr.entites to javafx.base;
}
