import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class Graphe {
    private ArrayList<Sommets> LSommets;
    private ArrayList<Aretes> LAretes;

    public Graphe(ArrayList<Sommets> LSommets, ArrayList<Aretes> LAretes){
        this.LAretes = LAretes;
        this.LSommets = LSommets;
    }

    public ArrayList<Aretes> getLAretes() {
        return LAretes;
    }

    public ArrayList<Sommets> getLSommets() {
        return LSommets;
    }

    public void setLAretes(ArrayList<Aretes> LAretes) {
        this.LAretes = LAretes;
    }

    @Override
    public String toString() {
        return "Graphe{" +
                "LSommets=" + LSommets +
                ", LAretes=" + LAretes +
                '}';
    }

//    for(int i=0;i<graphe.getLAretes().size();i++){
//        graphe.getLAretes().get(i).getInfos();
//    }

    public void getVoisins(Sommets sommets){
        for (int i = 0; i<LAretes.size();i++){
            if(LAretes.get(i).getSommetA()==sommets||LAretes.get(i).getSommmetB()==sommets)
                LAretes.get(i).getInfos();
        }
    }

//    public void charge(){
//        JSONParser parser = new JSONParser().parse();
//    }

}
