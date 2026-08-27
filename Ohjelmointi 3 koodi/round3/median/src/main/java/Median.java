/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Sakari
 */
public class Median {

    public static void main(String[] args) {
        System.out.print("Enter numbers:");
        Scanner myScanner = new Scanner(System.in);
        
        String line = myScanner.nextLine();
        String[] numbers = line.split(" ");
        ArrayList doublenumbers = new ArrayList<Double>();
        
        for (int i = 0; i < numbers.length; i++) {
          double d = Double.parseDouble(numbers[i]);
          doublenumbers.add(d);
        }
        
        Collections.sort(doublenumbers);
        double anwser = 0;
        
        int size = doublenumbers.size();
        
        if (doublenumbers.size()%2 == 0) {
        anwser = (double) doublenumbers.get(size/2);
        anwser += (double) doublenumbers.get(size/2-1);
        anwser = anwser/2;
        }
        else {
        anwser = (double) doublenumbers.get(size/2);        
        }
        
        System.out.print("\n"+"Median: " + anwser);
        
        
        
    }          
}
