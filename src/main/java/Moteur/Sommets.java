package Moteur;

public class Sommets {
    private String type;
    private String name;

    public Sommets(String type, String name){
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Moteur.Sommets{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
