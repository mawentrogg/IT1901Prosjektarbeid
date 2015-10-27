package guiFixed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
	public void closePopup()
	{
		this.MainApp.showLogin();
	}

	@FXML
	public void registerNewUser() 
	{
//		TODO add lables/text for each print
		Connection con = DBTools.quickConnect();

		String insertToTable = "INSERT INTO Users(Username,Password,Name,Phone,Email,ADMIN) VALUES" + "(?,?,?,?,?,?)";
		try {
			PreparedStatement report = (PreparedStatement) con.prepareStatement(insertToTable);
			if(!checkUsernameValidity())
			{
				System.out.println("invalid username");
			}
			else if(!checkPasswordsMatch())
			{
				System.out.println("Passwords do not match");
			}
			else if(!validPassLen())
			{
				System.out.println("Passwords need to be between 4 and 15 characters");
			}
			else if(newName.getText().equals(""))
			{
				System.out.println("empty name");
			}
			else if(!noNums(newName.getText()))
			{
				System.out.println("Invalid character(s) in name");
			}
			else if(newEpost.getText().equals("") || !validEmail())
			{
				System.out.println("invalid email");
			}
			else if(newPhone.getText().length() != 8 || newPhone.getText().equals("") || !isNum(newPhone.getText()))
			{
				System.out.println("invalid phonenumber");
			}
			else
			{
				report.setString(1, newUsername.getText());
				report.setString(2, newPass1.getText());
				report.setString(3, newName.getText());
				report.setString(4, newPhone.getText());
				report.setString(5, newEpost.getText());
				report.setBoolean(6, false); // Maa settes til true for aa lage admin-bruker
				report.executeUpdate();
				con.close();
				this.MainApp.showLogin();
				System.out.println("Success");
			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isNum(String a)
	{
		try
		{
			Double.parseDouble(a);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
	
	private boolean noNums(String a)
	{
		List<String> list = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));
		for(String number : list)
		{
			if(a.contains(number))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean validEmail()
	{
		String[] temp = newEpost.getText().split("@");
		if(temp.length == 2 && temp[1].split("\\.").length > 1)
		{
			if(!temp[1].split("\\.")[0].equals(""))
			{
				return true;
			}
		}
		return false;
	}

	private boolean checkPasswordsMatch() {
		if (newPass1.getText().equals(newPass2.getText())) 
		{
			return true;
		}
		return false;
	}
	
	private boolean validPassLen()
	{
		if(newPass1.getText().length() > 15 || newPass1.getText().length() < 4)
		{
			return false;
		}
		return true;
	}

	private boolean checkUsernameValidity() {
		if(newUsername.getText().equals(""))
		{
			return false;
		}
		Connection con = DBTools.quickConnect();
		ResultSet resultSet = DBTools.getTableValues(con, "Users", "Username");
		try {
			while (resultSet.next()) {
				if (newUsername.getText().toLowerCase().equals(resultSet.getString(1).toLowerCase()))
				{
					System.out.println("Hello error my old friend");
					return false;
				}
			}
			System.out.println("true");
			return true;

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
			if(newName.getText().equals(""))
			{
				System.out.println("empty name");
			}
			else
			{
				report.setString(1, newUsername.getText());
				report.setString(2, newPass1.getText());
				report.setString(3, newName.getText());
				report.setString(4, newPhone.getText());
				report.setString(5, newEpost.getText());
				report.setBoolean(6, false); // Maa settes til true for aa lage admin-bruker
				report.executeUpdate();
				con.close();
				System.out.println("Success");
			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
