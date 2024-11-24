package org.example;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static org.example.pendingOrdersController.regularOrders;
import static org.example.pendingOrdersController.vipOrders;

public class order implements Comparable<order>, Serializable {
    private static final long serialVersionUID = 1L;
    private static int id=1000;
    private static final transient Scanner scanner =new Scanner(System.in);
    private customer customer;
    private int orderId;
    protected Set<foodItem> items;  // Use a Set to prevent duplicates
    private LocalDate orderDate;
    private String status;
    protected TreeMap<foodItem,Integer> qtyMap;
    protected Map<foodItem,String> statusMap;
    private String specialRequest;
    protected static Queue<order> regularPendingOrders=new LinkedList<>();
    protected static Queue<order> regularVIPOrders= new LinkedList<>();
    public order(customer customer, String status) {
        this.customer = customer;
        this.orderId = id;
        this.orderDate = LocalDate.now();
        regularPendingOrders=regularOrders;
        regularVIPOrders=vipOrders;
        if (regularPendingOrders == null) {
            regularPendingOrders = new LinkedList<>(); // Initialize regularOrders if it's null
        }
        if (regularVIPOrders == null) {
            regularVIPOrders = new LinkedList<>(); // Initialize regularOrders if it's null
        }
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

    public LocalDate getOrderDate() {
        return orderDate;
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

