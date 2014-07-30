/**
 * Created by sci-lmw1 on 29/07/2014.
 * Package class, as in part 2 of CP1200 2014 SP1 Assignment 2
 */

public class CateringPackage {
    public final String name;
    public final double adultPrice;
    public final double childPrice;

    public CateringPackage(String name, double adultPrice, double childPrice) {
        this.name = name;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
    }

    @Override
    public String toString() {
        return "CateringPackage{" +
                "name='" + name + '\'' +
                ", adultPrice=" + adultPrice +
                ", childPrice=" + childPrice +
                '}';
    }
}
