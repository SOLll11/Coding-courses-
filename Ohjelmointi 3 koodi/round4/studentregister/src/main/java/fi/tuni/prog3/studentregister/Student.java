/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.studentregister;

/**
 *
 * @author Sakari
 */
public class Student {
    
    private String name;
    private String studentnumber ;

    public Student(String name, String studentnumber) {
        this.name = name;
        this.studentnumber = studentnumber;
    }
    
    public String getName() {
        return name;
    }
    
    public String getStudentNumber() {
        return studentnumber;
    }
  
}


