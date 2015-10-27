package guiFixed;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import guiFixed.models.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import tools.DBTools;

public class ReportMenuController {
	
	private List<Report> reportList;
	private Report Report;
	
	private MainApp mainApp;
	private String Username;

    @FXML
    private ComboBox<String> cabinDropDown;

    @FXML
    private TextArea commentField;

    @FXML
    private TextArea missingField;

    @FXML
    private Label userLabel;
    
    @FXML
    private Label numberOfLogsLabel;

    @FXML
    private Label dateLabel;
    
    @FXML
    private Text missingPrompt;

    @FXML
    void displayReport(ActionEvent event) {
    	missingField.clear();
    	commentField.clear();
    	userLabel.setText("");
    	numberOfLogsLabel.setText("");
    	missingPrompt.setText("");
		List<Report> reports = DBTools.getReports();
		Report chosenCabinReport;
		for (Report report : reports){
			if(report.getcabin() == getCabinID()){
				chosenCabinReport = report;
				StringBuilder builder = new StringBuilder();
				for (String string : chosenCabinReport.getMissing()){
					builder.append("- "+string + "\r\n ");
				}
				missingField.setText(builder.toString());
				commentField.setText(chosenCabinReport.getcomment());
				userLabel.setText(chosenCabinReport.getusername());
//				System.out.println(chosenCabinReport.getnumberoflogs());
				numberOfLogsLabel.setText(chosenCabinReport.getnumberoflogs()+ " ");
				missingPrompt.setFill(javafx.scene.paint.Color.GREEN);
				missingPrompt.setText("Hentet Rapport");
				break;
    		}
			else{
				missingPrompt.setFill(javafx.scene.paint.Color.RED);
				missingPrompt.setText("Ingen rapport på denne koien");
			}
			
    		
    	}
    	
    }
    
	int getCabinID() {

		Connection myConnection = null;
		PreparedStatement myStatement = null;
		ResultSet myResult = null;

		try {
			myConnection = mainApp.getConnection();
			myStatement = (PreparedStatement) myConnection.prepareStatement("SELECT CabinID FROM Cabin WHERE Name=?");
			myStatement.setString(1, cabinDropDown.getValue());
			myResult = myStatement.executeQuery();
			int k = 0;
			if (myResult.next()) {
				k = myResult.getInt("CabinID");
			}
			return k;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return 0;

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}

	public void setUsername(String username) {
		this.Username = username;
		
	}
	
	public void setCabinList(ArrayList<String> arrayList) {
		cabinDropDown.getItems().addAll(arrayList);

	}
	public void setReportList(){
		reportList = DBTools.getReports();
	}


}
