package Macchinetta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Cassa {
    private Map<Double, Integer> coins; //mappa monete (valore, quantità)

    public Cassa(){
        coins = new TreeMap<>(Collections.reverseOrder()); //Mappa delle monete (ordinate per dare resto da + grande a -)
        coins.put(0.05, 10);
        coins.put(0.10, 5);
        coins.put(0.20, 5);
        coins.put(0.50, 5);
        coins.put(1.00, 5);
        coins.put(2.00, 5);
    }

    public void addCoin(double value, int quantity){
       coins.put(value, coins.getOrDefault(value, 0) + quantity);
       //inserisce e aggiorna il valore della moneta, verifica se la moneta è gia presente nlla Map, se esiste ne restituisce la quantità
       //se non esiste restituisce 0 se no aggiunge la quantità inserita.
    } 

    public void removeCoin(double value, int quantity){
        if(coins.containsKey(value) && coins.get(value) >= quantity){ //verifica se ci sono abbastanza monete
            coins.put(value, coins.get(value) - quantity); //rimuove la quantità inserita da quelle presenti
        } else {
            System.out.println("Non ci sono monete da " + value + " da rimuovere");
        }
    }

    /*public void printCassa(double value, int quantity){ //stampa l'inventario delle monete a quel punto
        for (Map.Entry<Double, Integer> entry : coins.entrySet()) { //per ogni chiave nella collezione stampa
            System.out.println("Moneta: " + entry.getKey() + " EUR, Quantità: " + entry.getValue());  
        }
    } */
    public void printCassa() {
        System.out.println("Inventario monete:");
        for (Map.Entry<Double, Integer> entry : coins.entrySet()) {
            double valore = entry.getKey();
            int quantita = entry.getValue();
            System.out.println("Moneta: " + valore + " EUR - Quantità: " + quantita);
        }
    }

    public double calcuteTotal() {
        double total = 0;
        for (Map.Entry<Double, Integer> entry : coins.entrySet()) {
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }
}