/**
 * @author Csaba Farkas
 * Email: csaba.farkas@mycit.ie
 * Date of last modification: 02/11/2015
 */

package com.cit.r00117945.windspeedconverterapplication;

/**
 * Class extend WindSpeedCalculator. It converts values from Beaufort and
 * returns a range of values eg. from-to.
 */
public class FromBeaufortCalculator extends WindSpeedCalculator {

    Double[] values;
    double originalValue;
    int measureTo;

    /**
     * Constructor method creates an instance of this class. It initiates the instance variables and
     * converts the originalValue to a range of values.
     *
     * @param originalValue value entered by user
     * @param measureFrom this is always indicating Beaufort
     * @param measureTo indicating the measure of desired outcome.
     */
    public FromBeaufortCalculator(double originalValue, int measureFrom, int measureTo) {
        super(originalValue, measureFrom, measureTo);
        this.originalValue = originalValue;
        this.measureTo = measureTo;

        //measureFrom is always Beaufort, we are interested in measureTo
        //always calculate lower and upper values of the range in km/h first
        //and convert it to other measures if necessary
        this.values = calculateValues(originalValue);

        //if mesureTo is not km/h, then convert the values to measureTo
        if(measureTo != MainActivity.KM_PER_HOUR_INDEX) {
            values[0] = super.convert(measureTo, values[0]);
            values[1] = super.convert(measureTo, values[1]);
        }

        this.values[0] = Double.parseDouble(super.getDecimalFormat().format(this.values[0]));
        this.values[1] = Double.parseDouble(super.getDecimalFormat().format(this.values[1]));
    }

    /**
     * Private method which creates a range of values that is appropriate for the input
     * Beaufort value in km/h.
     *
     * @param originalValue entered by user
     * @return a Double array containing 2 elements: lower limit and upper limit
     */
    private Double[] calculateValues(double originalValue) {
        Double[] toReturn = new Double[2];
        switch((int) originalValue) {
            case 0:
                toReturn[0] = 0.0;
                toReturn[1] = 1.1;
                break;
            case 1:
                toReturn[0] = 1.2;
                toReturn[1] = 5.5;
                break;
            case 2:
                toReturn[0] = 5.6;
                toReturn[1] = 11.9;
                break;
            case 3:
                toReturn[0] = 12.0;
                toReturn[1] = 19.7;
                break;
            case 4:
                toReturn[0] = 19.8;
                toReturn[1] = 28.7;
                break;
            case 5:
                toReturn[0] = 28.8;
                toReturn[1] = 38.8;
                break;
            case 6:
                toReturn[0] = 39.9;
                toReturn[1] = 49.9;
                break;
            case 7:
                toReturn[0] = 50.0;
                toReturn[1] = 61.8;
                break;
            case 8:
                toReturn[0] = 61.9;
                toReturn[1] = 74.6;
                break;
            case 9:
                toReturn[0] = 74.7;
                toReturn[1] = 88.1;
                break;
            case 10:
                toReturn[0] = 88.2;
                toReturn[1] = 102.4;
                break;
            case 11:
                toReturn[0] = 102.5;
                toReturn[1] = 117.4;
                break;
            case 12:
                toReturn[0] = 117.5;
                toReturn[1] = 0.0;
                break;
            default:
                //Do nothing. Input was validated before passing it to this function.
        }
        return toReturn;
    }

    /**
     * Convert values that are in the Double array to Strings. Use special case for
     * Beaufort 12 where there is no upper limit. The desired outcome is "number" - "number"
     * or "number" <
     *
     * @return a string object which can be displayed in ResultActivity.
     */
    public String getConvertedString() {
        //if measureTo is beaufort, return original value
        if(this.measureTo == MainActivity.BEAUFORT_INDEX) {
            return (new Double(this.originalValue)).toString();
        }
        String lower, upper;
        lower = values[0].toString();
        if(values[1] == 0.0) {
            upper = " <";
        } else {
            upper = " - " + values[1].toString();
        }
        return lower.concat(upper);
    }

    /**
     * Override getIsWindStrong method: return true if Beaufort is greater than
     * or equal to 10 so the AlertDialog can be displayed in ResultActivity.
     *
     * @return a boolean value indicating the strength of the wind.
     */
    @Override
    public boolean getIsWindStrong() {
        return this.originalValue >= 10;
    }
}
