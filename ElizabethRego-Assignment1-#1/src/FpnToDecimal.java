/**
 * FpnToDecimal converts 32-bit floating point numbers into their decimal equivalents.
 * Written by Elizabeth Rego on 9/30/14.
 */
public class FpnToDecimal {

    /**
     * Helper method to perform binary to decimal conversions for
     * fractional parts of binary numbers.
     * Used by binaryToDecimal().
     * @param baseTwoFraction binary fractional part to be converted
     * @return decimal fractional part conversion result
     */
    private static String binaryFractionToDecimal(String baseTwoFraction) {
        Double baseTen = new Double(0);

        // Instantiate exponent as -1 and decrement it for each
        // fractional digit in baseTwoFraction.
        int exponent = baseTwoFraction.length();
        for (int i = -1; i > (exponent * -1) - 1; i--) {
            // Left side of * operator resolves to '0' or '1', which is
            // multiplied by 2^exponent and added to the baseTen to
            // calculate the decimal result.
            baseTen += (baseTwoFraction.charAt((i * -1) - 1) - 48) * Math.pow(2, i);
        }

        // Remove "0." at beginning of decimal result for use in binaryToDecimal().
        return baseTen.toString().substring(2, baseTen.toString().length());
    }

    /**
     * Helper method used to perform binary to decimal conversions.
     * If the binary number to be converted involves a fractional part,
     * it calls binaryFractionToDecimal() and adds the result to the
     * whole number converted here.
     * Used by fpnToDecimal().
     * @param baseTwo binary number to be converted
     * @return decimal conversion result
     */
    private static String binaryToDecimal(String baseTwo) {
        String fraction = null;
        int baseTen = 0;
        String decimal = "";

        // If the binary number input includes a fractional part,
        // separate that part from the whole number.
        if(baseTwo.contains(".")) {
            int binaryPointIndex = baseTwo.indexOf(".");
            fraction = baseTwo.substring(binaryPointIndex + 1, baseTwo.length());
            baseTwo = baseTwo.substring(0, binaryPointIndex);
        }

        // Instantiate exponent to be raised by 2 and multiplied by most
        // significant digit of binary whole number.
        int exponent = baseTwo.length() - 1;
        for (int i = 0; i < baseTwo.length(); i++) {
            baseTen += (baseTwo.charAt(i) - 48) * Math.pow(2, exponent);

            // Decrement exponent to properly calculate decimal number.
            exponent--;
        }

        // If the binary number has a fractional part, add the strings
        // representing the whole and fractional parts together to
        // represent full decimal result.
        if (fraction != null) {
            StringBuilder fractionBuilder = new StringBuilder();
            fractionBuilder.append(baseTen + ".");
            fractionBuilder.append(binaryFractionToDecimal(fraction));
            decimal = fractionBuilder.toString();
        }
        // If the binary number has no fractional part, the decimal whole
        // number represents the full decimal result.
        else {
            Integer baseTenWrapper = (Integer) baseTen;
            decimal = baseTenWrapper.toString();
        }
        return decimal;
    }

    /**
     * Converts a 32-bit floating point numbers to its decimal equivalent.
     * @param fpn floating point number to be converted
     * @return decimal result
     */
    public static float fpnToDecimal(String fpn) {
        // fpn input must be 32 digits long.
        if (fpn.length() == 32) {

            // Create array to hold the sign, exponent, and mantissa of fpn.
            String[] components = {fpn.substring(0, 1), fpn.substring(1, 9), fpn.substring(9, 32)};

            // Calculate exponent.
            int exponent = Integer.parseInt(binaryToDecimal(components[1])) - 127;

            // Find index of last digit '1' in mantissa.
            int lastDigit = components[2].lastIndexOf('1');

            StringBuilder mantissa = new StringBuilder();
            if (exponent > 0) {
                // If exponent is positive, move decimal to appropriate position
                mantissa.append("1");
                mantissa.append(components[2].substring(0, exponent));
                if (exponent <= lastDigit) {
                    mantissa.append("." + components[2].substring(exponent, lastDigit + 1));
                }
            } else if (exponent <= 0) {
                // If exponent is negative, add the appropriate amount of zeros to mantissa.
                exponent *= -1;
                mantissa.append("0.");
                while (exponent > 1) {
                    mantissa.append("0");
                    exponent--;
                }
                mantissa.append("1" + components[2].substring(0, lastDigit + 1));
            } else if (exponent == -127) {
                // Get correct result for zero.
                if (lastDigit != -1) {
                    mantissa.append("1." + components[2].substring(0, lastDigit + 1));
                } else {
                    mantissa.append("0");
                }
            }
            float decimal = Float.parseFloat(binaryToDecimal(binaryToDecimal(mantissa.toString())));

            // Properly represent negative numbers
            if (components[0].equals("1"))
                return decimal * -1;
            return decimal;
        } else return 0;
    }

}
