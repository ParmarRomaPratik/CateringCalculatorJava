/*
    CP1300 Demo
    Lindsay Ward, IT@JCU
    July 2014
    This is a Java version of the first (Python) assignment from CP1200 2014 SP1
    Demonstrating constants, functions, calculation, loops, selection and string formatting
 */

import java.util.Scanner;

public class CateringCalculatorV1 {

    public static final String MENU = "\nMenu:\n(I)nstructions\n(C)alculate Catering\n(Q)uit";
    private static final Scanner inputStream = new Scanner(System.in);
    private static final double ADULT_COST_PER_HEAD = 10.0;
    private static final double CHILD_COST_PER_HEAD = 6.0;
    private static final double PREMIUM_RATE = 1.25;
    public static final String INSTRUCTIONS = String.format("Enter number of adults and children and choose a " +
            "service type.\nBasic:   food only    = $%.2f per adult\nPremium: food & drink = $%.2f per adult\n" +
            "Children are always %.0f%% of the price of adults.",
            ADULT_COST_PER_HEAD, ADULT_COST_PER_HEAD * PREMIUM_RATE, CHILD_COST_PER_HEAD / ADULT_COST_PER_HEAD * 100);

    public static void main(String[] args) {

        System.out.println("Welcome to the Java version of the CP1200 Catering Calculator (Assignment 1, 2014)");
        System.out.println(MENU);
        String menuChoice = inputStream.next().toUpperCase();
        while (!menuChoice.equals("Q")) {
            if (menuChoice.equals("I"))
                System.out.println(INSTRUCTIONS);
            else if (menuChoice.equals("C")) {
                calculateAndPrintCatering();
            }
            else
                System.out.println("Invalid menu choice.");
            System.out.println(MENU);
            menuChoice = inputStream.next().toUpperCase();

        }
        System.out.println("Thank you for using the Great CP1200 Catering Calculator, Java version.");
    }

    private static void calculateAndPrintCatering() {
        System.out.println("Please enter number of adults:");
        int numberAdults = inputStream.nextInt();
        while (numberAdults < 0) {
            System.out.println("Error. Please enter a number >= 0");
            System.out.println("Please enter number of adults:");
            numberAdults = inputStream.nextInt();
        }
        System.out.println("Please enter number of children:");
        int numberChildren = inputStream.nextInt();
        while (numberChildren < 0) {
            System.out.println("Error. Please enter a number >= 0");
            System.out.println("Please enter number of children:");
            numberChildren = inputStream.nextInt();
        }
        System.out.println("Would you like (B)asic or (P)remium service?: ");
        String serviceType = inputStream.next().toUpperCase();
        while (!(serviceType.equals("B") || (serviceType.equals("P")))) {
            System.out.println("Error. Please enter b or p");
            System.out.println("Would you like (B)asic or (P)remium service?: ");
            serviceType = inputStream.next().toUpperCase();
        }

        double cost = numberAdults * ADULT_COST_PER_HEAD + numberChildren * CHILD_COST_PER_HEAD;
        String serviceMessage = "basic";
        if (serviceType.equals("P")) {
            cost *= PREMIUM_RATE;
            serviceMessage = "premium";
        }
        String childWord = "children";
        if (numberChildren == 1) {
            childWord = "child";
        }
        String adultWord = "adults";
        if (numberAdults == 1) {
            adultWord = "adult";
        }
        System.out.println(String.format("That will be $%.2f for the %s service for %d %s and %d %s. Enjoy!", cost, serviceMessage, numberAdults, adultWord, numberChildren, childWord));
    }
}
