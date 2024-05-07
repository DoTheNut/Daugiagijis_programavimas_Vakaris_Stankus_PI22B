import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Gijos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Meniu:");
            System.out.println("1. Matricos");
            System.out.println("2. Žodžio paieška");
            System.out.println("3. Išeiti");
            System.out.print("Įveskite pasirinkimą: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    MatricosSumavimas.performMatrixCalculation(scanner);
                    Delay();
                    break;
                case 2:
                    FailoPaieska.performFileSearch(scanner);
                    Delay();
                    break;
                case 3:
                    System.out.println("Išeinama.");
                    System.exit(0);
                default:
                    System.out.println("Neteisingas pasirinkimas.");
            }
        }
    }

    private static void Delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
