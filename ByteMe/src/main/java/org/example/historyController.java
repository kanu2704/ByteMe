package org.example;

import java.io.*;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.example.customer.customerPreviousOrders;

public class historyController {
    public static TreeMap<customer, TreeSet<order>> customerOrders = new TreeMap<>();

    // Save history to file
    public static void saveHistoryToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("orderHistories.txt"))) {

            oos.writeObject(customerPreviousOrders);
            System.out.println("Histories saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the order history.");
            e.printStackTrace();
        }
    }

    // Load history from file
    public static void loadHistoryFromFile() {
        File file = new File("orderHistories.txt");

        if (!file.exists() || file.length() == 0) {
            System.out.println("No order histories found or the file is empty.");
            customerOrders = new TreeMap<>();
            return; // Skip loading if the file is empty or doesn't exist
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            customerOrders = (TreeMap<customer, TreeSet<order>>) ois.readObject();
            if (customerOrders == null) {
                customerOrders = new TreeMap<>(); // Initialize if it's null
            }
            System.out.println("Histories loaded successfully.");
        } catch (EOFException e) {
            System.out.println("EOFException: The file might be incomplete or corrupted.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization:");
            e.printStackTrace();
        }
    }
}
