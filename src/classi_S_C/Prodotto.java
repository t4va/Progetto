package classi_S_C;

public abstract class Prodotto {
    protected int id;
    protected boolean disponibile;
    protected String dataAcquisto;
    protected double prezzo;
    protected String titolo;

    public Prodotto(int id, boolean disponibile,
                    String dataAcquisto, double prezzo,
                    String titolo) {

        this.id = id;
        this.disponibile = disponibile;
        this.dataAcquisto = dataAcquisto;
        this.prezzo = prezzo;
        this.titolo = titolo;
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean d) {
        disponibile = d;
    }

    public synchronized void ordina(String cliente) {
        System.out.println("Cliente " + cliente + " sta tentando di ordinare il prodotto " + titolo);
        while (!disponibile) {
            System.out.println("Prodotto " + titolo + " NON disponibile. Cliente " + cliente + " in attesa...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        consegna(cliente);
    }

    public synchronized void fornisci() {
        System.out.println("Fornitore sta rifornendo il prodotto: " + titolo);
        disponibile = true;
        notifyAll();
    }

    public synchronized void consegna(String cliente) {
        disponibile = false;
        System.out.println("Prodotto " + titolo + " CONSEGNATO al cliente " + cliente);
    }

    @Override
    public abstract String toString();
}
