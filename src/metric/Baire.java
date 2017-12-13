package metric;

/**
 * <p>Title: Calculates baire distance for precision k </p>
 * Baire.java
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2012. All rights reserved. <br>
 * Project name: ultrametric_analysis. <br>
 * Created on: Dec 4, 2012. <br>
 */

import java.math.BigDecimal;

public class Baire {

    public Baire() {
    }

    /**
     * This takes two string and compare character by character to determine how
     * many common prefixes these two string have, in this particular case the
     * "." and "-" characters aren't consider, therefore subtracted if they
     * appear.
     *
     * @param xx String first string
     * @param yy String second string
     * @return int longest common prefix
     */
    public int commonPrefix(String xx, String yy) {
        // transforms xx and yy variables to x and y, this deal with the scientific notation of some values
        String x = new BigDecimal(Double.toString(Double.parseDouble(xx))).toPlainString();
        String y = new BigDecimal(Double.toString(Double.parseDouble(yy))).toPlainString();

        int commonPrefix = 0;
        int length = Math.min(x.length(), y.length()); //retrieve the leght of the shortest of two string

        for (int i = 0; i < length; i++) {
            if ((x.charAt(i) == y.charAt(i))) {
                commonPrefix++;
                if ((x.charAt(i) == '.') || (x.charAt(i) == '-')) {
                    commonPrefix = (commonPrefix - 1);
                }
            } else {
                break;
            }
        }
        

        return commonPrefix;
    }

    /**
     * This retrieve the the digits based on the baire distance, por example if we have
     * a baire = 3 for the string "0.015746", we retrieve 001.
     * @param xx
     * @param distance
     * @return
     */
   public String clusterIndex(String xx, int distance) {
        String x = new BigDecimal(Double.toString(Double.parseDouble(xx))).toPlainString();
        String y ="";
        for (int i = 0; i < x.length(); i++) {

            if ((x.charAt(i) == '.') || (x.charAt(i) == '-')) {

            }else {
               y =  y.concat(Character.toString(x.charAt(i)));
            }
        }

        String clusterIndex = y.substring(0, distance);

        return clusterIndex;
    }

    /**
     * Calculates the Baire distance in base to the longes common prefix
     * @param x int  longest common prefix
     * @return double Baire distance
     */
    public double distance(int x) {
        double baireDistance = 0;
        baireDistance = Math.pow(2, -x);

        return baireDistance;
    }
}