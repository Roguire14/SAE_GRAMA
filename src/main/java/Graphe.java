import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Graphe {
    private ArrayList<ArrayList<Aretes>> graphe;

    public Graphe(){
        this.graphe = load();
    }

    private ArrayList load(){
        JSONParser parser = new JSONParser();
        ArrayList<ArrayList<Aretes>> stock = new ArrayList<>();
        Aretes aretes = null;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("graphe-MAP.json"));

            for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();){
                String key = (String) iterator.next();
//                System.out.println(key);
                String[] info = key.split(",");
                String type_sommet = info[0];
                String nom_sommet = info[1];
                Sommets sommets = new Sommets(type_sommet,nom_sommet);
                if(jsonObject.get(key)instanceof JSONObject){
                    ArrayList<Aretes> list = new ArrayList<>();
                    JSONObject jsonObject1 = ((JSONObject)jsonObject.get(key));
                    for(Iterator iterator1 = jsonObject1.keySet().iterator(); iterator1.hasNext();){
                        String key1 = (String) iterator1.next();
                        String[] info2 = key1.split(",");
                        String type_sommet2 = info2[0];
                        String nom_sommet2 = info2[1];
                        Sommets sommets1 = new Sommets(type_sommet2,nom_sommet2);
//                        System.out.println("   "+key1);
                        if(jsonObject1.get(key1) instanceof JSONArray){
                            JSONArray jsonArray = ((JSONArray)jsonObject1.get(key1));
                            for(int i = 0; i<jsonArray.size();i++){
//                                System.out.println("      "+jsonArray.get(i));
                                String[] info3 = jsonArray.get(i).toString().split(",");
                                String type_arete = info3[0];
                                int distance_arete = Integer.valueOf(info3[1]);
                                aretes = new Aretes(type_arete,distance_arete,sommets,sommets1);
                                list.add(aretes);
                            }
                        }else{
//                            System.out.println("      "+jsonObject1.get(key1));
                            String[] info3 = jsonObject1.get(key1).toString().split(",");
                            String type_arete = info3[0];
                            int distance_arete = Integer.valueOf(info3[1]);
                            aretes = new Aretes(type_arete,distance_arete,sommets,sommets1);
                            list.add(aretes);
                        }
                    }stock.add(list);
                }
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }return stock;
    }

    public void afficheStock(){
        for(int i=0; i< graphe.size();i++){
            ArrayList<Aretes> list = graphe.get(i);
            for(int j = 0; j< list.size(); j++){
                System.out.println(list.get(j).toString());
            }
        }
    }

    public void infoSommet(String sommets){
        if(sommetExiste(sommets)) {
            System.out.println(sommets + " est lié avec:");
            for (int i = 0; i < graphe.size(); i++) {
                for (int j = 0; j < graphe.get(i).size(); j++) {
                    if (graphe.get(i).get(j).getSommetA().getName().equals(sommets)) {
                        Aretes found = graphe.get(i).get(j);
                        System.out.println("    " + found.getSommmetB().getName() + " qui est de type "+found.getSommmetB().getType()+" par " + found.getTyparete() + " de distance " + found.getDistance() + " km");
                    }
                }
            }
        }else{
            System.out.println("Aucun sommet de ce nom n'existe");
        }
    }

    private boolean sommetExiste(String sommet){
        boolean existe = false;
        for(int i =0;i<graphe.size();i++) {
            for (int j = 0; j < graphe.get(i).size(); j++) {
                if (graphe.get(i).get(j).getSommetA().getName().equals(sommet)) existe = true;
            }
        }
        return existe;
    }
}
