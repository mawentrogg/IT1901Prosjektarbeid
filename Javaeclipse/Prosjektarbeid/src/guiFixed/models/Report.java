package guiFixed.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tools.DBTools;
public class Report{

	/* Brukeropplysninger */
	private int cabin;
	private String username;
	private String inventory;
	private String comment;
	
	private List<String> missing = new ArrayList<>();
	
//	øx, lampe, vaskeklut, kasserolle, sag, lampeglass, kjøkkenklut, mixer, sawbench, vaskemiddler,
//	bestikk, bok, spade, borste, cups&plates, hammer, primus, stekepanne, naaler, wick, rule, blanket,
//	extinguisher
	
	private final List<String> equipment = new ArrayList<String>(){{add("øks"); add("kerosenlampe"); 
	add("vaskeklut"); add("kasserolle"); add("sag"); add("ekstra lampeglass"); add("kjøkkenklut");
	add("mixer"); add("sawbench"); add("vaskemiddel"); add("bestikk"); add("hyttebok"); add("spade");
	add("oppvaskbørste"); add("kopper og tallerkner"); add("hammer"); add("primus"); add("stekepanne");
	add("2 rensenåler til primus"); add("ekstra veke til lampene"); add("tommestokk"); add("brannteppe");
	add("branslukkingsapparat");}};

	//antall ved som bruker tenker å bruke
	private int numberoflogs;
	
	public Report (int cabin,String username, String inventory, String comment, int logs, Date date){
		this.cabin=cabin;
		this.username=username;
		this.inventory=inventory;
		this.comment= comment;
		this.numberoflogs= logs;
		makeEquipmentList();
	}


	
	public  void setcabin(int cabin) {
		// TODO Auto-generated method stub
	this.cabin=cabin;	
	}


	
	public void setusername(String navn) {
		// TODO Auto-generated method stub
		this.username=navn;
	}


	
	public void setinventory(String inventory) {
		// TODO Auto-generated method stub
		this.inventory=inventory;
	}


	
	public void setcomment(String comment) {
		// TODO Auto-generated method stub
		this.comment=comment;
	}


	
	public void setnumberoglogs(int logs) {
		// TODO Auto-generated method stub
		this.numberoflogs=logs;
	}


	
	public int getcabin() {
		// TODO Auto-generated method stub
		return this.cabin;
	}



	public String getusername() {
		// TODO Auto-generated method stub
		return this.username;
	}



	public String getinventory() {
		// TODO Auto-generated method stub
		return this.inventory;
	}



	public String getcomment() {
		// TODO Auto-generated method stub
		return this.comment;
	}



	public int getnumberoflogs() {
		// TODO Auto-generated method stub
		return this.numberoflogs;
	}

	
	public String writemoreerrors(String morefailures ){
		String feil=morefailures;
		
		return feil;
		
	}
	
	//Må Køyra funksjonen for å få utstyr inn i lista. Alle ting får verdien true. 
	public void makeEquipmentList() 
	{
		for(int i = 0; i < inventory.length(); i++)
		{
			if(inventory.charAt(i) == '1')
			{
				missing.add(equipment.get(i));
			}
		}
	}
	
	public List<String> getMissing()
	{
		return this.missing;
	}

	public void sendReport(){
		Connection con = DBTools.quickConnect();
		String insertToTable = "INSERT INTO Reportuser" + "(CABIN, USERNAME, INVENTORY, COMMENT, DATE, TIMESTAMP, LOGS) VALUES" + "(?,?,?,?,?,?,?)";
		try {
			PreparedStatement report = con.prepareStatement(insertToTable);
			
			report.setInt(1, 5); 
			report.setString(2, getusername());
			report.setString(3, getinventory() );
			report.setString(4, getcomment());
			report.setDate(5, null);
			report.setInt(6, 234);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	
	
}
