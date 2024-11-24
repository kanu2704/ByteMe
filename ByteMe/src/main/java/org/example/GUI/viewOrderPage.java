package org.example.GUI;

import org.example.foodItem;
import org.example.order;
import org.example.pendingOrdersController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Queue;

public class viewOrderPage extends JFrame {
    private static Queue<order> RegularOrders;
    private static Queue<order> VipOrders;
    public viewOrderPage() throws IOException, ClassNotFoundException {
        setTitle("View Orders");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton btnVIPOrders = new JButton("View VIP Orders");
        JButton btnRegularOrders = new JButton("View Regular Orders");
        JButton btnBack = new JButton("Back"); // Back button

        buttonPanel.add(btnVIPOrders);
        buttonPanel.add(btnRegularOrders);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);

        // Load orders from file
        pendingOrdersController.loadOrderFromFile();
        RegularOrders = pendingOrdersController.regularOrders;
        VipOrders = pendingOrdersController.vipOrders;

        btnVIPOrders.addActionListener(e -> displayVIPOrders(tablePanel));
        btnRegularOrders.addActionListener(e -> displayRegularOrders(tablePanel));

        // Action listener for Back button
        btnBack.addActionListener(e -> {
            dispose();
            try {
                new guiEntryPoint().setVisible(true);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void displayVIPOrders(JPanel tablePanel) {
        tablePanel.removeAll(); // Clear previous content

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order ID");
        model.addColumn("Customer Name");
        model.addColumn("Items Ordered");
        model.addColumn("Status");

        for (order order : VipOrders) {
            StringBuilder items = new StringBuilder();
            for (foodItem item : order.getItems()) {
                items.append(item.getName())
                        .append(" (Qty: ")
                        .append(order.getQtyMap().get(item))
                        .append("), ");
            }
            if (!items.isEmpty()) items.setLength(items.length() - 2);
            model.addRow(new Object[]{
                    order.getOrderId(),
                    order.getCustomer().getName(),
                    items.toString(),
                    order.getStatus()
            });
        }
        JTable table = new JTable(model);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void displayRegularOrders(JPanel tablePanel) {
        tablePanel.removeAll(); // Clear previous content
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order ID");
        model.addColumn("Customer Name");
        model.addColumn("Items Ordered");
        model.addColumn("Status");
        System.out.println(RegularOrders.size());

        for (order order : RegularOrders) {
            StringBuilder items = new StringBuilder();
            for (foodItem item : order.getItems()) {
                items.append(item.getName())
                        .append(" (Qty: ")
                        .append(order.getQtyMap().get(item))
                        .append("), ");
            }
            // Remove trailing comma and space
            if (!items.isEmpty()) items.setLength(items.length() - 2);

            model.addRow(new Object[]{
                    order.getOrderId(),
                    order.getCustomer().getName(),
                    items.toString(),
                    order.getStatus()
            });
        }

        JTable table = new JTable(model);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
