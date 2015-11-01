package edu.mbhs.speedtrig.util;

import java.math.BigInteger;

/**
 * Container for miscellaneous utility methods
 * @author eytsegay
 * created 11/1/15
 */
public class Util {

    /**
     * Converts a raw value to an operand for a speed trig quiz question (String)
     * @param rawValue value to convert
     * @return rational representation of raw value, or "" if it isn't a valid operand for a speed
     * trig quiz question
     */
    public static String getPiFraction(double rawValue) {
        int value = (int) Math.round(rawValue / Math.PI * 12); // numerator in x/12 form
        if (value == 0) return "0";
        if (value == 12) return "π";

        int gcd = gcd(value, 12);
        int numer = value / gcd;
        int denom = 12 / gcd;

        if (numer == 1) return String.format("π/%d", denom);
        return String.format("%dπ/%d", numer, denom);
    }

    /**
     * Returns the greatest common denominator of two numbers using BigInteger.gcd()
     * @param a an int
     * @param b an int
     * @return their greatest common denominator
     */
    public static int gcd(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

}
