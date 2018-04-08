import javax.swing.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class Main
{
	final static String userFile 		= "users.txt";
	final static String facilityFile 	= "facilities.txt";
	final static String bookingFile 	= "bookings.txt";
	private static int currentUserId;
	private static int currentUserType; //1 = admin, 2 = user;
	private static ArrayList<User> users		  = new ArrayList<User>();
	private static ArrayList<Facility> facilities = new ArrayList<Facility>();
	private static ArrayList<Booking> bookings 	  = new ArrayList<Booking>();
	
	
	public static void main (String[] args)
	{
		restore();
		checkFacilityAvailability();
	}

	public static void restore() //TESTING***** ON START LOADS FROM FILE TO ARRAYLISTS 
	{
		Scanner in;
		String[] fileElements;
		try
		{
			File userFileName 		= new File(userFile);
			File facilityFileName	= new File(facilityFile);
			File bookingFileName 	= new File(bookingFile);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String datePattern 		= "(0-9){2}/(0-9){2}(0-9){4}";
			in = new Scanner(userFileName);
			while(in.hasNext())
			{
				fileElements 			= in.nextLine().split(",");
				int	a 					= Integer.parseInt(fileElements[0]);
				String b				= fileElements[1];
				String c				= fileElements[2];
				int d 					= Integer.parseInt(fileElements[3]);
				User aUser  			= new User(a, b, c, d);
				users.add(aUser);
			}
			in = new Scanner(facilityFileName);
			while(in.hasNext())
			{
				int e;
				String f;
				double g;
				fileElements 			= in.nextLine().split(",");
				if (fileElements.length == 3)
				{
					e					= Integer.parseInt(fileElements[0]);
					f 					= fileElements[1];
					g					= Double.parseDouble(fileElements[2]);
					Facility aFacility 	= new Facility(e, f, g);
					facilities.add(aFacility);
				}
				else
				{
					e					= Integer.parseInt(fileElements[0]);
					f 					= fileElements[1];
					g					= Double.parseDouble(fileElements[2]);
					String temp1		= fileElements[3];
					Date h				= format.parse(temp1);
					Facility bFacility 	= new Facility(e, f, g, h);
					facilities.add(bFacility);
				}
			}
			in = new Scanner(bookingFileName);
			while(in.hasNext())
			{
				fileElements			= in.nextLine().split(",");
				int i 					= Integer.parseInt(fileElements[0]);
				int j 					= Integer.parseInt(fileElements[1]);
				int k 					= Integer.parseInt(fileElements[2]);
				String temp2			= fileElements[3];
				Date l					= format.parse(temp2);
				int m					= Integer.parseInt(fileElements[4]);
				boolean n				= Boolean.parseBoolean(fileElements[5]);
				Booking aBooking		= new Booking(i, j, k, l, m, n);
				bookings.add(aBooking);
			}
			in.close();
		}
		catch(Exception e)
		{}
	}
	
	public static boolean loginMethod(String email, String password) 
	{
		boolean found = false;
		int loginAttempts = 3;
		try
		{
			while(!found)
			{
				for (int i=0;i<users.size();i++)
				{
					if (users.get(0).getEmail().equalsIgnoreCase(email) && users.get(i).getPassword().equals(password))
					{
						found = true;
						currentUserId 	= users.get(i).getUserId();
						currentUserType = users.get(i).getUserType();
					}	
				}
				if (!found)
				{
					loginAttempts--;
					if (loginAttempts == 0)
					{
						JOptionPane.showMessageDialog(null, "No attempts remaining");
						break;
					}
					JOptionPane.showMessageDialog(null, "Incorrect login details.\n" + loginAttempts + " attempts remaining:");
					email	 = JOptionPane.showInputDialog(null, "Enter email");
					password = JOptionPane.showInputDialog(null, "Enter password");
				}
				else
					JOptionPane.showMessageDialog(null, "Successfully logged in as " + email);
			}
		}
		catch(Exception e)
		{}
		return found;
	}

	public static void adminMainMenu() //EMPTY
	{
	}
	
	public static void userMainMenu() //EMPTY
	{
		
	}

	public static boolean isValidEmail(String email)
	{
		boolean isValid = false;
		String pattern1  = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$");
		String pattern2  = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})+(.[a-zA-Z]{2,})$");
		if (email.matches(pattern1) || email.matches(pattern2))
			isValid = true;
		return isValid;
	}

	public static boolean emailExists(String email)
	{
		boolean found = false;
		
		for(int i=0;i<users.size();i++)
		{
			if (users.get(i).getEmail().equals(email))
				found = true;
				return found;
		}
		return found;
	}

	public static void createNewUser()
	{
		String email = JOptionPane.showInputDialog(null, "Please enter an email:");
		while(!(isValidEmail(email)))
		{
			email = JOptionPane.showInputDialog(null, "Invalid email address.\nEnter another email:");
			isValidEmail(email);
		}
		while(emailExists(email))
		{
			email = JOptionPane.showInputDialog(null, "Email already registered.\nEnter another email:");
			emailExists(email);
		}
		int userId   = users.size()+1;
		User aUser   = new User(userId, email);
		users.add(aUser);
		String info  = aUser.userToString();
		writeFile(info, userFile);
	}
	
	public static void writeFile(String info, String fileName)
	{
		FileWriter aFileWriter;
		PrintWriter aPrintWriter;
		try
		{
			aFileWriter  = new FileWriter(fileName, true);
			aPrintWriter = new PrintWriter(aFileWriter);
			aPrintWriter.print(info);
			aPrintWriter.println();
			aPrintWriter.close();
			aFileWriter.close();
		}
		catch(Exception e)
		{}
	}

	public static void removeLineFromFile(String fileName, String itemInLineToDelete, int pos)
	{
		String line = "";
		String[] fileElements;
		Scanner in;
		PrintWriter pw;
		File aFile;
		File tempFile;
		try
		{
			tempFile = new File("temp.txt");
			aFile 	 = new File(fileName);
			in 		 = new Scanner(aFile);
			pw		 = new PrintWriter(new FileWriter(tempFile));
			if (!aFile.isFile())
				return;
			while(in.hasNext())
			{
				line 			= in.nextLine();
				fileElements	= line.split(",");
				if (!(fileElements[pos].equals(itemInLineToDelete)))
				{
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			//Delete original file
			if (!aFile.delete())
				return;
			//Rename new file to original filename
			if (!tempFile.renameTo(aFile))
				return;
		}
		catch(Exception e)
		{}
	}
	
	public static void createNewFacility()
	{
		try
		{
			SimpleDateFormat format 		= new SimpleDateFormat("dd/MM/yyyy");
			String datePattern 				= "(0-9){2}/(0-9){2}(0-9){4}";
			String facilityName 			= JOptionPane.showInputDialog(null, "Enter a facility name:");
			while (facilityExists(facilityName))
			{
				facilityName 				= JOptionPane.showInputDialog(null, "Facility already exists.\nPlease enter another facility name:");
				facilityExists(facilityName);
			}
			String pricePerHourString 		= JOptionPane.showInputDialog(null, "Enter a price per hour");
			double pricePerHour				= Double.parseDouble(pricePerHourString);
			int facilityId					= facilities.size()+1;
			String decommissionedUntil  	= JOptionPane.showInputDialog(null, "Enter a date your facility will be decommissioned until.");
			boolean dateFormatCheck			= false;
			while(!(decommissionedUntil.matches(datePattern)))
				decommissionedUntil  = JOptionPane.showInputDialog(null, "Enter a date your facility will be decommissioned until.\nFormat:(dd/mm/yyyy)");
			Date decommissionedUntilDate 	= format.parse(decommissionedUntil);
			Facility aFacility 				=	new Facility(facilityId, facilityName, pricePerHour, decommissionedUntilDate); 
			String info 					= aFacility.facilityToString();
			writeFile(info, facilityFile);
		}
		catch (Exception e)
		{}
	}
	
	public static boolean facilityExists(String facilityName)
	{
		boolean found = false;
		for (int i=0;i<facilities.size();i++)
		{
			if (facilities.get(i).getFacilityName().equals(facilityName))
				found = true;
				return found;
		}
		return found;
	}
	
	public static void checkFacilityAvailability()
	{
		try
		{
			SimpleDateFormat format 		= new SimpleDateFormat("dd/MM/yyyy");
		//	String datePattern 				= "(0-9){2}/(0-9){2}(0-9){4}"; //CHANGE TO ALLOW CURRENT DATES ONLY
			
			String[] timeSlots = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
			String[] options 	= new String[facilities.size()];
			for (int i=0;i<facilities.size();i++)
			{

				options[i] 	   = facilities.get(i).getFacilityName();
			}
			boolean available = false;
			int timeSlotCheck = 0;
			System.out.println("TESTLN274");
			String facilityNameChoice 	  = dropDown(options, "Select a facility:");
			int facilityChoiceId		  = 0;

			System.out.println("TESTLN277");
			Date actualBookingDateChoice;
			String bookingDateChoice      = JOptionPane.showInputDialog(null, "Enter date");
			//while(!(bookingDateChoice.matches(datePattern)))
		//		bookingDateChoice 		  = JOptionPane.showInputDialog(null, "Enter date\nFormat(dd/mm/yyyy)");
			actualBookingDateChoice  	  = format.parse(bookingDateChoice);
			
			String timeSlotChoice		  = dropDown(timeSlots, "Select a time slot:");
			int actualTimeSlotChoice	  = Arrays.asList(timeSlots).indexOf(timeSlotChoice) + 1;
			
			for (int j= 0;j<facilities.size();j++)
			{
				if (facilities.get(j).getFacilityName().equals(facilityNameChoice))
					facilityChoiceId = facilities.get(j).getFacilityId();
			}
			
			for (int k=0;k<bookings.size();k++)
			{
				if (bookings.get(k).getFacilityId() == facilityChoiceId && bookings.get(k).getBookingDate() == actualBookingDateChoice)
				{		
					timeSlotCheck = bookings.get(k).getBookingSlot();
					if (timeSlotCheck != actualTimeSlotChoice)
						available = true;
				}	
			}
			if (available)
			{
				JOptionPane.showMessageDialog(null, "This facility is available at this time slot."); //Give option to make booking here
			}	
		}
		catch(Exception e)
		{}
		
	}
	
	public static String dropDown(String[] options, String dialogText)
	{
		String selection = "";
		if (options.length != 0)
		{
			selection = (String)JOptionPane.showInputDialog(null, dialogText, "Input", 1, null, options, options[0]);
		}
		return selection;
	}
	
}
