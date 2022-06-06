package edu.umgc.swen656.aop2;

import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class AddressBook {
	
	public static String logMessage;
	private final Map<String, HashMap<String, String>> addresses = new HashMap<>();
	private String ADDRESS_BOOK_FILE = "./src/edu/umgc/swen656/aop2/addresses.xml";
//	private String LOG_FILE = "./src/edu/umgc/swen656/aop2/logs.txt";
	
	public AddressBook() {
		loadCurrentAddressBook();
		printIntro();
		try {
			showUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadCurrentAddressBook() {
		try {
			File file = new File("./src/edu/umgc/swen656/aop2/addresses.xml");  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(file);  
			doc.getDocumentElement().normalize(); 
			NodeList nodeList = doc.getElementsByTagName("contact");  
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);  
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					String contactId = getValueFromTag(e, "contactId");
					String fullName = getValueFromTag(e, "fullName");
					String street = getValueFromTag(e, "street");
					String city = getValueFromTag(e, "city");
					String state = getValueFromTag(e, "state");
					String zip = getValueFromTag(e, "zip");
					String phoneNumber = getValueFromTag(e, "phoneNumber");
					
					HashMap<String, String> details = new HashMap<>();
					details.put("fullName", fullName);
					details.put("street", street);
					details.put("city", city);
					details.put("state", state);
					details.put("zip", zip);
					details.put("phoneNumber", phoneNumber);
					
					addresses.put(contactId, details);
				}  
			}  
		} catch(Exception e) {  
			e.printStackTrace();
		}
	}
	
	private static void printIntro() {
		System.out.println("Viet Nguyen - UMGC SWEN 656 - AOP Project 2");
		System.out.println("~*~ Address Book ~*~");
	}
	
	private void showUI() throws IOException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Pick an action by typing a number then hit Enter/Return.");
		System.out.println("1. Display the address book.");
		System.out.println("2. Add an contact to the address book.");
		System.out.println("3. Update a contact from the address book.");
		System.out.println("4. Delete a contact from the address book.");
		System.out.print("Pick an action: ");
		
		try {
			int action = sc.nextInt();
			doAction(action);
		} catch(InputMismatchException e) {
			// Catch input is not integer.
			System.out.println("\tInvalid input. Please enter a number.");
			showUI();
		}
		sc.close();
	}
	
	private void doAction(int action) throws IOException {
		switch (action) {
	        case 1: printAddressBook();
	        	break;
	        case 2: addContact();
	        	break;
	        case 3: updateContact();
	        	break;
	        case 4: deleteContact();
	        	break;
	        default: System.out.println("Invalid action.");
	        break;
		}
	}
	
	private void printAddressBook() {
		System.out.println("===== Current address book:");
		try {
			File file = new File(ADDRESS_BOOK_FILE);  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(file);  
			doc.getDocumentElement().normalize(); 
			NodeList nodeList = doc.getElementsByTagName("contact");  
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);  
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					String contactId = getValueFromTag(e, "contactId");
					String fullName = getValueFromTag(e, "fullName");
					String street = getValueFromTag(e, "street");
					String city = getValueFromTag(e, "city");
					String state = getValueFromTag(e, "state");
					String zip = getValueFromTag(e, "zip");
					String phoneNumber = getValueFromTag(e, "phoneNumber");
					
					System.out.println("\nContact ID: "+ contactId);  
					System.out.println("Full name: "+ fullName);  
					System.out.println("Address: " + street + ", " + 
							city + " " +
							state + " " + 
							zip + " " + 
							phoneNumber
					);
				}  
			}  
		} catch(Exception e) {  
			e.printStackTrace();
		}
	}
	
	private void addContact() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n----- Add new contact");
		
	    System.out.println("Full name:");
	    String fullName = sc.nextLine();
	    while (!isValidName(fullName)) {
	    	System.out.println("Invalid name. Name cannot be empty or contain digits.");
	    	System.out.println("Full name:");
		    fullName = sc.nextLine();
	    }
	    
	    System.out.println("Street:");
	    String street = sc.nextLine();
	    while (isEmpty(street)) {
	    	System.out.println("Invalid street name. Street name cannot be empty.");
	    	System.out.println("Street:");
	    	street = sc.nextLine();
	    }
	    
	    System.out.println("City:");
	    String city = sc.nextLine();
	    while (isEmpty(city)) {
	    	System.out.println("Invalid city name. City name cannot be empty.");
	    	System.out.println("City:");
	    	city = sc.nextLine();
	    }
	    
	    System.out.println("State (format: AB):");
	    String state = sc.nextLine();
	    while (!isValidState(state)) {
	    	System.out.println("Invalid state. State must have 2 uppercase letters.");
	    	System.out.println("State (format: AB):");
	    	state = sc.nextLine();
	    }
	    
	    System.out.println("Zip (format: 12345):");
	    String zip = sc.nextLine();
	    while (!isValidZip(zip)) {
	    	System.out.println("Invalid zip. Zip must have 5 digits.");
	    	System.out.println("Zip (format: 12345):");
	    	zip = sc.nextLine();
	    }
	    
	    System.out.println("Phone number (format: 2401231234):");
	    String phoneNumber = sc.nextLine();
	    while (!isValidPhoneNumber(phoneNumber)) {
	    	System.out.println("Invalid phone number. Phone number must have 10 digits.");
	    	System.out.println("Phone number:");
	    	phoneNumber = sc.nextLine();
	    }
	    
	    // Clean up inputs.
	    fullName = fullName.trim().replaceAll("\\s+"," ");
	    street = street.trim().replaceAll("\\s+"," ");
	    city = city.trim().replaceAll("\\s+"," ");
	    phoneNumber = addHyphen(phoneNumber);
	    sc.close();
	    
	    // Prepare contact details.
	    HashMap<String, String> details = new HashMap<>();
		details.put("fullName", fullName);
		details.put("street", street);
		details.put("city", city);
		details.put("state", state);
		details.put("zip", zip);
		details.put("phoneNumber", phoneNumber);
		
		// Generate unique Contact ID.
		String newId = generateId();
		while (addresses.containsKey(newId)) {
			newId = generateId();
		}
		// Save new contact to hash map.
	    addresses.put(newId, details);
	    try {
			updateAddressBook();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    System.out.println("\n===== New record added");
	    System.out.println("Contact name: " + fullName);
	    System.out.println("Address: " + street + ", " + city + " " + state + " " + zip + " " + phoneNumber);
	    String message = "New record added: ";
	    message += fullName + " " +
	    		street + " " + 
	    		city + " " +
	    		state + " " +
	    		zip + " " +
	    		phoneNumber;
	    logMessage = message;
	}
	
	private void updateContact() throws IOException {
		printAddressBook();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\n----- Update contact");
		System.out.println("Contact ID:");
		String contactId = sc.nextLine();
		
		if (addresses.containsKey(contactId)) {
			HashMap<String, String> contact = addresses.get(contactId);
			String fullName = contact.get("fullName");
			String street = contact.get("street");
			String city = contact.get("city");
			String state = contact.get("state");
			String zip = contact.get("zip");
			String phoneNumber = contact.get("phoneNumber");
			
			System.out.println("Street: " + street);
		    String newStreet = sc.nextLine();
		    newStreet = newStreet.trim().replaceAll("\\s+"," ");
		    // If user types in a new name, update the current name.
		    if (!isEmpty(newStreet)) {
		    	contact.put("street", newStreet);
		    	street = newStreet;
		    }
		    
		    System.out.println("City: " + city);
		    String newCity = sc.nextLine();
		    newCity = newCity.trim().replaceAll("\\s+"," ");
		    // If user types in a new name, update the current name.
		    if (!isEmpty(newCity)) {
		    	contact.put("city", newCity);
		    	city = newCity;
		    }
		    
		    System.out.println("State: " + state);
		    String newState = sc.nextLine();
		    // If user types in a new value, update.
		    if (!isEmpty(newState)) {
		    	while (!isValidState(newState)) {
			    	System.out.println("Invalid state. State must have 2 uppercase letters.");
			    	System.out.println("State (format: AB):");
			    	newState = sc.nextLine();
			    }
		    	contact.put("state", newState);
		    	state = newState;
		    }
		    
		    System.out.println("Zip: " + zip);
		    String newZip = sc.nextLine();
		    // If user types in a new value, update.
		    if (!isEmpty(newZip)) {
		    	while (!isValidZip(newZip)) {
			    	System.out.println("Invalid zip. Zip must have 5 digits.");
			    	System.out.println("Zip (format: 12345):");
			    	newZip = sc.nextLine();
			    }
		    	contact.put("zip", newZip);
		    	zip = newZip;
		    }
		    
		    System.out.println("Phone number: " + phoneNumber);
		    String newPhoneNumber = sc.nextLine();
		    // If user types in a new value, update.
		    if (!isEmpty(newPhoneNumber)) {
		    	while (!isValidPhoneNumber(newPhoneNumber)) {
			    	System.out.println("Invalid phone number. Phone number must have 10 digits.");
			    	System.out.println("Phone number:");
			    	newPhoneNumber = sc.nextLine();
			    }
		    	newPhoneNumber = addHyphen(newPhoneNumber);
		    	contact.put("phoneNumber", newPhoneNumber);
		    	phoneNumber = newPhoneNumber;
		    }
		    
			sc.close();
			
			String message = "Record updated: ";
			message += fullName + " " + 
					street + ", " + city + " " + state + " " + zip + " " + 
					phoneNumber;
			logMessage = message;
			
			updateAddressBook();
			
			return;
		}
		
		sc.close();
		System.out.println("Contact ID does not exist in the address book.");
	}
	
	private void deleteContact() throws IOException {
		printAddressBook();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\n----- Delete contact");
		System.out.println("Contact ID:");
		String contactId = sc.nextLine();
		sc.close();
		
		if (addresses.containsKey(contactId)) {
			HashMap<String, String> contact = addresses.get(contactId);
			String fullName = contact.get("fullName");
			String street = contact.get("street");
			String city = contact.get("city");
			String state = contact.get("state");
			String zip = contact.get("zip");
			String phoneNumber = contact.get("phoneNumber");
			String message = "Record deleted: ";
			message += fullName + " " + 
					street + ", " + city + " " + state + " " + zip + " " + 
					phoneNumber;
			
			addresses.remove(contactId);
			logMessage = message;
			updateAddressBook();
			
			return;
		}
		
		System.out.println("Contact ID does not exist in the address book.");
	}
	
	private String addHyphen(String phoneNumber) {
		return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
	}
	
	private void updateAddressBook() throws IOException {
		String newAddressBookStr = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>\n";
		newAddressBookStr += "<addressBook>\n";
		for(Map.Entry<String, HashMap<String, String>> addresses : addresses.entrySet()){
			String contactId = addresses.getKey();
			newAddressBookStr += "\t<contact>\n";
			newAddressBookStr += "\t\t<contactId>" + contactId + "</contactId>\n";
	        for (Map.Entry<String, String> entry : addresses.getValue().entrySet()) {
	        	newAddressBookStr += 
	        			"\t\t<" + entry.getKey() + ">" + 
	        			entry.getValue() + 
	        			"</" + entry.getKey() + ">\n";
	        }
	        newAddressBookStr += "\t</contact>\n";
	    }
		newAddressBookStr += "</addressBook>";
		BufferedWriter writer = new BufferedWriter(new FileWriter(ADDRESS_BOOK_FILE));
	    writer.write(newAddressBookStr);
	    writer.close();
	}
	
	private boolean isEmpty(String input) {
		if (input == null || input.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	private boolean isValidName(String name){
		if (isEmpty(name)) {
			return false;
		}
	    String pattern= "^[a-zA-Z ]*$";
	    return name.matches(pattern);
	}
	
	private boolean isValidState(String state){
		if (isEmpty(state) || state.length() != 2) {
			return false;
		}
	    String pattern= "^[A-Z]*$";
	    return state.matches(pattern);
	}
	
	private boolean isValidZip(String zip) {
		if (isEmpty(zip) || zip.length() != 5) {
			return false;
		}
		String pattern= "^[0-9]*$";
	    return zip.matches(pattern);
	}
	
	private boolean isValidPhoneNumber(String phoneNumber) {
		if (isEmpty(phoneNumber) || phoneNumber.length() != 10) {
			return false;
		}
		String pattern= "^[0-9]*$";
	    return phoneNumber.matches(pattern);
	}
	
	private String getValueFromTag(Element e, String tag) {
		return e.getElementsByTagName(tag).item(0).getTextContent();
	}
	
	private String generateId() {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 6;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	   return generatedString;
	}
}
