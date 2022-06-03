package Interface;

import Moteur.Graphe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Ecran_Un extends JFrame
{
    private Graphe engine;
    private MainWindow window;

    public void ecranStop(){window.setVisible(true);}

    public Ecran_Un(Graphe graphe, MainWindow window)
    {
        super();
        WindowListener l = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ecranStop();
            }
        };
        engine = graphe;
        addWindowListener(l);
        this.window = window;
        constrFen();
    }

    private void constrFen()
    {
        setTitle("Écran 1 - information 1-distance");
        setSize(new Dimension(800,300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(constrPan());
        setVisible(true);
    }

    private JPanel constrPan()
    {
        JPanel main = new JPanel();
        main.add(new JTextField("I exist"));

        return main;
    }
}
