/**
 * Driver tests the functionality of BinaryToHex.
 * Created by Elizabeth Rego on 9/30/14.
 */
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a binary number to be converted to hexadecimal: ");
        String binary = reader.next();
        String hex = BinaryToHex.binaryToHex(binary);
        System.out.println("Hexadecimal Equivalent: " + hex);
        System.out.println("Converted back to binary: " + BinaryToHex.hexToBinary(hex) + "\n");

        boolean continueConverting = true;

        while (continueConverting) {
            System.out.println("Convert more binary numbers to hexadecimal? [y/n]");
            String userResponse = reader.next();
            if (userResponse.toUpperCase().equals("Y") ||
                    userResponse.toUpperCase().equals("YES")) {
                System.out.println("-----------------------------------------");
                System.out.println("Enter a binary number: ");
                String userBinary = reader.next();
                String userHex = BinaryToHex.binaryToHex(userBinary);
                System.out.println("Hexadecimal Equivalent: " + userHex);
                System.out.println("Converted back to binary: " + BinaryToHex.hexToBinary(userHex) + "\n");
            }
            else
                continueConverting = false;
        }
    }
}
