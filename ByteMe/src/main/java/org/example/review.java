package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class review implements Comparable<review>, Serializable {
    private static final long serialVersionUID = 1L;
    private static transient Scanner scanner=new Scanner(System.in);
    private customer customer;
    private String reviewDescription;
    private foodItem item;
    private int stars;
    private Scanner getScanner() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);  // Initialize if not already initialized
        }
        return this.scanner;
    }
    public review(customer customer,foodItem item,int stars){
        this.scanner=getScanner();
        this.customer=customer;
        this.item=item;
        this.stars=stars;
    }

    public org.example.customer getCustomer() {
        return customer;
    }

    public void setCustomer(org.example.customer customer) {
        this.customer = customer;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public foodItem getItem() {
        return item;
    }

    public void setItem(foodItem item) {
        this.item = item;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public int compareTo(review other) {
        return Integer.compare(other.stars, this.stars);
    }
    public static void seeReviews(foodItem item) {
        if (item.getReviews().isEmpty()) {
            System.out.println("No reviews available for this item.");
            return;
        }
        for (Map.Entry<customer, List<review>> entry : item.getReviews().entrySet()) {
            customer customer = entry.getKey();
            List<review> reviewList = entry.getValue();
            System.out.println("Customer: " + customer.getName());
            for (review rev : reviewList) {
                System.out.println("Review: " + rev.stars +"stars :) "+ rev.getReviewDescription());
            }
            System.out.println();
        }
    }

    public static void addReview(foodItem item,customer customer){
        if(!customer.getPastFoodItem().contains(item)){
            System.out.println("Item not ordered earlier you cant put a review");
        }else{
            String des;
            int st;
            System.out.println("Enter the number of stars(1-5)");
            st=scanner.nextInt();
            System.out.println("Enter the description :");
            des=scanner.next();
            review rev=new review(customer,item,st);
            rev.setReviewDescription(des);
            item.getReviews().putIfAbsent(customer,new ArrayList<>());
            item.getReviews().get(customer).add(rev);
            customer.getReviews().add(rev);
            System.out.println("----------Review added!!-----------");
        }
    }
}
