/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.jsoncountries;

/**
 *
 * @author Sakari
 */
public class Country implements  Comparable<Country> {
    
    private String Name;
    private double Area;
    private long Population;
    private double Gdp;

    public Country(String Name, double Area, long Population, double Gdp) {
        this.Name = Name;
        this.Area = Area;
        this.Population = Population;
        this.Gdp = Gdp;
    }

    public String getName() {
        return Name;
    }

    public double getArea() {
        return Area;
    }

    public long getPopulation() {
        return Population;
    }

    public double getGdp() {
        return Gdp;
    }

    
    

    @Override
    public int compareTo(Country o) {
        return this.Name.compareTo(o.getName());
    }
    
    
    @Override
    public String toString(){
        String newString = String.format("%s\n  Area: %.1f km2\n  Population: %d\n  GDP: %.1f (2015 USD)\n", Name, Area, Population, Gdp);
        return newString;
        
    }
    
    
}
