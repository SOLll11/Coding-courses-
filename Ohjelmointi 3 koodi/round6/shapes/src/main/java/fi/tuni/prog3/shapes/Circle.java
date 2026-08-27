/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fi.tuni.prog3.shapes;



/**
 *
 * @author Sakari
 */
public class Circle implements IShapeMetrics {
    
    double radius;

    public Circle(double radius) {
        this.radius = radius;
    }
    
    public String toString() {
       String radiusstring = String.format ("%.2f",radius);
       return "Circle with radius: " +radiusstring;
        
    }


    @Override
    public String name() {
        return "circle";
        
    }

    @Override
    public double area() {
       double area = PI*radius*radius;
       return area;
    }

    @Override
    public double circumference() {
        double circumference = 2*PI*radius;
        return circumference;
    }
    
  

    
}
