/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.jsoncountries;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Sakari
 */
public class CountryData {
    
    public static List<Country> readFromJsons(String areaFile, String populationFile, String gdpFile) throws FileNotFoundException{
        List<Country> countries = new ArrayList<>();
        Gson gson = new Gson();
        FileReader areaReader = new FileReader(areaFile);
        FileReader populationReader = new FileReader(populationFile);
        FileReader gdpReader = new FileReader(gdpFile);
        
        JsonObject areaJson = gson.fromJson(areaReader, JsonObject.class);
        JsonObject populationJson = gson.fromJson(populationReader, JsonObject.class);
        JsonObject gdpJson = gson.fromJson(gdpReader, JsonObject.class);
        
        JsonArray areaRecords = areaJson.getAsJsonObject("Root").getAsJsonObject("data").getAsJsonArray("record");
        JsonArray populationRecords = populationJson.getAsJsonObject("Root").getAsJsonObject("data").getAsJsonArray("record");
        JsonArray gdpRecords = gdpJson.getAsJsonObject("Root").getAsJsonObject("data").getAsJsonArray("record");
        
         for (int i = 0; i < areaRecords.size(); i++) {
             
            JsonObject areaRecord = areaRecords.get(i).getAsJsonObject();
            JsonObject populationRecord = populationRecords.get(i).getAsJsonObject();
            JsonObject gdpRecord = gdpRecords.get(i).getAsJsonObject();
            
            String name = areaRecord.getAsJsonArray("field")
                    .get(0)
                    .getAsJsonObject()
                    .get("value")
                    .getAsString();
            
            
            double area = Double.parseDouble(areaRecord.getAsJsonArray("field")
                    .get(2)
                    .getAsJsonObject()
                    .get("value")
                    .getAsString());
            
           
            long population = Long.parseLong(populationRecord.getAsJsonArray("field")
                    .get(2)
                    .getAsJsonObject()
                    .get("value")
                    .getAsString());
            
            
            double gdp = Double.parseDouble(gdpRecord.getAsJsonArray("field")
                    .get(2)
                    .getAsJsonObject()
                    .get("value")
                    .getAsString());
            
            Country country = new Country(name, area, population, gdp);
            countries.add(country);
            
            
            
         }
         return countries;
    }
    
    public static void writeToJson(List<Country> countries, String countryFile) throws IOException{
        
        
        for (int i = 0; i <countries.size();++i){
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, String> stringMap = new LinkedHashMap<>();
            Map<String, Double> areaMap = new LinkedHashMap<>();
            Map<String, Double> gdpMap = new LinkedHashMap<>();
            Map<String, Long> longMap = new LinkedHashMap<>();
            
            stringMap.put("name", countries.get(i).getName());
            areaMap.put("area", countries.get(i).getArea());
            longMap.put("population", countries.get(i).getPopulation());
            gdpMap.put("gdp", countries.get(i).getGdp());
            
            gson.toJson(stringMap);
            gson.toJson(areaMap);
            gson.toJson(longMap);
            gson.toJson(gdpMap);
            
            FileWriter file = new FileWriter(countryFile);
            file.write(gson.toString());
            file.close();

        }
        
    }
    
}
