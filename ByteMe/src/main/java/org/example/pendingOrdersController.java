package org.example;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.example.customer.customerPendingOrders;
import static org.example.order.regularPendingOrders;
import static org.example.order.regularVIPOrders;

public class pendingOrdersController {

    public static Queue<order> regularOrders;
    public static Queue<order> vipOrders;
    public static TreeMap<customer, TreeSet<order>> CustomerPendingOrders;

    // Save orders to file
    public static void saveOrderToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("customerPendingOrders.txt"))) {
            oos.writeObject(regularPendingOrders);
            oos.writeObject(regularVIPOrders);
            oos.writeObject(customerPendingOrders);
            System.out.println("Orders saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving orders.");
            e.printStackTrace();
        }
    }

    // Load orders from file
    public static void loadOrderFromFile() {
        File file = new File("customerPendingOrders.txt");

        // Check if the file exists and is not empty
        if (!file.exists() || file.length() == 0) {
            System.out.println("No orders found or the file is empty.");
            // Initialize to default values
            regularOrders = new LinkedList<>();
            vipOrders = new LinkedList<>();
            CustomerPendingOrders = new TreeMap<>();
            return;
        }

        // Try to load the file content
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Read objects from the file
            regularOrders = (Queue<order>) ois.readObject();
            vipOrders = (Queue<order>) ois.readObject();
            CustomerPendingOrders = (TreeMap<customer, TreeSet<order>>) ois.readObject();

            // Check if any of them are null, and initialize them
            if (regularOrders == null) {
                System.out.println("no regular pending orders here");
                regularOrders = new LinkedList<>();
            }
            if (vipOrders == null) {
                vipOrders = new LinkedList<>();
            }
            if (CustomerPendingOrders == null) {
                CustomerPendingOrders = new TreeMap<>();
            }

            System.out.println("Orders loaded successfully.");
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
