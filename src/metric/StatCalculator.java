package metric;


/**
 *
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: RHUL</p>
 *
 * @author Pedro Contreras
 * @version 1.0
 */
import java.text.DecimalFormat;


public class StatCalculator {

    private int    count;      // Number of numbers that have been entered.
    private double sum;        // The sum of all the items that have been entered.
    private double squareSum;  // The sum of the squares of all the items.
    private double max = Double.NEGATIVE_INFINITY;  // Largest item seen.
    private double min = Double.POSITIVE_INFINITY;  // Smallest item seen.
    DecimalFormat  decPlaces = new DecimalFormat("0.0000");

    /**
     * 
     * @param num
     */
    public void enter(double num) {
        // Add the number to the dataset.
        count++;
        sum += num;
        squareSum += num * num;
        if (num > max) {
            max = num;
        }
        if (num < min) {
            min = num;
        }
    }

    /**
     * @return Return number of items that have been entered.
     */
    public int getCount() {
        return count;
    }

    /**
     * @return Return the sum of all the items that have been entered
     */
    public double getSum() {
        return sum;
    }

    /**
     * @return Return average of all the items that have been entered.
     * Value is Double.NaN if count == 0
     */
    public double getMean() {
        return sum / count;
    }

    /**
     * @return Return standard deviation of all the items that have been entered.
     *  Value will be Double.NaN if count == 0.
     */
    public double getStandardDeviation() {
        double mean = getMean();
        return Math.sqrt(squareSum / count - mean * mean);
    }

    /**
     *
     * @return Return the smallest item that has been entered.
     *    Value will be infinity if no items have been entered.
     */
    public double getMin() {
        return min;
    }


    /**
     *
     * @return Return the largest item that has been entered.
     *     Value will be -infinity if no items have been entered.
     */
    public double getMax() {
        return max;
    }

    /**
     *
     * @param X dividend
     * @param Y divisor
     * @return Return the division value of X and Y
     */
    public  String division(int X, int Y) {
        DecimalFormat sixPlaces = new DecimalFormat("0.000000");
        double aA = (double) X; // cast int into double to make the division
        double percentage = aA / Y;

        // this is to avoid to deal with the scientific notation
        String res = sixPlaces.format(percentage);
        return res;

    }
}