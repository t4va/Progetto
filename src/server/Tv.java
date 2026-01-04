package server;

public class Tv extends Prodotto {
    private String marca;
    private String modello;

    public Tv(int id, boolean disp, String dataAcq,
              double prezzo, String titolo,
              String marca, String modello) {

        super(id, disp, dataAcq, prezzo, titolo);
        this.marca = marca;
        this.modello = modello;
    }

    @Override
    public String toString() {
        return id + "," + disponibile + "," + dataAcquisto + "," +
                prezzo + ",Tv," + titolo +
                ",N/A,N/A,N/A,N/A,N/A," +
                marca + "," + modello;
    }
}
