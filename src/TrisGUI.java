import javax.swing.*;
import java.awt.*;

public class TrisGUI extends JFrame {

    // Componenti globali
    private JLabel statusLabel;
    private JButton[] buttons = new JButton[9]; // Array per le 9 caselle
    private JButton resetButton;

    // Colori e Font personalizzati
    private final Font mainFont = new Font("Ink Free", Font.BOLD, 60); // Font stile "mano"
    private final Color bgGame = new Color(50, 50, 50); // Grigio scuro
    private final Color btnColor = new Color(240, 240, 240); // Bianco sporco

    public TrisGUI() {
        // 1. Configurazione Finestra Principale (JFrame)
        this.setTitle("Java Tris");
        this.setSize(500, 600); // Dimensioni: Larghezza x Altezza
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); // Layout principale: Nord, Centro, Sud
        this.setLocationRelativeTo(null); // Centra la finestra nello schermo

        // --- SEZIONE NORD: Intestazione ---
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(bgGame);
        titlePanel.setPreferredSize(new Dimension(500, 80));

        statusLabel = new JLabel("Tocca a: X");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titlePanel.add(statusLabel);

        this.add(titlePanel, BorderLayout.NORTH);

        // --- SEZIONE CENTRO: Griglia di Gioco ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3, 5, 5)); // 3 righe, 3 col., 5px di spazio
        buttonPanel.setBackground(bgGame); // Colore di sfondo tra i bottoni

        // Ciclo per creare i 9 bottoni
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(mainFont);
            buttons[i].setBackground(btnColor);
            buttons[i].setFocusable(false); // Rimuove il bordo di selezione al click

            // Aggiungiamo un listener fittizio per vedere l'effetto grafico
            buttons[i].addActionListener(e -> {
                JButton btnClicked = (JButton) e.getSource();
                if (btnClicked.getText().equals("")) {
                    btnClicked.setText("X"); // Esempio: mette sempre X
                    btnClicked.setForeground(new Color(255, 0, 0)); // Rosso per X
                }
            });

            buttonPanel.add(buttons[i]);
        }

        this.add(buttonPanel, BorderLayout.CENTER);

        // --- SEZIONE SUD: Tasto Reset ---
        JPanel bottomPanel = new JPanel();
        resetButton = new JButton("Nuova Partita");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resetButton.setFocusable(false);

        // Listener per pulire la griglia (aspetto grafico)
        resetButton.addActionListener(e -> {
            for(JButton btn : buttons) {
                btn.setText("");
                btn.setBackground(btnColor);
            }
            statusLabel.setText("Tocca a: X");
        });

        bottomPanel.add(resetButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Rendiamo tutto visibile
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Avvia la GUI nel thread corretto
        SwingUtilities.invokeLater(() -> new TrisGUI());
    }
}