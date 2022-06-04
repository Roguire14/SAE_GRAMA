package Action;

import Interface.Ecran_Deux;
import Moteur.Graphe;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionButtonED extends AbstractAction {

    private Graphe graphe;
    private Ecran_Deux ecranDeux;

    public ActionButtonED(String buttonName, Graphe graphe, Ecran_Deux ecranDeux ){
        super(buttonName);
        this.graphe = graphe;
        this.ecranDeux = ecranDeux;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sA =(String)ecranDeux.getComboBoxSA().getSelectedItem();
        String sB = (String)ecranDeux.getComboBoxSB().getSelectedItem();
        if(sA.equals(sB))
            ecranDeux.getIsTwoD().setText("Veuillez ne pas séléctionner le même sommet, ça n'a pas de sens");
        else {
            boolean b = graphe.isTwoDistance(sA,sB);
            if (b)  ecranDeux.getIsTwoD().setText(sA+" et "+sB+" sont à 2-distance");
            else  ecranDeux.getIsTwoD().setText(sA+" et "+sB+" ne sont pas à 2-distance");
        }

    }
}
