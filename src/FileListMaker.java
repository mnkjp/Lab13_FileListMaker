import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileListMaker {
    private static ArrayList<String> list = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean needsToBeSaved = false;
    private static String currentFile = null;

    public static void main(String[] args) {
        String command;
        do {
            command = displayMenu();
            try {
                switch (command.toUpperCase()) {
                    case "A":
                        add(scanner);
                        break;
                    case "D":
                        deleteItem();
                        break;
                    case "I":
                        insertItem();
                        break;
                    case "V":
                        printList();
                        break;
                    case "M":
                        moveItem();
                        break;
                    case "O":
                        openList();
                        break;
                    case "S":
                        saveFile();
                        break;
                    case "C":
                        clearList();
                        break;
                    case "Q":
                        if (confirmQuit()) {
                            System.out.println("Exiting program...");
                        }
                        break;
                    default:
                        System.out.println("Invalid command. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error with file operations: " + e.getMessage());
            }
        } while (!command.equalsIgnoreCase("Q"));
    }

    private static String displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("V - View (display) the list");
        System.out.println("M - Move an item in the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
        System.out.print("Enter your command: ");
        return scanner.nextLine();
    }

    public static void add(Scanner pipe) {
        System.out.println("Enter the item to add: ");
        String retVal = pipe.nextLine();
        list.add(retVal);
        needsToBeSaved = true;
        System.out.println(retVal + " has been added to the list.");
    }

    private static void deleteItem() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. No items to delete.");
            return;
        }
        printList();
        int index = getRangedInt("Enter the index of the item to delete (0 - " + (list.size() - 1) + "): ", 0, list.size() - 1);
        String removedItem = list.remove(index);
        needsToBeSaved = true;
        System.out.println("Item deleted: " + removedItem);
    }

    private static void insertItem() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. No items to insert.");
            return;
        }
        printList();
        int index = getRangedInt("Enter the index to insert the item at (0 - " + list.size() + "): ", 0, list.size());
        System.out.print("Enter the item to insert: ");
        String item = scanner.nextLine();
        list.add(index, item);
        needsToBeSaved = true;
        System.out.println("Item inserted at index " + index + ": " + item);
    }

    private static void moveItem() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. No items to move.");
            return;
        }
        printList();
        int fromIndex = getRangedInt("Enter the index of the item to move (0 - " + (list.size() - 1) + "): ", 0, list.size() - 1);
        int toIndex = getRangedInt("Enter the index to move the item to (0 - " + (list.size() - 1) + "): ", 0, list.size() - 1);
        String item = list.remove(fromIndex);
        list.add(toIndex, item);
        needsToBeSaved = true;
        System.out.println("Item moved from index " + fromIndex + " to index " + toIndex + ": " + item);
    }

    private static void printList() {
        if (list.isEmpty()) {
            System.out.println("The list is currently empty.");
        } else {
            System.out.println("Current List:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private static void openList() throws IOException {
        if (needsToBeSaved) {
            if (!confirmSave()) {
                System.out.println("Aborting open operation. Unsaved data.");
                return;
            }
        }
        System.out.print("Enter the filename to open: ");
        String filename = scanner.nextLine();
        loadFile(filename);
    }

    private static void loadFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            System.out.println("File not found. A new list will be created.");
            list.clear();
            currentFile = filename;
            needsToBeSaved = false;
            return;
        }
        list.clear();
        Files.lines(path).forEach(list::add);
        currentFile = filename;
        needsToBeSaved = false;
        System.out.println("List loaded from " + filename);
    }

    private static void saveFile() throws IOException {
        if (currentFile == null) {
            System.out.print("Enter a filename to save the list: ");
            currentFile = scanner.nextLine();
        }
        Path path = Paths.get(currentFile);
        Files.write(path, list);
        needsToBeSaved = false;
        System.out.println("List saved to " + currentFile);
    }

    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }

    private static boolean confirmQuit() {
        if (needsToBeSaved) {
            return confirmSave();
        }
        return true;
    }

    private static boolean confirmSave() {
        System.out.print("You have unsaved changes. Do you want to save the list? (Y/N): ");
        String response = scanner.nextLine().toUpperCase();
        if (response.equals("Y")) {
            try {
                saveFile();
            } catch (IOException e) {
                System.out.println("Error saving the file: " + e.getMessage());
                return false;
            }
        }
        return response.equals("Y");
    }

    private static int getRangedInt(String prompt, int low, int high) {
        int value = -1;
        while (value < low || value > high) {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.print("Please enter a valid number: ");
            }
            value = scanner.nextInt();
            scanner.nextLine();
        }
        return value;
    }
}