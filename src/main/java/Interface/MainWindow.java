package Interface;

import Moteur.Graphe;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
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

//        JTextPane test = new JTextPane();
//        try{
//            SimpleAttributeSet centrer = new SimpleAttributeSet();
//            StyleConstants.setAlignment(centrer, StyleConstants.ALIGN_CENTER);
//            StyledDocument doc = test.getStyledDocument();
//            doc.insertString(doc.getLength(),engine.infoSommet("Lyon"),centrer);
//            doc.setParagraphAttributes(0,doc.getLength(),centrer,false);
//        }catch (BadLocationException e) {
//            e.printStackTrace();
//        }
        List<String> test = engine.infoSommet("Lyon");
        for(String s: test) {
            JLabel oui = new JLabel(s);
            oui.setAlignmentX(Component.CENTER_ALIGNMENT);
            main.add(oui);
        }

        return main;
    }

}
