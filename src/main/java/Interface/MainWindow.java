package Interface;

import Moteur.Graphe;
import Moteur.Sommets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {

    private Graphe engine;

    public MainWindow(File file){
        super();
        engine = new Graphe(this,file);
        constrFen();
    }

    private void constrFen() {
        setTitle("SAE Graphe");
        setSize(600,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(constrPan());
//        pack();
        setVisible(true);
    }

    private JPanel constrPan() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JLabel msg = new JLabel("SAE GRAMA by Chatloupidoux et Roguiré14");
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(msg);
        JLabel status;
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
                    if(!stringBuffer.toString().endsWith(".csv"))stringBuffer.append(".csv");
                    engine.JSONintoCSV(stringBuffer.toString());
                }
            });
            button1.setAlignmentX(CENTER_ALIGNMENT);
            main.add(button1);
        }

        JMenuItem jMenuItem0 = new JMenuItem("Écran de base");
        jMenuItem0.setEnabled(false);
        JMenuItem jMenuItem = new JMenuItem("Écran 0 - information 0-distance");
        jMenuItem.addActionListener(e -> new Ecran_Zero(engine));
        JMenuItem jMenuItem1 = new JMenuItem("Écran 1 - voisinnage direct -> 1-distance");
        JMenuItem jMenuItem2 = new JMenuItem("Écran 2 - voisinage à 2 sauts -> 2-distance");
        JMenuItem jMenuItem3 = new JMenuItem("Écran 3 - comparaison de sites à 2 sauts ou plus -> >= 2-distance");
        JMenu action = new JMenu("Écrans");
        action.add(jMenuItem0);
        action.add(jMenuItem);
        action.add(jMenuItem1);
        action.add(jMenuItem2);
        action.add(jMenuItem3);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(action);
        this.setJMenuBar(menuBar);

        return main;
    }

}
