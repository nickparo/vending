package Macchinetta;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Cassa implements Serializable {
    private Map<Double, Integer> coins; //mappa monete (valore, quantità)

    public Cassa(){
        coins = new TreeMap<>(Collections.reverseOrder()); //Mappa delle monete (ordinate per dare resto da + grande a -)
        coins.put(0.05, 10);
        coins.put(0.10, 10);
        coins.put(0.20, 10);
        coins.put(0.50, 10);
        coins.put(1.00, 10);
        coins.put(2.00, 10);
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


    public void printCassa() {
        System.out.printf("%-10s %-10s%n", "\nMoneta", "Quantità");
        System.out.println("-------------------------");
        for (Map.Entry<Double, Integer> entry : coins.entrySet()) {
            System.out.printf("%-10.2f %-10d%n", entry.getKey(), entry.getValue());
        }
    }

    public boolean giveChange(double change) {
        change = Math.round(change * 100.0) / 100.0;
    
        // Copia ordinata della cassa (dal taglio più alto al più basso)
        Map<Double, Integer> cassaTemporanea = new TreeMap<>(Collections.reverseOrder());
        cassaTemporanea.putAll(coins); // copia della cassa
    
        // Monete da restituire
        Map<Double, Integer> restoDaDare = new LinkedHashMap<>();
    
        for (Map.Entry<Double, Integer> entry : cassaTemporanea.entrySet()) {
            double taglio = entry.getKey();
            int disponibili = entry.getValue();
            int daUsare = 0;
    
            // Finché il resto è sufficiente e ci sono monete disponibili
            while (change >= taglio && disponibili > 0) {
                change -= taglio;
                change = Math.round(change * 100.0) / 100.0;
                disponibili--;
                daUsare++;
            }
    
            if (daUsare > 0) {
                restoDaDare.put(taglio, daUsare);
            }
    
            if (change == 0) {
                break;
            }
        }
    
        // Se il resto è stato erogato correttamente
        if (Math.abs(change) < 0.01) {
            // Solo ora aggiorniamo la vera cassa
            for (Map.Entry<Double, Integer> entry : restoDaDare.entrySet()) {
                removeCoin(entry.getKey(), entry.getValue());
            }
    
            System.out.println("Resto dato:");
            for (Map.Entry<Double, Integer> entry : restoDaDare.entrySet()) {
                System.out.println(entry.getValue() + " moneta/e da " + entry.getKey() + " EUR");
            }
            return true;
        } else {
            System.out.println("Impossibile dare il resto corretto con le monete disponibili.");
            return false;
        }
    }
}    