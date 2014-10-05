/**
 * BinaryToHex converts binary numbers to their hexadecimal equivalents, and back again.
 * Created by Elizabeth Rego on 10/1/14.
 */
public class BinaryToHex {
    // Create jagged array to store mappings of binary numbers to
    // their hexadecimal equivalents
    public static String[][] mappingBinaryToHex
            = { {"0000", "0"}, {"0001", "1"},
            {"0010", "2"}, {"0011", "3"}, {"0100", "4"},
            {"0101", "5"}, {"0110", "6",}, {"0111", "7"},
            {"1000", "8"}, {"1001", "9"}, {"1010", "A"},
            {"1011", "B"}, {"1100", "C"}, {"1101", "D"},
            {"1110", "E"}, {"1111", "F"}
              };

    /**
     * Converts a binary number to its hexadecimal equivalent.
     * @param binary binary number to be converted
     * @return hexadecimal conversion result
     */
    public static String binaryToHex(String binary) {
        int numBinaryDigits = binary.length();
        int totalBinaryDigits = numBinaryDigits;

        boolean isMultipleOfFour = true;
        if ((numBinaryDigits % 4) != 0)
            isMultipleOfFour = false;

        // Compute number of positions in array of hex digits.
        int numHexDigits = 0;
        if (isMultipleOfFour) {
            numHexDigits = totalBinaryDigits / 4;
        } else numHexDigits = (totalBinaryDigits / 4) + 1;

        // Create array to store sets of 4 binary digits to
        // be converted to hex digits.
        String[] toHex = new String[numHexDigits];
        int toHexIndex = 0;

        // Keep track of how many binary digits have already
        // been stored in array.
        int usedBinaryIndex = 0;

        // If length of binary digits is not a multiple of 4
        if ((numBinaryDigits % 4) != 0) {
            totalBinaryDigits = numBinaryDigits + (4 - (numBinaryDigits % 4));

            // Add appropriate amount of 0s to first set of binary digits.
            int numLeadingZeros = totalBinaryDigits - numBinaryDigits;
            StringBuilder mostSignificantHexDigit = new StringBuilder();
            for (int i = 0; i < numLeadingZeros; i++) {
                mostSignificantHexDigit.append("0");
            }

            // Add appropriate amount of digits from binary input to make
            // length of string 4
            for (int i = 0; i < 4 - numLeadingZeros; i++) {
                mostSignificantHexDigit.append(binary.charAt(i));
                usedBinaryIndex++;
            }

            // Assign this 4-bit binary digit to first position in array
            toHex[0] = mostSignificantHexDigit.toString();
            toHexIndex++;
        }

        // Assign rest of digits to appropriate positions in array
        for (int i = usedBinaryIndex; i < numBinaryDigits; i += 4) {
            toHex[toHexIndex] = binary.substring(i, i + 4);

            // Move to next position in array
            toHexIndex++;
        }

        StringBuilder hexBuilder = new StringBuilder();
        for (String fourBitBinary : toHex) {
            for (int i = 0; i < mappingBinaryToHex.length; i++) {
                if (fourBitBinary.equals(mappingBinaryToHex[i][0])) {
                    hexBuilder.append(mappingBinaryToHex[i][1]);
                    break;
                }
            }
        }

        hexBuilder.insert(0, "0x");
        return hexBuilder.toString();
    }

    /**
     * Converts a hexadecimal number to its binary equivalent.
     * @param hex hexadecimal number to be converted
     * @return binary number result
     */
    public static String hexToBinary(String hex) {
        char[] hexadecimal = hex.substring(2).toCharArray();
        StringBuilder binaryBuilder = new StringBuilder();

        // Map each hexadecimal digit to its 4-bit binary equivalent
        for (char h : hexadecimal) {
            for (int i = 0; i < mappingBinaryToHex.length; i++) {
                if (Character.toString(h).equals(mappingBinaryToHex[i][1])) {
                    binaryBuilder.append(mappingBinaryToHex[i][0]);
                    break;
                }
            }
        }

        return binaryBuilder.toString();
    }
}