/**
 * @author Csaba Farkas
 * Email: csaba.farkas@mycit.ie
 * Date of last modification: 02/11/2015
 */

package com.cit.r00117945.windspeedconverterapplication;

import java.text.DecimalFormat;

/**
 * This class consists of the logic of the application. It converts values to different meauseres
 * except for when converting from Beaufort. That is done with FromBeaufortCalculater which extends
 * this class.
 */
public class WindSpeedCalculator {

    //Values are converting from 1 km/h
    protected final static double METER_PER_SECOND = 0.27778;
    protected final static double KNOTS = 0.53996;
    protected final static double MILES_PER_HOUR = 0.62137;
    protected final static double FEET_PER_SECOND = 0.91134;

    private double convertedValue;
    private DecimalFormat decimalFormat;
    private boolean isWindStrong;

    /**
     * Constructor method has three parameters. Original value is the value entered by the user.
     * If this values is not in km/h and not in Beaufort (as it is done in a separate class) then it
     * is converted to km/h.
     * After this initial convert, isWindStrong is set to true if wind speed is greater than 90 km/h.
     * This triggers the AlertDialog in ResultActivity.
     * Then the initially converted value is converted again to the desired "measureTo" measure using
     * a private method and the above declared static variables.
     *
     * @param originalValue indicating value the user entered.
     * @param measureFrom indicating the index of "from" measure in spinner in mainActivity
     * @param measureTo indicating the index of "to" measure in spinner in mainActivity
     */
    public WindSpeedCalculator(double originalValue, int measureFrom, int measureTo) {
        //if measureFrom is not KM/H, convert it to KM/H, except Beaufort values
        if(measureFrom != MainActivity.KM_PER_HOUR_INDEX && measureFrom != MainActivity.BEAUFORT_INDEX) {
            originalValue = convertToKmPerHour(measureFrom, originalValue);
        }

        if(originalValue > 90) {
            this.isWindStrong = true;
        } else {
            this.isWindStrong = false;
        }
        this.convertedValue = convert(measureTo, originalValue);

        this.decimalFormat = new DecimalFormat("#.###");
        this.convertedValue = Double.parseDouble(this.decimalFormat.format(this.convertedValue));
    }

    /**
     * Getter method for decimalFormat instance variable. It is reused in FromBeaufortCalculator class.
     *
     * @return decimalFormat
     */
    protected DecimalFormat getDecimalFormat() {
        return this.decimalFormat;
    }

    /**
     * Method converts the initially converted (to km/h) original value to the
     * desired outcome.
     * Reused in FromBeaufortCalculator class.
     *
     * @param measureTo indicating position of measure in spinner in mainActivity
     * @param originalValue indicating original value converted to km/h
     *
     * @return the converted value
     */
    protected double convert(int measureTo, double originalValue) {
        switch (measureTo) {
            case MainActivity.KM_PER_HOUR_INDEX:
                return originalValue;
            case MainActivity.METER_PER_SECOND_INDEX:
                return originalValue * METER_PER_SECOND;
            case MainActivity.KNOTS_INDEX:
                return originalValue * KNOTS;
            case MainActivity.MILES_PER_HOUR_INDEX:
                return originalValue * MILES_PER_HOUR;
            case MainActivity.FEET_PER_SECOND_INDEX:
                return originalValue * FEET_PER_SECOND;
            default:
                return calculateToBeaufort(originalValue);
        }
    }

    /**
     * Gette method for convertedValue.
     *
     * @return convertedValue
     */
    public Double getConvertedValue() {
        return this.convertedValue;
    }

    /**
     * Getter method for isWindSttrong.
     *
     * @return isWindStrong
     */
    public boolean getIsWindStrong() {
        return this.isWindStrong;
    }

    /**
     * Converting original values to km/h. Initial conversion.
     *
     * @param measureFrom position of measure in spinner in mainActivity
     * @param convertValue position of measure in spinner in mainActivity
     * @return original value converted to km/h
     */
    protected double convertToKmPerHour(int measureFrom, double convertValue) {
        switch (measureFrom) {
            case MainActivity.METER_PER_SECOND_INDEX:
                return convertValue / METER_PER_SECOND;
            case MainActivity.FEET_PER_SECOND_INDEX:
                return convertValue / FEET_PER_SECOND;
            case MainActivity.MILES_PER_HOUR_INDEX:
                return convertValue / MILES_PER_HOUR;
            case MainActivity.KNOTS_INDEX:
                return convertValue / KNOTS;
            default:
                return 0;
        }
    }

    /**
     * Beaufort calculation is done in a different manner. Beaufort scale is
     * classifying winds in a to-from range. Logic is straight forwards. The input
     * is the initially converted (km/h) original value.
     * The output is the appropriate Beaufort scale.
     *
     * @param convertValue initially converted value in km/h
     * @return appropriate Beaufort scale
     */
    public double calculateToBeaufort(double convertValue) {
        if(convertValue < 1.1) {
            return 0;
        } else if(convertValue < 5.5) {
            return 1;
        } else if(convertValue < 11.9) {
            return 2;
        } else if(convertValue < 19.7) {
            return 3;
        } else if(convertValue < 28.7) {
            return 4;
        } else if(convertValue < 38.8) {
            return 5;
        } else if(convertValue < 49.9) {
            return 6;
        } else if(convertValue < 61.8) {
            return 7;
        } else if(convertValue < 74.6) {
            return 8;
        } else if(convertValue < 88.1) {
            return 9;
        } else if(convertValue < 102.4) {
            return 10;
        } else if(convertValue < 117.4) {
            return 11;
        } else {
            return 12;
        }
    }
}
