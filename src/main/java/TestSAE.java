import Interface.MainWindow;
import Interface.SelectWindow;
import Moteur.Graphe;

import javax.swing.*;

public class TestSAE {
    public static void main(String args[]){

//        Graphe graphe = new Graphe();
//        graphe.afficheElt("L");
//        System.out.println("");
//        graphe.afficheElt("A");
//        graphe.getSommets();
//        graphe.getAretes();
//        graphe.affNbType("");
//        System.out.println(graphe.getVoisins("Lagnieu","V"));
//        graphe.getVoisins("Lyon","R");
//        graphe.infoAretes(graphe.graphe.get(0).get(0));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectWindow();
            }
        });
    }
}
