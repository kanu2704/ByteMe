import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class cartOperationsTest {

    private TreeMap<String, foodItem> allItems; // Mock allItems data
    private customer testCustomer;
    private HashMap<String,customer> allCustomers;
    private cart cart;
    private foodItem item1;
    private foodItem item2;
    @BeforeEach
    void setUp() {
        allItems = new TreeMap<>();
        testCustomer = new customer("k", "k");
        allCustomers = new HashMap<>();
        allCustomers.put(testCustomer.getEmail(), testCustomer);
        item1 = new foodItem("Burger", 5.0, "F01", 4); // Out of stock
        item2 = new foodItem("Pizza", 10.0, "F02", 10); // In stock
        allItems.put("F01",item1);
        allItems.put("F02",item2);
        cart=testCustomer.getCustomerCart();
        menu.allItems = allItems;
        Main.allCustomers = allCustomers;
    }
    @Test
    void testAddItemToCartUpdatesTotalPrice() {
        System.out.println("siufgsiu");
        int qtyNow1=3;
        cart.getQtyMap().put(item1,qtyNow1);
        int qtyNow2=4;
        cart.getQtyMap().put(item2,qtyNow2);
        cart.getItems().add(item1);
        cart.getItems().add(item2);
        System.out.println("checking total");
        double expectedTotalPrice = 15.0+40.0; // Price of Pizza (F02) * quantity (2)
        assertEquals(expectedTotalPrice, testCustomer.getCustomerCart().totalPriceCalCart(), "Total price should reflect the sum of added items");
    }

    @Test
    void testModifyItemQuantityUpdatesTotalPrice() {
        System.out.println("siufgsiu");
        int qtyNow1=3;
        cart.getQtyMap().put(item1,qtyNow1);
        int qtyNow2=4;
        cart.getQtyMap().put(item2,qtyNow2);
        cart.getItems().add(item1);
        cart.getItems().add(item2);
        cart.getQtyMap().put(item2,5);
        double expectedTotalPrice = 15.0+50.0; // Updated quantity (3) * price per item
        assertEquals(expectedTotalPrice, cart.totalPriceCalCart(), "Total price should reflect the updated quantity of the item");
    }
    @Test
    void testPreventNegativeQuantity() {
        int newQty=-1;
        cart.checkNegative(newQty,item1,testCustomer);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String consoleOutput = outputStream.toString();
        assertFalse(consoleOutput.contains("Invalid quantity. Please enter a positive number."),
                "Expected an no negative value allowed in the output");
        assertFalse(consoleOutput.contains("Quantity updated successfully!"),
                "Out-of-stock item should not be added");
    }
}
