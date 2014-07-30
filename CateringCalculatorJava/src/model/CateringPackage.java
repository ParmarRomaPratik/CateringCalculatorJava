package model;

/**
 * Created by sci-lmw1 on 29/07/2014.
 * Package class, as in part 2 of CP1200 2014 SP1 Assignment 2
 */

/**
 * A type of object that represents a single catering package. Note that prices
 * are in AUD.
 * 
 * @author Jason Holdsworth
 * @version 0.1
 * @since 2014-7-30
 */
public class CateringPackage {
	protected final String name;
	protected final double adultPrice;
	protected final double childPrice;

	/**
	 * Initial-value constructor - used to setup the catering package details
	 * 
	 * @param name
	 *            - the descriptive name of the package
	 * @param adultPrice
	 *            - cost of a single adult for this particular package in AUD
	 * @param childPrice
	 *            - cost of a single child for this particular package in AUD
	 */
	public CateringPackage(String name, double adultPrice, double childPrice) {
		this.name = name;

		this.adultPrice = adultPrice;
		this.childPrice = childPrice;
	}

	public String getName() {
		return name;
	}
	
	public double getAdultPrice() {
		return adultPrice;
	}
	
	public double getChildPrice() {
		return childPrice;
	}

	@Override
	public String toString() {
		return "CateringPackage{" + "name='" + name + '\'' + ", adultPrice="
				+ adultPrice + ", childPrice=" + childPrice + '}';
	}
}
