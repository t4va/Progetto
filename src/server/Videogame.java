package server;

public class Videogame extends Prodotto {
    private String piattaforma;
    private String azienda;

    public Videogame(int id, boolean disp, String dataAcq,
                     double prezzo, String titolo,
                     String piattaforma, String azienda) {

        super(id, disp, dataAcq, prezzo, titolo);
        this.piattaforma = piattaforma;
        this.azienda = azienda;
    }

    @Override
    public String toString() {
        return id + "," + disponibile + "," + dataAcquisto + "," +
                prezzo + ",Videogame," + titolo +
                ",N/A,N/A,N/A," +
                piattaforma + "," + azienda + ",N/A,N/A";
    }
}
