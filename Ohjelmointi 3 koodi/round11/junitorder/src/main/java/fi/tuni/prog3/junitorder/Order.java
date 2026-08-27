/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fi.tuni.prog3.junitorder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 *
 * @author Sakari
 */
public class Order {
       private List<Entry> entries;

    public Order() {
        entries = new ArrayList<>();
    }

    public boolean addItems(Item item, int count) throws IllegalArgumentException, IllegalStateException {
        if (count <= 0) {
            throw new IllegalArgumentException("Item unit count must be positive");
        }

        for (Entry entry : entries) {
            if (entry.item.name.equals(item.name)) {
                if (entry.item.price != item.price) {
                    throw new IllegalStateException("Item with the same name but different price already exists in the order.");
                }
                entry.count += count;
                return true;
            }
        }

        entries.add(new Entry(item, count));
        return true;
    }

    public boolean addItems(String name, int count) throws IllegalArgumentException, NoSuchElementException {
        if (count <= 0) {
            throw new IllegalArgumentException("Item unit count must be positive");
        }

        for (Entry entry : entries) {
            if (entry.item.name.equals(name)) {
                entry.count += count;
                return true;
            }
        }

        throw new NoSuchElementException("No entry with the specified item name found in the order.");
    }

    public List<Entry> getEntries() {
        return new ArrayList<>(entries);
    }

    public int getEntryCount() {
        return entries.size();
    }

    public int getItemCount() {
        int totalCount = 0;
        for (Entry entry : entries) {
            totalCount += entry.count;
        }
        return totalCount;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (Entry entry : entries) {
            totalPrice += entry.item.price * entry.count;
        }
        return totalPrice;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public boolean removeItems(String name, int count) throws IllegalArgumentException, NoSuchElementException {
        if (count <= 0) {
            throw new IllegalArgumentException("Item unit count to remove must be positive");
        }

        for (Entry entry : entries) {
            if (entry.item.name.equals(name)) {
                if (entry.count < count) {
                    throw new IllegalArgumentException("Item unit count to remove is larger than the existing entry's item unit count.");
                }

                entry.count -= count;
                if (entry.count == 0) {
                    entries.remove(entry);
                }
                return true;
            }
        }

        throw new NoSuchElementException("No entry with the specified item name found in the order.");
    }

    
    
    public static class Item {
        
        private String name;
        private double price;

        public Item(String name, double price) {
            this.name = name;
            this.price = price;
            if ( name == null || price < 0){
                throw new IllegalArgumentException();
            }
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
        
        @Override
        public String toString() {
            
          return "Item("+ name + "," + price+")";    
        }

    }
    
    public static class Entry {
        
        Order.Item item;
        private int count;

        public Entry(Item item, int count) {
            this.item = item;
            this.count = count;
    
            if (item == null || count < 0) {
                throw new IllegalArgumentException();
            }
        }

        public Item getItem() {
            return item;
        }

        public int getCount() {
            return count;
        }
        
        String getItemName () {
            return item.getName();
        }
        
        double getUnitPrice() {
            return item.getPrice();
        }
        
        @Override
        public String toString() {
           return count +" units of "+ item.getName();
        }
        
    }
    
    
}
