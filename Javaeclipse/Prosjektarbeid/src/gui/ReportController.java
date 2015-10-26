package gui;

import java.util.List;

import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tools.DBTools;

public class ReportController 
{
	private String comments;
	
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

    private List<CheckBox> checkboxes = new ArrayList<>();
    
    private void fillList()
    {
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
    

    @FXML
    private TextArea textArea;
	
	
	@FXML
	public void printUsername()
	{
		fillList();
		setCommodities();
		setText();
//		start();
		System.out.println(comments);
		System.out.println(commodities);
		System.out.println(sqlDate);
		
	}
	
	@FXML
	public void setText()
	{
		this.comments = textArea.getText();
		this.wood = Wood.getText();
	}
	
	public void setCommodities()
	{
		commodities = "";
		for(CheckBox n : checkboxes)
		{
			if(n.isSelected())
			{
				commodities += "1";
			}
			else
			{
				commodities += "0";
			}
		}
		sqlDate= new Date(java.util.Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
//		date2 = new Date(date.getValue().toEpochDay());
	}
	
	@FXML 
	private void checkWood()
	{
		//NEEDS TESTING
		if(Wood.getText().equals(""))
		{
		}
		else if(! isNumber(Wood.getText()))
		{
			woodErr.setText("Please choose a numerical value");
			Wood.setText("");
		}
		else
		{
			woodErr.setText("");
		}
	}
	
	private boolean isNumber(String a)
	{
		try
		{
			int number = Integer.parseInt(a);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
	
	@FXML
	public void start()
	{
		if(date.getValue() == null)
		{
			dateWarning.setText("Please choose a date");
			date.requestFocus();
		}
		else if(Wood.getText().equals(null) || ! isNumber(Wood.getText()))
		{
			
			Wood.requestFocus();
		}
		else
		{
			fillList();
			setCommodities();
			setText();
			Connection con = DBTools.quickConnect();
//			System.out.println(new Date(Integer.parseInt(commodities)));
			System.out.println(sqlDate);
			DBTools.insertIntoReport(con,"'"+ sqlDate+"'", "10", "'username'","'" + commodities + "'", "'" + comments + "'", wood);
		}
	}
}
