package guiFixed;
//TESTING
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tools.DBTools;

public class MainApp extends Application {
	
	private Connection con = DBTools.quickConnect();

	private Stage primaryStage;
	private Stage popupStage;
	private Stage popupStage2;
	private BorderPane rootLayout;
	private String username;

	public String getUsername() {
		return username;
	}

	public Connection getConnection() {
		return con;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void start(Stage primaryStage) {
		
		try{
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Koier og rapporter");
			
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		showLogin();
		
	}
	
	public void showLogin(){
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/Login.fxml"));
			AnchorPane login = (AnchorPane) loader.load();
			rootLayout.setCenter(login);
			LoginController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void showMainMenu(){
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MainMenu.fxml"));
			AnchorPane mainMenu = (AnchorPane) loader.load();
			rootLayout.setCenter(mainMenu);
			
			MainMenuController controller = loader.getController();
			controller.setMainApp(this);
			controller.setUsername(getUsername());
			controller.checkMessages();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void showAdminMenu(){
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/AdminHome.fxml"));
			AnchorPane adminMenu = (AnchorPane) loader.load();
			rootLayout.setCenter(adminMenu);
			MainMenuController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void showNewUser(){
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/NewUser.fxml"));
			AnchorPane newUser = (AnchorPane) loader.load();
			rootLayout.setCenter(newUser);
			
			NewUserController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showWriteReport(){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/WriteReport.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			
			popupStage = new Stage();
			popupStage.setTitle("Skriv rapport");
			popupStage.initModality(Modality.WINDOW_MODAL); //Gjer at du ikkje kan interaktere med det førre vinduet før du er ferdig med pop-upen
			popupStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			popupStage.setScene(scene);
			
			WriteReportController controller = loader.getController();
			controller.setMainApp(this);
			controller.setUsername(getUsername());
			controller.setCabinDrop(getCabinList());
			
			popupStage.showAndWait();
			
			
			
		}catch (IOException e) {
	        e.printStackTrace();
		}
	}
	
	public void showOneReport(){
		
	}
	public ArrayList<String> getCabinList(){
		try {			
			Connection con = getConnection();
			PreparedStatement statement = (PreparedStatement) con.prepareStatement("SELECT Name FROM Cabin");
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while (result.next()){
				array.add(result.getString("Name"));
			}
			return array;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
		
	}

	
	public void showReportMenu() {
try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ReportMenu.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			
			popupStage2 = new Stage();
			popupStage2.setTitle("Skriv rapport");
			popupStage2.initModality(Modality.WINDOW_MODAL); //Gjer at du ikkje kan interaktere med det førre vinduet før du er ferdig med pop-upen
			popupStage2.initOwner(primaryStage);
			Scene scene = new Scene(page);
			popupStage2.setScene(scene);
			
			ReportMenuController controller = loader.getController();
			controller.setMainApp(this);
			controller.setUsername(getUsername());
			controller.setCabinList(getCabinList());
			
			popupStage2.showAndWait();
			
			
			
		}catch (IOException e) {
	        e.printStackTrace();
		}
	}

	public void showPrognosis(){
		try{
//			Parent root = FXMLLoader.load(getClass().getResource("Prognose.fxml"));
//	        Scene scene = new Scene(root);
//	        popupStage.setTitle("Koiemeny");
//	        popupStage.setScene(scene);
//	        popupStage.show();
//			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Prognose.fxml"));
			StackPane page = (StackPane) loader.load();
			
			popupStage = new Stage();
//			popupStage.initModality(Modality.WINDOW_MODAL); //Gjer at du ikkje kan interaktere med det førre vinduet før du er ferdig med pop-upen
			popupStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			popupStage.setScene(scene);
			
			PrognoseController controller = loader.getController();
			controller.setCabinList(getCabinList());
			controller.setMainApp(this);
			controller.setUsername(getUsername());
			
			popupStage.show();
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public void closePopup(){
		popupStage.hide();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void closePopup2() {
		popupStage2.hide();
		
	}

	public void showMessageMenu() {
		
try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SendMessages.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			
			popupStage = new Stage();
			popupStage.setTitle("Send melding");
			popupStage.initModality(Modality.WINDOW_MODAL); //Gjer at du ikkje kan interaktere med det førre vinduet før du er ferdig med pop-upen
			popupStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			popupStage.setScene(scene);
			
			SendMessageController controller = loader.getController();
			controller.setMainApp(this);
			controller.setUsername(getUsername());
			controller.setCabinList(getCabinList());
			
			popupStage.showAndWait();
			
			
			
		}catch (IOException e) {
	        e.printStackTrace();
		}
	}
	
		
		
	
}
