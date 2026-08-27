
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author Sakari
 */
public class Parameters {

    public static void main(String[] args) {
        System.out.print("Parameters:");
        Scanner myScanner = new Scanner(System.in);
        
        ArrayList paraList = new ArrayList<>();
        while(myScanner.hasNextLine()) {
           String line = myScanner.nextLine();
           paraList.add(line);
        }
        
        Collections.sort(paraList);
        
        int length_of_one = Integer.toString(paraList.size()).length();
        
        
        int length_of_two = 0;
        
        for (var para : paraList ) {
          String name = para.toString();
          int length = name.length();
          if (length > length_of_two) {
              length_of_two = length;
          }   
        }
        
        String bar = "";
        for (int i = 0; i < length_of_two + length_of_one + 7; i++ ) {
            bar += "#";
        }
        String bartwo = "";
        bartwo+="#";
        bartwo+="-".repeat(length_of_one+2);
        bartwo+="+";
        bartwo+="-".repeat(length_of_two+2);
        bartwo+="#";
        
        int count = 1;
        System.out.print("\n"+bar);
        for (var para : paraList ) {
            String name = para.toString();
            int namelength = name.length();
            String vali = "";
            if (Integer.toString(count).length()< length_of_one) {
                
                for (int i = 0; i< length_of_one- Integer.toString(count).length()+1; ++i) {
                    vali += " ";
                }
                
            }
            else {
                 vali += " ";
            }
            String toinenvali = "";
            for (int i = 0; i< length_of_two - namelength; ++i) {
                toinenvali += " ";
            }
            System.out.print("\n"+ "#"+vali+count+" | "+name+toinenvali+" #");
            if (count == paraList.size()){
                break;
            }
            else {
                System.out.print("\n"+bartwo);
            }
            ++count;
        }
        System.out.print("\n"+bar);
        
    

}
}

    

