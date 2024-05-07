import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FailoPaieska {
    private static ArrayBlockingQueue<File> taskQueue = new ArrayBlockingQueue<>(100);

    public static void performFileSearch(Scanner scanner) {
        scanner.nextLine();

        System.out.println("Failo paieškos gija (ID: " + Thread.currentThread().getId() + ") pradėjo.");
        System.out.print("Irašykite folderio direktorija: ");
        String folderPath = scanner.nextLine();
        System.out.println("Kokio zodzio ieskote ? ");
        String searchString = scanner.nextLine();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            try {
                produceTasks(new File(folderPath));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.execute(() -> {
            try {
                consumeTasks(searchString);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.shutdown();
        System.out.println("Failo paieškos gija (ID: " + Thread.currentThread().getId() + ") Užbaigė.");
    }

    private static void produceTasks(File directory) throws InterruptedException {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        taskQueue.put(file);
                    }
                }
            }
        }
    }

    private static void consumeTasks(String searchString) throws InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            File file = taskQueue.take();
            searchFile(file, searchString);
        }
    }

    private static void searchFile(File file, String searchString) {
        System.out.println("Failo paieškos gija (ID: " + Thread.currentThread().getId() + ") Skanuoja faila: " + file.getName());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchString)) {
                    System.out.println("Rastas zodis '" + searchString + "' faile: " + file.getName());
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + file.getName() + " - " + e.getMessage());
        }
        System.out.println("Failo paieškos gija (ID: " + Thread.currentThread().getId() + ") Uzbaigė skanuoti: " + file.getName());
    }
}
