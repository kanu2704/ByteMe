package org.example;

import java.time.LocalDateTime;
import java.util.*;

import static org.example.Main.allCustomers;
import static org.example.menu.allItems;

public class cart extends order {
    private final Scanner scanner = new Scanner(System.in);

    public cart(String email, String status) {
        super(allCustomers.get(email), status);
    }
    public void cartMenu(customer customer) {
        boolean continueCart = true;
        while (continueCart) {
            System.out.println("Select one of the following options for modifying the menu:");
            System.out.println("1. Add items");
            System.out.println("2. Modify quantities");
            System.out.println("3. Remove Items");
            System.out.println("4. View total");
            System.out.println("5. Checkout Process");
            System.out.println("6. Go back to the Customer Home page");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItems();
                    break;
                case 2:
                    modifyQuantities();
                    break;
                case 3:
                    removeItems();
                    break;
                case 4:
                    System.out.println("The total price of the cart : "+totalPriceCalCart());
                    break;
                case 5:
                    checkout(customer);
                    continueCart = false;
                    break;
                case 6:
                    System.out.println("Returning to the Customer Home page...");
                    continueCart = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    protected int totalPriceCalCart() {
        double total = 0;
        for (foodItem item : items) {
            total += item.getPrice() * qtyMap.get(item);
        }
        return (int) total;
    }

    public void addItems(){
        System.out.println("-------------Add items only after viewing the menu----------------- ");
        String choice = "y";
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("Enter the Food ID:");
            String foodId = scanner.next();
            if (allItems.containsKey(foodId)) {
                foodItem item = allItems.get(foodId);
                System.out.println("Enter the quantity for " + item.getName() + ":");
                int foodQty = scanner.nextInt();
                if (allItems.get(foodId).getQty()+foodQty<allItems.get(foodId).getAvailability()) {
                    if(items.contains(item)){
                        qtyMap.compute(item, (k, crtQty) -> crtQty + foodQty);
                    }else{
                        items.add(item);
                        qtyMap.put(item,foodQty);
                        statusMap.put(item," On the way ....");
                    }
                    item.setQty(item.getQty()+foodQty);
                    System.out.println("Item added successfully!");
                } else {
                    System.out.println("Food item not available anymore!");
                }
            } else {
                System.out.println("---INCORRECT FOOD-ITEM ID PROVIDED----> Try again!");
            }
            System.out.println("Do you want to add another item? (y/n)");
            choice = scanner.next();
        }
        System.out.println("Exiting item addition.");
    }

    protected void displayCartItems() {
        TreeSet<foodItem> sortedItems = new TreeSet<>(foodItem.byQty);
        sortedItems.addAll(items);
        System.out.println("Cart Items (sorted by quantity):");
        for (foodItem item : sortedItems) {
            System.out.println(item.getFoodId()+" -> "+item.getName() + " - Quantity: " + qtyMap.get(item)+ ", Price: $" + item.getPrice());
        }
    }

    public void modifyQuantities() {
        System.out.println("___________________________________________________");
        String choice = "y";
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("---- Modify the quantity of a food item ----");
            displayCartItems();
            System.out.println("Enter the Food ID to modify:");
            String foodId = scanner.next();
            boolean itemFound = false;
            for (foodItem cartItem : items) {
                if (cartItem.getFoodId().equals(foodId)) {
                    System.out.println("Enter the new quantity for " + cartItem.getName() + ":");
                    int newQty = scanner.nextInt();
                    if (newQty > 0) {
                        cartItem.setQty(cartItem.getQty()-qtyMap.get(cartItem)+newQty);
                        qtyMap.put(cartItem,newQty);
                        System.out.println("Quantity updated successfully!");
                    } else {
                        System.out.println("Invalid quantity. Please enter a positive number.");
                    }
                    itemFound = true;
                    break;
                }
            }
            if (!itemFound) {
                System.out.println("Item not found in the cart. Please try again.");
            }
            System.out.println("Do you want to modify another item? (y/n)");
            choice = scanner.next();
        }
        System.out.println("Exiting quantity modification.");
    }

    public void removeItems() {
        System.out.println("_____________________________________________________");
        String choice = "y";
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("---- Remove an item from the cart ----");
            displayCartItems();
            System.out.println("Enter the Food ID of the item you want to remove:");
            String foodId = scanner.next();
            boolean itemRemoved = items.removeIf(cartItem -> cartItem.getFoodId().equals(foodId));
            if (itemRemoved) {
                qtyMap.remove(allItems.get(foodId));
                statusMap.remove(allItems.get(foodId));
                System.out.println("Item removed successfully!");
            } else {
                System.out.println("Item not found in the cart. Please try again.");
            }
            System.out.println("Do you want to remove another item? (y/n)");
            choice = scanner.next();
        }
        System.out.println("Exiting item removal.");
    }

    public void checkout(customer customer) {
        System.out.println("-------------Checking out-----------");
        order finalizedOrder = this;
        order newOrder = new order(customer, "On the way.......");
        newOrder.setItems(new HashSet<>(finalizedOrder.getItems()));
        newOrder.setQtyMap(new TreeMap<>(finalizedOrder.getQtyMap()));
        newOrder.setStatusMap(new HashMap<>(finalizedOrder.getStatusMap()));
        items.clear();
        qtyMap.clear();
        statusMap.clear();
        System.out.println("Do you want to add any special Requests...(y/n)???");
        String ans=scanner.nextLine();
        if(ans.equals("y")){
            System.out.print("Enter the request in one go for any items: ");
            String req=scanner.nextLine();
            newOrder.setSpecialRequest(req);
            admin.speReq.add(newOrder);
        }
        System.out.println("______________________________________");
        Set<foodItem> set = newOrder.getItems();
        System.out.println("Items in this order are :");
        for (foodItem item : set) {
            item.getPendingOrders().add(newOrder);
            System.out.println(item.getName()+" with qty"+newOrder.getQtyMap().get(item));
        }
        if(customer.isVIP){
            admin.regularVIPOrders.add(newOrder);
        }else{
            admin.regularPendingOrders.add(newOrder);
        }
        customer.addOrder(newOrder);
        admin.orderIdMap.put(newOrder.getOrderId(),newOrder);
        System.out.println("order total: "+newOrder.totalPriceCal() +" with order ID "+newOrder.getOrderId());
        System.out.println("Checkout complete. Cart is now empty.");
        System.out.println("----------------------------------------------");
    }
}