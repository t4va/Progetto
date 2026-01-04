import java.util.ArrayList;
import java.util.List;

public class Fornitore{
    private int id;
    private String piva;
    private String nome;
    private String email;
    
    private List<Prodotto> prodottiDaFornire = new ArrayList<>();

    public Fornitore(int id, String piva, String nome, String email) {
        this.id = id;
        this.piva = piva;
        this.nome = nome;
        this.email = email;
    }
    
    public int getIdFornitore() { return id; }
    public String getNome() { return nome; }

    public void aggiungiProdotto(Prodotto p) {
        prodottiDaFornire.add(p);
    }

    public void run() {
        for (Prodotto p : prodottiDaFornire) {
            if (!p.isDisponibile()) {
                System.out.println("FORNITORE " + nome + ": Rilevato ordine/mancanza per " + p.getTitolo());
                p.fornisci(this);
            }
        }
    }
}
