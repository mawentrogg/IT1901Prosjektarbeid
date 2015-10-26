package main;
import rapport.Report;
import tools.DBTools;
import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;

import database.databaseTester;

public class Runfunction {
	
	public static void main(String[] args) {
		Scanner scanner= new Scanner(System.in);
		
		
		System.out.println("Write down cabin nr:");
		int cabinnr= scanner.nextInt();
		
		System.out.println("Username:");
		String username= scanner.next(); 
	
		
		
		System.out.println("inventory:");
		String inventory= scanner.next();
		
		System.out.println("Comment:");	
		String comment= scanner.next();
		
		System.out.println("How many logs have you planned to use?");
		int nrlogs= scanner.nextInt();
		
		scanner.close();
		
		Date date = null;
//		Report report = new Report(cabinnr, username, inventory, comment, nrlogs, date);
		/*  Report report = new report(Connection, String, Date, String[]);*/
		
//		
//		databaseTester addreport = new databaseTester();
//		
//		Connection con = DBTools.quickConnect();
//		DBTools.useDB(con, "kimera_koier");
//		DBTools.insertIntoTable2(con, "Reportuser",report.getcabin()+"", "'"+report.getusername()+"'","'"+ report.getinventory()+"'", "'"+report.getcomment()+"'", report.getnumberoflogs()+"");
//		
		
	}
}

