package Interface;

import Moteur.Graphe;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {

    private Graphe engine;

    public MainWindow(){
        super();
        engine = new Graphe(this,leChooser());
        constrFen();
    }

    private File leChooser(){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileFilter filter = new FileNameExtensionFilter("JSON Files","json");
        FileFilter filter1 = new FileNameExtensionFilter("CSV Files","csv");
        fileChooser.addChoosableFileFilter(filter); fileChooser.addChoosableFileFilter(filter1);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.showOpenDialog(this);
        return fileChooser.getSelectedFile();
    }

    private void constrFen() {
        setTitle("SAE Graphe");
        setSize(1060,800);
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
        }else{
            status = new JLabel("Succès du chargement du graphe");
            status.setForeground(Color.GREEN);
        }status.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createRigidArea(new Dimension(0,20)));
        main.add(status);



        return main;
    }

}
