package org.example;

import java.io.*;
import java.util.TreeMap;

import static org.example.customer.customerCarts;

public class cartController {

    public static TreeMap<customer, cart> pendingCarts = new TreeMap<>();

    // Save carts to file
    public static void saveCartsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userCart.txt"))) {
            oos.writeObject(customerCarts);
            System.out.println("Carts saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving carts.");
            e.printStackTrace();
        }
    }

    // Load carts from file
    public static void loadCartsFromFile() {
        File file = new File("userCart.txt");

        if (!file.exists() || file.length() == 0) {
            System.out.println("File doesn't exist or is empty. No data to load.");
            pendingCarts = new TreeMap<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Load the objects from the file
            pendingCarts = (TreeMap<customer, cart>) ois.readObject();
            if (pendingCarts == null) {
                pendingCarts = new TreeMap<>(); // Initialize pendingCarts if it is null
            }
            System.out.println("Carts loaded successfully.");
        } catch (EOFException e) {
            System.out.println("EOFException: The file might be incomplete or corrupted.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization:");
            e.printStackTrace();
        }
    }
}
