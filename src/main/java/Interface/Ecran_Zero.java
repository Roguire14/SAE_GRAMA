package Interface;

import Moteur.Aretes;
import Moteur.Graphe;
import Moteur.Sommets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ecran_Zero extends JFrame {

    private Graphe engine;
    private MainWindow window;

    private void ecranZeroStop(){
        window.setVisible(true);
    }

    public Ecran_Zero(Graphe graphe, MainWindow window){
        super();
        WindowListener l = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ecranZeroStop();
            }
        };
        engine = graphe;
        addWindowListener(l);
        this.window = window;
        constrFen();
    }

    private void constrFen() {
        setTitle("Écran 0 - information 0-distance");
        setSize(new Dimension(800,300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(constrPan());
        setVisible(true);
    }

    private JPanel constrPan() {
        JPanel main = new JPanel();
        main.add(Box.createRigidArea(new Dimension(0,30)));
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        JPanel requete = new JPanel();
        requete.setLayout(new GridLayout(2,2,30,30));
        requete.setMaximumSize(new Dimension(740,100));
        JPanel counter = new JPanel();
        counter.setLayout(new BoxLayout(counter,BoxLayout.Y_AXIS));

        JLabel counterSommet = new JLabel();
        counterSommet.setAlignmentX(CENTER_ALIGNMENT);
        counter.add(counterSommet);
        counter.add(Box.createRigidArea(new Dimension(0,20)));
        JLabel counterArete = new JLabel();
        counterArete.setAlignmentX(CENTER_ALIGNMENT);
        counter.add(counterArete);

        String[] choixTypeTab = {"Tout","Ville","Centre de loisir","Restaurant"};
        String[] choixArrTab= {"Tout","Autoroute","Nationale","Départementale"};
        JComboBox choixTypeBox = new JComboBox<>(choixTypeTab);
        JComboBox choixArrBox = new JComboBox<>(choixArrTab);
        choixArrBox.setSelectedItem(null);
        choixTypeBox.setSelectedItem(null);

        JComboBox choixSelonPreviousSommet = new JComboBox<>();
//        choixSelonPreviousSommet.setPreferredSize(new Dimension(choixSelonPreviousSommet.getPreferredSize().width,choixSelonPreviousSommet.getPreferredSize().height));
        choixTypeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    if(choixTypeBox.getSelectedItem().equals("Tout")){
                        choixSelonPreviousSommet.removeAllItems();
                        int count = engine.getNbType("V")+engine.getNbType("R")+engine.getNbType("L");
                        counterSommet.setText("Il y a "+count+" sommets");
                        for (Object o : engine.afficheElt("V"))
                            if (o instanceof Sommets)
                                choixSelonPreviousSommet.addItem(((Sommets) o).getType()+" "+((Sommets) o).getName());
                        for (Object o : engine.afficheElt("R"))
                            if (o instanceof Sommets)
                                choixSelonPreviousSommet.addItem(((Sommets) o).getType()+" "+((Sommets) o).getName());
                        for (Object o : engine.afficheElt("L"))
                            if (o instanceof Sommets)
                                choixSelonPreviousSommet.addItem(((Sommets) o).getType()+" "+((Sommets) o).getName());
                    }
                    if ("Ville".equals(choixTypeBox.getSelectedItem())) {
                        choixSelonPreviousSommet.removeAllItems();
                        counterSommet.setText(engine.affNbType("V"));
                        for (Object o : engine.afficheElt("V"))
                            if (o instanceof Sommets)
                                choixSelonPreviousSommet.addItem(((Sommets) o).getName());
                    }
                    if("Centre de loisir".equals(choixTypeBox.getSelectedItem())) {
                        choixSelonPreviousSommet.removeAllItems();
                        counterSommet.setText(engine.affNbType("L"));
                        for (Object o : engine.afficheElt("L"))
                            if (o instanceof Sommets)
                                choixSelonPreviousSommet.addItem(((Sommets) o).getName());
                    }
                    if("Restaurant".equals(choixTypeBox.getSelectedItem())){
                        choixSelonPreviousSommet.removeAllItems();
                        counterSommet.setText(engine.affNbType("R"));
                        for(Object o: engine.afficheElt("R")) if(o instanceof Sommets) choixSelonPreviousSommet.addItem(((Sommets) o).getName());
                    }
                }
            }
        });
        choixTypeBox.setPreferredSize(new Dimension(choixTypeBox.getPreferredSize().width,choixTypeBox.getPreferredSize().height));

        JComboBox choixSelonPreviousArrete = new JComboBox<>();
