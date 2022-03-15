public class Aretes {
    private String typarete;
    private int distance;
    private Sommets sommetA;
    private Sommets sommmetB;

    public Aretes(String typarete, int distance, Sommets sommetA, Sommets sommmetB){
        this.typarete = typarete;
        this.distance = distance;
        this.sommetA = sommetA;
        this.sommmetB = sommmetB;
    }

    public int getDistance() {
        return distance;
    }

    public String getTyparete() {
        return typarete;
    }

    public Sommets getSommetA() {
        return sommetA;
    }

    public Sommets getSommmetB() {
        return sommmetB;
    }
}
