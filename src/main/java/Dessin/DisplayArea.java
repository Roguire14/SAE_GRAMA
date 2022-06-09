package Dessin;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import Moteur.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;

public class DisplayArea extends JFrame
{
    private ArrayList<Sommets> listeSommets;
    private ArrayList<Aretes> listeAretes;

    private Graphe engine;

    public DisplayArea(ArrayList sommets, ArrayList aretes, Graphe graphe)
    {
        super();
        listeSommets = sommets;
        listeAretes = aretes;
        engine = graphe;
        constrFen();
    }

    private void constrFen()
    {
        setTitle("Visualisation du graphe");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(new Dimension(750,750));
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(constrPan());
        setVisible(true);
    }

    private JPanel constrPan()
    {
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        Graph leGraphe = new DefaultGraph("yes");
        for (Sommets sommet : listeSommets)
            leGraphe.addNode(sommet.getName());
        for(Aretes arete : listeAretes)
        {
            try{
                leGraphe.addEdge("("+leGraphe.getEdgeCount()+")"+arete.getTyparete()+","+arete.getDistance(), arete.getSommetA().getName(), arete.getSommetB().getName());
            }catch (org.graphstream.graph.EdgeRejectedException edgeRejectedException){
                System.out.println("Ratio la Edge");
            }
        }

        leGraphe.display();

        return main;
    }
}
