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
        requete.setLayout(new GridLayout(2,2,30,30));
        requete.setMaximumSize(new Dimension(250,350));

        String[] choixTypeTab = {"Ville","Centre de loisir","Restaurant"};
        String[] choixArrTab= {"Autoroute","Nationale","Départementale"};
        JComboBox choixTypeBox = new JComboBox<>(choixTypeTab);
        JComboBox choixArrBox = new JComboBox<>(choixArrTab);
        choixArrBox.setSelectedItem(null);
        choixTypeBox.setSelectedItem(null);

        JComboBox choixSelonPreviousSommet = new JComboBox<>();
        choixTypeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    if ("Ville".equals(choixTypeBox.getSelectedItem())) {
                        choixSelonPreviousSommet.removeAllItems();
                        for (Object o : engine.afficheElt("V"))
                            if (o instanceof Sommets)
                                choixSelonPreviousSommet.addItem(((Sommets) o).getName());
                    }
                    if("Centre de loisir".equals(choixTypeBox.getSelectedItem())) {
                        choixSelonPreviousSommet.removeAllItems();
                        for (Object o : engine.afficheElt("L"))
                            if (o instanceof Sommets)
                                choixSelonPreviousSommet.addItem(((Sommets) o).getName());
                    }
                    if("Restaurant".equals(choixTypeBox.getSelectedItem())){
                        choixSelonPreviousSommet.removeAllItems();
                        for(Object o: engine.afficheElt("R")) if(o instanceof Sommets) choixSelonPreviousSommet.addItem(((Sommets) o).getName());
                    }
                }
            }
        });

        JComboBox choixSelonPreviousArrete = new JComboBox<>();
        choixArrBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    if(choixArrBox.getSelectedItem().equals("Autoroute")){
                        choixSelonPreviousArrete.removeAllItems();
                        for(Object o: engine.afficheElt("A"))
                            if(o instanceof Aretes) {
                                Aretes aretes = (Aretes) o;
                                choixSelonPreviousArrete.addItem(new String(aretes.getTyparete()+" "+aretes.getSommetA().getName()+" ~ "+aretes.getSommetB().getName()));
                            }
                    }
                }
            }
        });

        requete.add(choixTypeBox);
        requete.add(choixSelonPreviousSommet);
        requete.add(choixArrBox);
        requete.add(choixSelonPreviousArrete);
        main.add(requete);


//        JComboBox answers =


        return main;
    }

}
