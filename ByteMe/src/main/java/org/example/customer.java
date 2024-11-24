package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.example.historyController.customerOrders;
import static org.example.cartController.pendingCarts;
import static org.example.pendingOrdersController.CustomerPendingOrders;

public class customer implements Comparable<customer>, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private TreeSet<order> allOrders;//to see for order history
    private cart customerCart;
    private TreeSet<order> pendingOrders;
    private TreeSet<review> reviews;
    protected boolean isVIP;
    protected List<foodItem> pastFoodItem;
    public static TreeMap<customer, TreeSet<order>> customerPreviousOrders;
    public static TreeMap<customer,cart> customerCarts;
    public static TreeMap<customer,TreeSet<order>> customerPendingOrders;
    public customer(String name, String email){
        this.name=name;
        this.email=email;
        System.out.println("made customer");
        customerCarts=pendingCarts;
        customerPreviousOrders=customerOrders;
        customerPendingOrders=CustomerPendingOrders;
        if (customerPreviousOrders == null) {
            customerPreviousOrders = new TreeMap<>();
        }else{
            if(customerPreviousOrders.containsKey(this)){
                this.allOrders=customerPreviousOrders.get(this);
            }else{
                this.allOrders=new TreeSet<>();
            }
        }
        if (customerCarts == null) {
            customerCarts = new TreeMap<>();
        }else{
            if(customerCarts.containsKey(this)){
                this.customerCart=customerCarts.get(this);
            }else{
                this.customerCart= new cart(email,"Cart empty..no order placed..");
            }
        }
        System.out.println("made cart also");
        if (customerPendingOrders == null) {
            customerPendingOrders = new TreeMap<>();
        }else{
            if(customerPendingOrders.containsKey(this)){
                this.pendingOrders=customerPendingOrders.get(this);
            }else{
                this.pendingOrders=new TreeSet<>();
            }
        }
        this.reviews=new TreeSet<>();
        this.isVIP=false;
        this.pastFoodItem=new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public void addOrder(order newOrder) {
        pendingOrders.add(newOrder);
        System.out.println(pendingOrders.size());
    }
    public cart getCustomerCart() {
        return customerCart;
    }
    public TreeSet<order> getPendingOrders(){
        return pendingOrders;
    }
    public void addToAllOrders(order order){
        allOrders.add(order);
    }
    public TreeSet<order> getAllOrders() {
        return allOrders;
    }
    public TreeSet<review> getReviews() {
        return reviews;
    }

    public List<foodItem> getPastFoodItem() {
        return pastFoodItem;
    }


    @Override
    public int compareTo(customer other) {
        return this.name.compareTo(other.name);
    }
}
