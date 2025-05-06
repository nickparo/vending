package Macchinetta;

public class Coin { //gestione monete (?)
    private double value;
    private int quantity;

    public Coin(double value, int quantity){
        this.value = value;
        this.quantity = quantity;
    }
    
    public double getValue() {
        return value;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void removeQuantity(int quantity){
        if(this.quantity >= quantity){
            this.quantity = this.quantity - quantity;
        } else {
            System.out.println("Non ci sono abbastanza monete");
        }
    }

    @Override
    public String toString() {
        return String.format("Moneta: %.2f, Quantità: %d", value, quantity);
    }

}
