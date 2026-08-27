/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.shapes;

/**
 *
 * @author Sakari
 */
public class Rectangle implements IShapeMetrics {
    
    double height;
    double width;

    public Rectangle(double height, double width) {
        this.height = height;
        this.width = width;
    }
     
    public String toString() {
       String heightstring = String.format ("%.2f",height);
       String widthstring = String.format ("%.2f",width);
       return "Rectangle with height " +heightstring +" and width "+widthstring;    
    }

    @Override
    public String name() {
        return "rectangle";
    }

    @Override
    public double area() {
        double area = height*width;
        return area;
    }

    @Override
    public double circumference() {
       double circumference = height+width+height+width;
       return circumference;
    }
    
    
}
