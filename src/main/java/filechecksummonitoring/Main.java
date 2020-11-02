package filechecksummonitoring;

import filechecksummonitoring.digitalsignature.DigitalSignature;
import filechecksummonitoring.rsacipher.RsaCipher;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;

public class Main {

    private static final String ACTION_COMPLETE = "Action complete";

    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        RsaCipher rsaCipher = new RsaCipher(objectMapper);
        DigitalSignature digitalSignature = new DigitalSignature(rsaCipher, objectMapper);

        String filename = null;

        int variant = 0;
        Scanner in = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb
                .append("\n1. ").append("Enter path to file")
                .append("\n2. ").append("Create digital signature")
                .append("\n3. ").append("Check digital signature")
                .append("\n0. ").append("Exit");


        do {
            System.out.println("Enter variant of actions");
            System.out.println(sb.toString());
            variant = in.nextInt();

            switch (variant) {

                case 1 : {
                    System.out.println("Enter path to file: ");
                    filename = in.next();
                    System.out.println(ACTION_COMPLETE);
                    break;
                }

                case 2 : {
                    digitalSignature.create(filename);
                    System.out.println();
                    System.out.println(ACTION_COMPLETE);
                    break;
                }

                case 3 : {
                    System.out.println(digitalSignature.check());
                    System.out.println(ACTION_COMPLETE);
                    break;
                }

                case 0 : {
                    System.out.println("Bye");
                }
            }
        }
        while (variant != 0);
    }
}
