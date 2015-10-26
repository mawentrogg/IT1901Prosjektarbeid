package database;
import java.sql.Connection;
import java.sql.ResultSet;

import tools.DBTools;

public class QuerryTester 
{
	private static final Connection con = DBTools.quickConnect();
			
	public ResultSet loadRepors(String table)
	{
		return DBTools.readTable(con, table);
	}
	
	public static void main(String[] args) 
	{
		DBTools.deleteTable(con, "Reportuser");
	}
}
