package org.example;

import java.time.LocalDate;
import java.util.*;

public class order implements Comparable<order> {
    private static int id=1000;
    private static final Scanner scanner =new Scanner(System.in);
    private customer customer;
    private int orderId;
    protected Set<foodItem> items;  // Use a Set to prevent duplicates
    private LocalDate orderDate;
    private String status;
    protected TreeMap<foodItem,Integer> qtyMap;
    protected Map<foodItem,String> statusMap;
    private String specialRequest;


    public order(customer customer, String status) {
        this.customer = customer;
        this.orderId = id;
        this.orderDate = LocalDate.now();
        this.status = status;
        this.items = new HashSet<>();
        this.qtyMap=new TreeMap<>();
        this.statusMap=new HashMap<>();
        id++;
    }

    protected int totalPriceCal() {
        double total = 0;
        for (foodItem item : items) {
            total += item.getPrice() * item.getQty();
        }
        return (int) total;
    }

    public Set<foodItem> getItems() {
        return items;
    }

    public void setItems(Set<foodItem> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public TreeMap<foodItem, Integer> getQtyMap() {
        return qtyMap;
    }

    public void setQtyMap(TreeMap<foodItem, Integer> qtyMap) {
        this.qtyMap = qtyMap;
    }


    public Map<foodItem, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<foodItem, String> statusMap) {
        this.statusMap = statusMap;
    }

    public org.example.customer getCustomer() {
        return customer;
    }

    public void setCustomer(org.example.customer customer) {
        this.customer = customer;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }


    @Override
    public int compareTo(order other) {
        int dateComparison = this.orderDate.compareTo(other.orderDate);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return Integer.compare(this.orderId, other.orderId);
    }
}

