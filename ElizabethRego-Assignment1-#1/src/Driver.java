/**
 * Driver tests the functionality of FpnToDecimal.
 * Created by Elizabeth Rego on 9/30/14.
 */
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String fpn = "11000001000100000000000000000000";
        System.out.println("FPN:\t" + fpn);
        System.out.println("Decimal Equivalent: " + FpnToDecimal.fpnToDecimal(fpn) + "\n");

        boolean continueConverting = true;
        Scanner reader = new Scanner(System.in);

        while (continueConverting) {
            System.out.println("Convert more FPNs to Decimal? [y/n]");
            String userResponse = reader.next();
            if (userResponse.toUpperCase().equals("Y") ||
                    userResponse.toUpperCase().equals("YES")) {
                System.out.println("-----------------------------------------");
                System.out.println("Enter an FPN: ");
                String userFpn = reader.next();
                System.out.println("Decimal Equivalent: " + FpnToDecimal.fpnToDecimal(userFpn) + "\n");
            }
            else
                continueConverting = false;
        }

    }
}
