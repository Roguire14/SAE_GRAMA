package Interface;

import Moteur.Graphe;
import Moteur.Sommets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Ecran_Deux extends JFrame {

    private Graphe engine;
    private MainWindow window;

    private void ecranDeuxStop(){
        window.setVisible(true);
    }

    public Ecran_Deux(Graphe engine, MainWindow window) {
        super();
        WindowListener l = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ecranDeuxStop();
            }
        };
        this.engine = engine;
        this.window = window;
        addWindowListener(l);
        constrFen();
    }

    private void constrFen() {
        setTitle("Écran 2 - voisinage à 2 sauts -> 2-distance");
        setSize(new Dimension(800,300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(constrPan());
        setVisible(true);
    }

    private JPanel constrPan() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        return main;
    }
}
