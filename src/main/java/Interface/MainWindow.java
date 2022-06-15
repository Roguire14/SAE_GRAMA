package Interface;

import Dessin.DisplayArea;
import Moteur.Graphe;
import Moteur.Sommets;
import Dessin.DisplayArea;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {

    private Graphe engine;
    private DisplayArea displayArea;
    private JLabel status;

    private void ecranStart(int numero)
    {
        switch(numero)
        {
            case 0:
                new Ecran_Zero(engine, this);
                break;
            case 1:
                new Ecran_Un(engine, this);
                break;
            case 2:
                new Ecran_Deux(engine,this);
                break;
            case 3:
                new Ecran_Trois(engine, this);
                break;
        }
        this.setVisible(false);
    }

    private void autoStatus(){
        if(engine.getStatus()==0){
            status.setForeground(Color.RED);
            status.setText("Erreur lors du chargement du graphe");
        }else{
            status.setForeground(Color.GREEN);
            status.setText("Succès du chargement du graphe");
        }
    }

    private void newGraphe(){
        if(engine.getStatus()==1){
            engine.clearEverything();
            displayArea.off();}
        LeFileChooser fileChooser = new LeFileChooser();
        File file = fileChooser.getFileChooser().getSelectedFile();
        engine = new Graphe(this,file);
        autoStatus();
        if(engine.getStatus()==1)
            displayArea = new DisplayArea(engine.getAllSommet(),engine.getAllArete(),engine);
        setVisible(false);
        setVisible(true);
    }

    public MainWindow(File file){
        super();
        engine = new Graphe(this,file);
        displayArea = new DisplayArea(engine.getAllSommet(), engine.getAllArete(), engine);
        constrFen();
    }

    private void constrFen() {
        setTitle("SAE Graphe");
        setSize(600,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(constrPan());
        setResizable(false);
        setVisible(true);
    }

    private JPanel constrPan() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(Box.createRigidArea(new Dimension(0,50)));
        JLabel msg = new JLabel("SAE GRAMA by Chatloupidoux et Roguiré14");
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(msg);
        JLabel msg_suite = new JLabel("aka Baptiste BOISMENU et Romain GUION");
        msg_suite.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(msg_suite);
        if(engine.getStatus()==0){
            status = new JLabel("Erreur lors du chargement du graphe");
            status.setForeground(Color.RED);
            status.setAlignmentX(Component.CENTER_ALIGNMENT);
            main.add(Box.createRigidArea(new Dimension(0,20)));
            main.add(status);
        }else{
            status = new JLabel("Succès du chargement du graphe");
            status.setForeground(Color.GREEN);
            status.setAlignmentX(Component.CENTER_ALIGNMENT);
            main.add(Box.createRigidArea(new Dimension(0,20)));
            main.add(status);
            if(engine.getExtension(engine.getFileName()).get().equals("json")){
                JButton button1 = new JButton("Clique ici pour créer une save du JSON en CSV");
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int taille = 0;
                        String input = null;
                        while (taille == 0) {
                            input = JOptionPane.showInputDialog(null, "Veuillez insérer le nom du fichier", "Nom du fichier", JOptionPane.INFORMATION_MESSAGE);
                            taille = input.length();
                        }
                        StringBuffer stringBuffer = new StringBuffer(input);
                        if (!stringBuffer.toString().endsWith(".csv")) stringBuffer.append(".csv");
                        engine.JSONintoCSV(stringBuffer.toString());
                    }
                });
                button1.setAlignmentX(CENTER_ALIGNMENT);
                main.add(button1);
            }
        }
        JMenuBar menuBar = new JMenuBar();
        JMenuItem jMenuItem0 = new JMenuItem("Écran 0 - information 0-distance");
        jMenuItem0.addActionListener(e -> ecranStart(0));
        JMenuItem jMenuItem1 = new JMenuItem("Écran 1 - voisinnage direct -> 1-distance");
        jMenuItem1.addActionListener(e -> ecranStart(1));
        JMenuItem jMenuItem2 = new JMenuItem("Écran 2 - voisinage à 2 sauts -> 2-distance");
        jMenuItem2.addActionListener(e -> ecranStart(2));
        JMenuItem jMenuItem3 = new JMenuItem("Écran 3 - comparaison de sites à 2 sauts ou plus -> >= 2-distance");
        jMenuItem3.addActionListener(e -> ecranStart(3));
        JMenu action = new JMenu("Écrans");
        action.add(jMenuItem0);
        action.add(jMenuItem1);
        action.add(jMenuItem2);
        action.add(jMenuItem3);
        menuBar.add(action);

        JMenu ppt = new JMenu("Options");
        JMenuItem ppt_1 = new JMenuItem("Quitter");
        JMenuItem ppt_2 = new JMenuItem("Ouvrir un autre graphe");
        ppt_2.addActionListener(e -> newGraphe());
        ppt_1.addActionListener(e -> System.exit(42));
        ppt.add(ppt_2);
        ppt.add(ppt_1);
        menuBar.add(ppt);

        this.setJMenuBar(menuBar);

        return main;
    }

}
