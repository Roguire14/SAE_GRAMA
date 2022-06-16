package Dessin;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import Moteur.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;

public class DisplayArea
{
    private ArrayList<Sommets> listeSommets;
    private ArrayList<Aretes> listeAretes;

    private Graphe engine;

    public DisplayArea(ArrayList sommets, ArrayList aretes, Graphe graphe)
    {
        listeSommets = sommets;
        listeAretes = aretes;
        engine = graphe;

        Graph leGraphe = new DefaultGraph("yes");
        for (Sommets sommet : listeSommets)
            switch(sommet.getType().charAt(0))
            {
                case 'V':
                    leGraphe.addNode(sommet.getName());
                    Node ville = leGraphe.getNode(sommet.getName());
                    ville.addAttribute("ui.style", "shape: circle; fill-color: red; size: 25px; text-alignment: center;");
                    ville.addAttribute("ui.label", sommet.getName());
                    break;
                case 'R':
                    leGraphe.addNode(sommet.getName());
                    Node resto = leGraphe.getNode(sommet.getName());
                    resto.addAttribute("ui.style", "shape: box; fill-color: blue; size: 11px; text-alignment: center;");
                    resto.addAttribute("ui.label", sommet.getName());
                    break;
                case 'L':
                    leGraphe.addNode(sommet.getName());
                    Node fun = leGraphe.getNode(sommet.getName());
                    fun.addAttribute("ui.style", "shape: polygon; fill-color: green; size: 11px; text-alignment: center;");
                    fun.addAttribute("ui.label", sommet.getName());
                    break;
            }
        for(Aretes arete : listeAretes)
        {
            try {
                leGraphe.addEdge("(" + leGraphe.getEdgeCount() + ")" + arete.getTyparete() + "," + arete.getDistance(), arete.getSommetA().getName(), arete.getSommetB().getName());
            } catch (org.graphstream.graph.EdgeRejectedException edgeRejectedException) {
            }
        }
        leGraphe.display();
    }
}
