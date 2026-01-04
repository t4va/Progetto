public class Film extends Prodotto {
    private String regista;
    private String genere;
    private int anno;
    private String produttore;

    public Film(int id, String titolo, double prezzo, int fornitoreId, 
                String regista, String genere, int anno, String produttore) {
        super(id, titolo, prezzo, fornitoreId);
        this.regista = regista;
        this.genere = genere;
        this.anno = anno;
        this.produttore = produttore;
    }

    @Override
    public String toString() {
        return super.toString() + " [FILM] Regia: " + regista + ", Genere: " + genere;
    }
}
