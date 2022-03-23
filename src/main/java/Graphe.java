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

    public void setLSommets(ArrayList<Sommets> LSommets) {
        this.LSommets = LSommets;
    }
}
