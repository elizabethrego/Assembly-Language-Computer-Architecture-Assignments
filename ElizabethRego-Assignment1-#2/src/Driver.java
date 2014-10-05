/**
 * Driver tests the functionality of DecimalToFpn.
 * Created by Elizabeth Rego on 9/30/14.
 */
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String decimal = "12";
        System.out.println("Decimal:\t" + decimal);
        System.out.println("FPN Equivalent: " + DecimalToFpn.decimalToFpn(decimal) + "\n");

        boolean continueConverting = true;
        Scanner reader = new Scanner(System.in);

        while (continueConverting) {
            System.out.println("Convert more decimals to FPN? [y/n]");
            String userResponse = reader.next();
            if (userResponse.toUpperCase().equals("Y") ||
                    userResponse.toUpperCase().equals("YES")) {
                System.out.println("-----------------------------------------");
                System.out.println("Enter a decimal: ");
                String userDecimal = reader.next();
                System.out.println("FPN Equivalent: " + DecimalToFpn.decimalToFpn(userDecimal) + "\n");
            }
            else
                continueConverting = false;
        }

    }
}
