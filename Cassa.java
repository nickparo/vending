package Macchinetta;

import java.util.HashMap;
import java.util.Map;

public class Cassa {
    private Map<Double, Integer> coins; // Mappa delle monete (valore, quantità)

    public Cassa(){
        coins = new HashMap<>();

        //Aggiungo monete
        coins.put(0.05, 10);
        coins.put(0.10, 5);
        coins.put(0.20, 5);
        coins.put(0.50, 5);
        coins.put(1.00, 5);
        coins.put(2.00, 5);
    }

    public void addCoin(double value, int numMon){
        if(coins.containsKey(value)) {
            
        }
    } 
}