package guiFixed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tools.DBTools;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NewUserController {

	public NewUserController() {

	}

	private MainApp MainApp;

	public void setMainApp(MainApp mainApp) {
		this.MainApp = mainApp;
	}

	@FXML
	private TextField newName;

	@FXML
	private TextField newPhone;

	@FXML
	private TextField newEpost;

	@FXML
	private TextField newUsername;

	@FXML
	private PasswordField newPass1;

	@FXML
	private PasswordField newPass2;

	@FXML
	public void registerNewUser() {
		if (checkPasswordsMatch()&&checkUsernameValidity())
			uploadToDatabase();
		this.MainApp.showLogin();

	}

	private boolean checkPasswordsMatch() {
		if (newPass1.getText().equals(newPass2.getText())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkUsernameValidity() {
		Connection con = DBTools.quickConnect();
		ResultSet resultSet = DBTools.getTableValues(con, "Users", "Username");
		try {
			while (resultSet.next()) {
				if (!newName.getText().equals(resultSet.toString()))
					;
				{
					return true;
				}
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	private void uploadToDatabase() {
		Connection con = DBTools.quickConnect();

		String insertToTable = "INSERT INTO Users(Username,Password,Name,Phone,Email,ADMIN) VALUES" + "(?,?,?,?,?,?)";
		try {
			PreparedStatement report = (PreparedStatement) con.prepareStatement(insertToTable);

			report.setString(1, newUsername.getText());
			report.setString(2, newPass1.getText());
			report.setString(3, newName.getText());
			report.setString(4, newPhone.getText());
			report.setString(5, newEpost.getText());
			report.setBoolean(6, false); // Må settes til true for å lage admin-bruker
			report.executeUpdate();
			con.close();
			System.out.println("Success");
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
