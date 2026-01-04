public class Tv extends Prodotto {
    private String marca;
    private String modello;

    public Tv(int id, String titolo, double prezzo, int fornitoreId, String marca, String modello) {
        super(id, titolo, prezzo, fornitoreId);
        this.marca = marca;
        this.modello = modello;
    }

    @Override
    public String toString() {
        return super.toString() + " [TV] Marca: " + marca + ", Modello: " + modello;
    }
}
