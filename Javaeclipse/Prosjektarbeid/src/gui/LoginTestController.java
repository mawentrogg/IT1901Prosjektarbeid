package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class LoginTestController {
	
	private MainApp MainApp;
	
	public void setMainApp(MainApp mainApp) {
        this.MainApp = mainApp;
	}
	public LoginTestController(){
		
	}
	@FXML
	private void initialize() {
    }

    @FXML
    private TextField Username;

    @FXML
    private TextField Password;

    @FXML
    void Login() {
    	MainApp.getPrimaryStage().setScene(null);
//    	if (Username.getText().equals("A") && (Password.getText().equals("B"))){
//    		MainApp.showKnapp();
//    	}
//    	else{
//    		System.out.println("stupid");
//    		MainApp.showRapport();
//    	}
    	

    }

    @FXML
    void createNewUser() {

    }

}
