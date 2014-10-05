/**
 * DecimalToFpn converts decimal numbers to their 32-bit floating point number equivalents.
 * Created by Elizabeth Rego on 9/30/14.
 */
public class DecimalToFpn {

    /**
     * Helper method used to perform decimal to binary conversions
     * for fractional parts of decimal numbers.
     * Used by decimaltoBinary().
     * @param baseTenFraction decimal fractional part to be converted
     * @return binary fractional part conversion result
     */
    private static String decimalFractionToBinary(String baseTenFraction) {
        // Decimal fraction to be converted must include point
        if (baseTenFraction.contains(".")) {
            float baseTen = Float.parseFloat(baseTenFraction); // equals .5
            StringBuilder baseTwo = new StringBuilder();
            StringBuilder multiplied = new StringBuilder();

            // If the decimal fraction is zero, so is the binary fraction
            if (baseTen == 0.0) {
                baseTwo.append("0");
            } else {
                // Multiply the whole number part of the decimal until it
                // equals one, storing the whole number part as a binary
                // digit each time
                while (!multiplied.toString().equals("1.0")) {
                    multiplied.setLength(0);
                    multiplied.append(new Float(baseTen * 2));
                    baseTwo.append(multiplied.charAt(0) - 48);
                    baseTen = Float.parseFloat(multiplied.substring(1));

                    // If binary fractional part is too long, we don't
                    // want it to be infinite
                    if (baseTwo.length() > 20) {
                        break;
                    }
                }
            }
            return baseTwo.toString();
        }
        return null;
    }

    /**
     * Helper method used to perform decimal to binary conversions.
     * If the decimal number to be converted involves a fractional part,
     * it calls decimalFractionToBinary() and adds the result to the
     * whole number converted here.
     * @param baseTen decimal number to be converted
     * @return binary conversion result
     */
    private static String decimalToBinary(int baseTen) {
        // No need to convert negative decimal numbers in this assignment.
        if (baseTen < 0)
            return "No negative integers.";

        StringBuilder baseTwo = new StringBuilder();

        // Store remainder of mod 2 operation on remaining whole number
        // part of decimal input as last digit of binary number  until
        // the result is 0 or 1.
        while (baseTen > 1) {
            baseTwo.insert(0, baseTen % 2);
            baseTen = baseTen / 2;
        }
        if (baseTen == 1)
            baseTwo.insert(0, 1);
        return baseTwo.toString();
    }

    /**
     * Converts a decimal number to its 32-bit floating point number equivalent.
     * @param baseTwo decimal number to be converted
     * @return binary conversion result
     */
    public static String decimalToFpn(String baseTwo) {
        // Create array to store sign, exponent, and mantissa of FPN.
        String[] components = new String[3];
        String decimal = baseTwo;

        components[0] = "0";
        // If decimal input is negative, so is the FPN.
        if (Float.parseFloat(decimal) < 0) {
            components[0] = "1";
            decimal = baseTwo.substring(1);
        }

        String fraction = null;
        String normalizedBinary = null;
        int exponent = 0;
        StringBuilder normalizer = new StringBuilder();

        // If the decimal input has a fractional part, separate the whole
        // and fraction parts and convert them to binary separately.
        if (decimal.contains(".")) {
            int pointIndex = decimal.indexOf(".");
            int normalizedPointIndex = -1;
            String wholeNumber = null;

            if (Float.parseFloat(decimal) > 1) {
                wholeNumber = decimalToBinary(Integer.parseInt(decimal.substring(0, pointIndex)));
            } else wholeNumber = "0";

            fraction = decimalFractionToBinary(decimal.substring(pointIndex));
            normalizedBinary = wholeNumber + "." + fraction;
            int newPointIndex = normalizedBinary.indexOf(".");

            // Normalize the binary number by moving its point to the appropriate position.
            if (normalizedBinary.charAt(0) == '1') {
                normalizer.append(normalizedBinary.substring(0, 1) + ".");

                // If the point needs to move to the right, do so.
                if (newPointIndex != 1) {
                    for (int i = 1; i < newPointIndex; i++) {
                        normalizer.append(normalizedBinary.charAt(i));
                    }
                    for (int i = newPointIndex + 1; i < normalizedBinary.length(); i++) {
                        normalizer.append(normalizedBinary.charAt(i));
                    }
                }
                else {
                    normalizer.append(fraction);
                }
                normalizedPointIndex = normalizer.toString().indexOf(".");
            } else {
                // If the point needs to move to the right, do so and allow for
                // proper calculation of exponent.
                boolean foundLeadingOne = false;
                int leadingZeroCount = 0;
                for (int i = 0; i < normalizedBinary.length(); i++) {
                    if (normalizedBinary.charAt(i) == '1') {
                        normalizer.append(normalizedBinary.charAt(i) + ".");
                        foundLeadingOne = true;
                    } else if (normalizedBinary.charAt(i) == '0') {
                        leadingZeroCount++;
                    }
                    if (foundLeadingOne)
                        break;
                }
                normalizedPointIndex = leadingZeroCount + 1;
                normalizer.append(normalizedBinary.substring(normalizedPointIndex + 1));
            }

            // Calculate exponent by computing how far it moved during normalization
            exponent = newPointIndex - normalizedPointIndex + 127;
        } else {
            // If the decimal does not include a fractional part, our work is much
            // simpler.
            normalizedBinary = decimalToBinary(Integer.parseInt(decimal));
            normalizer.append(normalizedBinary.substring(0, 1) + ".");
            normalizer.append(normalizedBinary.substring(1));
            // Since there is no point in the decimal, calculate exponent by
            // computing how far it moved to the left.
            exponent = normalizedBinary.length() + 126;
        }

        StringBuilder binaryExponent = new StringBuilder();
        binaryExponent.append(decimalToBinary(exponent));
        boolean incompleteExponent = true;

        // If binary exponent is not 8-bits, add  an appropriate amount
        // of 0s to the digits to the left.
        while (incompleteExponent) {
            if (binaryExponent.toString().length() < 8) {
                binaryExponent.insert(0, "0");
            } else incompleteExponent = false;
        }
        components[1] = binaryExponent.toString();

        StringBuilder mantissa = new StringBuilder();
        mantissa.append(normalizer.toString().substring(2));
        boolean incompleteMantissa = true;

        // If the binary mantissa is not 23-bits, add an appropriate
        // amount of zeros to the digits on the right.
        while (incompleteMantissa) {
            if (mantissa.length() < 23) {
                mantissa.append("0");
            } else incompleteMantissa = false;
        }
        components[2] = mantissa.toString();

        // Concatenate sign, exponent, and mantissa to return complete FPN.
        return components[0] + components[1] + components[2];
    }

}
