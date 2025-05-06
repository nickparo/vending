package Macchinetta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Machine {
    private List<Product> products;
    private Admin admin;
    Scanner sc;

    public Machine(){
        initProducts(); 
        sc = new Scanner(System.in);
    }

    //utente arriva alla macchinetta
    private boolean isAdmin(){ //è admin? si no
        System.out.println("Sei admin?");
        String risposta = sc.next();
        if(risposta.equalsIgnoreCase("si")){
            return true;
        } else {
            return false;
        }
    }

    private void userFlow(){ //Gestione utente
        List<Product> cart = new ArrayList<>();
        double total = 0.00;//inizializzo a 0 importo
        boolean continueShopping = true;

        while (continueShopping) {
            System.out.println("\nProdotti disponibili:\n");
            for (Product p : products){
                System.out.println(p);
            } 
            System.out.println("Inserisci il numero del prodotto che vuoi acquistare");
            int sceltaId = sc.nextInt();
            sc.nextLine(); 
    
            Product selectedProduct = findProductById(sceltaId);
    
            if(selectedProduct != null && selectedProduct.getQuantity()>0){
                cart.add(selectedProduct);
                total += selectedProduct.getPrice(); //totale prezzo
                selectedProduct.setQuantity(selectedProduct.getQuantity()-1);//tolgo dalla quantità totale del prodotto selezionato un pezzo
            } else {
                System.out.println("Prodotto non valido o non disponibile");
            }
    
            System.out.println("Vuoi continuare ad acquistare? ");
            String risp = sc.nextLine();
            if(risp.equalsIgnoreCase("si")){
                continueShopping = true;
            } else {
                continueShopping = false;
                break;
            }
        }
        System.out.println("Totale da pagare: " + total);
    }//se no, (utente) (metodo isUser) si apre e visualizza tutti i prodotti della macchinetta divisi per numero (cercare libreria)
    //l'utente sceglie un prodotto- (implementare lista prodotti e menù visualizzazione)
    //ne vuole un altro? 
    //se si continua la selezione se no il sistema visualizza il totale dovuto
    //l'utente decide un metodo di pagamento (monete, banconote o carta(simulando un pagamento senza essere implementato...))
    //se monete inserisce e si aspetta un eventuale resto
    //se banconote non più di 5€
    //resto viene gestito dal numero di monete dentro la macchinetta
    //se carta paga 
    //grazie e riparto dal primo punto, admin?

    
    private Product findProductById(int id) { //metodo trova prodotto
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    
    private void adminFlow(){ //se admin
        System.out.println("Inserisci la password admin: ");
        Scanner sc = new Scanner(System.in); //immettere password
        String password = sc.nextLine();
        if(password.equals("mario11")){
            System.out.println("ciao admin");
        } else {
            System.out.println("password errata");
        }
    }
    //visualizzazione prodotti e quanti ce ne sono
    //aggiungi prodotto?
    //se si selezione tramite numero e aggiungo quanti aggiungere
    //se no, Aggiungi monete?
    //visualizzazione monete dentro la macchinetta
    //vuoi aggiungere monete?
    //se si quali e quanto
    //se no, vuoi ritirare soldi o monete?
    //finito? se no riprendi
    //se si, esce da admin e riparte dall'inizio

    private void initProducts(){ //gestisco prodotti
        products = new ArrayList<>();
        products.add(new Product(1, "Caffè", 1.00, 20));
        products.add(new Product(2, "The", 1.00, 10));
        products.add(new Product(3, "Latte", 1.00, 5));
        products.add(new Product(4, "Acqua", 0.70, 20));
        products.add(new Product(5, "Patatine", 1.50, 10));
        products.add(new Product(6, "Cracker", 1.50, 10));
        products.add(new Product(7, "Croccantelle", 1.80, 10));
        products.add(new Product(8, "Kinder", 2.40, 10));
        products.add(new Product(9, "Cioccolato", 2.75, 10));
        products.add(new Product(10, "Merendina", 3.00, 10));
        products.add(new Product(11, "Succo ", 2.20, 10));
        products.add(new Product(12, "CC", 0.50, 1));

    }

    public void start(){
        if (isAdmin()){
            adminFlow();
        } else {
            userFlow();
        }
    }
    
    public static void main(String[] args) {
        Machine machine =new Machine();
        machine.start();
        
    }
}
