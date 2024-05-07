import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatricosSumavimas {
    public static void performMatrixCalculation(Scanner scanner) {
        System.out.println("Matricos Sumavimo gija (ID: " + Thread.currentThread().getId() + ") Pradejo.");
        System.out.print("Iveskite matricos eiluciu skaiciu: ");
        int numberOfRows = scanner.nextInt();
        System.out.print("Iveskite matricos stulpeliu skaiciu: ");
        int numberOfColumns = scanner.nextInt();

        int[][] matrix = new int[numberOfRows][numberOfColumns];

        System.out.println("Iveskite matricos skaicius:");

        for (int i = 0; i < numberOfRows; i++) {
            System.out.print("Eilute " + (i + 1) + ": ");
            for (int j = 0; j < numberOfColumns; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(numberOfRows + numberOfColumns);

        for (int i = 0; i < numberOfRows; i++) {
            final int row = i;
            executor.execute(() -> calculateRowSum(matrix, row));
        }

        for (int j = 0; j < numberOfColumns; j++) {
            final int column = j;
            executor.execute(() -> calculateColumnSum(matrix, column));
        }

        executor.shutdown();
        System.out.println("Matricos sumavimo gija (ID: " + Thread.currentThread().getId() + ") uzbaige.");
    }

    private static void calculateRowSum(int[][] matrix, int row) {
        int sum = 0;
        for (int j = 0; j < matrix[row].length; j++) {
            sum += matrix[row][j];
        }
        System.out.println("Eilutes " + (row + 1) + " suma: " + sum + " (Thread ID: " + Thread.currentThread().getId() + ")");
    }

    private static void calculateColumnSum(int[][] matrix, int column) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][column];
        }
        System.out.println("Stulpelio " + (column + 1) + " Suma: " + sum + " (Thread ID: " + Thread.currentThread().getId() + ")");
    }
}
