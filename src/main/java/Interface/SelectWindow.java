package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectWindow extends JFrame {

    public SelectWindow(){
        super();
        constrFen();
    }

    private void constrFen() {
        setTitle("Sélection du fichier");
        setSize(400,150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(constrPan());
        setResizable(false);
        setVisible(true);
    }

    public void changeVisibility(){
        setVisible(false);
    }

    private JPanel constrPan() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(Box.createRigidArea(new Dimension(0,20)));

        JLabel selec = new JLabel("Veuillez sélectionner un fichier");
        selec.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(selec);

        main.add(Box.createRigidArea(new Dimension(0,20)));

        JButton button = new JButton("Cliquez ici pour choisir le fichier");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LeFileChooser fileChooser = new LeFileChooser();
                File file = fileChooser.getFileChooser().getSelectedFile();
                changeVisibility();
                new MainWindow(file);
            }
        });
        main.add(button);

        return main;
    }

}
