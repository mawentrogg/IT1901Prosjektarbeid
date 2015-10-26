package user;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import tools.*;


public class User {
	
	private String username;
	private String email;
	private String phoneNumber;
	private String name;
	Connection con = null;
	
	public User(String username, String email, String phoneNumber, String name){
		setUsername(username);
		setEmail(email);
		setPhoneNumber(phoneNumber);
		setName(name);
		con = DBTools.quickConnect();
		
	}
	
	private void setUsername(String newUsername){
		ResultSet b = DBTools.getTableValues(con, "users", "usernames");
		try 
		{
			while (b.next()){
				if (b.toString().equals(newUsername)){
					System.out.println("Username tken");
				}else{
					this.username = newUsername;
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getUsername(){
		return username;
	}
	
	private void setName(String name)
	{
		this.name=name;
	}
	
	private void setEmail(String newEmail){
		ResultSet b = DBTools.getTableValues(con, "users", "emails");
		try 
		{
			while (b.next()){
				if (b.toString().equals(newEmail)){
					System.out.println("Email already registered");
				}else{
					this.username = newEmail;
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getEmail(){
		return email;
	}
	
	private void setPhoneNumber(String newPhoneNumber){
		ResultSet b = DBTools.getTableValues(con, "users", "phoneNumbers");
		try 
		{
			while (b.next()){
				if (b.toString().equals(newPhoneNumber)){
					System.out.println("Username tken");
				}else{
					this.username = newPhoneNumber;
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public String getName()
	{
		return this.name;
	}

}
