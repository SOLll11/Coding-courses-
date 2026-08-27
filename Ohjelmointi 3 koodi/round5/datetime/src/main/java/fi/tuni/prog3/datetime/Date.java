/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.datetime;

/**
 *
 * @author Sakari
 */
public class Date {
    
    private int year;
    private int month;
    private int day;

     public Date(int year, int month, int day) throws DateException {
        if (!isValidDate(year, month, day)) {
            throw new DateException("Illegal date " + String.format("%02d.%02d.%04d", day, month, year));
        }

        this.year = year;
        this.month = month;
        this.day = day;
    }
    

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
    
    public String toString() {
        return String.format("%02d.%02d.%04d", day, month, year);
    }
    
    private boolean isValidDate(int year, int month, int day) {
        if (year < 1 || month < 1 || month > 12 || day < 1) {
            return false;
        }
        int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (year % 4 == 0 && year % 100 != 0 || (year % 400 == 0)){
            daysInMonth[1] = 29;
        }

        return day <= daysInMonth[month - 1];
    }
}




         

        
 


