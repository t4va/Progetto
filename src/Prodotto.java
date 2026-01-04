public abstract class Prodotto {
    private int id;
    private String titolo;
    private double prezzo;
    private boolean disponibile;
    private String dataAcquisto;
    private int fornitoreId; // Link to supplier

    public Prodotto(int id, String titolo, double prezzo, int fornitoreId) {
        this.id = id;
        this.titolo = titolo;
        this.prezzo = prezzo;
        this.fornitoreId = fornitoreId;
        this.disponibile = false; // Initially not available until supplied? Or depends on simulation.
    }

    public int getId() { return id; }
    public String getTitolo() { return titolo; }
    public double getPrezzo() { return prezzo; }
    public boolean isDisponibile() { return disponibile; }
    public void setDisponibile(boolean disponibile) { this.disponibile = disponibile; }
    public int getFornitoreId() { return fornitoreId; }
    public void setDataAcquisto(String data) { this.dataAcquisto = data; }

    // 2a. Ordinare un prodotto da parte di un cliente
    public synchronized void ordina(Cliente cliente) {
        System.out.println("CLIENTE " + cliente.getNome() + ": Tenta di ordinare " + titolo);
        while (!disponibile) {
            try {
                System.out.println("CLIENTE " + cliente.getNome() + ": Prodotto " + titolo + " non disponibile. Attendo...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("CLIENTE " + cliente.getNome() + ": Interrotto durante l'attesa.");
                return;
            }
        }
        System.out.println("CLIENTE " + cliente.getNome() + ": Ha ordinato " + titolo);
        // Logic to mark as reserved or similar could go here, but requirements are simple flow
    }

    // 2b. Fornire un prodotto da parte del fornitore nel magazzino
    public synchronized void fornisci(Fornitore fornitore) {
        System.out.println("FORNITORE " + fornitore.getNome() + ": Sta fornendo " + titolo + " al magazzino.");
        this.disponibile = true;
        System.out.println("MAGAZZINO: " + titolo + " è ora disponibile.");
        notifyAll(); // Notify waiting customers
    }

    // 2c. Consegnare un prodotto al cliente presente in magazzino
    // This implies the warehouse actually delivering it. 
    // Usually triggered after 'ordina' succeeds, or part of it?
    // Requirement says "Consegnare un prodotto al cliente presente in magazzino".
    // If 'ordina' waits for availability, then 'consegna' happens after?
    // Or 'ordina' just places order, and 'consegna' is the actual pickup?
    // Let's assume 'consegna' is the step where the product leaves the warehouse to the client.
    public synchronized void consegna(Cliente cliente) {
        if (disponibile) {
            System.out.println("MAGAZZINO: Consegna di " + titolo + " a " + cliente.getNome() + " completata.");
            this.disponibile = false; // Consumed? Or just delivered one Unit? Assuming single unit logic for simulation.
            this.dataAcquisto = java.time.LocalDate.now().toString();
        } else {
            System.out.println("MAGAZZINO: Impossibile consegnare " + titolo + ", non disponibile.");
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Titolo: " + titolo + ", Prezzo: " + prezzo;
    }
}
