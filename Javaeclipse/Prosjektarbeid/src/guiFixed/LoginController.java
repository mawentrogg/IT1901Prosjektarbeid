package guiFixed;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tools.DBTools;

public class LoginController {

	private MainApp MainApp;

	public void setMainApp(MainApp mainApp) {
		this.MainApp = mainApp;
	}

	@FXML
	private TextField Username;

	@FXML
	private PasswordField Password;
	@FXML
	private Text usernotfound;
	@FXML
	private Text wrongpassword;

	@FXML
	public void Login(ActionEvent event) {
		if (!usernameIsValid()) {
			usernotfound.setText("Fant ikke brukeren");
		} else if (validatePassword()) {
			if (isAdmin()){
				this.MainApp.setUsername(Username.getText());
				this.MainApp.showAdminMenu();
		}
			else{

			this.MainApp.setUsername(Username.getText());
			this.MainApp.showMainMenu();}
		} else {
			wrongpassword.setText("Feil passord");
			usernotfound.setText("Gyldig bruker");
			usernotfound.setFill(Color.GREEN);

		}

	}

	private boolean isAdmin() {
		try {
			String tekst = "SELECT ADMIN FROM Users WHERE Username='" + Username.getText() + "'";
			Connection con = MainApp.getConnection();
			PreparedStatement statement = (PreparedStatement) con.prepareStatement(tekst);
			ResultSet result = statement.executeQuery();
			boolean k = false;
			while (result.next()) {
				k = result.getBoolean("ADMIN");
			}

			return k;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;

	}

	@FXML
	public void createNewUser(ActionEvent event) {
		this.MainApp.showNewUser();
	}

	private boolean validatePassword() {
		try {
			String tekst = "SELECT Password FROM Users WHERE Username='" + Username.getText() + "'";
			Connection con = MainApp.getConnection();
			PreparedStatement statement = (PreparedStatement) con.prepareStatement(tekst);
			ResultSet result = statement.executeQuery();
			String k = "";
			while (result.next()) {
				k = result.getString("Password");
			}
			if (Password.getText().equals(k))
				return true;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;

	}

	private boolean usernameIsValid() {
		Connection con = MainApp.getConnection();
		ResultSet resultSet = DBTools.getTableValues(con, "Users", "Username");
		String input = Username.getText();
		try {
			while (resultSet.next()) {
				String dbname = resultSet.getString("Username");
				if (input.equals(dbname)) {
					return true;
				}
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

}
