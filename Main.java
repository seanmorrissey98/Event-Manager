import java.io.*;
import javax.swing.*;
import java.util.*;

public class Main
{
	final static String userFileName="Users.txt";
	final static String facilityFileName="Facilities.txt";
	final static String bookingFileName="Bookings.txt";
	private static int currentID;
 	private static int userType;
	private static ArrayList<User> users		  = new ArrayList<User>();
	private static ArrayList<Facility> facilities = new ArrayList<Facility>();
	private static ArrayList<Booking> bookings 	  = new ArrayList<Booking>();
 
	
	public static void main(String [] args)
	{

		createNewUser();
		createNewUser();
				
			//	System.out.println(users.get(0).getEmail() + "LN23");
			//	System.out.println(users.get(1).getEmail()+ "LN24");
		createNewUser();
		for(int i = 0;i<users.size();i++)
	{
			System.out.println(users.get(i).getEmail());
	}
	//	System.out.println(users.get(0).getEmail() +"LN26");
		//System.out.println(users.get(1).getEmail() +"LN27");
		//System.out.println(users.get(2).getEmail() +"LN28");
		
	}

	public static void adminMenu()
	{
		String [] initialOptions = { "Register User", "Facility Menu", "Record Payments", "View Account Statements" };
		String [] subOptions	 = {"Add Facility","View Facility Availability", "View Facility Bookings", "Remove Facility", "Decommission Facility", "Recommission Facility","Make Booking"};
	        boolean main = true;
		int x = 0;
		while(main && x==0||x==1||x==2||x==3)  // && not null 
		{	
		    x = optionBoxs(initialOptions,"Choose an option");
		    int y = 0;
		    int z = 0;
		
		    switch (x)
		    {
			    case 0: createNewUser();
		        break;
			    case 1: y = optionBoxs(subOptions,"Choose an option");
					switch (y)
					{
						case 0: createNewFacility();
						break;
						case 1: //Facility Availability
						break;
						case 2:	//View Facility Bookings		 
						break;
						case 3: //Remove Facility
						break;
						case 4: //Decommission Facility
						break;
						case 5: //Recommission Facility
						break;
						case 6: //Make Booking
						break;
					}
                break;
				case 2: //Record Payments;
				break;
				case 3: //View Account Statements;
				break;
			}
		}
	}
	
	public static void userMenu()
	{
		String [] initialOptions = { "View Bookings", "View Account Statement"};
	    boolean main = true;
		int x = 0;
		while (main && x==0||x==1||x==2||x==3)  // && not null 
		{	
		    x = optionBoxs(initialOptions,"Choose an option");
		    int y = 0;
		    switch (x)
		    {
			    case 0: //View Bookings
		        break;
			    case 1: //View Account Statement
                break;				
			}
		}	
	}
	
	
	
	

  public static String generatePassword()
	{
		boolean isValidPassword 	= false;
		int passwordLength			= (int)((Math.random()*3)+8);
		int positionOfCharacter;
		String password				= "";
		String pattern				= "(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*?[^\\w]).{8,}";
		String possibleCharacters	= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghiklmnopqrstuvwxyz1234567890!£$%^&*()_+=@;.#~/?<>:'-";
		while(!isValidPassword)
		{
			for(int i=0;i<passwordLength;i++)
			{
				positionOfCharacter	= (int)(Math.random()*possibleCharacters.length()-1);
				password			= password+possibleCharacters.substring(positionOfCharacter,positionOfCharacter+1);
			}
			if(password.matches(pattern))
				isValidPassword = true;
			else
				password = "";
		}
		return password;
	}
  
	public static String menuBox(String options)
	{
		String input="";
		try
		{
			input = JOptionPane.showInputDialog(null,options);
			return input;
		}
		catch(Exception e)
		{
		JOptionPane.showMessageDialog(null,"Error: no String entered");
		return menuBox(options);
		}
	}
	
	public static int menuBoxInt(String options)
	{
		String input="";
		int inputAsInt=0;
		try
		{
			input=JOptionPane.showInputDialog(null,options);
			inputAsInt=Integer.parseInt(input);
			return inputAsInt;
		}
		catch(NumberFormatException e)
		{
		JOptionPane.showMessageDialog(null,"Error:Input is not a number entered");
		return menuBoxInt(options);
		}
	}
	
	public static void createNewUser()
	{
		String email	= menuBox("Please enter an email:");
		while (!(validEmail(email)))
		{
			email 		= menuBox("Please enter a valid email:");
			validEmail(email);
		}
		while (nameExists(email))
		{
			email 		= menuBox("Email already registered.\nPlease enter another email");
			nameExists(email);
		}

		String password	= generatePassword();
		int userType	= 2;
		int userId 		= users.size()+1;
		
		User aUser;
		aUser 			= new User(userId, email, password, userType);
		users.add(aUser);
		String info		= aUser.userToString();
		writeFile(info,userFileName);
	}
	
	
	public static void writeFile(String input, String fileName)
    {
		try
	    {
            FileWriter aFileWriter = new FileWriter(fileName,true);
            PrintWriter out 	   = new PrintWriter(aFileWriter);
	    	out.print(input);
            out.println();
            out.close();
            aFileWriter.close();		
	    }
	    catch(Exception e)
	    {}
    }
	

	public static int optionBoxs(String[] options,String whatYouWantItToSay)
	{
        int result = JOptionPane.showOptionDialog(null, whatYouWantItToSay, "League Manager", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return result;
	}
	/**
	  * Searches a file for a given string in a given position and regenerates the file without the line
	  * Copies every line from the file except the specified line and writes them to a temp file
	  * The original file is then deleted and the temp file is renamed to the name of the original file
	  * Input: Takes the filename, the string item to search for, and its position in a line
	  **/
	public static void removeLineFromFile(String fileName, String itemInLineToDel, int pos)
	{
		try 
		{
		    String[] fileElements;
			String line = "";
			File inFile = new File(fileName);
			if (!inFile.isFile()) 
			{
				System.out.println("Parameter is not an existing file");
				return;
			}
			File tempFile 	  = new File("temp.txt");
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			PrintWriter pw 	  = new PrintWriter(new FileWriter(tempFile));
			while ((line = br.readLine()) != null) 
			{
				fileElements = line.split(","); 
				if (!fileElements[pos].equals(itemInLineToDel)) 
				{
				pw.println(line);
				pw.flush();
				}
			}
			pw.close();
			br.close();
			//Delete the original file
			if (!inFile.delete()) 
			{
			System.out.println("Could not delete file");
			return;
			} 
			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile)) 
			{
				System.out.println("Could not rename file");
				return;
			}
       } 
		catch (FileNotFoundException ex) 
		{
          ex.printStackTrace();
        } 
		catch (IOException ex) 
		{
         ex.printStackTrace();
        }
	}
		
	public static boolean nameExists(String email)
	{
		boolean found = false;
		for (int i = 0;i< users.size();i++)
		{
			if (users.get(i).getEmail().equals(email))
			{
				found = true;
				return found;
			}
		}
		return found;
	}
	
	
	
	
	
	
	
	
	/**
	  * Checks if an inputted string is a valid email address by comparing it to
	  * a simple email pattern
	  * Input: Takes an email string
	  * Output: Returns a true boolean value if the email matches the pattern and 
	  * returns false if it does not
	  **/
	public static boolean validEmail(String email)
	{
		boolean isValid = false; 
		String pattern  = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$");
		
		if (email.matches(pattern))
			isValid = true;
		
		return isValid;
	}
}
