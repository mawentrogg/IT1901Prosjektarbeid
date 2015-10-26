package database;
import rapport.Report;
import tools.DBTools;

import java.sql.*;

public class DBmanager {
	
	
	
	
	
	
	//Must be connected to NTNU via VPN, or at site.
	public static void main(String[] args) {
		
		Connection con = DBTools.quickConnect();
		DBTools.getTables(con);
		
		ResultSet result;
		try {
			Statement statement = con.createStatement();
			result = statement.executeQuery("select * from Reportuser");
			while (result.next()){
				System.out.println(result.getInt("cabin") + " " + result.getString("username"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String insertToTable = "INSERT INTO Reportuser" + "(CABIN, USERNAME, INVENTORY, COMMENT, DATE, TIMESTAMP, LOGS) VALUES" + "(?,?,?,?,?,?,?)";
		try {
			PreparedStatement report = con.prepareStatement(insertToTable);
			
			report.setInt(1, 1); 
			report.setString(2, "Andreas");
			report.setString(3, "inventory" );
			report.setString(4, "comment");
			report.setDate(5, null);
			report.setDate(6, null);
			report.setInt(7, 234);
			System.out.println("Updated");
			report.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try{
			
//			String user = "kimera_gruppe15";
//			String password = "cdji2005";
//			String database = "kimera_venner";
//			String host = "mysql.stud.ntnu.no";
////			String host = "mysql.stud.ntnu.no/";
//			Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
//			
//			Statement statement = conn.createStatement();
//			
//			ResultSet result = statement.executeQuery("select * from venner");
//			while (result.next()){
//				System.out.println(result.getInt("tlf") + " " + result.getString("fornavn"));
//			}
//			
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
	}


}
