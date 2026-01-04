public class Videogame extends Prodotto {
    private String piattaforma;
    private String genere;
    private int anno;
    private String produttore;

    public Videogame(int id, String titolo, double prezzo, int fornitoreId,
                     String piattaforma, String genere, int anno, String produttore) {
        super(id, titolo, prezzo, fornitoreId);
        this.piattaforma = piattaforma;
        this.genere = genere;
        this.anno = anno;
        this.produttore = produttore;
    }

    @Override
    public String toString() {
        return super.toString() + " [GAME] Piattaforma: " + piattaforma;
    }
}
