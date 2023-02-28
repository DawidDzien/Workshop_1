package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TaskManager {



    public static void main(String[] args) {
        File file = new File("data.csv");
        System.out.println(ConsoleColors.BLUE + "What would you like to do? Please select an option:");
        System.out.println(ConsoleColors.RESET);
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not load file! File not found!");
        }

    }


}



