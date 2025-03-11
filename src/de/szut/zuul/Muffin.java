package de.szut.zuul;

public class Muffin extends Item{
    private double increaseLoadingCapacity;

    public Muffin(String name, String description, double weight, double capacity)
    {
        super(name, description, weight);
        this.increaseLoadingCapacity = capacity;
    }

    public double getLoadingCapacity() {
        return increaseLoadingCapacity;
    }

    public String toString()
    {
        return (super.toString()+ ", increases the loading capacity about "+ increaseLoadingCapacity +" kg");
    }


}
