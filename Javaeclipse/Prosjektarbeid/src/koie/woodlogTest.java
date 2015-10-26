package koie;

public class woodlogTest {

	public static void main(String[] args) {
		
		Cabin cabin = new Cabin(50, 10, 1, "sentrum");
		System.out.println("Skal være 50:");
		System.out.println(cabin.getWoodStatus());
		System.out.println("Skal være 0:");
		System.out.println(cabin.getWoodPrognosis());
		cabin.setWoodStatus(46);
		System.out.println("Skal være 46:");
		System.out.println(cabin.getWoodStatus());
		System.out.println("Skal være 4:");
		System.out.println(cabin.getWoodPrognosis());
		cabin.setWoodStatus(38);
		System.out.println("Skal være 38:");
		System.out.println(cabin.getWoodStatus());
		System.out.println("Skal være 6:");
		System.out.println(cabin.getWoodPrognosis());
		
		

	}

}
