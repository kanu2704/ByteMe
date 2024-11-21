package org.example;
import java.util.*;

import static org.example.foodItem.byQty;
import static org.example.menu.*;

public class admin {
    private static final Scanner scanner = new Scanner(System.in);
    protected static Queue<order> regularPendingOrders= new LinkedList<>();
    protected static Queue<order> regularVIPOrders= new LinkedList<>();
    protected static TreeMap<Integer,order> orderIdMap=new TreeMap<>();
    protected static List<order> speReq=new ArrayList<>();
    protected static Map<customer,Integer> refunds=new HashMap<>();
    private static List<order> dailyOrders=new ArrayList<>();
    private static int loss;
    protected static TreeSet<foodItem> dailyItemSales=new TreeSet<>(byQty);

    protected static void displayHomePage() {
        System.out.println("---------------Welcome to the Admin Home page------------------");

        int choice;
        do {
            System.out.println("Please choose an option:");
            System.out.println("1. Manage menu");
            System.out.println("2. Manage orders");
            System.out.println("3. generate the daily report for today :");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    boolean cont=true;
                    while (cont) {
                        System.out.println("Select one of the following options for modifying the menu:");
                        System.out.println("1. Add new Items to the menu");
                        System.out.println("2. Update existing items ");
                        System.out.println("3. remove items ");
                        System.out.println("4. Go back to the adminHome Page ");
                        System.out.print("Enter your choice: ");
                        int ch = scanner.nextInt();
                        scanner.nextLine();
                        switch (ch) {
                            case 1:
                                System.out.println("-----------------------------------------------------------------------");
                                menu.addMenuItem();
                                break;
                            case 2:
                                System.out.println("-----------------------------------------------------------------------");
                                String continueUpdating = "y";
                                while (continueUpdating.equals("y")) {
                                    System.out.print("Enter the food ID of the item you want to update: ");
                                    String foodId = scanner.nextLine();
                                    foodItem itemToUpdate = menu.allItems.get(foodId);
                                    if (itemToUpdate == null) {
                                        System.out.println("No item found with the given food ID. Please try again.");
                                        continue;
                                    }
                                    System.out.println("Updating item: " + itemToUpdate.getName());
                                    System.out.println("What would you like to change?");
                                    System.out.println("1. Update Name");
                                    System.out.println("2. Update Price");
                                    System.out.println("3. Update availability");
                                    System.out.println("4. Update food id");
                                    System.out.println("5. Go back to adminHome page");
                                    System.out.print("Enter your choice: ");
                                    int updateChoice = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (updateChoice) {
                                        case 1:
                                            System.out.print("Enter new name: ");
                                            String newName = scanner.nextLine();
                                            itemToUpdate.setName(newName);
                                            System.out.println("Item name updated successfully.");
                                            break;
                                        case 2:
                                            System.out.print("Enter new price: ");
                                            double newPrice = scanner.nextDouble();
                                            itemToUpdate.setPrice(newPrice);
                                            System.out.println("Item price updated successfully.");
                                            break;
                                        case 3:
                                            System.out.print("Enter new quantity: ");
                                            int newQty = scanner.nextInt();
                                            itemToUpdate.setAvailability(newQty);
                                            System.out.println("Item quantity updated successfully.");
                                            break;
                                        case 4:
                                            System.out.print("Enter new id: ");
                                            String newId = scanner.nextLine();
                                            itemToUpdate.setFoodId(newId);
                                            System.out.println("Item id updated successfully.");
                                            break;
                                        case 5:
                                            continueUpdating = "n";
                                            displayHomePage();
                                            break;
                                        default:
                                            System.out.println("Invalid choice. Please try again.");
                                            break;
                                    }
                                    System.out.println("Enter whether you want to continue updating or not(y/n) :");
                                    continueUpdating=scanner.nextLine();
                                }
                                break;
                            case 3:
                                System.out.println("--------------------------------------------");
                                boolean continueUpdate = true;
                                while (continueUpdate) {
                                    if (menu.allItems.isEmpty()) {
                                        System.out.println("No items to remove -----! :(((");
                                        break;
                                    }
                                    System.out.print("Enter the food ID of the item you want to remove : ");
                                    String foodId = scanner.nextLine();
                                    foodItem itemToUpdate = menu.allItems.get(foodId);
                                    if (itemToUpdate == null) {
                                        System.out.println("No item found with the given food ID. Please try again.");
                                        continue;
                                    }
                                    for (order order : itemToUpdate.getPendingOrders()) {
                                        order.getStatusMap().put(itemToUpdate, "denied");
                                        customer orderCustomer = order.getCustomer();
                                        int refundAmount = (int) (itemToUpdate.getPrice() * order.getQtyMap().get(itemToUpdate));
                                        admin.refunds.put(orderCustomer, admin.refunds.getOrDefault(orderCustomer, 0) + refundAmount);
                                        loss+=refundAmount;
                                    }
                                    menu.allItems.remove(foodId);
                                    System.out.println("Food item with code "+foodId+" removed!!");
                                    if(normalMenu.containsKey(foodId)){
                                        normalMenu.remove(foodId);
                                    }
                                    else if(drinkItems.containsKey(foodId)){
                                        drinkItems.remove(foodId);
                                    }
                                    else packagedItems.remove(foodId);
                                    for (Map.Entry<String, TreeMap<String, TreeMap<String, foodItem>>> mealEntry : dailyMenus.entrySet()) {
                                        TreeMap<String, TreeMap<String, foodItem>> dayMap = mealEntry.getValue();
                                        for (Map.Entry<String, TreeMap<String, foodItem>> dayEntry : dayMap.entrySet()) {
                                            TreeMap<String, foodItem> itemsMap = dayEntry.getValue();
                                            itemsMap.remove(foodId);
                                        }
                                    }
                                    System.out.print("Do you want to update another item? (yes/no): ");
                                    String response = scanner.nextLine();
                                    continueUpdate = response.equalsIgnoreCase("yes");
                                }
                                break;
                            case 4:
                                cont = false;
                                displayHomePage();
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("--------------------------------------------------------------------");
                    boolean conti=true;
                    while (conti) {
                        System.out.println("Select one of the following options for modifying the menu:");
                        System.out.println("1. View pending Orders");
                        System.out.println("2. Update order Status ");
                        System.out.println("3. Process refunds ");
                        System.out.println("4. Handle Special Requests ");
                        System.out.println("5. Go back to the admin Home page ");
                        System.out.print("Enter your choice: ");
                        int ch = scanner.nextInt();
                        scanner.nextLine();
                        switch (ch) {
                            case 1:
                                int i = 1;
                                System.out.println("All the pending VIP orders are as follows :");
                                for (order order : admin.regularVIPOrders) {
                                    System.out.println(i + " Order with order ID " + order.getOrderId() + " by customer " + order.getCustomer().getName());
                                    System.out.println("Items in this order :");
                                    for(foodItem item: order.getItems()){
                                        System.out.println(item.getName()+" with qty "+order.getQtyMap().get(item));
                                    }
                                    i++;
                                    System.out.println("-----------------");
                                }
                                int j = 1;
                                System.out.println("All the pending regular customer orders are as follows :");
                                for (order order : admin.regularPendingOrders) {
                                    System.out.println(j + " Order with order ID " + order.getOrderId() + " by customer " + order.getCustomer().getName());
                                    System.out.println("Items in this order :");
                                    for(foodItem item: order.getItems()){
                                        System.out.println(item.getName()+" with qty "+order.getQtyMap().get(item));
                                    }
                                    j++;
                                    System.out.println("------------------");
                                }
                                break;
                            case 2:
                                System.out.println("------------------------------------------------------------------");
                                System.out.println("Dealing with the VIP orders first....");
                                String c1 = "y";
                                while (!regularVIPOrders.isEmpty() && c1.equals("y")) {
                                    order order = regularVIPOrders.peek();
                                    System.out.println("The items in this order are :");
                                    for(foodItem item: order.getItems()){
                                        System.out.println(item.getName()+" with availability "+(item.getAvailability()-item.getQty()));
                                    }
                                    System.out.print("Enter status for all items in order (Delivered/Denied/etc.): ");
                                    String status = scanner.nextLine().trim();
                                    for (foodItem item : order.getItems()) {
                                        order.getStatusMap().put(item, status);
                                    }
                                    if (status.equalsIgnoreCase("Delivered")) {
                                        order.setStatus("Delivered");
                                        order.getCustomer().addToAllOrders(order);
                                        order.getCustomer().getPendingOrders().remove(order);
                                        regularVIPOrders.poll();
                                        dailyOrders.add(order);
                                        dailyItemSales.addAll(order.getItems());
                                        if (speReq.contains(order)) {
                                            speReq.remove(order);
                                            System.out.println(order.getSpecialRequest());
                                        }
                                        for(foodItem item: order.getItems()){
                                            if(!order.getCustomer().getPastFoodItem().contains(item)){
                                                order.getCustomer().getPastFoodItem().add(item);
                                            }
                                        }
                                        System.out.println("Order status updated to " + status + " for customer " + order.getCustomer().getName());
                                    } else {
                                        System.out.println("Order status set to " + status + " but not marked as delivered.");
                                    }
                                    System.out.println("If you want to continue dealing with orders..enter y else enter anything:");
                                    c1 = scanner.nextLine().trim();
                                }
                                System.out.println("Dealing with the regular orders now ....");
                                c1 = "y";
                                if(regularVIPOrders.isEmpty()){
                                    while (!regularPendingOrders.isEmpty() && c1.equals("y")) {
                                        order order = regularPendingOrders.peek();
                                        for(foodItem item: order.getItems()){
                                            System.out.println(item.getName()+" with availability "+(item.getAvailability()-item.getQty()));
                                        }
                                        System.out.print("Enter status for all items in order (Delivered/Denied/etc.): ");
                                        String status = scanner.nextLine().trim();
                                        for (foodItem item : order.getItems()) {
                                            order.getStatusMap().put(item, status);
                                        }
                                        if (status.equalsIgnoreCase("Delivered")) {
                                            order.setStatus("Delivered");
                                            order.getCustomer().addToAllOrders(order);
                                            order.getCustomer().getPendingOrders().remove(order);
                                            regularPendingOrders.poll();
                                            dailyOrders.add(order);
                                            dailyItemSales.addAll(order.getItems());
                                            if (speReq.contains(order)) {
                                                speReq.remove(order);
                                                System.out.println(order.getSpecialRequest());
                                            }
                                            for(foodItem item: order.getItems()){
                                                if(!order.getCustomer().getPastFoodItem().contains(item)){
                                                    order.getCustomer().getPastFoodItem().add(item);
                                                }
                                            }
                                            System.out.println("Order status updated to delivered for customer " + order.getCustomer().getName());
                                        } else {
                                            System.out.println("Order status set to " + status + " but not marked as delivered.");
                                        }
                                        System.out.println("If you want to continue dealing with orders..enter y else enter anything:");
                                        c1 = scanner.nextLine().trim();
                                    }
                                }else{
                                    System.out.println("Can't deal with regular orders since the VIP orders are not finished!");
                                }
                                break;
                            case 3:
                                System.out.println("-----------------------------------------------------------");
                                System.out.println("Processing Refunds...");
                                Iterator<Map.Entry<customer, Integer>> refundIterator = refunds.entrySet().iterator();
                                while (refundIterator.hasNext()) {
                                    Map.Entry<customer, Integer> entry = refundIterator.next();
                                    customer refundCustomer = entry.getKey();
                                    Integer refundAmount = entry.getValue();
                                    System.out.println("Refund request for customer " + refundCustomer.getName() + " with amount: $" + refundAmount);
                                    System.out.print("Do you want to process this refund? (y/n): ");
                                    String refundChoice = scanner.nextLine().trim();
                                    if (refundChoice.equalsIgnoreCase("y")) {
                                        System.out.println("Refund processed for customer " + refundCustomer.getName());
                                        refundIterator.remove();
                                    } else {
                                        System.out.println("Refund not processed for customer " + refundCustomer.getName());
                                    }
                                    System.out.print("Do you want to continue with other refunds? (y/n): ");
                                    String continueChoice = scanner.nextLine().trim();
                                    if (!continueChoice.equalsIgnoreCase("y")) {
                                        break;
                                    }
                                }
                                break;
                            case 4:
                                System.out.println("-----------------------------------------------------------");
                                System.out.println("The special requests are as follows :");
                                for (order order : speReq) {
                                    System.out.println("For order Id " + order.getOrderId() + " Special requests are :" + order.getSpecialRequest());
                                }
                                break;
                            case 5:
                                admin.displayHomePage();
                                conti = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("-----------------------------------------------------------------------");
                    int totalSales = 0;
                    for (order ord : dailyOrders) {
                        totalSales += ord.totalPriceCal();
                    }
                    int totalOrders = dailyOrders.size();
                    List<foodItem> popularItems = new ArrayList<>(dailyItemSales);
                    foodItem mostPopularItem = null;
                    foodItem secondMostPopularItem = null;
                    if (popularItems.size() >= 2) {
                        mostPopularItem = popularItems.getLast();
                        secondMostPopularItem = popularItems.get(popularItems.size() - 2);
                    } else if (popularItems.size() == 1) {
                        mostPopularItem = popularItems.getFirst();
                    }
                    System.out.println("----- Daily Sales Report -----");
                    System.out.println("Total Sales: $" + (totalSales-loss));
                    System.out.println("Total Orders: " + totalOrders);
                    if (mostPopularItem != null) {
                        System.out.println("Most Popular Item: " + mostPopularItem.getName() + " - Sold " + mostPopularItem.getQty() + " times");
                    }
                    if (secondMostPopularItem != null) {
                        System.out.println("Second Most Popular Item: " + secondMostPopularItem.getName() + " - Sold " + secondMostPopularItem.getQty() + " times");
                    }
                    System.out.println("------------------------------");
                    break;
                case 4:
                    System.out.println("Going back.....");
                    Main.openingPage();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }while(!(choice==4));
    }
}
