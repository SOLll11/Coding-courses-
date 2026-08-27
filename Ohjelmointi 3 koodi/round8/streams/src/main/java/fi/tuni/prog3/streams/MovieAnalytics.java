/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.streams;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Sakari
 */
public class MovieAnalytics {
    
    ArrayList<Movie> movieList = new ArrayList<>();

    public static Consumer<Movie> showInfo(){
        return (Movie t) -> {
            System.out.println(t.getTitle()+" (By "+t.getDirector()+", "+t.getReleaseYear()+")");
        };
         
    }

    public void populateWithData(String fileName) throws FileNotFoundException, IOException {
    try (var br = new BufferedReader(new FileReader(fileName))) {
        movieList = (ArrayList<Movie>) br.lines()
                .map(line -> line.split(";"))
                .map(acc -> new Movie(acc[0], Integer.parseInt(acc[1]), Integer.parseInt(acc[2]), acc[3], Double.parseDouble(acc[4]), acc[5]))
                .collect(Collectors.toList());
    }
    }
    
    public Stream<Movie> moviesAfter(int year){
        return movieList.stream()
                .distinct()
                .filter(movie -> movie.getReleaseYear()>= year)
                .sorted(Comparator
                        .comparingInt(Movie::getReleaseYear)
                        .thenComparing(Movie::getTitle));
    }
    
    public Stream<Movie> moviesBefore(int year){
        return movieList.stream()
                .distinct()
                .filter(movie -> movie.getReleaseYear()<= year)
                .sorted(Comparator
                        .comparingInt(Movie::getReleaseYear)
                        .thenComparing(Movie::getTitle));
        
    }
    
    public Stream<Movie> moviesBetween(int yearA, int yearB) {
        return movieList.stream()
                .distinct()
                .filter(movie -> movie.getReleaseYear()>= yearA)
                .filter(movie -> movie.getReleaseYear()<= yearB)
                .sorted(Comparator
                        .comparingInt(Movie::getReleaseYear)
                        .thenComparing(Movie::getTitle));
    }
    
    public Stream<Movie> moviesByDirector(String director) {
        return movieList.stream()
                .distinct()
                .filter(movie -> movie.getDirector().equalsIgnoreCase(director))
                .sorted(Comparator
                        .comparingInt(Movie::getReleaseYear)
                        .thenComparing(Movie::getTitle));
                
    }
    
    
}
