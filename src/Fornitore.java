import java.util.ArrayList;
import java.util.List;

public class Fornitore extends Thread {
    private int id; // Matches fornitoreId in Prodotto? Or PIVA is primary?
    // Prompt says "Ogni prodotto è legato con il fornitore tramite l’attributo id"
    // And "Nei prodotti sono presenti tutti i prodotti... perciò vi saranno valori N/A".
    // Let's assume ID is the link.
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

    @Override
    public void run() {
        for (Prodotto p : prodottiDaFornire) {
            try {
                // Determine if we need to supply based on availability?
                // Or just supply everything associated?
                // Simulation: Wait a bit, then supply.
                Thread.sleep(2000 + (long)(Math.random() * 2000));
                
                if (!p.isDisponibile()) {
                    System.out.println("FORNITORE " + nome + ": Rilevato ordine/mancanza per " + p.getTitolo());
                    p.fornisci(this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
