package de.szut.zuul;

public class Item {
    private String name;
    private String description;
    private double weight;

    public Item(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return name + ": " + description + ", "+ weight + "kg";
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
}
