package server;

import classi_S_C.Prodotto;

public class Film extends Prodotto {
    private String produttore;
    private String genere;
    private int anno;

    public Film(int id, boolean disp, String dataAcq,
                double prezzo, String titolo,
                String produttore, String genere, int anno) {

        super(id, disp, dataAcq, prezzo, titolo);
        this.produttore = produttore;
        this.genere = genere;
        this.anno = anno;
    }

    @Override
    public String toString() {
        return id + "," + disponibile + "," + dataAcquisto + "," +
                prezzo + ",Film," + titolo + "," +
                produttore + "," + genere + "," + anno +
                ",N/A,N/A,N/A,N/A";
    }
}
