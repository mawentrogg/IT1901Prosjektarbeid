package koie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cabin {

	private final int cabinnr;
	private final String name;
	private int woodstatus;
	private int bednr;
	private ArrayList<Integer> woodlog;
	private int prevWoodPrognosis;
	private int sleepovernr;
	private Map<String, Integer> equipments = new HashMap<>();
	private Map<String, Integer> missing = new HashMap<>();

	public Cabin(int woodstatus, int beds, int cabinnr, String name) {
		if (woodstatus < 0 || beds < 0 || cabinnr < 0) {
			throw new IllegalArgumentException();
		} else {
			woodlog = new ArrayList<Integer>();
			sleepovernr = 0;
			prevWoodPrognosis = 0;
			setWoodStatus(woodstatus);
			this.cabinnr = cabinnr;
			this.bednr = beds;
			this.name = name;
			this.setEquipments(equipments);
		}
	}

	public int getWoodPrognosis() { // Returnerer et estimat på hvor mye ved som
									// brukes per overnatting
		if (woodlog.size() <= 1) {
			return getPrevWoodPrognosis(); // For kort logg for å kunne beregne
											// gjennomsnitt, så returnerer
											// forrige antatte prognose
		}
		int total = 0;
		int sleepovers = woodlog.size() - 1;
		for (int i = 0; i < sleepovers; i++) {
			total += woodlog.get(i) - woodlog.get(i + 1);
		}
		setPrevWoodPrognosis((int) total / (sleepovers)); // Estimerer et snitt
															// på kubber per
															// overnatting
		// Hvis vi vil returnere hvor mange overnattinger nåværende vedlager kan
		// vare:
		// daysLeft = (int) getWoodStatus()/getPrevWoodPrognosis();
		return getPrevWoodPrognosis();

	}

	public int getPrevWoodPrognosis() {
		return prevWoodPrognosis;
	}

	public void setPrevWoodPrognosis(int prevWoodPrognosis) {
		this.prevWoodPrognosis = prevWoodPrognosis;
	}

	public void addWoodstatus(int newstatus) {
		// Sjekker om det har blitt fylt på ved, isåfall må listen tømmes
		// for at gjennomsnittsberegningen skal bli riktig
		if (!woodlog.isEmpty() && newstatus > woodlog.get(woodlog.size() - 1)) {
				woodlog.clear();
			}
		woodlog.add(newstatus);
	}

	public int getWoodStatus() {
		return woodstatus;
	}

	public void setWoodStatus(int woodstatus) {
		this.woodstatus = woodstatus;
		addWoodstatus(woodstatus);
	}

	public int getBednr() {
		return bednr;
	}

	public int getSleepoverNr() {
		return sleepovernr;
	}

	public Map<String, Integer> getEquipments() {
		return equipments;
	}

	public void setEquipments(Map<String, Integer> equipment) {
		equipment.put("axe", 1);
		equipment.put("saw", 1);
		equipment.put("fire carpet", 1);
		equipment.put("cleaning needles", 2);
		equipment.put("primus", 1);
		equipment.put("operating manual", 1);
		equipment.put("sawbench", 1);
		equipment.put("spade", 1);
		equipment.put("kerosene lamp", 1);
		equipment.put("kitchen cloth", 1);
		equipment.put("washing-up cloth", 1);
		equipment.put("detergent", 1);
		equipment.put("washing- up brush", 1);
		equipment.put("lamp glass", 1);
		equipment.put("wick", 1);
		equipment.put("cutlery", 12);
		equipment.put("plates", 6);
		equipment.put("cups", 6);
		equipment.put("frying pan", 1);
		equipment.put("casseroles", 3);
		equipment.put("rotary beater", 1);
		equipment.put("cabin book", 1);
		equipment.put("hammer", 1);
		equipment.put("folding ruler", 1);

	}

	public void changeEquipments(String equipment, int amount) {
		equipments.put(equipment, amount);
	}

	public Map<String, Integer> getMissing() {
		return missing;
	}

	public void setMangler(Map<String, Integer> mangler) {
		this.missing = mangler;
	}

	public void changeMissing(String missingEquipment, int amount) {
		missing.put(missingEquipment, amount);
	}

	public void addSleepovernr() {
		sleepovernr++;
	}

}
