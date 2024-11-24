package org.example;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

import static org.example.order.regularPendingOrders;
import static org.example.order.regularVIPOrders;

public class customerHomePage implements Serializable {
    private static final long serialVersionUID = 1L;

    private static transient Scanner scanner = new Scanner(System.in); // Scanner to get user input
    private static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);  // Initialize if not already initialized
        }
        return scanner;
    }

    protected static void displayHomePage(customer customer) throws FileNotFoundException {
        scanner=getScanner();
        System.out.println("---------------Welcome to the Customer Home page------------------");

        int choice;
        do {
            System.out.println("Please choose an option:");
            System.out.println("1. View Menu");
            System.out.println("2. Manage Cart");
            System.out.println("3. Manage Your Orders");
            System.out.println("4. Become a VIP ");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    menu.displayMenu(customer);
                    break;
                case 2:
                    customer.getCustomerCart().cartMenu(customer);
                    break;
                case 3:
                    boolean continueCart = true;
                    while (continueCart) {
                        System.out.println("Select one of the following options for modifying the menu:");
                        System.out.println("1. View order status");
                        System.out.println("2. Cancel pending order");
                        System.out.println("3. View your previous orders' details");
                        System.out.println("4. Provide review for a foodItem ");
                        System.out.println("5. See reviews for foodItems");
                        System.out.println("6. Go back to Customer Home page");
                        System.out.print("Enter your choice: ");
                        int ch = scanner.nextInt();
                        scanner.nextLine();
                        switch (ch) {
                            case 1:
                                for (order order : customer.getPendingOrders()) {
                                    System.out.println("Order with order ID :" + order.getOrderId() + " and date :" + order.getOrderDate() + " is " + order.getStatus());
                                    for (Map.Entry<foodItem, String> entry : order.getStatusMap().entrySet()) {
                                        foodItem key = entry.getKey();
                                        String value = entry.getValue();
                                        System.out.println(key.getName() + " with status " + value);
                                        System.out.println("---------------------------");
                                    }
                                }
                                System.out.println("----------------------");
                                break;
                            case 2:
                                if (customer.getPendingOrders().isEmpty()) {
                                    System.out.println("No pending orders to cancel.");
                                    return;
                                }
                                boolean continueCancellation = true;
                                while (continueCancellation) {
                                    System.out.println("Enter the ID of the order that you want to cancel:");
                                    int id = scanner.nextInt();
                                    scanner.nextLine();
                                    Optional<order> orderToCancel = customer.getPendingOrders().stream()
                                            .filter(order -> order.getOrderId() == id)
                                            .findFirst();
                                    if (orderToCancel.isPresent()) {
                                        orderToCancel.get().setStatus(" Cancelled..");
                                        admin.refunds.put(customer,orderToCancel.get().totalPriceCal());
                                        customer.getPendingOrders().remove(orderToCancel.get());
                                        customer.getAllOrders().add(orderToCancel.get());
                                        regularPendingOrders.remove(orderToCancel);
                                        regularVIPOrders.remove(orderToCancel);
                                        System.out.println("Order with ID " + id + " has been canceled.");
                                    } else {
                                        System.out.println("Order ID not found. Please enter a valid ID.");
                                    }
                                    System.out.println("Do you want to cancel another order? (yes/no)");
                                    String response = scanner.nextLine();
                                    if (response.equalsIgnoreCase("no")) {
                                        continueCancellation = false;
                                        System.out.println("Exiting order cancellation.");
                                    }
                                    if (customer.getPendingOrders().isEmpty()) {
                                        System.out.println("No more pending orders to cancel.");
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                System.out.println("Select an option:");
                                System.out.println("1. See your order's history");
                                System.out.println("2. Reorder a previous order");
                                System.out.println("3. See your refunds");
                                System.out.println("4. go back to customer home page");
                                int c = scanner.nextInt();
                                scanner.nextLine();
                                switch (c) {
                                    case 1:
                                        System.out.println("-----Your Orders' History--------");
                                        if (customer.getAllOrders().isEmpty()) {
                                            System.out.println("No previous orders... Your order history is empty :(");
                                        } else {
                                            for (order order : customer.getAllOrders()) {
                                                System.out.println("Order ID: " + order.getOrderId() +
                                                        ", Date: " + order.getOrderDate() +
                                                        ", Status: " + order.getStatus());
                                                for(foodItem item: order.getItems()){
                                                    System.out.println("item :"+item.getName()+" with qty :"+item.getQty());
                                                }
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (customer.getAllOrders().isEmpty()) {
                                            System.out.println("No previous orders to reorder.");
                                        } else {
                                            System.out.println("Enter the Order ID of the previous order you want to reorder:");
                                            int reorderId = scanner.nextInt();
                                            scanner.nextLine();
                                            Optional<order> orderToReorder = customer.getAllOrders().stream()
                                                    .filter(order -> order.getOrderId() == reorderId)
                                                    .findFirst();
                                            if (orderToReorder.isPresent()) {
                                                order previousOrder = orderToReorder.get();
                                                order newOrder = new order(customer,"On the way.....");
                                                newOrder.setItems(new HashSet<>(previousOrder.getItems()));
                                                newOrder.setQtyMap(new TreeMap<>(previousOrder.getQtyMap()));
                                                newOrder.setStatusMap(new HashMap<>(previousOrder.getStatusMap()));
                                                customer.getPendingOrders().add(newOrder);
                                                System.out.println("Do you want to add any special Requests...(y/n)???");
                                                String ans=scanner.nextLine();
                                                if(ans.equals("y")){
                                                    System.out.print("Enter the request in one go for any items: ");
                                                    String req=scanner.nextLine();
                                                    newOrder.setSpecialRequest(req);
                                                    admin.speReq.add(newOrder);
                                                }
                                                System.out.println("______________________________________");
                                                System.out.println("The items for this order are :");
                                                Set<foodItem> set = newOrder.getItems();
                                                for (foodItem item : set) {
                                                    item.getPendingOrders().add(newOrder);
                                                }
                                                if(customer.isVIP){
                                                    regularVIPOrders.add(newOrder);
                                                }else{
                                                    regularPendingOrders.add(newOrder);
                                                }
                                                admin.orderIdMap.put(newOrder.getOrderId(),newOrder);
                                                System.out.println("order total: "+newOrder.totalPriceCal());
                                            } else {
                                                System.out.println("Order ID not found. Please try again.");
                                            }
                                        }
                                        break;
                                    case 3:
                                        if (admin.refunds.containsKey(customer)) {
                                            System.out.println("Your refund amount: $" + admin.refunds.get(customer));
                                        } else {
                                            System.out.println("No refunds available.");
                                            System.out.println("All your refunds are made....");
                                        }
                                        break;
                                    case 4:
                                        displayHomePage(customer);
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please select a valid option.");
                                        break;
                                }
                                break;
                            case 4:
                                System.out.println("Enter the foodId for which you wnt to add the review :");
                                String fid=scanner.next();
                                foodItem item=menu.allItems.get(fid);
                                review.addReview(item,customer);
                                break;
                            case 5:
                                System.out.println("Enter the foodId for which you want the review :");
                                String foodid=scanner.next();
                                foodItem it=menu.allItems.get(foodid);
                                review.seeReviews(it);
                                break;
                            case 6:
                                displayHomePage(customer);
                                System.out.println("Returning to the Customer Home page...");
                                continueCart = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                    break;
                case 4:
                    if(customer.isVIP){
                        System.out.println("You're already a VIP customer ...");
                    }else{
                        System.out.println("To become a VIP customer ..you have to play rupees 200 :");
                        System.out.println("Do you want to become a VIP (if yes enter yes..if no enter anything else ;)) :");
                        if (scanner.hasNextLine()) scanner.nextLine();
                        String ans=scanner.nextLine();
                        if(ans.equals("yes")){
                            System.out.println(" DOING THE PAYMENTT!!!!!!");
                            customer.isVIP=true;
                            System.out.println("Congrats!!!..you are a VIP customer now..");
                        }
                    }
                    displayHomePage(customer);
                    break;
                case 5:
                    System.out.println("Exiting the Customer Home page. Goodbye!");
                    Main.openingPage();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!(choice ==4));
    }
}

