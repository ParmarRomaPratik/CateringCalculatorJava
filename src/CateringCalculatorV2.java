/*
    CP1300 Demo: Python to Java - Catering Calculator
    Lindsay Ward, IT@JCU
    30th July, 2014 - updated 4th August (just to test lab setup)
    This is a Java version of the second (Python) assignment from CP1200 2014 SP1 
    Demonstrating some more features including lists, file I/O, dictionary (HashMap)    
 */

import java.io.*;
import java.util.*;

public class CateringCalculatorV2 {

	// Here are the constants. final is the Java keyword for making a constant.
	// Note that they need to be static so they can be accessed within the Class, not just an Object/Instance
	private static final String MENU = "\nMenu:\n(I)nstructions\n(C)alculate Catering\n(D)isplay Packages\n"
			+ "(L)oad Packages\n(S)ave Packages\n(A)dd Package\n(Q)uit\n>>> ";
	private static final String INSTRUCTIONS = "This program allows you to calculate catering costs based on choice of "
			+ "package and number of adults and children.\nYou can load packages from a file, add new ones and " + "save the file for next time.";
	private static final String NO_PACKAGES_MESSAGE = "You need to add or load a package first.";
	public static final String PACKAGES_FILE = "packages.txt";
	private static final int PACKAGE_NAME_WIDTH = 16;
	// The above constants are just strings and numbers. The ones below are new instances of classes
	private static final Scanner INPUT_STREAM = new Scanner(System.in);
	private static final Random RANDOM = new Random();

	// A HashMap is like a dictionary in Python. This one maps strings (areas) to Doubles (costs)
	// Note that the value must be a class (Double) not a primitive type (double)
	// There is no way of specifying the initial values with a literal, so a static block is used
	private static final HashMap<String, Double> DELIVERY_COSTS;
	static {
		DELIVERY_COSTS = new HashMap<String, Double>(4);
		DELIVERY_COSTS.put("North", 8.5);
		DELIVERY_COSTS.put("South", 17.5);
		DELIVERY_COSTS.put("East", 15.0);
		DELIVERY_COSTS.put("West", 15.0);
	}

	/**
	 * main function for CateringCalculatorV2 basic while loop for menu, handling inputs using if/else if (could be done
	 * with switch)
	 */
	public static void main(String[] args) {

		// Create main packages list using Java's ArrayList class
		// Java doesn't have a tuple construct like Python, so the easiest thing
		// to use is a class (as in part 2 of the assignment)
		// We could just use ArrayList<Object> for each package, but this is not a good idea
		List<CateringPackage> packages = new ArrayList<CateringPackage>();

		System.out.println("Welcome to the Java version of the CP1200 Catering Calculator 2.0 (Assignment 2, 2014)");
		System.out.print(MENU);
		String menuChoice = INPUT_STREAM.nextLine().toUpperCase();
		while (!menuChoice.equals("Q")) {
			// Note, we could also use a switch (with the first letter as the variable to compare)
			if (menuChoice.equals("I"))
				System.out.println(INSTRUCTIONS);
			else if (menuChoice.equals("C")) {
				if (packages.size() == 0)
					System.out.println(NO_PACKAGES_MESSAGE);
				else
					calculateAndPrintCatering(packages);
			} else if (menuChoice.equals("D")) {
				if (packages.size() == 0)
					System.out.println(NO_PACKAGES_MESSAGE);
				else
					displayPackages(packages);
			} else if (menuChoice.equals("L")) {
				packages = loadPackages();
				System.out.println(packages.size() + " packages loaded");
			} else if (menuChoice.equals("S")) {
				if (packages.size() == 0)
					System.out.println(NO_PACKAGES_MESSAGE);
				else {
					savePackages(packages);
					System.out.println(packages.size() + " packages saved");
				}
			} else if (menuChoice.equals("A")) {
				CateringPackage newPackage = getPackage();
				packages.add(newPackage);
				System.out.println(newPackage.name + " added");
			} else
				System.out.println("Invalid menu choice.");
			System.out.print(MENU);
			menuChoice = INPUT_STREAM.nextLine().toUpperCase();
		}
		System.out.println("Thank you for using the Great CP1200 Catering Calculator, Java version.");
	}

