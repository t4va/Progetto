package server;

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

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean d) {
        disponibile = d;
    }

    @Override
    public abstract String toString();
}
