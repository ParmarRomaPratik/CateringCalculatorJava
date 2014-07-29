/*
    CP1300 Demo
    Lindsay Ward, IT@JCU
    July 2014
    This is a Java version of the second (Python) assignment from CP1200 2014 SP1
    Demonstrating some more features including lists, file I/O, dictionary
 */

import java.util.*;

public class CateringCalculatorV2 {

    private static final String MENU = "\nMenu:\n(I)nstructions\n(C)alculate Catering\n(D)isplay Packages\n" +
            "(L)oad Packages\n(S)ave Packages\n(A)dd Package\n(Q)uit\n>>> ";
    private static final Scanner inputStream = new Scanner(System.in);
    private static final String INSTRUCTIONS = "This program allows you to calculate catering costs based on choice of " +
            "package and number of adults and children.\nYou can load packages from a file, add new ones and " +
            "save the file for next time.";
    private static final String NO_PACKAGES_MESSAGE = "You need to add or load a package first.";
    private static final int PACKAGE_NAME_WIDTH = 16;

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

    public static void main(String[] args) {

        // Create main packages list using Java's ArrayList class
        // Java doesn't have a tuple construct like Python, so the easiest thing to use is a class (as in part 2)
        // We could just use ArrayList<Object> for each package, but this is not a good idea
        List<CateringPackage> packages = new ArrayList<CateringPackage>();

        System.out.println("Welcome to the Java version of the CP1200 Catering Calculator 2.0 (Assignment 2, 2014)");
        System.out.println(MENU);
        String menuChoice = inputStream.nextLine().toUpperCase();
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
            System.out.println(MENU);
            menuChoice = inputStream.nextLine().toUpperCase();
        }
        System.out.println("Thank you for using the Great CP1200 Catering Calculator, Java version.");
    }

    private static List<CateringPackage> loadPackages() {
        // TODO: loadPackages
        System.out.println("loading...");
        return null;
    }

    private static void savePackages(List<CateringPackage> packages) {
        // TODO: savePackages
        System.out.println("saving...");
    }

    private static void displayPackages(List<CateringPackage> packages) {
        for (int i = 0; i < packages.size(); i++) {
            System.out.println(String.format("%d. %s - $%5.2f / $%5.2f", i + 1, packages.get(i).name, packages.get(i).adultPrice, packages.get(i).childPrice));
        }
    }

    private static void displayDeliveryChoices() {
        for (String area : DELIVERY_COSTS.keySet()) {
            System.out.println(String.format("%-5s - $%.2f", area, DELIVERY_COSTS.get(area)));
        }
    }

    private static CateringPackage getPackage() {
        System.out.print("Enter package name: ");
        String name = inputStream.nextLine();
        while (name.equals("") || name.length() > PACKAGE_NAME_WIDTH) {
            System.out.println(String.format("Package name can not be blank and must be less than %d characters", PACKAGE_NAME_WIDTH + 1));
            System.out.print("Enter package name: ");
            name = inputStream.nextLine();
        }
        double adultPrice = getValidDouble("Enter package price per adult: $", "Price must be valid and >= $0.01.", 0.01);
        double childPrice = getValidDouble("Enter package price per child: $", "Price must be valid and >= $0.01.", 0.01);
        return new CateringPackage(name, adultPrice, childPrice);
    }

    private static void calculateAndPrintCatering(List<CateringPackage> packages) {
        int numberAdults = (int)getValidDouble("Please enter number of adults: ", "Number must be valid and >= 0", 0);
        int numberChildren = (int)getValidDouble("Please enter number of children: ", "Number must be valid and >= 0", 0);
        displayPackages(packages);
        int packageChoice = (int)getValidDouble("Which package would you like? ", "Number must be valid and >= 1", 1);
        while (packageChoice > packages.size()) {
            System.out.println(String.format("Number must be <= %d", packages.size()));
            packageChoice = (int)getValidDouble("Which package would you like? ", "Number must be valid and >= 1", 1);
        }
        packageChoice -= 1;

        System.out.println("None");
        displayDeliveryChoices();
        String deliveryChoice = inputStream.nextLine();
        // TODO: Handle as title case
        while (!DELIVERY_COSTS.containsKey(deliveryChoice) && !deliveryChoice.equals("") && !deliveryChoice.equals("None")) {
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

        double childPrice = packages.get(packageChoice).childPrice;
        String discountMessage = "";
        Random rand = new Random();
        double adultPrice = packages.get(packageChoice).adultPrice;
        if (rand.nextInt() % 10 == 1) {
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
        System.out.println(String.format("That will be $%.2f for the %s%s package%s for %d %s and %d %s. Enjoy!",
                cost, packages.get(packageChoice).name, discountMessage, deliveryMessage, numberAdults, adultWord, numberChildren, childWord));
    }

    private static double getValidDouble(String prompt, String error) {
        while (true) {
            System.out.print(prompt);
            if (inputStream.hasNextDouble()) {
                double inputDouble = inputStream.nextDouble();
                // nextLine required to clear the stream since nextDouble doesn't remove the \n character
                inputStream.nextLine();
                return inputDouble;
            } else {
                System.out.println(error);
                // nextLine required to clear the stream, otherwise it loops infinitely
                // TODO: Fix... only handles single line errors. How do I clear the whole stream?
//                while (inputStream.hasNext())
                inputStream.nextLine();
            }
        }
    }

    /**
     * This method shows polymorphism - using the same method name but different parameters
     * It re-uses the getValidDouble method with two parameters, adding a minimum value requirement
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
