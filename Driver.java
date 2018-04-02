import javax.swing.*;
import java.io.*;
import java.util.*;

public class Driver
{
	final static String userFileName = "users.txt";
	private static String currentID;
	
	public static void main (String[] args)
	{
		String 	username = JOptionPane.showInputDialog(null, "Enter username");
		String password = JOptionPane.showInputDialog(null,"Enter password");
		
		boolean isLoggedIn = loginMethod(username, password);
		if (isLoggedIn)
		{
			if (currentID.equals("A"))
				adminMainMenu();
			else
				userMainMenu();
		}
	}
	
	/**
	*ASSUMES ADMIN ID IS "A", AND USERS USE NUMBERS.
	**/
	public static boolean loginMethod(String username, String password)
	{
		File userFile = new File(userFileName);
		Scanner in;
		String[] fileElements;
		boolean found = false;
		int loginAttempts = 3;
		try
		{
			if (userFile.exists())
			{
				while (!found)
				{
					in = new Scanner(userFile);
					while (in.hasNext())
					{
						fileElements = in.nextLine().split(",");
						if (username.equals(fileElements[1]) && password.equals(fileElements[2]))
						{
							found = true;
							currentID = ((fileElements[0]));
						}
					}
					in.close();
					if (!found)
					{
						loginAttempts--; 
						if (loginAttempts == 0)
						{
							JOptionPane.showMessageDialog(null, "No attempts remaining.");
							break;
						}
						 JOptionPane.showMessageDialog(null, "Incorrect login details.\n" + loginAttempts + " attempt(s) remaining.");
						username = JOptionPane.showInputDialog(null, "Enter username");
						password = JOptionPane.showInputDialog(null,"Enter password");
					}
					else
						JOptionPane.showMessageDialog(null, "Successfully logged in as " + username);	
				}
			}
		}
		catch(Exception e)
		{}
		return found;
	} 
	
	public static void adminMainMenu() //TESTING METHOD
	{
		System.out.println("ADMIN");
	}
	
	public static void userMainMenu() //TESTING METHOD
	{
		System.out.println("USER");
	}
}