package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents and manages the collection of individual catering packages
 * 
 * @author Jason Holdsworth
 * @version 0.1
 * @since 2014-7-30
 * 
 */
public class CateringPackages {
	protected List<CateringPackage> packages;
	public static final String PACKAGES_FILE = "packages.txt";

	public CateringPackages() {
		packages = new ArrayList<>(); // create the empty list of packages
	}

	public boolean isEmpty() {
		return packages.isEmpty();
	}

	/**
	 * Get the current number of packages
	 * @return an integer value, 0 if there are currently no packages available
	 */
	public int count() {
		return packages.size();
	}
	
	public void add(CateringPackage newPackage) {
		packages.add(newPackage);
	}
	
	/**
	 * Get a particular package
	 * @param index - a zero-indexed number
	 * @return the package associated with the index
	 */
	public CateringPackage get(int index) {
		return packages.get(index);
	}
	
	/**
	 * loadPackages uses the Scanner class with a File as its stream (instead of
	 * System.in for console input) File opening potentially throws a checked
	 * exception so it must be caught with try/catch loop through file while it
	 * has a next line, convert each line (string) to an array with the split
	 * method, convert strings to doubles using the parseDouble method of the
	 * Double class, add (append) an anonymous CateringPackage object to the
	 * packages list
	 */
	public void loadPackages() {
		packages.clear(); // remove current packages

		try {
			File file = new File(PACKAGES_FILE);
			Scanner inFileScanner = new Scanner(file);

			while (inFileScanner.hasNextLine()) {
				String line = inFileScanner.nextLine();
				String[] parts = line.split(",");
				packages.add(new CateringPackage(parts[0], Double
						.parseDouble(parts[1]), Double.parseDouble(parts[2])));
			}

			inFileScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * savePackages uses the FileWriter class inititalised using the text file
	 * name, writes each package to a new line much the same as it is printed in
	 * the displayPackages function File opening potentially throws a checked
	 * exception so it must be caught with try/catch
	 */
	public void savePackages() {
		try {
			FileWriter fileWriter = new FileWriter(PACKAGES_FILE);
			for (int i = 0; i < packages.size(); i++)
				fileWriter.write(String.format("%s, %.2f, %.2f\n",
						packages.get(i).name, packages.get(i).adultPrice,
						packages.get(i).childPrice));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
