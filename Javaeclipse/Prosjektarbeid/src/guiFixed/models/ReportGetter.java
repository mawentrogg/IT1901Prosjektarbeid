package guiFixed.models;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rapport.Report;
import tools.DBTools;

public class ReportGetter {
	
	private List<String> completedListSorted;
	
	
	public List<Report> getReports(){
		try{
			Connection con = DBTools.quickConnect();
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery("select * from ( select * from Reportuser order by Date asc ) x group by Cabin");
			List<Report> reports = new ArrayList();
			while (result.next()){
				int cabinNumber = result.getInt("cabin");
				String username = result.getString("Username");
				String inventory = result.getString("Inventory");
				String comment = result.getString("comment");
				Date date = result.getDate("date");
				int logs = result.getInt("LOGS");
				int reportID = result.getInt("ReportID");
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
