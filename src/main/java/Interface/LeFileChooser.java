package Interface;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class LeFileChooser extends JFrame {

    private JFileChooser fileChooser;

    public LeFileChooser(){
        super();
        fileChooser = constrFC();
    }

    private JFileChooser constrFC() {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileFilter filter = new FileNameExtensionFilter("Fichier JSON","json");
        FileFilter filter1 = new FileNameExtensionFilter("Fichier CSV","csv");
        fileChooser.addChoosableFileFilter(filter); fileChooser.addChoosableFileFilter(filter1);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.showOpenDialog(this);
        return fileChooser;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }
}
