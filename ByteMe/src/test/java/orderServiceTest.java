import org.example.Main;
import org.example.customer;
import org.example.foodItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class orderServiceTest {

    private TreeMap<String, foodItem> allItems; // Mock allItems data
    private customer testCustomer;
    private HashMap<String,customer> allCustomers;

    @BeforeEach
    void setUp() {
        allItems = new TreeMap<>();
        testCustomer = new customer("k", "k");

        allCustomers = new HashMap<>();
        allCustomers.put(testCustomer.getEmail(), testCustomer);
        foodItem outOfStockItem = new foodItem("Burger", 5.0, "F01", 0); // Out of stock
        foodItem availableItem = new foodItem("Pizza", 10.0, "F02", 10); // In stock
        allItems.put("F01", outOfStockItem);
        allItems.put("F02", availableItem);

        // Set shared resources
        org.example.menu.allItems = allItems;
        Main.allCustomers = allCustomers;
    }

    @Test
    void testAddOutOfStockItem() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        testCustomer.getCustomerCart().checkAvailableAndAdd(allItems.get("F01"),1,"F01",testCustomer);// Call the method being tested
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Food item not available anymore!"),
                "Expected an out-of-stock message in the output");
        assertFalse(testCustomer.getCustomerCart().getItems().contains(allItems.get("F01")) ,
                "Out-of-stock item should not be added");
    }
}