//        choixSelonPreviousArrete.setPreferredSize(new Dimension(choixSelonPreviousArrete.getPreferredSize().width,choixSelonPreviousArrete.getPreferredSize().height));
        choixArrBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    if(choixArrBox.getSelectedItem().equals("Tout")){
                        choixSelonPreviousArrete.removeAllItems();
                        int total = engine.getNbType("A") + engine.getNbType("D") + engine.getNbType("N");
                        counterArete.setText("Il y a "+ total+" aretes");
                        for(Object o: engine.afficheElt("A"))
                            if(o instanceof Aretes) {
                                Aretes aretes = (Aretes) o;
                                choixSelonPreviousArrete.addItem(aretes.getTyparete()+" "+aretes.getSommetA().getName()+" ~ "+aretes.getSommetB().getName()+" ~ "+aretes.getDistance()+"km");
                            }
                        for(Object o: engine.afficheElt("D"))
                            if(o instanceof Aretes) {
                                Aretes aretes = (Aretes) o;
                                choixSelonPreviousArrete.addItem(aretes.getTyparete()+" "+aretes.getSommetA().getName()+" ~ "+aretes.getSommetB().getName()+" ~ "+aretes.getDistance()+"km");
                            }
                        for(Object o: engine.afficheElt("N"))
                            if(o instanceof Aretes) {
                                Aretes aretes = (Aretes) o;
                                choixSelonPreviousArrete.addItem(aretes.getTyparete()+" "+aretes.getSommetA().getName()+" ~ "+aretes.getSommetB().getName()+" ~ "+aretes.getDistance()+"km");
                            }
                    }
                    if(choixArrBox.getSelectedItem().equals("Autoroute")){
                        choixSelonPreviousArrete.removeAllItems();
                        counterArete.setText(engine.affNbType("A"));
                        for(Object o: engine.afficheElt("A"))
                            if(o instanceof Aretes) {
                                Aretes aretes = (Aretes) o;
                                choixSelonPreviousArrete.addItem(aretes.getSommetA().getName()+" ~ "+aretes.getSommetB().getName()+" ~ "+aretes.getDistance()+"km");
                            }
                    }if(choixArrBox.getSelectedItem().equals("Nationale")){
                        choixSelonPreviousArrete.removeAllItems();
                        counterArete.setText(engine.affNbType("N"));
                        for(Object o: engine.afficheElt("N"))
                            if(o instanceof Aretes){
                                Aretes a = (Aretes) o;
                                choixSelonPreviousArrete.addItem(a.getSommetA().getName()+" ~ "+a.getSommetB().getName()+" ~ "+a.getDistance()+"km");
                            }
                    }if(choixArrBox.getSelectedItem().equals("Départementale")){
                        choixSelonPreviousArrete.removeAllItems();
                        counterArete.setText(engine.affNbType("D"));
                        for(Object o: engine.afficheElt("D"))
                            if(o instanceof Aretes){
                                Aretes a = (Aretes) o;
                                choixSelonPreviousArrete.addItem(a.getSommetA().getName()+" ~ "+a.getSommetB().getName()+" ~ "+a.getDistance()+"km");
                            }
                    }
                }
            }
        });
        choixArrBox.setPreferredSize(new Dimension(choixArrBox.getPreferredSize().width,choixArrBox.getPreferredSize().height));


        requete.add(choixTypeBox);
        requete.add(choixSelonPreviousSommet);
        requete.add(choixArrBox);
        requete.add(choixSelonPreviousArrete);
        main.add(requete);
        main.add(Box.createRigidArea(new Dimension(0,30)));
        main.add(counter);

        return main;
    }

}
