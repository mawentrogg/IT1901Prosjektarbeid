package guiFixed.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tools.DBTools;

public class woodlogPrognosis {
	//Denne funksjonen køyrer dei andre funksjonane, slik at ein kun treng å køyre denne. 
	public static ArrayList woodlogPrognosisProcess(int cabinNr){
		ArrayList<ArrayList> listWithList = dataFromDB(cabinNr);
		ArrayList<Map> hashMapList = new ArrayList<Map>();
		int totalWoodUsed = findTotal(listWithList);
		double woodUsagePerDay = findWoodlogUsagePerDay(totalWoodUsed);
		Map<Integer, Integer> woodlogUsedMap = woodlogUsedHashMap(listWithList);
		Map<Integer, Integer> prognosisMap = prognosisHashMap(listWithList, woodUsagePerDay);
		hashMapList.add(woodlogUsedMap);
		hashMapList.add(prognosisMap);
		return hashMapList;
	}
	
	//Hentar dato og vedstatus fra DB, returnerar som ArrayList<ArrayList<int>>. 
	//[[ved, dag mnd],[ved,dag,mnd]]
	public static ArrayList dataFromDB(int cabinNr){
		Connection con = DBTools.quickConnect();
		DBTools.getTables(con);
		ResultSet result;
		
		ArrayList<ArrayList> listWithList = new ArrayList<ArrayList>();
		String date;
		String[] dateSplit;
		String[] daySplit;
		String day;
		String month;
		Integer intDay;
		Integer intMonth;
		try {
			Statement statement = con.createStatement();
			result = statement.executeQuery("select * from Reportuser");
			while (result.next()){
				ArrayList<Integer> listen = new ArrayList<Integer>();
				if(result.getInt("cabin") == cabinNr ) {
					date = result.getString("date");
					dateSplit = date.split("-");
					month = dateSplit[1];
					day = dateSplit[2];
					intDay = Integer.valueOf(day);
					intMonth = Integer.valueOf(month);
					listen.add(result.getInt("logs"));
					listen.add(intDay);
					listen.add(intMonth);
					listWithList.add(listen);
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return listWithList;
	}
	
	//Reknar ut total bruk fra listene som kjem fra DB. 
	public static int findTotal(ArrayList<ArrayList> listen){
		int difference;
		int previousWoodlogStatus = 0;
		int currentTotal = 0;
		for(ArrayList<Integer> list:listen){
			if(previousWoodlogStatus == 0){
				previousWoodlogStatus = list.get(0);
				continue;
			}
			difference = previousWoodlogStatus - list.get(0);
			if(difference < 0){
				previousWoodlogStatus = list.get(0);
				continue;
			}else{
				currentTotal += difference;
				
			}
			previousWoodlogStatus = list.get(0);
		}
		return currentTotal;
	}

	//Brukar lista fra DB og lagar Hashmap for ved bruk. 
	//Map<dag,vedstatus>, der siste raport blir satt til dag 0. Alt før får ein negativ verdi. 
	public static Map woodlogUsedHashMap(ArrayList<ArrayList> listen){
		Map<Integer, Integer> woodlogUsed = new HashMap<>();
		int lastInListen = listen.size()-1;
		int dayOfLastReport = (int) listen.get(lastInListen).get(1);
		int monthOfLastReport = (int) listen.get(lastInListen).get(2);
		int daysIntoYearOnLastReport = daysIntoYear(dayOfLastReport, monthOfLastReport); //Dag inn i året for siste raporten. 
		woodlogUsed.put(-30, (int)listen.get(0).get(0)); //For å få grafen til å gå heilt fra starten.
		for(ArrayList<Integer> list:listen){//Går gjennom lista med listene for kvar raport
			woodlogUsed.put((daysIntoYear(list.get(1),list.get(2)) - daysIntoYearOnLastReport),list.get(0));
		}	//Reknar ut kor mange dagar før siste raport nåverande raport var, og legg det i HashMap saman med vedstatus. 
			//blir altså .put(dagCurrentRaport - dagSisteRaport, vedstatusCurrentRaport. 
		
		return woodlogUsed;
	}
	
	//Lagar HashMap som startar der den andre slutta, og legger til 30 dagar etter, med ny vedstatus. 
	public static Map prognosisHashMap ( ArrayList<ArrayList> listen, double woodUsagePerDay){
		Map<Integer, Integer> woodlogPrognosis = new HashMap<>();
		int woodstatusFromLastReport = (Integer) listen.get(listen.size()-1).get(0); //Der vedbruk slutta. 
		woodlogPrognosis.put(0,woodstatusFromLastReport);
		if(woodstatusFromLastReport < woodUsagePerDay * 30){
			int daysToZeroWood = (int) ( woodstatusFromLastReport/woodUsagePerDay);
			woodlogPrognosis.put(daysToZeroWood,0);
		}else{
			int woodlogPrognosisAtEnd = (int) (woodstatusFromLastReport - Math.round(woodUsagePerDay * 30));//30 dagar fram i tid.
			woodlogPrognosis.put(30,woodlogPrognosisAtEnd);
		}
		
		//int woodlogPrognosisAtEnd = (int) (woodstatusFromLastReport - Math.round(woodUsagePerDay * 30));//30 dagar fram i tid.
		//woodlogPrognosis.put(30,woodlogPrognosisAtEnd);
		
		
		return woodlogPrognosis;
	}
	
	//Funksjon for å rekne ut kva dag i eit år ein gitt dato er. 
	public static int daysIntoYear(int day, int month){
		int days = 0;
		if(month==1){
			days += 0;
		}else if(month==2){
			days += 31;
		}else if(month==3){
			days += 59;
		}else if(month==4){
			days += 90;
		}else if(month==5){
			days += 120;
		}else if(month==6){
			days += 151;
		}else if(month==7){
			days += 181;
		}else if(month==8){
			days += 212;
		}else if(month==9){
			days += 243;
		}else if(month==10){
			days += 273;
		}else if(month==11){
			days += 304;
		}else if(month==12){
			days += 334;
		}
		days += day;
		
		return days;
	}
	
	//Reknar ut kor mykje som blir brukt gjennomsnittleg på 30 dagar. 
	public static double findWoodlogUsagePerDay(int totalUsed){
		return totalUsed/30.0;
	}
	
}
