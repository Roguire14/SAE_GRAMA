package Interface;

import Moteur.Graphe;
import Moteur.Sommets;
import Action.ActionButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class Ecran_Trois extends JFrame {

    private Graphe engine;
    private MainWindow window;
    private JComboBox<String> comboBoxSA;
    private JComboBox<String> comboBoxSB;
    private JComboBox<String> comboBoxChoix;
    private JLabel label;

    public JComboBox<String> getComboBoxSA() {
        return comboBoxSA;
    }

    public JComboBox<String> getComboBoxSB() {
        return comboBoxSB;
    }

    public JComboBox<String> getComboBoxChoix() {
        return comboBoxChoix;
    }

    public JLabel getLabel() {
        return label;
    }

    public Ecran_Trois(Graphe engine, MainWindow window){
        this.engine = engine;
        this.window = window;
        WindowListener l = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ecranTroisStop();
            }
        };
        addWindowListener(l);
        constrFen();
    }

    private void constrFen() {
        setTitle("Écran 3 - comparaison de sites à 2 sauts ou plus -> >= 2-distance");
        setSize(800,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(constrPan());
        setVisible(true);
    }

    private JPanel constrPan() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        JPanel box = new JPanel(new GridLayout(2,3,10,0));
        box.setMaximumSize(new Dimension(740,80));
        List<Sommets> sommets = engine.getAllSommet();
        String[] sommetsName = new String[sommets.size()];
        for(int i =0; i<sommets.size();i++)
            sommetsName[i]= sommets.get(i).getName();
        String[] strings = {"Ouverte","Gastronomique","Culturelle"};
        comboBoxSA = new JComboBox<>(sommetsName);
        comboBoxSA.setSelectedItem(null);
        comboBoxSB = new JComboBox<>(sommetsName);
        comboBoxSB.setSelectedItem(null);
        comboBoxChoix = new JComboBox<>(strings);
        comboBoxChoix.setSelectedItem(null);
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sommetA = new JLabel("Sommet A");
        box.add(sommetA);
        JLabel sommetB = new JLabel("Sommet B");
        box.add(sommetB);
        box.add(new JLabel("Type"));
        box.add(comboBoxSA);
        box.add(comboBoxSB);
        box.add(comboBoxChoix);
        main.add(Box.createRigidArea(new Dimension(0,20)));
        main.add(box);
        main.add(Box.createRigidArea(new Dimension(0,20)));

        label = new JLabel();
        JButton button = new JButton(new ActionButton("Vérifier",engine,this));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(button);
        main.add(Box.createRigidArea(new Dimension(0,20)));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(label);

        return main;
    }

    private void ecranTroisStop() {
        window.setVisible(true);
    }

}
