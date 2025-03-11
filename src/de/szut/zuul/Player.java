package de.szut.zuul;

import java.util.LinkedList;

public class Player {
    private Room currentRoom;
    private double loadCapacity;
    private LinkedList<Item> items;

    public Player(){
        this.loadCapacity=10.0;
        this.items = new LinkedList<Item>();
    }

    public void goTo(Room newRoom){
        this.currentRoom = newRoom;
    }

    public Room getCurrentRoom(){
        return this.currentRoom;
    }

    public boolean takeItem(Item item){
        if(isTakePossible(item)){
            this.items.add(item);
            return true;
        }
        return false;
    }

    private boolean isTakePossible(Item item){
        return calculateWeight() + item.getWeight() <= this.loadCapacity;
    }

    private double calculateWeight() {
        double totalWeight = 0;
        for(Item item: this.items)
            totalWeight = totalWeight + item.getWeight();
        return totalWeight;
    }

    public Item dropItem(String name){
        for(Item item: this.items){
            if(name.equals(item.getName())){
                this.items.remove(item);
                return item;
            }
        }
        return null;
    }

    public String showStatus(){
        StringBuilder result = new StringBuilder(">status of the player\nloadcapacity:"+ this.loadCapacity+"\ntaken items: ");
        if(this.items.isEmpty())
        {
            return result +"none";
        }
        else
        {
            for(Item item: this.items)
                result.append(item.toString()+"; ");
            result.append("\nabsorbed weight: "+calculateWeight());
            return result.toString();
        }
    }

    public void increaseLoadingCapacity(double value){
        this.loadCapacity += value;
    }
}
