package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

public class menu implements Serializable {
    private static final long serialVersionUID = 1L;
    private static transient Scanner scanner=new Scanner(System.in);
    public static TreeMap<String,foodItem> allItems=new TreeMap<>();
    static Map<String,foodItem> all=new HashMap<>();
    public static TreeMap<String, foodItem> normalMenu = new TreeMap<>(); // Sorted by item name
    protected static TreeMap<String, foodItem> drinkItems=new TreeMap<>();
    protected static TreeMap<String, foodItem> packagedItems=new TreeMap<>();
    protected static TreeMap<String, TreeMap<String, TreeMap<String, foodItem>>> dailyMenus = new TreeMap<>();
    private static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);  // Initialize if not already initialized
        }
        return scanner;
    }
    public static void displayMenu(customer customer) throws FileNotFoundException {
        scanner=getScanner();
        boolean continueMenu = true;
        while (continueMenu) {
            System.out.println("----------------------This is the Canteen Menu---------------------------");
            System.out.println("Select one of the following options for browsing the menu :");
            System.out.println("1. View all the items in the Menu");
            System.out.println("2. Search by Keyword");
            System.out.println("3. Filter by category");
            System.out.println("4. Sort the items by price");
            System.out.println("5. Go back to the Customer home page");
            System.out.print("Enter the choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("---------------------- All Food Items ----------------------");
                    for (Map.Entry<String, foodItem> entry : allItems.entrySet()) {
                        String itemId = entry.getKey();
                        foodItem item = entry.getValue();
                        item.displayDetails();
                    }
                    break;
                case 2:
                    boolean continueViewing = true;
                    while (continueViewing) {
                        System.out.print("Enter the name of the food item you want to view details for: ");
                        String itemName = scanner.nextLine().trim();
                        foodItem item = all.get(itemName);
                        if (item != null) {
                            System.out.println("Order id for item:"+ item.getFoodId());
                            System.out.println("Details for item: " + itemName);
                            System.out.println("Price: $" + item.getPrice());
                            System.out.println("Availability: " + (item.getAvailability()-item.getQty()));
                        } else {
                            System.out.println("No item found with the name '" + itemName + "'. Please try again.");
                        }

                        System.out.print("Do you want to view details for another item? (yes/no): ");
                        String response = scanner.nextLine().trim();
                        continueViewing = response.equalsIgnoreCase("yes");
                    }
                    System.out.println("Exiting item details view.");
                    break;
                case 3:
                    System.out.println("----------------------Filtering out------------------------");
                    System.out.println("Select the filter :");
                    System.out.println("1. today's menu: ");
                    System.out.println("2. everyday menu:");
                    System.out.println("3. packaged foods :");
                    System.out.println("4. drink items");
                    System.out.print("Enter the choice: ");
                    int ch = scanner.nextInt();
                    scanner.nextLine();
                    switch (ch) {
                        case 1:
                            displayTodaysMenu();
                            break;
                        case 2:
                            displaySortedMenuByName(normalMenu, "Everyday Items");
                            break;
                        case 3:
                            displaySortedMenuByName(packagedItems, "Packaged Items");
                            break;
                        case 4:
                            displaySortedMenuByName(drinkItems, "Drink Items");
                            break;
                    }
                    break;
                case 4:
                    displaySortedMenuByPrice(allItems,"All foodItems ");
                    break;
                case 5:
                    continueMenu = false;
                    System.out.println("Returning to the Customer home page...");
                    customerHomePage.displayHomePage(customer);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void displayTodaysMenu() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        String menuType;
        if (hour >= 8 && hour < 12) {
            menuType = "Breakfast";
        } else if (hour >= 12 && hour < 15) {
            menuType = "Lunch";
        } else if (hour >= 19 && hour < 22) {
            menuType = "Dinner";
        } else {
            System.out.println("No meal available at this time. Please check other menu categories.");
            return;
        }
        String currentDay = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        TreeMap<String, TreeMap<String, foodItem>> dayMenu = dailyMenus.get(menuType);
        if (dayMenu == null || !dayMenu.containsKey(currentDay)) {
            System.out.println("No items available for today's " + menuType + " menu.");
            return;
        }
        TreeMap<String, foodItem> todayItems = dayMenu.get(currentDay);
        List<Map.Entry<String, foodItem>> sortedItems = new ArrayList<>(todayItems.entrySet());
        sortedItems.sort(Map.Entry.comparingByValue(foodItem.byName));
        System.out.println("Today's " + menuType + " Menu (" + currentDay + "):");
        for (Map.Entry<String, foodItem> entry : sortedItems) {
            System.out.println(entry.getValue().getName() + " - $" + entry.getValue().getPrice()+" Availabilty :"+(entry.getValue().getAvailability()-entry.getValue().getQty()));
        }
    }
    private static void displaySortedMenuByName(TreeMap<String, foodItem> itemsMap, String header) {
        List<Map.Entry<String, foodItem>> sortedItems = new ArrayList<>(itemsMap.entrySet());
        sortedItems.sort(Map.Entry.comparingByValue(foodItem.byName));
        System.out.println(header + " sorted by name:");
        int count = 1;
        for (Map.Entry<String, foodItem> entry : sortedItems) {
            System.out.println(count + ". " + entry.getValue().getName() + " - $" + entry.getValue().getPrice()+" Availabilty :"+(entry.getValue().getAvailability()-entry.getValue().getQty()));
            count++;
        }

    }
    private static void displaySortedMenuByPrice(TreeMap<String, foodItem> itemsMap, String header){
        List<Map.Entry<String, foodItem>> sortedItems = new ArrayList<>(itemsMap.entrySet());
        sortedItems.sort(Map.Entry.comparingByValue(foodItem.byPrice));
        System.out.println(header + " sorted by price:");
        int count = 1;
        for (Map.Entry<String, foodItem> entry : sortedItems) {
            System.out.println(count + ". " + entry.getValue().getName() + " - $" + entry.getValue().getPrice()+" Availabilty :"+(entry.getValue().getAvailability()-entry.getValue().getQty()));
            count++;
        }
        System.out.println();
        sortedItems.sort(Map.Entry.comparingByValue(foodItem.byPrice.reversed()));
        System.out.println(header + " sorted by price (Descending):");
        count = 1;
        for (Map.Entry<String, foodItem> entry : sortedItems) {
            System.out.println(count + ". " + entry.getValue().getName() + " - $" + entry.getValue().getPrice() +
                    " Availability: " + (entry.getValue().getAvailability()-entry.getValue().getQty()));
            count++;
        }
    }
    protected static void addMenuItem() {
        boolean continueAdding = true;
        while (continueAdding) {
            System.out.println("-------- Add New Menu Item --------");
            System.out.println("Select the category to add the item:");
            System.out.println("1. Everyday Items");
            System.out.println("2. Drink Items");
            System.out.println("3. Packaged Foods");
            System.out.println("4. Add to the daily Menu");
            System.out.println("5. Exit to Main Menu");
            int categoryChoice = scanner.nextInt();
            scanner.nextLine();
            if (categoryChoice == 5) {
                continueAdding = false;
                System.out.println("Exiting to Main Menu...");
                break;
            }
            System.out.print("Enter item name: ");
            String name = scanner.nextLine();
            System.out.print("Enter item price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter item id: ");
            String id = scanner.nextLine();
            System.out.print("Enter the availability: ");
            int availability = scanner.nextInt();
            scanner.nextLine();
            foodItem newItem = new foodItem(name, price, id, availability);
            switch (categoryChoice) {
                case 1:
                    normalMenu.put(name, newItem);
                    System.out.println("Item added to Everyday Items.");
                    break;
                case 2:
                    drinkItems.put(name, newItem);
                    System.out.println("Item added to Drink Items.");
                    break;
                case 3:
                    packagedItems.put(name, newItem);
                    System.out.println("Item added to Packaged Foods.");
                    break;
                case 4:
                    System.out.print("Do you want to add this item to a daily menu (Breakfast, Lunch, Dinner)? (yes/no): ");
                    String addToDailyMenu = scanner.nextLine().trim();
                    if (addToDailyMenu.equalsIgnoreCase("yes")) {
                        System.out.println("Select the meal type:");
                        System.out.println("1. Breakfast");
                        System.out.println("2. Lunch");
                        System.out.println("3. Dinner");
                        int mealChoice = scanner.nextInt();
                        scanner.nextLine();
                        String mealType;
                        switch (mealChoice) {
                            case 1: mealType = "Breakfast"; break;
                            case 2: mealType = "Lunch"; break;
                            case 3: mealType = "Dinner"; break;
                            default:
                                System.out.println("Invalid meal type. Skipping daily menu addition.");
                                continue;
                        }
                        System.out.print("Enter the day of the week (e.g., Monday, Tuesday): ");
                        String dayOfWeek = scanner.nextLine().trim();
                        dailyMenus
                                .computeIfAbsent(mealType, k -> new TreeMap<>())
                                .computeIfAbsent(dayOfWeek, k -> new TreeMap<>())
                                .put(id, newItem);

                        System.out.println("Item added to " + mealType + " menu on " + dayOfWeek + ".");
                    }
                    System.out.print("Do you want to add another item? (yes/no): ");
                    continueAdding = scanner.nextLine().trim().equalsIgnoreCase("yes");
                    break;
                default:
                    System.out.println("Invalid category. Please try again.");
            }
            allItems.put(name, newItem);
        }
    }

}
