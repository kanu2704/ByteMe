package org.example.GUI;

import org.example.foodItem;
import org.example.menuController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.border.LineBorder;

import static org.example.menuController.loadMenuFromFile;

public class guiEntryPoint extends JFrame {
    public guiEntryPoint() throws IOException, ClassNotFoundException {
        setTitle("Restaurant Menu");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        getContentPane().setBackground(new Color(128, 128, 128)); // Light gray color for the background

        JLabel heading = new JLabel("Welcome to the Canteen Management Ordering System", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20)); // Set a larger font for the heading
        heading.setForeground(Color.BLACK); // Set heading text color
        add(heading, BorderLayout.NORTH);

        System.out.println("coming here");

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Horizontal position (column)
        gbc.insets = new Insets(10, 10, 10, 10); // Add space around the buttons

        // Create the buttons
        JButton viewPendingOrdersButton = new JButton("View Pending Orders");
        JButton viewMenuButton = new JButton("View Menu");
        viewPendingOrdersButton.setPreferredSize(new Dimension(200, 50)); // Set size (width, height)
        viewMenuButton.setPreferredSize(new Dimension(200, 50)); // Set size (width, height)
        viewPendingOrdersButton.setBorder(new LineBorder(Color.BLACK, 2)); // Black border with thickness of 2
        viewMenuButton.setBorder(new LineBorder(Color.BLACK, 2)); // Black border with thickness of 2

        viewMenuButton.addActionListener(e -> {
            System.out.println("View Menu button clicked!");
            try {
                menuPage menu = new menuPage();
                menu.setVisible(true);  // Make menu visible
                dispose();              // Close current window
            } catch (IOException | ClassNotFoundException ex) {
                // Print the exception stack trace for debugging
                System.out.println("Error loading menuPage: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        viewPendingOrdersButton.addActionListener(e->{
            System.out.println("pending orders button clicked");
            try {
                viewOrderPage orders = new viewOrderPage();
                orders.setVisible(true);  // Make menu visible
                dispose();              // Close current window
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Error loading menuPage: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        gbc.gridy = 0; // First row for the first button
        optionsPanel.add(viewPendingOrdersButton, gbc);

        gbc.gridy = 1; // Second row for the second button
        optionsPanel.add(viewMenuButton, gbc);
        add(optionsPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Starting GUI mode...");
        SwingUtilities.invokeLater(() -> {
            try {
                new guiEntryPoint().setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

