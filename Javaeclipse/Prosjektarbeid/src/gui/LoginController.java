package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void newUser(){
		//newUser frame
	}

	public void login(ActionEvent event, boolean user){
		
	}
    @FXML
    private TextField Username;
    @FXML
    private TextField Password;

    @FXML
    void login() {
    	if (Username.getText().equals("kim")){
    		System.out.println("JAAAAA");
    		
    	}

    }

	
	
}
