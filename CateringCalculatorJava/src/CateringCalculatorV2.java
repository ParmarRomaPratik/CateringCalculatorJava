import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import model.CateringPackage;
import model.CateringPackages;

/**
 * Rewritten by Jason Holdsworth using CP1300 design strategies. This is a Java
 * version of the second (Python) assignment from CP1200 2014 SP1 Demonstrating
 * some more features including lists, file I/O, dictionary (HasMap)
 * 
 * @author Lindsay Ward
 * @version 0.2
 * @since 2014-7-30
 */
public class CateringCalculatorV2 {

	private static Random random = new Random(); // Note: only need one random
													// generator...

	private static final String MENU = "\nMenu:\n(I)nstructions\n(C)alculate Catering\n(D)isplay Packages\n"
			+ "(L)oad Packages\n(S)ave Packages\n(A)dd Package\n(Q)uit\n>>> ";
	private static final Scanner inputStream = new Scanner(System.in);
	private static final String INSTRUCTIONS = "This program allows you to calculate catering costs based on choice of "
			+ "package and number of adults and children.\nYou can load packages from a file, add new ones and "
			+ "save the file for next time.";
	private static final String NO_PACKAGES_MESSAGE = "You need to add or load a package first.";
	private static final int PACKAGE_NAME_WIDTH = 16;

	// A HashMap is like a dictionary in Python. This one maps strings (areas)
	// to Doubles (costs)
	// Note that the value must be a class (Double) not a primitive type
	// (double)
	// There is no way of specifying the initial values with a literal, so a
	// static block is used

	// TODO: rewrite the static strings and delivery costs as file data?

	private static final HashMap<String, Double> DELIVERY_COSTS;
	static {
		DELIVERY_COSTS = new HashMap<String, Double>(4);
		DELIVERY_COSTS.put("North", 8.5);
		DELIVERY_COSTS.put("South", 17.5);
		DELIVERY_COSTS.put("East", 15.0);
		DELIVERY_COSTS.put("West", 15.0);
	}

	/**
	 * Application main - controls the program, and runs the menu system loop
	 * Note: handles input using if/else if (could be done with switch)
	 */
	public static void main(String[] args) {

		// Create main packages list using Java's ArrayList class
		// Java doesn't have a tuple construct like Python, so the easiest thing
		// to use is a class (as in part 2)
		// We could just use ArrayList<Object> for each package, but this is not
		// a good idea
		CateringPackages packages = new CateringPackages();

		System.out
				.println("Welcome to the Java version of the CP1200 Catering Calculator 2.1 (Assignment 2, 2014)");
		System.out.print(MENU);
		String menuChoice = inputStream.nextLine().toUpperCase();
		while (!menuChoice.equals("Q")) {
			// Note, we could also use a switch (with the first letter as the
			// variable to compare)
			switch (menuChoice) {
			case "I":
				System.out.println(INSTRUCTIONS);
				break;

			case "C":
				if (packages.isEmpty()) {
					System.out.println(NO_PACKAGES_MESSAGE);
				} else {
					calculateAndPrintCatering(packages);
				}
				break;

			case "D":
				if (packages.isEmpty()) {
					System.out.println(NO_PACKAGES_MESSAGE);
				} else {
					displayPackages(packages);
				}
				break;

			case "L":
				packages.loadPackages();
				System.out.println(packages.count() + " packages loaded");
				break;

			case "S":
				if (packages.isEmpty()) {
					System.out.println(NO_PACKAGES_MESSAGE);
				} else {
					packages.savePackages();
					System.out.println(packages.count() + " packages saved");
				}
				break;

			case "A":
				CateringPackage newPackage = defineNewPackage();
				packages.add(newPackage);
				System.out.println(newPackage.getName() + " added");
				break;

			default:
				System.out.println("Invalid menu choice.");
				break;
			}

			System.out.print(MENU);
			menuChoice = inputStream.nextLine().toUpperCase();
		}
		System.out
				.println("Thank you for using the Great CP1200 Catering Calculator, Java version.");
	}

	/**
	 * Display all the current packages in the cateringPackages object
	 */
	private static void displayPackages(CateringPackages packages) {
		for (int i = 0; i < packages.count(); i++) {
			System.out.println(String.format("%d. %-" + PACKAGE_NAME_WIDTH
					+ "s - $%5.2f / $%5.2f", i + 1, packages.get(i).getName(),
					packages.get(i).getAdultPrice(), packages.get(i)
							.getChildPrice()));
		}
	}

	/**
	 * displayDeliveryChoices uses a foreach style loop through the keys of the
	 * HashMap, printing each key and value
	 */
	private static void displayDeliveryChoices() {
		for (String area : DELIVERY_COSTS.keySet()) {
			System.out.println(String.format("%-5s - $%.2f", area,
					DELIVERY_COSTS.get(area)));
		}
	}

