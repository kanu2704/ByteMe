package org.example;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

import static org.example.menu.*;

public class menuController {

    public static Map<String, foodItem> AllItems;
    public static Map<String, foodItem> NormalMenu;
    public static Map<String, foodItem> DrinkItems;
    public static Map<String, foodItem> PackagedItems;
    public static TreeMap<String, TreeMap<String, TreeMap<String, foodItem>>> DailyMenus;

    // Save menu to file
    public static void saveMenuToFile() throws FileNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("menu.txt"))) {
            oos.writeObject(allItems);
            oos.writeObject(normalMenu);
            oos.writeObject(drinkItems);
            oos.writeObject(packagedItems);
            oos.writeObject(dailyMenus);
            System.out.println("Menu saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the menu.");
            e.printStackTrace();
        }
    }

    // Load menu from file
    public static void loadMenuFromFile() {
        File file = new File("menu.txt");

        // Check if the file exists and is not empty
        if (!file.exists() || file.length() == 0) {
            System.out.println("No menu found or the file is empty.");
            // Initialize collections to avoid NullPointerException later
            AllItems = new TreeMap<>();
            NormalMenu = new TreeMap<>();
            DrinkItems = new TreeMap<>();
            PackagedItems = new TreeMap<>();
            DailyMenus = new TreeMap<>();
            return;
        }

        // Try to load the file content
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Read objects from the file
            AllItems = (TreeMap<String, foodItem>) ois.readObject();
            NormalMenu = (TreeMap<String, foodItem>) ois.readObject();
            DrinkItems = (TreeMap<String, foodItem>) ois.readObject();
            PackagedItems = (TreeMap<String, foodItem>) ois.readObject();
            DailyMenus = (TreeMap<String, TreeMap<String, TreeMap<String, foodItem>>>) ois.readObject();

            // Ensure collections are not null after loading
            if (AllItems == null) {
                AllItems = new TreeMap<>(); // Initialize if null
            }
            if (NormalMenu == null) {
                NormalMenu = new TreeMap<>(); // Initialize if null
            }
            if (DrinkItems == null) {
                DrinkItems = new TreeMap<>(); // Initialize if null
            }
            if (PackagedItems == null) {
                PackagedItems = new TreeMap<>(); // Initialize if null
            }
            if (DailyMenus == null) {
                DailyMenus = new TreeMap<>(); // Initialize if null
            }

            System.out.println("Menu loaded successfully.");
        } catch (EOFException e) {
            System.out.println("End of file reached unexpectedly. The file might be corrupted.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while loading the file.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException in case of mismatched classes
            System.out.println("Class not found during deserialization.");
            e.printStackTrace();
        }
    }
}
