package guiFixed;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Date;

public class ReportReaderController 
{
	
	MainApp MainApp;
	String Username;
 	@FXML
    private Label Comments;

    @FXML
    private Label Missing;

    @FXML
    private Label Cabin;

    @FXML
    private Label Date;

    @FXML
    private Label User;

    @FXML
    private Label Logs;
    
    public ReportReaderController(String comment, String missing, int cabin, Date Date, String User, int Logs)
    {
    	this.Comments.setText(comment);
    	this.Missing.setText(missing);
    	this.Cabin.setText("Cabin: " + cabin);
    	this.Date.setText("Date: "+Date);
    	this.User.setText("User: " +User);
    	this.Logs.setText("Logs: " +Logs);
    }

	public void setMainApp(MainApp mainApp) {
		this.MainApp = mainApp;
		
	}

	public void setUsername(String username) {
		this.Username = username;
		
	}
}
