package koie;

public class woodlogTest {

	public static void main(String[] args) {
		
		Cabin cabin = new Cabin(50, 10, 1, "sentrum");
		System.out.println("Skal v�re 50:");
		System.out.println(cabin.getWoodStatus());
		System.out.println("Skal v�re 0:");
		System.out.println(cabin.getWoodPrognosis());
		cabin.setWoodStatus(46);
		System.out.println("Skal v�re 46:");
		System.out.println(cabin.getWoodStatus());
		System.out.println("Skal v�re 4:");
		System.out.println(cabin.getWoodPrognosis());
		cabin.setWoodStatus(38);
		System.out.println("Skal v�re 38:");
		System.out.println(cabin.getWoodStatus());
		System.out.println("Skal v�re 6:");
		System.out.println(cabin.getWoodPrognosis());
		
		

	}

}
