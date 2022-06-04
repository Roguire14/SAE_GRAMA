package Interface;

import Moteur.Graphe;
import Moteur.Sommets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

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
        List<Sommets> listeSommets = new LinkedList<Sommets>();
        for(Sommets som : engine.getAllSommet())
            listeSommets.add(som);

        StringBuffer resultat = new StringBuffer("Rien pour le moment");
        JLabel listeVoisins = new JLabel(resultat.toString());

        JComboBox selecteur = new JComboBox();
        for(Sommets s : listeSommets)
            selecteur.addItem(s.getName());
        selecteur.addItem("");
        selecteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Sommets element : listeSommets)
                    if(element.getName().equals(selecteur.getSelectedItem()))
                    {
                        resultat.delete(0,resultat.length());
                        for(String s : engine.voisinSommet(element))
                            resultat.append(s+"\n");
                        listeVoisins.setText(resultat.toString());
                    }
            }
        });
        selecteur.setMaximumSize(new Dimension(250, 40));

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(Box.createRigidArea(new Dimension(10, 30)));
        main.add(selecteur);
        main.add(Box.createRigidArea(new Dimension(10, 50)));
        main.add(listeVoisins);
        return main;
    }
}
