package Interface;

import Moteur.Aretes;
import Moteur.Graphe;
import Moteur.Sommets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Ecran_Zero extends JFrame {

    private Graphe engine;

    public Ecran_Zero(Graphe graphe){
        super();
        engine = graphe;
        constrFen();
    }

    private void constrFen() {
        setTitle("Écran 0 - information 0-distance");
        setSize(new Dimension(600,400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(constrPan());
        setVisible(true);
    }

    private JPanel constrPan() {
        JPanel main = new JPanel();
        main.add(Box.createRigidArea(new Dimension(0,30)));
        JPanel requete = new JPanel();
        requete.setLayout(new GridLayout(1,2,30,30));
        requete.setMaximumSize(new Dimension(250,350));

        String[] choixTypeTab = {"Ville","Centre de loisir","Restaurant"};
        JComboBox choixTypeBox = new JComboBox<>(choixTypeTab);
        choixTypeBox.setSelectedItem(null);

        JComboBox choixSelonPrevious = new JComboBox<>();
        choixTypeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    if ("Ville".equals(choixTypeBox.getSelectedItem())) {
                        choixSelonPrevious.removeAllItems();
                        for (Object o : engine.afficheElt("V"))
                            if (o instanceof Sommets)
                                choixSelonPrevious.addItem(((Sommets) o).getName());
                    }
                    if("Centre de loisir".equals(choixTypeBox.getSelectedItem())) {
                        choixSelonPrevious.removeAllItems();
                        for (Object o : engine.afficheElt("L"))
                            if (o instanceof Sommets)
                                choixSelonPrevious.addItem(((Sommets) o).getName());
                    }
                    if("Restaurant".equals(choixTypeBox.getSelectedItem())){
                        choixSelonPrevious.removeAllItems();
                        for(Object o: engine.afficheElt("R")) if(o instanceof Sommets) choixSelonPrevious.addItem(((Sommets) o).getName());
                    }
                }
            }
        });
        requete.add(choixTypeBox);
        requete.add(choixSelonPrevious);
        main.add(requete);


//        JComboBox answers =


        return main;
    }

}
