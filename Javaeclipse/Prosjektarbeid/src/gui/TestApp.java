package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import tools.DBTools;

public class TestApp extends Application {
	

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	// stratar programmet

	@Override
	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Rapport");
		primaryStage.setMinHeight(940);
		primaryStage.setMinWidth(940);
		initRootLayout();
		showLogin();
		
//		showKnapp();
		
	}
	
	//initaliserar root layout ("Bakgrunnen"), har moglegheit til legge til ein rekke element her om ynskjeleg
	
	public void initRootLayout(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			//visar "scena" med root layout
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	// visar Skriv rapport-knappen inni root layout
	
	public void showKnapp(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("SkrivRapport.fxml"));
			AnchorPane SkrivRapport = (AnchorPane) loader.load();
			
			//setter skriv rapport-knappen i sentrum va rootlayout
			
			rootLayout.setCenter(SkrivRapport);
			
			//gjev KnappKontroller tilgang til MainApp
			
			KnappKontroller controller = loader.getController();
			controller.setTestApp(this);
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	
	
	public void showRapport(){
		try{
			//lastar inn FXML-fila med rapport-gui'en og lagar ein stage for eit popup-vindu
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Rapport.fxml"));
			AnchorPane pane = (AnchorPane)loader.load();
			
			//lagar stagen
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Skriv rapport");
//			dialogStage.initModality(Modality.WINDOW_MODAL); //Gjer at du ikkje kan interaktere med det førre vinduet før du er ferdig med pop-upen
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);
			
			
			//visar pop-up vinduet og ventar til brukaren er ferdig med det
		
		
			dialogStage.showAndWait();
			
			
		}catch (IOException e) {
	        e.printStackTrace();
		}
	}
	public void showLogin(){
		try{
			  FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("Rapport"
	            		+ ".fxml"));
	            AnchorPane login = (AnchorPane) loader.load();
	            
	            rootLayout.setCenter(login);
			
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(MainApp.class.getResource("Login.fxml"));
//			AnchorPane page = (AnchorPane) loader.load();
//			primaryStage.setTitle("Login");
//			primaryStage = page;
			
			//lastar inn FXML-fila med rapport-gui'en og lagar ein stage for eit popup-vindu
			
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(MainApp.class.getResource("Login.fxml"));
//			AnchorPane page = (AnchorPane) loader.load();
//			
//			//lagar stagen
//			
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Login");
//			dialogStage.initOwner(primaryStage);
//			Scene scene = new Scene(page);
//			dialogStage.setScene(scene);
//			
//			
//			//visar pop-up vinduet og ventar til brukaren er ferdig med det
//			
//			dialogStage.showAndWait();
//			
			
		}catch (IOException e) {
	        e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
}
