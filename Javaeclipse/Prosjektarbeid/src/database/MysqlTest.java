package database;

import java.sql.*;


public class MysqlTest {
	
	//Must be connected to NTNU via VPN, or at site.
	public static void main(String[] args) {
		try{
			
			String user = "kimera_gruppe15";
			String password = "cdji2005";
			String database = "kimera_venner";
			String host = "mysql.stud.ntnu.no";
//			String host = "mysql.stud.ntnu.no/";
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery("select * from venner");
			while (result.next()){
				System.out.println(result.getInt("tlf") + " " + result.getString("fornavn"));
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
