package org.example;

import java.util.*;

public class foodItem implements Comparable<foodItem> { // Implement Comparable
    private String name;
    private double price;
    private String foodId;
    protected int qty;
    private int availability;
    private TreeMap<customer, List<review>> reviews;
    private List<order> pendingOrders;


    public foodItem(String name, double price, String foodId, int availability) {
        this.name = name;
        this.price = price;
        this.foodId = foodId;
        this.qty = 0;
        this.availability =availability ;
        this.reviews=new TreeMap<>();
        this.pendingOrders=new ArrayList<>();
    }
    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }

    public void displayDetails() {
        System.out.println("Name: " + this.name);
        System.out.println("ID: " + this.foodId);
        System.out.println("Price: " + this.price);
    }

    public String getName() {
        return name;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }


    public TreeMap<customer, List<review>> getReviews() {
        return reviews;
    }

    public void setReviews(TreeMap<customer, List<review>> reviews) {
        this.reviews = reviews;
    }


    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setPendingOrders(List<order> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public List<order> getPendingOrders() {
        return pendingOrders;
    }


    // Comparators
    public static final Comparator<foodItem> byName = Comparator.comparing(foodItem::getName);
    public static final Comparator<foodItem> byQty = Comparator.comparingInt(foodItem::getQty);
    public static final Comparator<foodItem> byPrice = Comparator.comparingDouble(foodItem::getPrice);

    @Override
    public int compareTo(foodItem other) {
        return this.name.compareTo(other.name); // Comparing based on name; change as needed
    }
}