	/**
	 * defineNewPackage reads console input (using Scanner), making use of the
	 * getValidDouble method returns an anonymous CateringPackage object
	 * constructed with initial values received from console input
	 */
	private static CateringPackage defineNewPackage() {
		System.out.print("Enter package name: ");
		String name = inputStream.nextLine();
		while (name.equals("") || name.length() > PACKAGE_NAME_WIDTH) {
			System.out
					.println(String
							.format("Package name can not be blank and must be less than %d characters",
									PACKAGE_NAME_WIDTH + 1));
			System.out.print("Enter package name: ");
			name = inputStream.nextLine();
		}
		double adultPrice = getValidDouble("Enter package price per adult: $",
				"Price must be valid and >= $0.01.", 0.01);
		double childPrice = getValidDouble("Enter package price per child: $",
				"Price must be valid and >= $0.01.", 0.01);
		return new CateringPackage(name, adultPrice, childPrice);
	}

	/**
	 * calculateAndPrintCatering takes in the list of packages, gets user input
	 * for catering parameters (with error checking), does the maths for the
	 * catering and prints the results making use of the String.format method
	 * The Random class is used to generate a random number for the lucky
	 * discount
	 */
	private static void calculateAndPrintCatering(CateringPackages packages) {
		int numberAdults = (int) getValidDouble(
				"Please enter number of adults: ",
				"Number must be valid and >= 0", 0);

		int numberChildren = (int) getValidDouble(
				"Please enter number of children: ",
				"Number must be valid and >= 0", 0);

		displayPackages(packages);

		int packageChoice = (int) getValidDouble(
				"Which package would you like? ",
				"Number must be valid and >= 1", 1);

		while (packageChoice > packages.count()) {
			System.out.println(String.format("Number must be <= %d",
					packages.count()));
			packageChoice = (int) getValidDouble(
					"Which package would you like? ",
					"Number must be valid and >= 1", 1);
		}
		packageChoice -= 1;

		System.out.println("None");
		displayDeliveryChoices();
		String deliveryChoice = inputStream.nextLine();
		// Convert deliveryChoice to Sentence case (only first letter
		// capitalised)
		deliveryChoice = deliveryChoice.substring(0, 1).toUpperCase()
				+ deliveryChoice.substring(1).toLowerCase();

		while (!DELIVERY_COSTS.containsKey(deliveryChoice)
				&& !deliveryChoice.equals("") && !deliveryChoice.equals("None")) {
			System.out.println("Invalid area - type the word.");
			deliveryChoice = inputStream.nextLine();
		}

		double deliveryCost = 0;
		String deliveryMessage;
		if (deliveryChoice.equals("") || deliveryChoice.equals("None"))
			deliveryMessage = " (pick up)";
		else {
			deliveryCost = DELIVERY_COSTS.get(deliveryChoice);
			deliveryMessage = ", delivered to " + deliveryChoice;
		}

		double childPrice = packages.get(packageChoice).getChildPrice();
		String discountMessage = "";
		double adultPrice = packages.get(packageChoice).getAdultPrice();
		if (random.nextInt() % 10 == 1) {
			adultPrice = childPrice;
			discountMessage = " (adults at kids' prices!)";
		}

		double cost = numberAdults * adultPrice + numberChildren * childPrice
				+ deliveryCost;

		String childWord = "children";
		if (numberChildren == 1) {
			childWord = "child";
		}
		String adultWord = "adults";
		if (numberAdults == 1) {
			adultWord = "adult";
		}

		System.out
				.println(String
						.format("That will be $%.2f for the %s%s package%s for %d %s and %d %s. Enjoy!",
								cost, packages.get(packageChoice).getName(),
								discountMessage, deliveryMessage, numberAdults,
								adultWord, numberChildren, childWord));
	}

	/**
	 * getValidDouble is a generic method that can be customised using the input
	 * parameters, returns a double
	 */
	private static double getValidDouble(String prompt, String error) {
		while (true) {
			System.out.print(prompt);
			if (inputStream.hasNextDouble()) {
				double inputDouble = inputStream.nextDouble();
				// nextLine required to clear the stream since nextDouble
				// doesn't remove the \n character
				inputStream.nextLine();
				return inputDouble;
			} else {
				System.out.println(error);
				inputStream.reset();
			}
		}
	}

	/**
	 * This method shows overloading - using the same method name but different
	 * parameters. It calls the getValidDouble method that has two parameters,
	 * adding a minimum value requirement (3rd parameter)
	 */
	private static double getValidDouble(String prompt, String error,
			double minimum) {
		double inputDouble = getValidDouble(prompt, error);
		while (inputDouble < minimum) {
			System.out.println(error);
			inputDouble = getValidDouble(prompt, error);
		}
		return inputDouble;
	}
}