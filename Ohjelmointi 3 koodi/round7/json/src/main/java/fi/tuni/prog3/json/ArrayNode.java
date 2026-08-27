/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.json;

import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author Sakari
 */
public class ArrayNode extends Node implements Iterable<Node> {

    ArrayList<Node> NodeList = new ArrayList<>();

    
    public void add(Node node){
        
        NodeList.add(node);
    }
    
    public int size(){
        return NodeList.size();
    }

    @Override
    public Iterator<Node> iterator() {
        return NodeList.iterator();
        
    }

    
    
    
}
