package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBTools 
{

//	OBS most of these functions can return "null" instead of an actual value if the connection
//	doesn't work (this can be changed to throw an exception but I did not want to limit us before
//	discussing the severity of a faulty connection to a db)

	
	private static final String NTNU =  "mysql.stud.ntnu.no";
	private static final String HOME = "db4free.net";
	
	
//	=================================================== CONECTORS ================================================================
	public static Connection connectToServer(String hostName, String username, String password)
	{
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://"+hostName,username, password);
			System.out.println("connection established");
			return con;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static Connection quickConnect()
//	OBS DO NOT USE BEFORE USERNAME AND PASSWORD HAS BEEN DECIDED UPON BY THE GROUP!
	{
		try
		{	
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/kimera_koier","kimera_gruppe15", "cdji2005");
			System.out.println("connection established");
			return con;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static Connection connectToDBOnServer(String hostName, String database, String username, String password)
//	this is pretty much obsolete but I'll leave it here for now, you can just add /database to the end of "hostname" in connectToServer and
//	get the same effect
	{
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://"+hostName+"/" + database, username, password);
			System.out.println("connection established");
			return con;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
//	============================================================ SETTERS ============================================================
	
	public static void useDB(Connection con, String DBname)
//	Sets a database to current database for the connection (allows us to have multiple DB's and choose as we see fit)
	{
		try
		{
			Statement s = con.createStatement();
			s.executeUpdate("USE " + DBname);
			System.out.println("now using: " + DBname);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
//	============================================================== GETTERS ==============================================================
//	these are technically all querries as well but I have separated them since these return the structure of the database instead of spesific
//	values
	
	public static ResultSet getDatabases(Connection con)
//	returns the set of databases in the current connection, returns null if none exist
	{
		try {
			return con.getMetaData().getCatalogs();
		} catch (SQLException e) {
//			System.err.println(e);
			e.printStackTrace();
			return null;
			
		}
	}
	
	public static void printDBList(Connection con)
//	prints all the databasenames as strings
	{
		try
		{
			ResultSet dbs = con.getMetaData().getCatalogs();
			while(dbs.next())
			{
				System.out.println(dbs.getString(1));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static ResultSet getTables(Connection con)
//OBS This does assume you're using a database allready, let me know if you need one that connects you to a db and returns the tables as well
//	TODO STILL NEED TO TEST THAT THIS IS A QUERY NOT AN UPDATE (feel free to do so yourself if I haven't gotten through this [or let me know
//	if it's important to have getTables done due to you needing it]
	{
		try
		{
			Statement s = con.createStatement();
			return s.executeQuery("SHOW TABLES");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
//	============================================================ CREATORS ===========================================================
	
	public static void createNewDB(Connection con, String name)
//	creates a new database in con with the name name
	{
		try 
		{
			Statement s = con.createStatement();
			s.executeUpdate("CREATE DATABASE " + name);
			System.out.println("database created");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void createTable(Connection con, String name, String... rows)
//	takes a string of row names and their variable types (usefull ones are: INTEGER not NULL, 
//	VARCHAR and INTEGER)
//	All of these (except INTEGER not NULL) are on the format "vartype(M)" where M = max to display
//	For large blocks of text use blob (this is a vartype)
//	if you need floating point values (decimals) specify it with FLOAT/DOUBLE(M, PRECISSION[IE: numbers behind comma])
//	For time use "timestamp(M)" where M gives the precision of the timestamp
//	timestamp(8) = YYYYMMDD   &  timestamp(12) = YYYYMMDDHHMMSS			(Y = year, M = month, D = Day, H = Hour, M = min, S = sec)
//	The format is "rowname vartype"
	{
		String command = "";
		boolean i = true;
		for(String row1 : rows)
		{
			if(i)
			{
				command = row1;
				i = false;
			}
			else
			{
				command = command + ", " + row1;
			}
		}
		try
		{
			Statement s = con.createStatement();
			s.executeUpdate("CREATE TABLE " + name + " (" + command + ")");
			System.out.println("table created");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void createStandardStringTable(Connection con,String name, String... rowNames)
//	This creates a standard all-string table without the need to specify types
//	The format will be: 
//	id[INTEGER not NULL] + rowName1[VARCHAR(255)] + ... + rowNameM[VARCHAR(255)] + KEY (id))
	{
		String command = "id INTEGER not NULL,";
		int i = 0;
		for(String x : rowNames)
		{
			command = command +" "+ x + " varchar(255),";
		}
		try
		{
			Statement s = con.createStatement();
			s.executeUpdate("CREATE TABLE " + name + " (" + command +" PRIMARY KEY(id) )");
			System.out.println("table created");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
//			System.err.println(e);
		}
	}
	
	
//	=========================================================== EDITORS ===============================================================
	
	public static void insertIntoTable(Connection con, String table, String... values)
//	a test is needed but this might be the case:
//	[MAYBE!] numerical values are ok, string values should be surrounded by '' (ex: 'hello') [MAYBE!]
//	it's probably ok to not do what's above but if you get errors try it
	{
		String insertValues = "";
		boolean i = true;
		for (String x : values)
		{
			if(i)
			{
				insertValues += x;
				i = false;
			}
			else
			{
				insertValues += ","+x;
			}
		}
		try
		{
			Statement s = con.createStatement();
			s.executeUpdate("INSERT INTO " + table + " VALUES (" + insertValues + ")");
			System.out.println("table updated");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
//			System.err.println(e);
		}
	}
	
	
	public static void insertIntoTable2(Connection con, String table, String... values)
	{
		
		// TODO add date support
		String insertValues = "";
		int i = 0;
		for (String x : values)
		{
			if(i==0)
			{
				insertValues += x;
				i += 1;
			}
			
			else
			{
				insertValues += ","+x;
				i += 1;
			}

		}
		try
		{
			Statement s = con.createStatement();
			s.executeUpdate("INSERT INTO " + table + "(cabin, Username, Inventory, comment, LOGS) " +"VALUES" + "(" + insertValues + ")");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void insertIntoReport(Connection con, String date, String... values)
	{
		
		// TODO add date support
		String insertValues = "" + date;
//		int i = 0;
		for (String x : values)
		{
//			if(i==0)
//			{
//				insertValues += x;
//				i += 1;
//			}
//			
//			else
//			{
				insertValues += ","+x;
//				i += 1;
//			}

		}
		try
		{
			Statement s = con.createStatement();
			s.executeUpdate("INSERT INTO " + "Reportuser" + "(Date, cabin, Username, Inventory, comment, LOGS) " +"VALUES" + "(" + insertValues + ")");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}


	
//	public static void TableInsertSpesific(Connection con, String... TargetsAndValues)
//	{
////		TODO
////		need to think of a clever way to do this (ie, a way where this is not less practical than just writing the statement in the code
////		for now just use insertIntoTable and leave a field as "" if you don't want anything in it (not ideal but it will do for now)
////		[Let me know if you need it to be fixed as a priority for some reason -Andreas]
//	}
	
	
//	============================================================= QUERIES ==============================================================
	
	public static ResultSet readTable(Connection con, String table)
//	returns the set of values in the table (these can be iterated through and the contents read
//	as various data types [inc String, and int])
	{
		try
		{
			Statement s = con.createStatement();
			return s.executeQuery("SELECT * FROM " + table);
		}
		catch(SQLException e)
		{
//			System.err.println(e);
			e.printStackTrace();
			return null;
		}
	}
	
	public static ResultSet getTableValues(Connection con, String table, String... coloumns)
//	will make a more specific one a little later, let me know if you need it immediately and I can get to work on it -Andreas
	{
		try
		{
			Statement s = con.createStatement();
			String data = "";
			boolean i = true;
			for (String x : coloumns)
			{
				if(i)
				{
					data = x;
					i = false;
				}
				else
				{
					data = data + ", " + x;
				}
			}
			return s.executeQuery("SELECT "+ data + " FROM " + table);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static ResultSet getAllSortedTableValues(Connection con, String table, String... sortBy)
	{
		try
		{
			Statement s = con.createStatement();
			String query ="SELECT * FROM " + table + " ORDER BY";
			boolean i = true;
			for(String x : sortBy)
			{
				if(i)
				{
					query = query + x; 
					i = false;
				}
				else
				{
					query = query + "," + x; 
				}
			}
			return s.executeQuery(query);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
//	============================================================= RESULT SETS ===========================================================
	
	public List<String> ResultSetToString(ResultSet set)
//	OBS this returns all fields as strings, if you want ints or other values let me know and I can try to make a context sensitive one
//	if I can't do that in a good way this one might simply be a case of needing to be done individually for each time you need more than
//	strings (alternatly I can make one that returns all ints etc (or you can copy paste this code and just change "set.toString()" to:
//	"set.toX()" X = desired vartype			-Andreas
	{
		List<String> list = new ArrayList<>();
		try
		{
			while(set.next())
			{
				list.add(set.toString());
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	
//	=============================================================== DELETION ============================================================
//	PLEASE FOR THE LOVE OF GOD BE CAREFULL WHEN USING THIS (IF YOU EVER EVEN HAVE TO [WHICH YOU SHOULDN'T (SERIOUSLY NEVER!)!!!])!!!!
//	EVEN THE ONE FOR TABLES IS USUALLY NOT NEEDED!
	
	public static void deleteDatabase(Connection con, String database)
	{
		try
		{
			Statement s = con.createStatement();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Type 'DELETE' to delete database: "+database + "\n" +"WARNING: This is irreversible");
			try
			{
				if(reader.readLine().equals("DELETE"))
				{
					s.executeUpdate("DROP DATABASE "+ database);
					System.out.println("database deleted");
				}
			}
			catch(IOException f)
			{
				f.printStackTrace();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void deleteTable(Connection con, String table)
//	If you just want to remove the current data use "clearTable" this is for removing the table all together!
	{
		try
		{
			Statement s = con.createStatement();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Type 'DELETE' to delete table: "+ table + "\n" +"WARNING: This is irreversible");
			try
			{
				if(reader.readLine().equals("DELETE"))
				{
					s.executeUpdate("DROP TABLE "+ table);
					System.out.println("table deleted");
				}
			}
			catch(IOException f)
			{
				f.printStackTrace();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
//	This one's better but stil not awesome unless you want to clear a table outright. Use removeElements to clear spesific table values
	
	public static void clearTable(Connection con, String table)
	{
		try
		{
			Statement s = con.createStatement();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Type 'CLEAR' to clear table: "+ table + " of all data \n" +"WARNING: This is irreversible");
			try
			{
				if(reader.readLine().equals("CLEAR"))
				{
					s.executeUpdate("DELETE FROM "+ table);
					System.out.println("table cleared");
				}
			}
			catch(IOException f)
			{
				f.printStackTrace();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void removeElements(Connection con, String table, String... dataToRemove)
//	dataToRemove should be formated like: RowName="x" or other conditions
//	ie to remove "kim" from "names" data to remove is: names="kim"  (as far as I know '' is a valid substitute for "" here but if not (...)
//	(...) use 'names="kim"' instead of "names='kim'"
	{
		try
		{
			Statement s = con.createStatement();
			String conditions = "";
			boolean i = true;
			for (String x : dataToRemove)
			{
				if (i)
				{
					conditions = x;
					i = false;
				}
				else
				{
					conditions = conditions + " AND " + x;
				}
			}
			s.executeUpdate("DELETE FROM "+ table + " WHERE " + conditions);
			System.out.println("Elements removed");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
}
