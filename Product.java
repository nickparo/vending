package Macchinetta;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private int quantity;

    public Product(int id, String name, double price, int quantity){
        this.id=id;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
   public double getPrice() {
       return price;
   }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return id+ "-" + name+ "- " +  price + " $" + ", disponibilità: " + quantity;
    }
}
