package Action;

import Interface.Ecran_Deux;
import Interface.Ecran_Trois;
import Moteur.Graphe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionButton extends AbstractAction {

    private Graphe graphe;
    private Ecran_Deux ecranDeux;
    private Ecran_Trois ecranTrois;

    public ActionButton(String buttonName, Graphe graphe, Ecran_Deux ecranDeux ){
        super(buttonName);
        this.graphe = graphe;
        this.ecranDeux = ecranDeux;
    }

    public ActionButton(String buttonName, Graphe graphe, Ecran_Trois ecranTrois ){
        super(buttonName);
        this.graphe = graphe;
        this.ecranTrois = ecranTrois;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(ecranDeux!=null) {
                String sA =(String)ecranDeux.getComboBoxSA().getSelectedItem();
                String sB = (String)ecranDeux.getComboBoxSB().getSelectedItem();
                if (sA.equals(sB)) {
                    ecranDeux.getIsTwoD().setForeground(Color.red);
                    ecranDeux.getIsTwoD().setText("Veuillez ne pas séléctionner le même sommet, ça n'a pas de sens");
                } else {
                    boolean b = graphe.isTwoDistance(sA, sB);
                    ecranDeux.getIsTwoD().setForeground(Color.BLACK);
                    if (b) ecranDeux.getIsTwoD().setText(sA + " et " + sB + " sont à 2-distance");
                    else ecranDeux.getIsTwoD().setText(sA + " et " + sB + " ne sont pas à 2-distance");
                }
            }if(ecranTrois!=null){
                String sA = (String) ecranTrois.getComboBoxSA().getSelectedItem();
                String sB = (String) ecranTrois.getComboBoxSB().getSelectedItem();
                String choice = (String) ecranTrois.getComboBoxChoix().getSelectedItem();
                if (sA.equals(sB)) {
                    ecranTrois.getLabel().setForeground(Color.red);
                    ecranTrois.getLabel().setText("Veuillez ne pas séléctionner le même sommet, ça n'a pas de sens");
                } else {
                    ecranTrois.getLabel().setForeground(Color.BLACK);
                    switch(choice){
                        case "Ouverte":
                            boolean oui = graphe.isMore(1,graphe.getSommet(sA),graphe.getSommet(sB));
                            if(oui)
                                ecranTrois.getLabel().setText(sA+" est plus ouverte que "+sB);
                            else ecranTrois.getLabel().setText(sA+" est moins ouverte que "+sB);
                            break;
                        case "Gastronomique":
                            boolean yes = graphe.isMore(2,graphe.getSommet(sA),graphe.getSommet(sB));
                            if(yes)
                                ecranTrois.getLabel().setText(sA+" est plus gastronomique que "+sB);
                            else ecranTrois.getLabel().setText(sA+" est moins gastronomique que "+sB);
                            break;
                        case "Culturelle":
                            boolean si = graphe.isMore(3,graphe.getSommet(sA),graphe.getSommet(sB));
                            if(si) ecranTrois.getLabel().setText(sA+" est plus culturelle que "+sB);
                            else ecranTrois.getLabel().setText(sA+" est moins culturelle que "+sB);
                    }
                }
            }
        }catch (NullPointerException nullPointerException){
            if(ecranDeux!=null) {
                ecranDeux.getIsTwoD().setForeground(Color.red);
                ecranDeux.getIsTwoD().setText("Veuilez remplir toutes les catégories");
            }if(ecranTrois!=null){
                ecranTrois.getLabel().setForeground(Color.RED);
                ecranTrois.getLabel().setText("Veuillez remplir toutes les catégories");
            }
        }

    }
}
