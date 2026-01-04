public class Cliente extends Thread {
    private String cf;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    // Address fields could be added but omitted for brevity unless needed for specific logic
    
    private Prodotto prodottoDesiderato; // For simulation

    public Cliente(String cf, String nome, String cognome, String email, String password) {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    // Setters/Getters
    public String getCf() { return cf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public void setProdottoDesiderato(Prodotto p) { this.prodottoDesiderato = p; }

    @Override
    public void run() {
        if (prodottoDesiderato == null) {
            System.out.println("CLIENTE " + nome + ": Nessun prodotto selezionato.");
            return;
        }
        
        try {
            // Simulation logic
            System.out.println("CLIENTE " + nome + ": Avvio procedura acquisto per " + prodottoDesiderato.getTitolo());
            Thread.sleep(1000); // Simulate time
            
            // 2a. Ordinare
            prodottoDesiderato.ordina(this);
            
            // 2c. Ricevere (Consegna)
            prodottoDesiderato.consegna(this);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
