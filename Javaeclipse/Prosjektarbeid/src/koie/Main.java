package koie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

	private ArrayList<Cabin> cabins = new ArrayList<Cabin>();
	private Map<String, List<Cabin>> cabinarea = new HashMap<>();
	
	
	
	public void addCabin(String name, int wood, int bednr){
		int cabinnr = cabins.size()+1; 
		Cabin cabin = new Cabin(wood, bednr, cabinnr, name);
		cabins.add(cabin); //Har ikke implementert koieområde
	}
	
	public void changeWoodStatus(int cabinnr, int wood){
		Cabin cabin = cabins.get(cabinnr-1);
		cabin.setWoodStatus(wood);
		
	}
	
	public void changeEquipment(int cabinnr, String equipment, int amount){
		Cabin cabin = cabins.get(cabinnr-1);
		cabin.changeEquipments(equipment, amount);
	}
	
	public void changeMissing(int cabinnr, String missing, int amount){
		Cabin cabin = cabins.get(cabinnr-1);
		cabin.changeMissing(missing, amount);
	}
	
}
