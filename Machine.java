package Macchinetta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Machine {
    private List<Product> products;
    
    private Cassa cassa;
    Scanner sc;

    public Machine(){
        initProducts();
        sc = new Scanner(System.in);
        cassa = new Cassa();
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

    private void userFlow(){ //Gestione utente si apre e visualizza tutti i prodotti della macchinetta divisi per numero 
        List<Product> cart = new ArrayList<>();
        double total = 0.00;//inizializzo a 0 importo
        boolean continueShopping = true;

        while (continueShopping) {
            System.out.println("\nProdotti disponibili:\n");
            printProducts();
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
    }
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
        System.out.println("\nInserisci la password admin: ");
        Scanner sc = new Scanner(System.in); //immettere password
        String password = sc.nextLine();
        if(password.equals("mario11")){
            
        } else {
            System.out.println("password errata");
        }

        boolean adminLoop = true;
        while(adminLoop){
            //TODO stampare cassa
            System.out.println("\n-----------MENU ADMIN-----------");
            System.out.println("\nGestione Monete o Gestione Prodotti? (monete/prodotti/esci)");

            String sceltaGest = sc.nextLine();
            if(sceltaGest.equalsIgnoreCase("monete")){
                cashManagement();
            } else if (sceltaGest.equalsIgnoreCase("prodotti")){
                productManagement();
            } else if (sceltaGest.equalsIgnoreCase("esci")) {
                System.out.println("Uscito dalla modalità admin");
                adminLoop = false; //TODO ritorno a menu principale
            } else {
                System.out.println("Errore, inserisci una voce del menu valida"); 
            }
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

    private void cashManagement() {
        boolean cashLoop = true;
        while (cashLoop) {
            System.out.println("\n-----------MENU ADMIN-----------");
            System.out.println("\nGestione Monete  -  scegli un opzione");
            System.out.println("1 Aggiungi Monete");
            System.out.println("2 Rimuovi Monete");
            System.out.println("3 Esci");
            System.out.println(" ");
        

            int scelta = sc.nextInt();
            switch (scelta) {
                case 1:
                    //TODO print cassa
                    System.out.println("\nQuale moneta vuoi aggiungere?");
                    double coinAdd = sc.nextDouble();
                    System.out.println("Quante ne vuoi aggiungere?");
                    int quantityAdd = sc.nextInt();
                    cassa.addCoin(coinAdd, quantityAdd);
                    System.out.println("\n"+quantityAdd + " monete da " + coinAdd + "Euro aggiunte.");
                    break;

                case 2:
                    System.out.println("\nQuale moneta vuoi rimuovere?");
                    double coinRem = sc.nextDouble();
                    System.out.println("Quante ne vuoi rimuovere?");
                    int quantityRem = sc.nextInt();
                    cassa.removeCoin(coinRem, quantityRem);
                    System.out.println("\n"+quantityRem + " monete da " + coinRem + "€ rimosse.");
                    break;

                case 3:
                    System.err.println("\nUscita dalla modalità Gestione Moneta");
                    cashLoop = false;
                    break;
                default:
                    System.out.println("\nOpzione non valida.");
                    break;

            }
        }
        
    }

    private void productManagement() {
        boolean productLoop = true;
        while (productLoop) {
            System.out.println("\n-----------MENU ADMIN-----------");
            System.out.println("\nGestione Prodotti  -  scegli un opzione");
            System.out.println("1 Ricarica Prodotto");
            System.out.println("2 Rimuovi Prodotto");
            System.out.println("3 Aggiungi Nuovo Prodotto");
            System.out.println("4 Esci");

            int sceltaProd = sc.nextInt();
            switch (sceltaProd) {
                case 1:
                    printProducts();
                    System.out.println("\nInserisci Id prodotto da ricaricare");
                    int idprod = sc.nextInt();
                    System.out.println("Inserisci quantità da aggiungere");
                    int qntProd = sc.nextInt();
                    Product prod = findProductById(idprod);
                    prod.setQuantity(prod.getQuantity() + qntProd );
                    System.out.println(prod.getName() +" è stato ricaricato di "+qntProd+ " pezzi");
                    break;
                case 2 :
                    printProducts();
                    System.out.println("\nInserisci Id prodotto da rimuovere");
                    int idprodotto = sc.nextInt();
                    Product p = findProductById(idprodotto);
                    products.remove(p);
                    System.out.println("hai rimosso il prodotto "+ p.getName());
                    break;
                case 3:
                    printProducts();
                    System.out.println("\nNome del nuovo prodotto");
                    String nomProd = sc.next();
                    System.out.println("Inserisci Prezzo");
                    double prezzProd = sc.nextDouble();
                    System.out.println("Inserisci Quantità");
                    int qntitProd = sc.nextInt();
                    int newId = products.size() +1;
                    products.add(new Product(newId, nomProd, prezzProd, qntitProd));
                    System.out.println("\nHai aggiunto "+ qntitProd+ " pezzi di "+ nomProd+ " al prezzo di " + prezzProd);
                    break;
                case 4: 
                    productLoop = false;
                    System.out.println("\nUscito dal menù Gestione Prodotto");
                default:
                    System.out.println("\nOpzione non valida");
                    break;
            }
        }
    }

    private void printProducts() { //stampo prodotti
        System.out.println("\n--- Lista Prodotti ---");
        for (Product p : products) {
            System.out.println(p.getId() + " - " + p.getName() + " | Prezzo: " + p.getPrice() + "€ | Quantità: " + p.getQuantity());
        }
    }

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