	/**
	 * loadPackages uses the Scanner class with a File as its stream (instead of System.in for console input) File
	 * opening potentially throws a checked exception so it must be caught with try/catch loop through file while it has
	 * a next line, convert each line (string) to an array with the split method, convert strings to doubles using the
	 * parseDouble method of the Double class, add (append) an anonymous CateringPackage object to the list, then return
	 * packages list
	 */
	private static List<CateringPackage> loadPackages() {
		List<CateringPackage> packages = new ArrayList<CateringPackage>();

		try {
			File file = new File(PACKAGES_FILE);
			Scanner inFileScanner = new Scanner(file);
			while (inFileScanner.hasNextLine()) {
				String line = inFileScanner.nextLine();
				String[] parts = line.split(",");
				packages.add(new CateringPackage(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
			}
			inFileScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return packages;
	}

	/**
	 * savePackages uses the FileWriter class inititalised using the text file name, writes each package to a new line
	 * much the same as it is printed in the displayPackages function File opening potentially throws a checked
	 * exception so it must be caught with try/catch
	 */
	private static void savePackages(List<CateringPackage> packages) {
		try {
			FileWriter fileWriter = new FileWriter(PACKAGES_FILE);
			for (int i = 0; i < packages.size(); i++)
				fileWriter.write(String.format("%s, %.2f, %.2f\n", packages.get(i).name, packages.get(i).adultPrice, packages.get(i).childPrice));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * displayPackages loops through the packages list with a standard for loop, displaying each package's details
	 * (accessed using the class's public attributes) Note that getting each package from packages is done using the
	 * get(i) method rather than indexing with [i] as in Python
	 */
	private static void displayPackages(List<CateringPackage> packages) {
		for (int i = 0; i < packages.size(); i++) {
			System.out.println(String.format("%d. %-" + PACKAGE_NAME_WIDTH + "s - $%5.2f / $%5.2f", i + 1, packages.get(i).name, packages.get(i).adultPrice,
					packages.get(i).childPrice));
		}
	}

	/**
	 * displayDeliveryChoices uses a foreach style loop through the keys of the HashMap, printing each key and value
	 */
	private static void displayDeliveryChoices() {
		for (String area : DELIVERY_COSTS.keySet()) {
			System.out.println(String.format("%-5s - $%.2f", area, DELIVERY_COSTS.get(area)));
		}
	}

	/**
	 * getPackage reads console input (using Scanner), making use of the getValidDouble method returns an anonymous
	 * CateringPackage object constructed with initial values received from console input
	 */
	private static CateringPackage getPackage() {
		System.out.print("Enter package name: ");
		String name = INPUT_STREAM.nextLine();
		while (name.equals("") || name.length() > PACKAGE_NAME_WIDTH) {
			System.out.println(String.format("Package name can not be blank and must be less than %d characters", PACKAGE_NAME_WIDTH + 1));
			System.out.print("Enter package name: ");
			name = INPUT_STREAM.nextLine();
		}
		double adultPrice = getValidDouble("Enter package price per adult: $", "Price must be valid and >= $0.01.", 0.01);
		double childPrice = getValidDouble("Enter package price per child: $", "Price must be valid and >= $0.01.", 0.01);
		return new CateringPackage(name, adultPrice, childPrice);
	}

	/**
	 * calculateAndPrintCatering takes in the list of packages, gets user input for catering parameters (with error
	 * checking), does the maths for the catering and prints the results making use of the String.format method The
	 * Random class is used to generate a random number for the lucky discount. Note that we only need one instance of
	 * the Random class (same as Scanner), so it's a constant
	 */
	private static void calculateAndPrintCatering(List<CateringPackage> packages) {
		int numberAdults = (int) getValidDouble("Please enter number of adults: ", "Number must be valid and >= 0", 0);
		int numberChildren = (int) getValidDouble("Please enter number of children: ", "Number must be valid and >= 0", 0);
		displayPackages(packages);
		int packageChoice = (int) getValidDouble("Which package would you like? ", "Number must be valid and >= 1", 1);
		while (packageChoice > packages.size()) {
			System.out.println(String.format("Number must be <= %d", packages.size()));
			packageChoice = (int) getValidDouble("Which package would you like? ", "Number must be valid and >= 1", 1);
		}
		packageChoice -= 1;

		System.out.println("None");
		displayDeliveryChoices();
		String deliveryChoice = INPUT_STREAM.nextLine();
		// Convert deliveryChoice to Sentence case (only first letter capitalised)
		// To avoid StringIndexOutOfBoundsException,
		// check for empty string before accessing it by index
		if (!deliveryChoice.equals(""))
			deliveryChoice = deliveryChoice.substring(0, 1).toUpperCase() + deliveryChoice.substring(1).toLowerCase();
		while (!DELIVERY_COSTS.containsKey(deliveryChoice) && !deliveryChoice.equals("") && !deliveryChoice.equals("None")) {
			System.out.println("Invalid area - type the word.");
			deliveryChoice = INPUT_STREAM.nextLine();
			if (!deliveryChoice.equals(""))
				deliveryChoice = deliveryChoice.substring(0, 1).toUpperCase() + deliveryChoice.substring(1).toLowerCase();
		}
		double deliveryCost = 0;
		String deliveryMessage;
		if (deliveryChoice.equals("") || deliveryChoice.equals("None"))
			deliveryMessage = " (pick up)";
		else {
			deliveryCost = DELIVERY_COSTS.get(deliveryChoice);
			deliveryMessage = ", delivered to " + deliveryChoice;
		}

		double childPrice = packages.get(packageChoice).childPrice;
		String discountMessage = "";
		double adultPrice = packages.get(packageChoice).adultPrice;
		// The line below generates a random number using nextInt, which could be negative.
		// Using % 10 restricts the number to 0-9, so this gives us the 1 in 10 probability we want.
		if (Math.abs(RANDOM.nextInt()) % 10 == 1) {
			adultPrice = childPrice;
			discountMessage = " (adults at kids' prices!)";
		}
		double cost = numberAdults * adultPrice + numberChildren * childPrice + deliveryCost;

		String childWord = "children";
		if (numberChildren == 1) {
			childWord = "child";
		}
		String adultWord = "adults";
		if (numberAdults == 1) {
			adultWord = "adult";
		}
		System.out.println(String.format("That will be $%.2f for the %s%s package%s for %d %s and %d %s. Enjoy!", cost, packages.get(packageChoice).name,
				discountMessage, deliveryMessage, numberAdults, adultWord, numberChildren, childWord));
	}

	/**
	 * getValidDouble is a generic method that can be customised using the input parameters, returns a double
	 */
	private static double getValidDouble(String prompt, String error) {
		double inputDouble;
		while (true) {
			System.out.print(prompt);
			String resultString = INPUT_STREAM.nextLine();
			try {
				inputDouble = Double.parseDouble(resultString);
				return inputDouble;
			} catch (NumberFormatException e) {
				System.out.println(error);
			}
		}
	}

	/**
	 * This method shows polymorphism - using the same method name but different parameters It calls the getValidDouble
	 * method that has two parameters, adding a minimum value requirement (3rd parameter)
	 */
	private static double getValidDouble(String prompt, String error, double minimum) {
		double inputDouble = getValidDouble(prompt, error);
		while (inputDouble < minimum) {
			System.out.println(error);
			inputDouble = getValidDouble(prompt, error);
		}
		return inputDouble;
	}
}
