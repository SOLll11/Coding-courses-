/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.json;

import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;


/**
 *
 * @author Sakari
 */
public class ObjectNode extends Node implements Iterable<String> {
    
    private Map<String, Node> NodeMap = new TreeMap<>();

    
    
    @Override
     public Iterator<String> iterator() {
        return NodeMap.keySet().iterator();
    }
    
    public Node get(String key){
             
        return NodeMap.get(key);
    }
    
    public void set(String key, Node node){
        NodeMap.put(key, node);
    }
    
    public int size(){
        return NodeMap.size();
    }
    
    
    
    
}
