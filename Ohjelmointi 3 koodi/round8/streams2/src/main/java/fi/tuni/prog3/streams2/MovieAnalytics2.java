/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.streams2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Sakari
 */
public class MovieAnalytics2 {
    
     ArrayList<Movie> movieList = new ArrayList<>();
     
    public void populateWithData(String fileName) throws FileNotFoundException, IOException {
    try (var br = new BufferedReader(new FileReader(fileName))) {
        movieList = (ArrayList<Movie>) br.lines()
                .map(line -> line.split(";"))
                .map(acc -> new Movie(acc[0], Integer.parseInt(acc[1]), Integer.parseInt(acc[2]), acc[3], Double.parseDouble(acc[4]), acc[5]))
                .collect(Collectors.toList());
    }
    }
    
    public void printCountByDirector(int n){
        
        Map<String, Long> directorMovieCounts = movieList.stream()
                .collect(Collectors.groupingBy(Movie::getDirector,Collectors.counting()));
        
        directorMovieCounts.entrySet().stream()
                .sorted((entry1, entry2) -> {
                   int countComparison = Long.compare(entry2.getValue(), entry1.getValue());  
                if (countComparison != 0) {
                        return countComparison;
                    } else {
                        return entry1.getKey().compareTo(entry2.getKey()); 
                    }
                })
                .limit(n)
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " movies")
                .forEach(System.out::println);
                
               
    }
    
    public void printAverageDurationByGenre() {
        
        Map<String, Double> genreMoviedurations = movieList.stream()
                .collect(Collectors.groupingBy(Movie::getGenre,Collectors.averagingDouble(Movie::getDuration)));
        
        genreMoviedurations.entrySet().stream()
                .sorted((entry1, entry2) -> {
                   int countComparison = Double.compare(entry1.getValue(), entry2.getValue());  
                if (countComparison != 0) {
                        return countComparison;
                    } else {
                        return entry1.getKey().compareTo(entry2.getKey()); 
                    }
                })
                .map(entry -> entry.getKey() + ": " + String.format("%.2f", entry.getValue()))
                .forEach(System.out::println);
    }
    
    public void printAverageScoreByGenre(){
        
         Map<String, Double> genreMoviescores = movieList.stream()
                .collect(Collectors.groupingBy(Movie::getGenre,Collectors.averagingDouble(Movie::getScore)));
        
        genreMoviescores.entrySet().stream()
                .sorted((entry1, entry2) -> {
                   int countComparison = Double.compare(entry2.getValue(), entry1.getValue());  
                if (countComparison != 0) {
                        return countComparison;
                    } else {
                        return entry1.getKey().compareTo(entry2.getKey()); 
                    }
                })
                .map(entry -> entry.getKey() + ": " + String.format("%.2f", entry.getValue()))
                .forEach(System.out::println);
    }
}