package guiFixed;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;

import guiFixed.models.Report;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import tools.DBTools;

public class SendMessageController {
	private String username;
	private MainApp mainApp;
	@FXML
	ComboBox<String> cabindrop;
	@FXML
	ComboBox<String> reservationdrop;
	@FXML
	Text promptempty;
	@FXML
	Button sendBtn;
	@FXML
	TextArea message;
	private String reservationUser;
	@FXML
	Text messagePrompt;
	@FXML
	Text writeMessage;

	public String getReservationUser() {
		return reservationUser;
	}

	public void setReservationUser(String reservationUser) {
		this.reservationUser = reservationUser;
	}

	private Map<String, Integer> idDateMap;

	@FXML
	void sendMessage() {
		String message = getMessage().getText();
		java.util.Date today = new java.util.Date();
		Timestamp date = new java.sql.Timestamp(today.getTime());

		if (!message.isEmpty() && reservationdrop.getValue() != null) {

			Connection myConnection = null;
			PreparedStatement myStatement = null;

			try {
				deleteLastMessage();
				myConnection = mainApp.getConnection();
				String stateText = "INSERT INTO `User messages`(Username, Dispatcher, Message, TIME_CREATED) VALUES (?, ?, ?, ?)";
				myStatement = (PreparedStatement) myConnection.prepareStatement(stateText);
				myStatement.setString(1, getReservationUser());
				myStatement.setString(2, getUsername());
				myStatement.setString(3, message);
				myStatement.setTimestamp(4, date);
				myStatement.executeUpdate();
				messagePrompt.setFill(javafx.scene.paint.Color.GREEN);
				messagePrompt.setText("Meldingen ble sendt");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (reservationdrop.getValue() == null) {
			messagePrompt.setFill(javafx.scene.paint.Color.RED);
			messagePrompt.setText("Du har ikke valgt reservasjon");
		} else if (message.isEmpty()) {
			messagePrompt.setFill(javafx.scene.paint.Color.RED);
			messagePrompt.setText("Du glemte å skrive inn en melding!");
		}

	}

	private void deleteLastMessage() {
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRslt = null;
		try {
			myCon = mainApp.getConnection();
			myStmt = (PreparedStatement) myCon
					.prepareStatement("DELETE FROM `User messages` WHERE Username=?");
			myStmt.setString(1, getReservationUser());
			int rowsUpdated = myStmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public TextArea getMessage() {
		return message;
	}

	void setUsername(String username) {
		this.username = username;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setCabinList(ArrayList<String> arrayList) {
		cabindrop.getItems().addAll(arrayList);

	}

	@FXML
	void getReservationUserName() {

		// Looks up the chosen Reservation in DB, and gets the name of the user
		// who has that reservation
		messagePrompt.setText("");
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		ResultSet myResult = null;
		String reservationdate = reservationdrop.getValue();

		if (reservationdate != null) {

			try {
				myConnection = mainApp.getConnection();
				myStatement = (PreparedStatement) myConnection
						.prepareStatement("SELECT User FROM Reservations WHERE ReservationID=?");
				int resID = idDateMap.get(reservationdate);
				myStatement.setInt(1, resID);
				myResult = myStatement.executeQuery();
				if (myResult.next()) {
					setReservationUser((myResult.getString("User")));
				}
				writeMessage.setText("Skriv melding til bruker '" + getReservationUser() + "' her:");

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			myStatement.setString(1, cabindrop.getValue());
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


	// SELECT ReservationID , Date FROM `Reservations` WHERE `CabinID` = '7'
	@FXML
	void setReservationList() {
		message.clear();
		fillMessage();
		writeMessage.setText("Skriv melding til bruker her:");
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		ResultSet myResult = null;
		try {
			myConnection = mainApp.getConnection();
			myStatement = (PreparedStatement) myConnection.prepareStatement(
					"SELECT ReservationID, DATE_FORMAT( Date, '%d-%m-%Y' ) FROM Reservations WHERE CabinID=?");
			myStatement.setInt(1, getCabinID());
			myResult = myStatement.executeQuery();
			if (!myResult.isBeforeFirst()) { // If the query returns an empty
												// result:
				reservationdrop.getItems().clear();
				promptempty.setText("Denne koien har ingen reservasjoner for øyeblikket");
			} else { // Make hashmap with reservation ID and cabin name,
				reservationdrop.getItems().clear();
				idDateMap = makeHashmap(myResult);
				ArrayList<String> array = new ArrayList<String>();
				array.addAll((idDateMap.keySet())); // Would be nice to have
													// this array sorted by the
													// dates
				reservationdrop.getItems().addAll(array);
				promptempty.setText("");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private void fillMessage() {
		List<Report> reports = getReports();
		Report chosenCabinReport;
		for (Report report : reports){
			if(report.getcabin() == getCabinID()){
				chosenCabinReport = report;
				StringBuilder builder = new StringBuilder();
				builder.append("Utstyr som mangler: \r\n ");
				for (String string : chosenCabinReport.getMissing()){
					builder.append("- "+string + "\r\n ");
				}
				message.setText(builder.toString());
				break;
		}}
		
	}

	private Map<String, Integer> makeHashmap(ResultSet myResult) throws SQLException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		while (myResult.next()) {
			int reservationID = myResult.getInt("ReservationID");
			String date = myResult.getString("DATE_FORMAT( Date, '%d-%m-%Y' )");
			map.put(date, reservationID);
		}
		return map;

	}

	public String getUsername() {
		return username;
	}
	public List<Report> getReports(){
		try{
			Connection con = mainApp.getConnection();
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery("select * from ( select * from Reportuser order by Date asc ) x group by Cabin");
			List<Report> reports = new ArrayList<Report>();
			while (result.next()){
				int cabinNumber = result.getInt("cabin");
				String username = result.getString("Username");
				String inventory = result.getString("Inventory");
				String comment = result.getString("comment");
				Date date = result.getDate("date");
				int logs = result.getInt("LOGS");
//				int reportID = result.getInt("ReportID");
				reports.add(new Report(cabinNumber, username, inventory, comment, logs, date));
			}
			return reports;

		
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

}
