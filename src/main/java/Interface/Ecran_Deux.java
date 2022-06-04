package Interface;

import Moteur.Graphe;
import Moteur.Sommets;
import Action.ActionButtonED;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Ecran_Deux extends JFrame {

    private Graphe engine;
    private MainWindow window;
    private JLabel isTwoD = new JLabel();
    private JComboBox comboBoxSA;
    private JComboBox comboBoxSB;

    public JLabel getIsTwoD() {return isTwoD;}
    public JComboBox getComboBoxSA() {return comboBoxSA;}
    public JComboBox getComboBoxSB() {return comboBoxSB;}

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
        setSize(new Dimension(800,275));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(constrPan());
        setResizable(false);
        setVisible(true);
    }

    private JPanel constrPan() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JPanel box = new JPanel(new GridLayout(2,2,10,0));
        box.setMaximumSize(new Dimension(740,80));
        List<Sommets> sommets = engine.getAllSommet();
        String[] sommetsName = new String[sommets.size()];
        for(int i =0; i<sommets.size();i++)
            sommetsName[i]= sommets.get(i).getName();
        comboBoxSA = new JComboBox<>(sommetsName);
        comboBoxSA.setSelectedItem(null);
        comboBoxSB = new JComboBox<>(sommetsName);
        comboBoxSB.setSelectedItem(null);
//        ((JLabel)comboBoxSA.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
//        ((JLabel)comboBoxSB.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sommetA = new JLabel("Sommet A");
        box.add(sommetA);
        JLabel sommetB = new JLabel("Sommet B");
        box.add(sommetB);
        box.add(comboBoxSA);
        box.add(comboBoxSB);
        main.add(Box.createRigidArea(new Dimension(0,20)));
        main.add(box);
        main.add(Box.createRigidArea(new Dimension(0,20)));

        JButton button = new JButton(new ActionButtonED("Vérifier",engine,this));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(button);
        main.add(Box.createRigidArea(new Dimension(0,20)));
        isTwoD.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(isTwoD);
        return main;
    }
}
