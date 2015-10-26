package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import tools.DBTools;

public class databaseTester 
{	
	private final static String database = "db4free.net";
	private final static String a = "gruppe15";
	
	public static void writeToDB(Connection con)
	{
		DBTools.clearTable(con, "rapport");
		DBTools.insertIntoTable(con, "rapport", "1", "'no'", "'cabin is perfectly fine'");
	}
	
	public static void main(String[] args) 
	{
//		skaper et connection objekt knyttet til vår mySQL server
		Connection con = DBTools.quickConnect();
		
//		Connection con = DBTools.connectToServer(database, a, a);
//		DBTools.createNewDB(con, "testDB");
//		DBTools.createNewDB(con, "andrris_koier");
		//velger en spesifik database (bruk a som "koier")
//		DBTools.useDB(con, a);
//		DBTools.createStandardStringTable(con, "rapport", "hasWood", "cabinStatus");
//		writeToDB(con);
		DBTools.useDB(con, "kimera_koier");
//		DBTools.createTable(con, "rapport_eks", "cabin INTEGER(255)", "name varchar(255)", "tlf char(10)", "mail varchar(255)", "PRIMARY KEY(cabin)");
//		DBTools.getDatabases(con);
//		ResultSet a = DBTools.getTables(con);
//		try 
//		{
//			while(a.next())
//			{
//				System.out.println(a);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		DBTools.insertIntoTable(con, "rapport_eks", "2", "'Kim'", "'4712345678'", "'exmail@whaterver.com'");
		DBTools.createTable(con, "reports", "cabinID INTEGER not NULL", "Name varchar(255)", "phonenumber char(10)", "email varchar(255)", "numberOfLogs INTEGER(255), PRIMARY KEY(cabinID)");
	}
	
	
	
}
