public class Aretes {
    private char typarete;
    private int distance;
    private Sommets sommetA;
    private Sommets sommmetB;

    public Aretes(char typarete, int distance, Sommets sommetA, Sommets sommmetB){
        this.typarete = typarete;
        this.distance = distance;
        this.sommetA = sommetA;
        this.sommmetB = sommmetB;
    }

    public int getDistance() {
        return distance;
    }

    public char getTyparete() {
        return typarete;
    }

    public Sommets getSommetA() {
        return sommetA;
    }

    public Sommets getSommmetB() {
        return sommmetB;
    }

    @Override
    public String toString() {
        return "Aretes{" +
                "typarete='" + typarete + '\'' +
                ", distance=" + distance +
                ", sommetA=" + sommetA +
                ", sommmetB=" + sommmetB +
                '}';
    }

    public void getInfos(){
        System.out.println("Il existe un lien entre "+sommetA.getName()+" et "+sommmetB.getName()+" qui fait "+distance+" km de type "+typarete);
    }
}
