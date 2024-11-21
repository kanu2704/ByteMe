package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class customer implements Comparable<customer>{
    private String name;
    private String email;
    private TreeSet<order> allOrders;//to see for order history
    private cart customerCart;
    private TreeSet<order> pendingOrders;
    private TreeSet<review> reviews;
    protected boolean isVIP;
    protected List<foodItem> pastFoodItem;

    public customer(String name, String email){
        this.name=name;
        this.email=email;
        this.customerCart= new cart(email,"Cart empty..no order placed..");
        this.allOrders=new TreeSet<>();
        this.reviews=new TreeSet<>();
        this.isVIP=false;
        this.pendingOrders=new TreeSet<>();
        this.pastFoodItem=new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public TreeSet<order> getOrders() {
        return allOrders;
    }
    public void addOrder(order newOrder) {
        pendingOrders.add(newOrder);
        System.out.println(pendingOrders.size());
    }
    public cart getCustomerCart() {
        return customerCart;
    }
    public void setCustomerCart(cart customerCart) {
        this.customerCart = customerCart;
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
    public void setAllOrders(TreeSet<order> allOrders) {
        this.allOrders = allOrders;
    }
    public TreeSet<review> getReviews() {
        return reviews;
    }

    public void setReviews(TreeSet<review> reviews) {
        this.reviews = reviews;
    }

    public List<foodItem> getPastFoodItem() {
        return pastFoodItem;
    }

    public void setPastFoodItem(List<foodItem> pastFoodItem) {
        this.pastFoodItem = pastFoodItem;
    }


    @Override
    public int compareTo(customer other) {
        return this.name.compareTo(other.name);
    }
}
