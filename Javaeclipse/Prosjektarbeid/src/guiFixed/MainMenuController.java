package guiFixed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.sun.corba.se.spi.orbutil.fsm.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import tools.DBTools;

public class MainMenuController {
	@FXML Text dispatchText;
	@FXML Text timeText;
	@FXML Text messageField;
	@FXML Label dispatch;
	@FXML Label timesent;



	private String username;
	@FXML Text newMessageText;
	public String getUsername() {
		return username;
	}

	private MainApp MainApp;
	@FXML
	private Text welcome;
	
	public void setMainApp(MainApp mainApp){
		this.MainApp = mainApp;
	}


    public void checkMessages() {
    	
    	Connection myCon = null;
    	PreparedStatement myStmt = null;
    	ResultSet myRslt = null;
    	try{
    		myCon = MainApp.getConnection();
    		myStmt = myCon.prepareStatement("SELECT Dispatcher, Message, TIME_CREATED FROM `User messages` WHERE Username=?");
    		myStmt.setString(1, getUsername());
    		myRslt = myStmt.executeQuery();
    		if (!myRslt.isBeforeFirst()) { // If the query returns an empty result:
    			newMessageText.setText("Ingen nye meldinger");
    		}
    		else{
    			newMessageText.setText("Du har fått en melding:");
    			dispatch.setText("Avsender:");
    			timesent.setText("Melding sendt:");
    			while (myRslt.next()) {
    				String dispatcher = myRslt.getString("Dispatcher");
    				String message = myRslt.getString("Message");
    				String timeSent = myRslt.getString("TIME_CREATED");

    				messageField.setText(message);
    				dispatchText.setText(dispatcher);
    				timeText.setText(timeSent);
    			}
    		}
    	}
    	catch(SQLException e){  
    		e.printStackTrace();
    	}
    	
	}
	@FXML
    void returnToLogin(ActionEvent event) {
    	MainApp.showLogin();

    }

    @FXML
    void writeReport(ActionEvent event) {
    	MainApp.showReport();

    }
    @FXML
    void showPrognosis(ActionEvent event){
    	MainApp.showPrognosis();
    	
    }
    @FXML
    void showMessageMenu(){
    	MainApp.showMessageMenu();
    }
   
    @FXML
    void showReportMenu(ActionEvent event){
    	MainApp.showReportMenu();
    }

	public void setUsername(String username) {
		this.username = username;
		welcome.setText("Welcome, "+getUsername());
		
		
	}
	
	

}
