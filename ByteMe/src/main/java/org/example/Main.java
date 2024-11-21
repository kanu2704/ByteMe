package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static org.example.menu.*;

public class Main {
    public static Map<String, customer> allCustomers = new HashMap<>(); // email, instance
    public static admin adminInstance = new admin();
    public static customerHomePage customerHomePage = new customerHomePage();
    public static menu menu = new menu();
    protected static void openingPage(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("------------Welcome to Byte Me !!!!!!!!!---------------");
        System.out.println();

        String userType;
        do {
            System.out.print("Are you an admin or a customer? (Enter 'admin', 'customer', or 'exit' to quit): ");
            userType = scanner.nextLine().trim().toLowerCase();
            customer customer = null;
            if (userType.equals("customer")) {
                System.out.print("Please enter your email: ");
                String email = scanner.nextLine().trim();
                if (allCustomers.containsKey(email)) {
                    customer = allCustomers.get(email);
                    customerHomePage.displayHomePage(customer);
                } else {
                    System.out.println("Customer not found. Please check your email.");
                }
            } else if (userType.equals("admin")) {
                adminInstance.displayHomePage();
            } else if (!userType.equals("exit")) {
                System.out.println("Invalid input. Please enter 'admin', 'customer', or 'exit' to quit.");
            }
        } while (!userType.equals("exit"));
        exit();
        scanner.close();
    }
    public static void main(String[] args){
        customer c1=new customer("kanu","kanu@example.com");
        customer c2=new customer("ishaan","ishaan@example.com");
        customer c3=new customer("manad","manad@example.com");
        allCustomers.put(c1.getEmail(),c1);
        allCustomers.put(c2.getEmail(),c2);
        allCustomers.put(c3.getEmail(),c3);
        foodItem f1=new foodItem("paratha",40,"APZ",10);
        foodItem f2=new foodItem("fries",50,"ff",20);
        foodItem f3=new foodItem("pepsi",20,"pp",30);
        drinkItems.put(f3.getFoodId(),f3);
        normalMenu.put(f2.getFoodId(),f2);
        normalMenu.put(f1.getFoodId(),f1);
        allItems.put(f1.getFoodId(),f1);
        allItems.put(f2.getFoodId(),f2);
        allItems.put(f3.getFoodId(),f3);
        all.put(f1.getName(),f1);
        all.put(f2.getName(),f2);
        all.put(f3.getName(),f3);
        foodItem pancakes = new foodItem("pancakes", 5.99, "B101", 10);
        foodItem omelette = new foodItem("omelette", 6.49, "B102", 8);
        foodItem burger = new foodItem("burger", 8.99, "L201", 5);
        foodItem pasta = new foodItem("pasta", 7.99, "D301", 12);
        foodItem steak = new foodItem("steak", 14.99, "D302", 7);
        allItems.put(pancakes.getFoodId(),pancakes);
        allItems.put(omelette.getFoodId(),omelette);
        allItems.put(burger.getFoodId(),burger);
        allItems.put(pasta.getFoodId(),pasta);
        allItems.put(steak.getFoodId(),steak);
        all.put(pancakes.getName(),pancakes);
        all.put(omelette.getName(),omelette);
        all.put(burger.getName(),burger);
        all.put(pasta.getName(),pasta);
        all.put(steak.getName(),steak);
        dailyMenus.computeIfAbsent("Breakfast", k -> new TreeMap<>())
                .computeIfAbsent("Monday", k -> new TreeMap<>())
                .put(pancakes.getFoodId(), pancakes);
        dailyMenus.computeIfAbsent("Breakfast", k -> new TreeMap<>())
                .computeIfAbsent("Monday", k -> new TreeMap<>())
                .put(omelette.getFoodId(), omelette);
        dailyMenus.computeIfAbsent("Lunch", k -> new TreeMap<>())
                .computeIfAbsent("Wednesday", k -> new TreeMap<>())
                .put(burger.getFoodId(), burger);
        dailyMenus.computeIfAbsent("Lunch", k -> new TreeMap<>())
                .computeIfAbsent("Wednesday", k -> new TreeMap<>())
                .put(f2.getFoodId(), f2);
        dailyMenus.computeIfAbsent("Dinner", k -> new TreeMap<>())
                .computeIfAbsent("Friday", k -> new TreeMap<>())
                .put(pasta.getFoodId(), pasta);
        dailyMenus.computeIfAbsent("Dinner", k -> new TreeMap<>())
                .computeIfAbsent("Friday", k -> new TreeMap<>())
                .put(steak.getFoodId(), steak);
        dailyMenus.computeIfAbsent("Breakfast", k -> new TreeMap<>())
                .computeIfAbsent("Sunday", k -> new TreeMap<>())
                .put(pasta.getFoodId(), pasta);
        dailyMenus.computeIfAbsent("Breakfast", k -> new TreeMap<>())
                .computeIfAbsent("Sunday", k -> new TreeMap<>())
                .put(steak.getFoodId(), steak);
        dailyMenus.computeIfAbsent("Lunch", k -> new TreeMap<>())
                .computeIfAbsent("Thursday", k -> new TreeMap<>())
                .put(pasta.getFoodId(), pasta);
        dailyMenus.computeIfAbsent("Lunch", k -> new TreeMap<>())
                .computeIfAbsent("Thursday", k -> new TreeMap<>())
                .put(steak.getFoodId(), steak);

        openingPage();
    }
    public static void exit(){
        System.out.println("Thank you for using Byte Me! Goodbye!!!!!!!!!!!!!!!!!!!.");
        System.exit(0);
    }
}

