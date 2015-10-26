package koie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import tools.DBTools;

public class woodlogMain {

	public static void main(String[] args) {
		ArrayList<Map> mapList = woodlogPrognosis.woodlogPrognosisProcess(99);
		//System.out.println(mapList.get(0)); //ved brukt map
		//System.out.println(mapList.get(1)); //ved prognose map
		
		

	}

}
