package org.example.GUI;

import org.example.foodItem;
import org.example.menu;
import org.example.menuController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.table.DefaultTableModel;

import static org.example.menuController.loadMenuFromFile;

public class menuPage extends JFrame {

    private Map<String, foodItem> allItems;
    private Map<String, foodItem> normalMenu;
    private Map<String, foodItem> drinkItems;
    private Map<String, foodItem> packagedItems;

    public menuPage() throws IOException, ClassNotFoundException {
        setTitle("------------------------------Menu------------------------------");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadMenuFromFile();
        allItems=menuController.AllItems;
        normalMenu=menuController.NormalMenu;
        drinkItems=menuController.DrinkItems;
        packagedItems=menuController.PackagedItems;
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));

        JButton btnAllMenu = new JButton("Entire Menu");
        JButton btnEverydayMenu = new JButton("Everyday Menu");
        JButton btnPackagedFoods = new JButton("Packaged Foods");
        JButton btnDrinkItems = new JButton("Drink Items");
        JButton btnBack = new JButton("Back");
        buttonPanel.add(btnAllMenu);
        buttonPanel.add(btnEverydayMenu);
        buttonPanel.add(btnPackagedFoods);
        buttonPanel.add(btnDrinkItems);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.WEST);

        JPanel tablePanel = new JPanel();
        add(tablePanel, BorderLayout.CENTER);
        btnAllMenu.addActionListener(e->allMenu(tablePanel));
        btnEverydayMenu.addActionListener(e -> displayEverydayMenu(tablePanel));
        btnPackagedFoods.addActionListener(e -> displayPackagedFoods(tablePanel));
        btnDrinkItems.addActionListener(e -> displayDrinkItems(tablePanel));
        btnBack.addActionListener(e -> {
            dispose();
            try {
                new guiEntryPoint().setVisible(true);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void allMenu(JPanel tablePanel) {
        tablePanel.removeAll(); // Clear previous table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Item Name");
        model.addColumn("Price");
        model.addColumn("Availability");
        allItems.forEach((name, item) -> {
            model.addRow(new Object[]{item.getName(), item.getPrice(), item.getAvailability()});
        });
        JTable table = new JTable(model);
        tablePanel.add(new JScrollPane(table)); // Add table to panel
        revalidate();
        repaint();
    }
    // Method to display Everyday Menu
    private void displayEverydayMenu(JPanel tablePanel) {
        tablePanel.removeAll(); // Clear previous table

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Item Name");
        model.addColumn("Price");
        model.addColumn("Availability");
        normalMenu.forEach((name, item) -> {
            model.addRow(new Object[]{item.getName(), item.getPrice(), item.getAvailability()});
        });
        JTable table = new JTable(model);
        tablePanel.add(new JScrollPane(table)); // Add table to panel
        revalidate();
        repaint();
    }

    private void displayPackagedFoods(JPanel tablePanel) {
        tablePanel.removeAll();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Item Name");
        model.addColumn("Price");
        model.addColumn("Availability");

        packagedItems.forEach((name, item) -> {
            model.addRow(new Object[]{item.getName(), item.getPrice(), item.getAvailability()});
        });
        JTable table = new JTable(model);
        tablePanel.add(new JScrollPane(table)); // Add table to panel
        revalidate();
        repaint();
    }

    private void displayDrinkItems(JPanel tablePanel) {
        tablePanel.removeAll(); // Clear previous table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Item Name");
        model.addColumn("Price");
        model.addColumn("Availability");
        drinkItems.forEach((name, item) -> {
            model.addRow(new Object[]{item.getName(), item.getPrice(), item.getAvailability()});
        });
        JTable table = new JTable(model);
        tablePanel.add(new JScrollPane(table)); // Add table to panel
        revalidate();
        repaint();
    }
}
