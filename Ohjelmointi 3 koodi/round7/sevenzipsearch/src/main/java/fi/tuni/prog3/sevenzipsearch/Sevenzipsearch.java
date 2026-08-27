/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fi.tuni.prog3.sevenzipsearch;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.File;


import java.io.IOException;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


import java.util.Scanner;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

/**
 *
 * @author Sakari
 */
public class Sevenzipsearch {

    public static void main(String[] args) 
            throws IOException {
        
        System.out.println("File:");
        Scanner myScanner = new Scanner(System.in);
        String FileName = myScanner.nextLine();
        System.out.println("Query:");
        String Word = myScanner.next();
        System. out. println();
        
        try (SevenZFile sevenZFile = new SevenZFile(new File(FileName))) {
            SevenZArchiveEntry entry;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                if (entry.getName().endsWith(".txt")) {
                    System.out.println(entry.getName());
                    ByteArrayOutputStream entryContent = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = sevenZFile.read(buffer)) != -1) {
                        entryContent.write(buffer, 0, bytesRead);
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(entryContent.toByteArray()), StandardCharsets.UTF_8));
                    
                    String line = reader.readLine();
                    
                    
                    String LowWord = Word.toLowerCase();
                    int rivi = 0;
                    while (line != null) {
                        
                        ++rivi;
                        if(line != null){
                            if (line.toLowerCase().contains(LowWord)) {
                                String resultString = line.replaceAll("(?i)" + LowWord, LowWord.toUpperCase());
                                System.out.println(rivi + ": " + resultString);
                                
                            }   
                        }
                        line = reader.readLine();
                        
                    }
                    System.out.println();
            
                    reader.close(); 
        }
    }
}
    }
}

