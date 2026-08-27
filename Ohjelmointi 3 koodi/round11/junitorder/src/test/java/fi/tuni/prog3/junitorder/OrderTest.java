/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3.junitorder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Sakari
 */
public class OrderTest {
    
    public OrderTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    
    }
    
    @AfterEach
    public void tearDown() {
    }
    
     @Test
    public void testAddItemsWithNegativeCountShouldThrowIllegalArgumentException() {
        Order instance = new Order();
        Order.Item item = new Order.Item("TestItem", 10.0);
        int count = -5;
        assertThrows(IllegalArgumentException.class, () -> instance.addItems(item, count));
    }

    @Test
    public void testAddItemsWithDifferentPriceForExistingItemShouldThrowIllegalStateException() {
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        instance.addItems(item1, 2);
        Order.Item item2 = new Order.Item("Item1", 15.0); // Different price
        int count = 3;
        assertThrows(IllegalStateException.class, () -> instance.addItems(item2, count));
    }
    
    
      @Test
    public void testAddItemsWithStringWithNegativeCountShouldThrowIllegalArgumentException() {
        Order instance = new Order();
        Order.Item item = new Order.Item("TestItem", 10.0);
        instance.addItems(item, 2);
        String name = "TestItem";
        int count = -5;
        assertThrows(IllegalArgumentException.class, () -> instance.addItems(name, count));
    }

    @Test
    public void testAddItemsWithNonExistentItemNameShouldThrowNoSuchElementException() {
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        instance.addItems(item1, 2);
        String name = "NonExistentItem";
        int count = 3;
        assertThrows(NoSuchElementException.class, () -> instance.addItems(name, count));
    }

    @Test
    public void testRemoveItemsWithNegativeCountShouldThrowIllegalArgumentException() {
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        instance.addItems(item1, 2);
        String name = "Item1";
        int count = -1;
        assertThrows(IllegalArgumentException.class, () -> instance.removeItems(name, count));
    }

    @Test
    public void testRemoveItemsWithNonExistentItemNameShouldThrowNoSuchElementException() {
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        instance.addItems(item1, 2);
        String name = "NonExistentItem";
        int count = 1;
        assertThrows(NoSuchElementException.class, () -> instance.removeItems(name, count));
    }

    @Test
    public void testRemoveItemsWithCountGreaterThanExistingCountShouldThrowIllegalArgumentException() {
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        instance.addItems(item1, 2);
        String name = "Item1";
        int count = 3; // Attempt to remove more than available
        assertThrows(IllegalArgumentException.class, () -> instance.removeItems(name, count));
    }

    @Test
    public void testGetTotalPriceWithMultipleItems() {
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        Order.Item item2 = new Order.Item("Item2", 15.0);
        instance.addItems(item1, 2);
        instance.addItems(item2, 3);
        double expResult = (2 * 10.0) + (3 * 15.0);
        double result = instance.getTotalPrice();
        assertEquals(expResult, result, 0);
    }

    @Test
    public void testIsEmptyWithNonEmptyOrder() {
        Order instance = new Order();
        Order.Item item = new Order.Item("Item1", 10.0);
        instance.addItems(item, 2);
        assertFalse(instance.isEmpty());
    }

    /**
     * Test of addItems method, of class Order.
     */
    @Test
    public void testAddItems_OrderItem_int() {
        System.out.println("addItems");
        Order.Item item = new Order.Item("TestItem", 10.0);
         int count = 5;
        Order instance = new Order();
        boolean expResult = true;
        boolean result = instance.addItems(item, count);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of addItems method, of class Order.
     */
    @Test
    public void testAddItems_String_int() {
        System.out.println("addItems");
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        Order.Item item2 = new Order.Item("Item2", 15.0);
        instance.addItems(item1, 2);
        instance.addItems(item2, 3);
        String name = "Item1";
        int count = 5;
        boolean expResult = true;
        boolean result = instance.addItems(name, count);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getEntries method, of class Order.
     */
    @Test
public void testGetEntries() {
    System.out.println("getEntries");

    Order orderInstance = new Order();

    // Create and add some items to the order
    Order.Item item1 = new Order.Item("Item1", 10.0);
    Order.Item item2 = new Order.Item("Item2", 15.0);
    orderInstance.addItems(item1, 2);
    orderInstance.addItems(item2, 3);

    List<Order.Entry> expResult = new ArrayList<>();
    expResult.add(new Order.Entry(item1, 2));
    expResult.add(new Order.Entry(item2, 3));

    List<Order.Entry> result = orderInstance.getEntries();

    // Check if the contents of the lists are the same
    assertEquals(expResult.size(), result.size());

}


    /**
     * Test of getEntryCount method, of class Order.
     */
    @Test
    public void testGetEntryCount() {
        System.out.println("getEntryCount");
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        Order.Item item2 = new Order.Item("Item2", 15.0);
        instance.addItems(item1, 2);
        instance.addItems(item2, 3);
        int expResult = 2;
        int result = instance.getEntryCount();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getItemCount method, of class Order.
     */
    @Test
    public void testGetItemCount() {
        System.out.println("getItemCount");
        Order instance = new Order();
        int expResult = 0;
        int result = instance.getItemCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotalPrice method, of class Order.
     */
    @Test
    public void testGetTotalPrice() {
        System.out.println("getTotalPrice");
        Order instance = new Order();
        double expResult = 0.0;
        double result = instance.getTotalPrice();
        assertEquals(expResult, result, 0);
        
    }
   

    /**
     * Test of isEmpty method, of class Order.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        Order instance = new Order();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of removeItems method, of class Order.
     */
    @Test
    public void testRemoveItems() {
        System.out.println("removeItems");
        Order instance = new Order();
        Order.Item item1 = new Order.Item("Item1", 10.0);
        Order.Item item2 = new Order.Item("Item2", 15.0);
        instance.addItems(item1, 2);
        instance.addItems(item2, 3);
        String name = "Item1";
        int count = 1;
        boolean expResult = true;
        boolean result = instance.removeItems(name, count);
        assertEquals(expResult, result);
        
    }
    
     @Test
    public void testValidEntryConstructor() {
        Order.Item validItem = new Order.Item("Item1", 1.30); 
        int validCount = 5;
        
        Order.Entry entry = new Order.Entry(validItem, validCount);
        
        assertEquals(validItem, entry.getItem());
        assertEquals(validCount, entry.getCount());
    }


    @Test
    public void testNegativeCountConstructor() {
        Order.Item validItem = new Order.Item("Item1", 1.30); 
        int negativeCount = -1;
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Order.Entry(validItem, negativeCount);
        });
    }
    
    @Test
    public void testValidItemConstructor() {
        String validName = "Valid Item";
        double validPrice = 10.0;
        
        Order.Item item = new Order.Item(validName, validPrice);
        
        assertEquals(validName, item.getName());
        assertEquals(validPrice, item.getPrice(), 0.01); // Use a delta for double comparison
    }

    @Test
    public void testNullNameConstructor() {
        double validPrice = 5.0;
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Order.Item(null, validPrice);
        });
    }

    @Test
    public void testNegativePriceConstructor() {
        String validName = "Valid Item";
        double negativePrice = -1.0;
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Order.Item(validName, negativePrice);
        });
    }
    
    @Test
    public void testGetName() {
        Order.Item item = new Order.Item("TestItem", 10.0);
        String name = item.getName();
        assertEquals("TestItem", name);
    }

    @Test
    public void testGetPrice() {
        Order.Item item = new Order.Item("TestItem", 10.0);
        double price = item.getPrice();
        assertEquals(10.0, price, 0.01); // Use a delta for double comparison
    }

    @Test
    public void testToString() {
        Order.Item item = new Order.Item("TestItem", 10.0);
        String str = item.toString();
        assertEquals("Item(TestItem, 10.00)", str);
    }
    
    @Test
    public void testGetItem() {
        Order.Item item = new Order.Item("TestItem", 10.0);
        Order.Entry entry = new Order.Entry(item, 5);
        Order.Item entryItem = entry.getItem();
        assertEquals(item, entryItem);
    }

    @Test
    public void testGetCount() {
        Order.Item item = new Order.Item("TestItem", 10.0);
        Order.Entry entry = new Order.Entry(item, 5);
        int count = entry.getCount();
        assertEquals(5, count);
    }

    @Test
    public void testGetItemName() {
        Order.Item item = new Order.Item("TestItem", 10.0);
        Order.Entry entry = new Order.Entry(item, 5);
        String itemName = entry.getItemName();
        assertEquals("TestItem", itemName);
    }

    @Test
    public void testGetUnitPrice() {
        Order.Item item = new Order.Item("TestItem", 10.0);
        Order.Entry entry = new Order.Entry(item, 5);
        double unitPrice = entry.getUnitPrice();
        assertEquals(10.0, unitPrice, 0.01); // Use a delta for double comparison
    }

    @Test
    public void testEntryToString() {
        Order.Item item = new Order.Item("TestItem", 10.0);
        Order.Entry entry = new Order.Entry(item, 5);
        String str = entry.toString();
        assertEquals("5 units of Item(TestItem, 10.00)", str);
    }
    
}
