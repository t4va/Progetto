package classi_S_C;

public class Cliente extends Thread implements java.io.Serializable {
    private String cf;
    private String nome;
    private String cognome;
    private String tel;
    private String email;
    private String password;
    private String via;
    private String civico;
    private String cap;
    private String comune;

    public Cliente(String cf, String nome, String cognome, String tel,
                   String email, String password,
                   String via, String civico, String cap, String comune) {

        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.comune = comune;
    }

    private Prodotto prodottoRef;

    public void setProdottoRef(Prodotto p) {
        this.prodottoRef = p;
    }

    public String getCf() {
        return cf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    @Override
    public void run() {
        if (prodottoRef != null) {
            prodottoRef.ordina(nome + " " + cognome);
        } else {
            System.out.println("Errore: Nessun prodotto selezionato per il cliente " + nome);
        }
    }

    public String toCSV() {
        return cf + ";" + nome + ";" + cognome + ";" + tel + ";" +
                email + ";" + password + ";" +
                via + ";" + civico + ";" + cap + ";" + comune;
    }
}
