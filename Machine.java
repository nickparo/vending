package Macchinetta;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        loadState();
    }

    //carico i dati della macchinetta
    @SuppressWarnings("unchecked")
    private void loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("state.ser"))) {
            products = (List<Product>) ois.readObject();
            cassa = (Cassa) ois.readObject();
            System.out.println("Stato caricato correttamente.");
        } catch (Exception e) {
            System.out.println("Nessun stato precedente trovato. Uso configurazione iniziale.");
            initProducts();
            cassa = new Cassa();
        }
    }

    //salvo i dati della macchinetta
    private void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("state.ser"))) {
            oos.writeObject(products);
            oos.writeObject(cassa);
            System.out.println("Stato salvato con successo.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio dello stato: " + e.getMessage());
        }
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

    private void userFlow() throws InterruptedException{ //Gestione utente si apre e visualizza tutti i prodotti della macchinetta divisi per numero 
        List<Product> cart = new ArrayList<>();
        double total = 0.00;//inizializzo a 0 importo
        boolean continueShopping = true;

        while (continueShopping) {
            System.out.println("\nProdotti disponibili:\n");
            printProducts();
            System.out.println("\nInserisci il numero del prodotto che vuoi acquistare");
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
    
            System.out.println("\nVuoi continuare ad acquistare? ");
            String risp = sc.nextLine();
            if(risp.equalsIgnoreCase("si")){
                continueShopping = true;
            } else {
                continueShopping = false;
                break;
            }
        }
        System.out.println("\nTotale da pagare: " + total+ " EUR");
        //payment(total);
        if (payment(total)) {
            System.out.println("Pagamento completato. Grazie per l'acquisto!");
        } else {
            System.out.println("Transazione annullata.");
        }
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

    private void cashManagement() {
        boolean cashLoop = true;
        while (cashLoop) {
            System.out.println("\n-----------MENU ADMIN-----------");
            System.out.println("\nGestione Monete  -  scegli un opzione");
            System.out.println("1 Aggiungi Monete");
            System.out.println("2 Ritira Monete");
            System.out.println("3 Esci");
            System.out.println(" ");
        

            int scelta = sc.nextInt();
            switch (scelta) {
                case 1:
                    cassa.printCassa();
                    System.out.println("\nQuale moneta vuoi aggiungere?");
                    double coinAdd = sc.nextDouble();
                    System.out.println("Quante ne vuoi aggiungere?");
                    int quantityAdd = sc.nextInt();
                    cassa.addCoin(coinAdd, quantityAdd);
                    System.out.println("\n"+quantityAdd + " monete da " + coinAdd + "EUR aggiunte.");
                    break;

                case 2:
                    cassa.printCassa();
                    System.out.println("\nQuale moneta vuoi ritirare?");
                    double coinRem = sc.nextDouble();
                    System.out.println("Quante ne vuoi ritirare?");
                    int quantityRem = sc.nextInt();
                    cassa.removeCoin(coinRem, quantityRem);
                    System.out.println("\n"+quantityRem + " monete da " + coinRem + "EUR ritirate.");
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
                    System.out.println("\nHai aggiunto "+ qntitProd+ " pezzi di "+ nomProd+ " al prezzo di " + prezzProd + " EUR");
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
        System.out.println("\n----------------- Lista Prodotti -----------------");
        System.out.printf("%-4s %-20s %-10s %-10s%n", "\nID", "Nome", "Prezzo", "Quantità");
        System.out.println("--------------------------------------------------");
        for (Product p : products) {
            System.out.printf("%-4d %-20s %-10.2f %-10d%n", 
            p.getId(), p.getName(), p.getPrice(), p.getQuantity());
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
        products.add(new Product(12, "Frutta Secca", 0.50, 1));

    }

    public boolean payment(double total) throws InterruptedException{
        double insertedAmount = 0.0;
        System.out.println("\n------Scegli un metodo di Pagamento  - scegli un opzione------");
        System.out.println("1 Monete");
        System.out.println("2 Banconote (Max 5 EUR)");
        System.out.println("3 Carta");

        int metodo = sc.nextInt();
        switch (metodo) {
            case 1:
                while (insertedAmount < total) {
                    System.out.println("\nInserisci le monete");
                    double moneta = sc.nextDouble();
                    if(moneta == 0.05 || moneta ==0.1 || moneta == 0.2 || moneta == 0.5 || moneta == 1 || moneta ==2){
                        cassa.addCoin(moneta, 1);
                        insertedAmount += moneta;
                        insertedAmount = Math.round(insertedAmount * 100.0) / 100.0;
                        System.out.println("Hai inserito " + insertedAmount + " EUR su " + total + " EUR" );
                    } else {
                        System.out.println("Moneta non accettata");
                    }
                }
                break;
            case 2:
                while (insertedAmount < total) {
                    System.out.println("Inserisci Banconota   (max 5 EUR)");
                    int banconota = sc.nextInt();
                    if (banconota == 5){
                        insertedAmount += banconota;
                        System.out.println("Hai inserito una banconota da "+banconota);
                    } else {
                        System.out.println("Banconota inserita non valida");
                    }
                }
                break;
            case 3:
                System.out.println("\nAppoggia la carta per il pagamento");
                Thread.sleep(1000);
                System.out.println("Pagamento di "+total+" EUR in corso, attendere prego");
                Thread.sleep(1000);
                System.out.println("\n---------------------");
                Thread.sleep(1000);
                System.out.println("\n---------------------");
                Thread.sleep(2000);
                insertedAmount = total;
                break;
            default:
                System.out.println("\nOpzione non valida");
                return false;
        }
        //calcolo resto
        double change = insertedAmount - total;
        change = Math.round(change * 100)/ 100.0;

        if(change > 0.0){
            System.out.println("Resto da erogare: "+ change +" EUR");
        }   
            if (!cassa.giveChange(change)){
                System.out.println("Resto non disponibile");
                return false;
            }
        return true;
    }

    public void start() throws InterruptedException{
        if (isAdmin()){
            adminFlow();
        } else {
            userFlow();
        }
        saveState();
    }
    
    public static void main(String[] args) throws InterruptedException {
        Machine machine =new Machine();
        machine.start();
        
    }
}
