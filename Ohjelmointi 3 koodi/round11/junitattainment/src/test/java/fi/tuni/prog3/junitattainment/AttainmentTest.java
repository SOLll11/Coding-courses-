/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3.junitattainment;

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
public class AttainmentTest {
    
    public AttainmentTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
   @org.junit.jupiter.api.Test
public void testConstructorWithIllegalParameter() {
    System.out.println("ConstructorWithIllegalParameter");
    int illegalValue = -1;
    
    assertThrows(IllegalArgumentException.class, () -> new Attainment("kurssi 1", "H300314", illegalValue));
}
    

    /**
     * Test of getCourseCode method, of class Attainment.
     */
    @org.junit.jupiter.api.Test
    public void testGetCourseCode() {
        System.out.println("getCourseCode");
        Attainment instance = new Attainment("kurssi 1","H300314",4);
        String expResult = "kurssi 1";
        String result = instance.getCourseCode();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getStudentNumber method, of class Attainment.
     */
    @org.junit.jupiter.api.Test
    public void testGetStudentNumber() {
        System.out.println("getStudentNumber");
        Attainment instance = new Attainment("kurssi 1","H300314",4);
        String expResult = "H300314";
        String result = instance.getStudentNumber();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getGrade method, of class Attainment.
     */
    @org.junit.jupiter.api.Test
    public void testGetGrade() {
        System.out.println("getGrade");
        Attainment instance = new Attainment("kurssi 1","H300314",4);
        int expResult = 4;
        int result = instance.getGrade();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of toString method, of class Attainment.
     */
    @org.junit.jupiter.api.Test
    public void testToString() {
        System.out.println("toString");
        Attainment instance = new Attainment("kurssi 1","H300314",4);
        String expResult = "kurssi 1 H300314 4";
        String result = instance.toString();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of compareTo method, of class Attainment.
     */
    @org.junit.jupiter.api.Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Attainment other = new Attainment("kurssi 1","H300314",4);
        Attainment instance = new Attainment("kurssi 1","H300314",4);
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        
        
    }

    
}
