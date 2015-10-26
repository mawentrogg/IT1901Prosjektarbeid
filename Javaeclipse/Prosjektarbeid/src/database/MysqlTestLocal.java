package database;

import java.sql.*;

public class MysqlTestLocal {
	public static void main(String[] args) {
		try{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "kimera", "cdji2005");
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery("select * from city");
			while (result.next()){
				System.out.println(result.getInt("Population") + " " + result.getString("Name"));
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
