import java.io.Serializable;

public class Cliente implements Serializable {
    private String cf;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    
    private Prodotto prodottoDesiderato;

    public Cliente(String cf, String nome, String cognome, String email, String password) {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public String getCf() { return cf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public void setProdottoDesiderato(Prodotto p) { this.prodottoDesiderato = p; }

    public void run() {
        if (prodottoDesiderato == null) {
            System.out.println("CLIENTE " + nome + ": Nessun prodotto selezionato.");
            return;
        }
        prodottoDesiderato.ordina(this);
        prodottoDesiderato.consegna(this);
    }
}
