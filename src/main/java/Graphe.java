import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

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

    public void getAretes(){
        for(ArrayList<Aretes> list : graphe)
            for(Aretes aretes : list)
                System.out.println(aretes);
    }

    public void infoSommet(String sommets){
        if(sommetExiste(sommets)) {
            System.out.println(sommets + " est lié avec:");
            LinkedList<Aretes> linkedList = getVoisins(sommets);
            for (Aretes aretes: linkedList) {
                if (aretes.getSommetA().getName().equals(sommets)) {
                    Aretes found = aretes;
                    System.out.println("    " + found.getSommetB().getName() + " qui est "+getTypeFull(found.getSommetB().getType())+" par " + getTypeFull(found.getTyparete()) + " de distance " + found.getDistance() + " km");
                }
                }
        }else
            System.out.println("Aucun sommet de ce nom n'existe");
    }

    public void infoSommet(Sommets sommets){
        infoSommet(sommets.getName());
    }

    private boolean sommetExiste(Object sommet){
        boolean existe = false;
        if(sommet instanceof String)
            for (ArrayList<Aretes> list : graphe)
                for (Aretes aretes : list) {
                    if (aretes.getSommetA().getName().equals(sommet)) existe = true;
                    if (existe) break;
                }
        else if(sommet instanceof Sommets)
            return sommetExiste(((Sommets) sommet).getName());
        return existe;
    }

    private String getTypeFull(String type){
        String result = null;
        switch (type){
            case "V":
                result = "une ville";
                break;
            case "L":
                result = "un centre de loisir";
                break;
            case "R":
                result = "un restaurant";
                break;
            case "A":
                result = "une autoroute";
                break;
            case "D":
                result = "une départementale";
                break;
            case "N":
                result = "une nationale";
                break;
        }return result;
    }

    public void JSONintoCSV(String name){
        List<Sommets> sommetsTraite = new ArrayList<Sommets>();
        try {
            PrintWriter writer = new PrintWriter(name + ".csv");
            for (ArrayList<Aretes> list : graphe) {
                for (Aretes aretes : list) {
                    if (!sommetsTraite.contains(aretes.getSommetA())) {
                        LinkedList<Aretes> linkedList = getVoisins(aretes.getSommetA());
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(aretes.getSommetA().getType())
                                .append(",")
                                .append(aretes.getSommetA().getName())
                                .append(":");
                        for (int i = 0; i < linkedList.size(); i++) {
                            Aretes aretes1 = linkedList.get(i);
                            if (i != linkedList.size() - 1)
                                stringBuffer.append(aretes1.getTyparete())
                                        .append(",")
                                        .append(aretes1.getDistance())
                                        .append("::")
                                        .append(aretes1.getSommetB().getType())
                                        .append(",")
                                        .append(aretes1.getSommetB().getName())
                                        .append(";");
                            else
                                stringBuffer.append(aretes1.getTyparete())
                                        .append(",")
                                        .append(aretes1.getDistance())
                                        .append("::")
                                        .append(aretes1.getSommetB().getType())
                                        .append(",")
                                        .append(aretes1.getSommetB().getName())
                                        .append(";;");
                        }
                        writer.println(stringBuffer.toString());
                        sommetsTraite.add(aretes.getSommetA());
                    }
                }
            }writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LinkedList<Aretes> getVoisins(String sommets){
        LinkedList<Aretes> aretesList = new LinkedList<>();
        for(ArrayList<Aretes> list: graphe)
            for(Aretes aretes : list)
                if(aretes.getSommetA().getName().equals(sommets))
                    aretesList.add(aretes);
        return aretesList;
    }

    private LinkedList<Aretes> getVoisins(Sommets sommets){
        return getVoisins(sommets.getName());
    }

    public void afficheElt(String type){
        switch(type){
            case "A":
            case "D":
            case "N":
                LinkedList<Aretes> aretesLinkedList = getTypeAretes(type);
                for(Aretes aretes: aretesLinkedList)
                    System.out.println(aretes);
                break;
            case "V":
            case "L":
            case "R":
                LinkedList<Sommets> sommetsLinkedList = getTypeVille(type);
                for(Sommets sommets: sommetsLinkedList)
                    System.out.println(sommets);
                break;
        }
    }

    private LinkedList<Sommets> getTypeVille(String type) {
        LinkedList<Sommets> linkedList = new LinkedList<>();
        List<Sommets> sommetTraite = new LinkedList<>();
        for (ArrayList<Aretes> list : graphe)
            for (Aretes aretes : list)
                if (aretes.getSommetA().getType().equals(type) && !sommetTraite.contains(aretes.getSommetA())){
                    linkedList.add(aretes.getSommetA());
                    sommetTraite.add(aretes.getSommetA());
                }
        return linkedList;
    }

    private LinkedList<Aretes> getTypeAretes(String type){
        LinkedList<Aretes> linkedList = new LinkedList<>();
        List<Aretes> sommetsTraite = new LinkedList<>();
        for(ArrayList<Aretes> list: graphe)
            for(Aretes aretes: list)
                if(aretes.getTyparete().equals(type)&&!sommetsTraite.contains(aretes)) {
                    linkedList.add(aretes);
                    sommetsTraite.add(aretes);
                }
        return linkedList;
    }

    public void getSommets(){
        LinkedList<Sommets> sommetsTraites = new LinkedList<>();
        for(ArrayList<Aretes> list : graphe)
            for(Aretes aretes: list)
                if(!sommetsTraites.contains(aretes.getSommetA())){
                    sommetsTraites.add(aretes.getSommetA());
                    System.out.println(aretes.getSommetA());
                }
    }
}
