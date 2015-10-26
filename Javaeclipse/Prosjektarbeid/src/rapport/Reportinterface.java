package rapport;

public interface Reportinterface {
	void setcabin(int cabin);
	void setusername(String navn);
	void setinventory(String number);
	void setcomment(String mail);
	void setnumberoglogs(int logs);
	
	
	int getcabin();
	String getusername();
	String getinventory();
	String getcomment();
	int getnumberoflogs();
	
	
}
