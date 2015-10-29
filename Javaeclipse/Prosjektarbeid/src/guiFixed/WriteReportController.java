package guiFixed;

import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import tools.DBTools;

public class WriteReportController {
	private String username;
	private MainApp MainApp;

	private String comments = "";

	private String commodities;

	private String wood;

	private Date sqlDate;

	@FXML
	private DatePicker date;

	@FXML
	private CheckBox axe;

	@FXML
	private CheckBox saw;

	@FXML
	private CheckBox bench;

	@FXML
	private CheckBox spade;

	@FXML
	private CheckBox lamp;

	@FXML
	private CheckBox lampGlass;

	@FXML
	private CheckBox detergent;

	@FXML
	private CheckBox brush;

	@FXML
	private CheckBox dishRag;

	@FXML
	private CheckBox kitchenRag;

	@FXML
	private CheckBox cutleries;

	@FXML
	private CheckBox cupsAndPlates;

	@FXML
	private CheckBox primus;

	@FXML
	private CheckBox needles;

	@FXML
	private CheckBox wick;

	@FXML
	private CheckBox pan;

	@FXML
	private CheckBox pot;

	@FXML
	private CheckBox mixer;

	@FXML
	private CheckBox book;

	@FXML
	private CheckBox hammer;

	@FXML
	private CheckBox rule;

	@FXML
	private CheckBox blanket;

	@FXML
	private CheckBox fireExtinguisher;

	@FXML
	private Label dateWarning;

	@FXML
	private TextField Wood;

	@FXML
	private Label woodErr;

	@FXML
	private ChoiceBox<String> dropdown;

	@FXML
	private Label cabinErr;
	
	@FXML
	private Label textErr;

	private List<CheckBox> checkboxes = new ArrayList<>();

	private void fillList() {
		checkboxes.add(axe);
		checkboxes.add(saw);
		checkboxes.add(bench);
		checkboxes.add(spade);
		checkboxes.add(lamp);
		checkboxes.add(lampGlass);
		checkboxes.add(detergent);
		checkboxes.add(brush);
		checkboxes.add(dishRag);
		checkboxes.add(kitchenRag);
		checkboxes.add(cutleries);
		checkboxes.add(cupsAndPlates);
		checkboxes.add(primus);
		checkboxes.add(needles);
		checkboxes.add(wick);
		checkboxes.add(pan);
		checkboxes.add(pot);
		checkboxes.add(mixer);
		checkboxes.add(book);
		checkboxes.add(hammer);
		checkboxes.add(rule);
		checkboxes.add(blanket);
		checkboxes.add(fireExtinguisher);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setMainApp(MainApp mainApp) {
		this.MainApp = mainApp;
	}

	

	@FXML
	void closeWindow(ActionEvent event) {
		MainApp.closePopup();
	}

	@FXML
	private TextArea textArea;

	@FXML
	public void printUsername() {
		fillList();
		setCommodities();
		setText();
		// start();
		System.out.println(comments);
		System.out.println(commodities);
		System.out.println(sqlDate);

	}

	@FXML
	public void setText() {
		this.comments = textArea.getText();
		this.wood = Wood.getText();
	}

	public void setCommodities() {
		commodities = "";
		for (CheckBox n : checkboxes) {
			if (n.isSelected()) {
				commodities += "1";
			} else {
				commodities += "0";
			}
		}
		sqlDate = new Date(
				java.util.Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
		// date2 = new Date(date.getValue().toEpochDay());
	}

	@FXML
	private void checkWood() {
		// NEEDS TESTING
		if (Wood.getText().equals("")) {
		} else if (!isNumber(Wood.getText())) {
			woodErr.setText("Please choose a numerical value");
			Wood.setText("");
		} else {
			woodErr.setText("");
		}
	}

	private boolean isNumber(String a) {
		try {
			double number = Double.parseDouble(a);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@FXML
	private Label textTest;
	
	@FXML
	private void checkInputLen()
	//TODO Fix this so it does not get called twice! (but still works)
	{
		System.out.println(textArea.getText().length());
		if(textArea.getText().length() >= 255)
		{
			textArea.setText(comments);
//			textArea.cancelEdit();
			textArea.end();
			textErr.setText("maximum 255characters");
		}
		else
		{
			comments = textArea.getText();
			textErr.setText("");
		}
	}

	@FXML
	public void sendReport() {
		dateWarning.setText("");
		woodErr.setText("");
		cabinErr.setText("");
		if (date.getValue() == null) {
			dateWarning.setText("Please choose a date");
			date.requestFocus();
//			java.util.Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime())
		} else if(java.util.Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime() > System.currentTimeMillis())
			{
			dateWarning.setText("We've got to go back Marty, back to the future!");
			date.requestFocus();
		}else if (Wood.getText().equals(null) || !isNumber(Wood.getText())) {
			woodErr.setText("Please fill in the amount of remaining wood");
			Wood.requestFocus();
		} else if (Double.parseDouble(Wood.getText()) > 20) {
			Wood.requestFocus();
			woodErr.setText("That estimate seems a bit high");
		} else if (dropdown.getValue() == null) {
			cabinErr.setText("Venligst velg en koie");
			dropdown.requestFocus();
		} else {
			fillList();
			setCommodities();
			setText();
			Connection con = DBTools.quickConnect();
			int cabin = cabinToInt(dropdown.getValue());
			// System.out.println(new Date(Integer.parseInt(commodities)));
			DBTools.insertIntoReport(con, "'" + sqlDate + "'", "" + cabin, "'" + this.MainApp.getUsername() + "'",
					"'" + commodities + "'", "'" + comments + "'", wood);
			MainApp.closePopup();
		}
	}

	private int cabinToInt(String cabin) {
			
			try {
				String tekst = "SELECT CabinID FROM Cabin WHERE Name='"+cabin+"'";
				Connection con = MainApp.getConnection();
				PreparedStatement statement = (PreparedStatement) con.prepareStatement(tekst);
				ResultSet result = statement.executeQuery();
				int k = 0;
				while(result.next()){
					k = result.getInt("CabinID");
				}
				return k;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return 0; 
		}

	public void setCabinDrop(ArrayList<String> list) {
		dropdown.getItems().addAll(list);


	}
}
