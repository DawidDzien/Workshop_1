package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class TaskManager {


    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static final String FILE_NAME = "tasks.csv";

    static final String PATH = "/home/shin/Workshop_1/tasks.csv";
    static String[][] TASKS;

    public static void main(String[] args)  {

        TASKS = loadDataToTab();
        showMenu(OPTIONS);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "add":
                    addTask();
                    System.out.println("The task was successfully added to the Task Manager.");
                    break;
                case "remove":
                    removeTask(TASKS, chooseTaskIndexNumber());
                    break;
                case "list":
                    printTab(TASKS);
                    break;
                case "exit":
                    saveTabToFile(FILE_NAME, TASKS);
                    System.out.println("Program terminated." + "\n" + "Have a great day!");
                    break;
                default:
                    System.out.println(ConsoleColors.RED_BOLD +
                            "You have selected an invalid command!" +
                            ConsoleColors.RESET + "\n" + ConsoleColors.GREEN +
                            "Please select add, remove, list or exit." +
                            ConsoleColors.RESET);
            }

        }
    }

    public static void showMenu(String[] tab) {
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE + "add " + ConsoleColors.GREEN + "+ enter - This will add a new task.");
        System.out.println(ConsoleColors.PURPLE + "remove " + ConsoleColors.GREEN + "+ enter - This will remove a task.");
        System.out.println(ConsoleColors.PURPLE + "list " + ConsoleColors.GREEN + "+ enter - This will list all stored tasks.");
        System.out.println(ConsoleColors.PURPLE + "exit " + ConsoleColors.GREEN + "+ enter - This will exit the program.");
        for (String x : tab) {
            System.out.println(ConsoleColors.RESET);
            System.out.println(ConsoleColors.PURPLE + x);


        }
        System.out.println();
        System.out.print("Type in your choice: ");
    }

    public static String[][] loadDataToTab() {
        Path dir = Paths.get(PATH);
        if (Files.notExists(dir)) {
            System.out.println(ConsoleColors.RED_BOLD + "Cannot load file! File does not exist!" +
                    ConsoleColors.RESET);
            System.exit(0);
        }

        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                System.arraycopy(split, 0, tab[i], 0, split.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;

    }

    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.RESET);
        System.out.println("Please add a short task description.");
        String description = scanner.nextLine();
        System.out.println("Please add task due date in the following format: yyyy-mm-dd");
        String dueDate = scanner.nextLine();
        System.out.println("Classify if the task is important: true/false");
        String isImportant = scanner.nextLine();
        TASKS = Arrays.copyOf(TASKS, TASKS.length + 1);
        TASKS[TASKS.length - 1] = new String[3];
        TASKS[TASKS.length - 1][0] = description;
        TASKS[TASKS.length - 1][1] = dueDate;
        TASKS[TASKS.length - 1][2] = isImportant;

    }

    public static void removeTask(String[][] tab, int index) {
        try {
            if (index >= 0 && index < tab.length) {
                TASKS = ArrayUtils.remove(tab, index);
                System.out.println("The task was successfully removed.");
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ConsoleColors.RED_BOLD + "Chosen element does not exist in the list of tasks." +
                    ConsoleColors.RESET);
        }
    }

    public static int chooseTaskIndexNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the number of task to be removed");

        String n = scanner.nextLine();
        while (!NumberUtils.isParsable(n) || Integer.parseInt(n) < 0) {
            System.out.println(ConsoleColors.RED_BOLD + "Number you have chosen is incorrect. Please provide a number greater or equal to 0" +
                    ConsoleColors.RESET);
            n = scanner.nextLine();
        }
        return Integer.parseInt(n);
    }

    public static void saveTabToFile(String FILE_NAME, String[][] tab) {
        Path dir = Paths.get(FILE_NAME);

        String[] lines = new String[TASKS.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}