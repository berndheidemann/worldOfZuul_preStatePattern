package de.szut.zuul;

import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        this.exits = new HashMap<String, Room>();
        this.items = new HashMap<String, Item>();
    }

    public void setExit(String direction, Room neighbour)
    {
        this.exits.put(direction, neighbour);
    }

    public Room getExit(String direction){
        return this.exits.get(direction);
    }

    public String exitsToString(){
        StringBuilder  result = new StringBuilder("");
        for(String key : this.exits.keySet())
            result.append(key).append(" ");
        return result.toString();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public String getLongDescription(){
        return "You are " + this.description + ".\nExits: " + exitsToString()+
                "\nItems in this room: \n"+itemsToString();
    }

    public void putItem(Item newItem){
        this.items.put(newItem.getName(), newItem);
    }

    public String itemsToString()
    {
        StringBuilder result = new StringBuilder();
        if(this.items.isEmpty())
        {
            return "none";
        }
        else
        {
            for(String key: this.items.keySet())
                result.append("- "+ this.items.get(key).toString() + "\n");
            return result.toString();
        }
    }

    public Item removeItem(String name){
        if(itemAvailable(name)){
            return this.items.remove(name);
        }
        return null;
    }

    private boolean itemAvailable(String name){
        return this.items.containsKey(name);
    }
}
