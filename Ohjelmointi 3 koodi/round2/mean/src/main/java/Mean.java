/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import java.util.Scanner;
/**
 *
 * @author Sakari
 */
public class Mean {

    public static void main(String[] args) {
       
        System.out.print("Enter numbers:");
        Scanner myScanner = new Scanner(System.in);
        
        String line = myScanner.nextLine();
        String[] numbers = line.split(" ");
        double vastaus = 0;
        for (int i = 0; i < numbers.length; i++) {
            double d = Double.parseDouble(numbers[i]);
            vastaus += d ;
        }
        
        System.out.print("\n"+"Mean: "+ vastaus/numbers.length);
    }
}
