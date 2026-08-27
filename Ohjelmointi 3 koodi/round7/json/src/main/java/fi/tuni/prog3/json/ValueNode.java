/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.json;

/**
 *
 * @author Sakari
 */
public class ValueNode extends Node {
   
    
    private Double doubleValue;
    private Boolean booleanValue;
    private String stringValue ;
    
    

    public ValueNode() {
        this.doubleValue = null;
        this.booleanValue = null;
        this.stringValue = null;
    }
    
   
    public ValueNode(double doubleValue) {
        this.doubleValue = doubleValue;
        this.booleanValue = null;
        this.stringValue = null;
    }
    
    public ValueNode(boolean value) {
        this.booleanValue = value;
        this.doubleValue = null;
        this.stringValue = null;
    }
    
    public ValueNode(String value) {
        this.stringValue = value;
        this.doubleValue = null;
        this.booleanValue = null;
    }
    
    public boolean isNumber() {
        return doubleValue != null;
    }
    
    public boolean isBoolean() {
        return booleanValue != null;
    }
         
    public boolean isString() {
        return stringValue != null;
        
    }
    
    public boolean isNull() {
        return stringValue == null && booleanValue == null && doubleValue == null;
    }

    public boolean getBoolean() {
        return booleanValue;
    }

    public String getString() {
        return stringValue;
    }
    
    public Object getNull(){
        return null;
    }

    public Double getNumber() {
        return doubleValue;
    }
  
}
