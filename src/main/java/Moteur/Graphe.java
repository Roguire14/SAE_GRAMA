package Moteur;

import Interface.MainWindow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class Graphe {
    private final ArrayList<ArrayList<Aretes>> graphe;
    private int status=0;
    private MainWindow window;

    public Graphe(MainWindow window,File file){
        this.graphe = load(file.getName());
        this.window = window;
    }

    public int getStatus() {
        return status;
    }

    private Optional<String> getExtension(String filename){
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private ArrayList load(String filename){
        JSONParser parser = new JSONParser();
        ArrayList<ArrayList<Aretes>> stock = new ArrayList<>();
        Aretes aretes = null;

        try {
            if(getExtension(filename).get().equals("json")){
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));

                for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext(); ) {
                    String key = (String) iterator.next();
//                System.out.println(key);
                    String[] info = key.split(",");
                    String type_sommet = info[0];
                    String nom_sommet = info[1];
                    Sommets sommets = new Sommets(type_sommet, nom_sommet);
                    if (jsonObject.get(key) instanceof JSONObject) {
                        ArrayList<Aretes> list = new ArrayList<>();
                        JSONObject jsonObject1 = ((JSONObject) jsonObject.get(key));
                        for (Iterator iterator1 = jsonObject1.keySet().iterator(); iterator1.hasNext(); ) {
                            String key1 = (String) iterator1.next();
                            String[] info2 = key1.split(",");
                            String type_sommet2 = info2[0];
                            String nom_sommet2 = info2[1];
                            Sommets sommets1 = new Sommets(type_sommet2, nom_sommet2);
//                        System.out.println("   "+key1);
                            if (jsonObject1.get(key1) instanceof JSONArray) {
                                JSONArray jsonArray = ((JSONArray) jsonObject1.get(key1));
                                for (int i = 0; i < jsonArray.size(); i++) {
//                                System.out.println("      "+jsonArray.get(i));
                                    String[] info3 = jsonArray.get(i).toString().split(",");
                                    String type_arete = info3[0];
                                    int distance_arete = Integer.valueOf(info3[1]);
                                    aretes = new Aretes(type_arete, distance_arete, sommets, sommets1);
                                    list.add(aretes);
                                }
                            } else {
//                            System.out.println("      "+jsonObject1.get(key1));
                                String[] info3 = jsonObject1.get(key1).toString().split(",");
                                String type_arete = info3[0];
                                int distance_arete = Integer.valueOf(info3[1]);
                                aretes = new Aretes(type_arete, distance_arete, sommets, sommets1);
                                list.add(aretes);
                            }
                        }
                        stock.add(list);
                    }
                }
                status = 1;
            }

        } catch (FileNotFoundException e){
            System.out.println("Fichier pas trouvé");
//            e.printStackTrace();
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

    public List<String> infoSommet(String sommets){
        List<String> strings = new LinkedList();
        if(sommetExiste(sommets)) {
            System.out.println(sommets + " est lié avec:");
            strings.add(sommets + " est lié avec:\n");
            LinkedList<Aretes> linkedList = getVoisins(sommets);
            for (Aretes aretes: linkedList) {
                if (aretes.getSommetA().getName().equals(sommets)) {
                    Aretes found = aretes;
                    System.out.println("    " + found.getSommetB().getName() + " qui est "+getTypeFull(found.getSommetB().getType(),1)+" par " + getTypeFull(found.getTyparete(),1) + " de distance " + found.getDistance() + " km");
                    strings.add("    " + found.getSommetB().getName() + " qui est "+getTypeFull(found.getSommetB().getType(),1)+" par " + getTypeFull(found.getTyparete(),1) + " de distance " + found.getDistance() + " km\n");
                }
            }
        }else {
            System.out.println("Aucun sommet de ce nom n'existe");
            strings.add("Aucun sommet de ce nom n'existe");
        }return strings;
    }

    public void infoSommet(Sommets sommets){
        infoSommet(sommets.getName());
    }

    private boolean sommetExiste(String sommet){
        boolean existe = false;
        ArrayList<Aretes> list = null;
        for(int i = 0; !existe&&i < graphe.size();i++){
            list = graphe.get(i);
            for (Aretes aretes : list)
                if (aretes.getSommetA().getName().equals(sommet)) existe = true;
        }
        return existe;
    }

    private boolean sommetExiste(Sommets sommets){
        return sommetExiste(sommets.getName());
    }

    private String getTypeFull(String type,int opt){
        String result = null;
        switch(opt) {
            case 1:
                switch (type) {
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
                }
                break;
            case 2:
                switch (type) {
                    case "V":
                        result = "ville";
                        break;
                    case "L":
                        result = "centre de loisir";
                        break;
                    case "R":
                        result = "restaurant";
                        break;
                    case "A":
                        result = "autoroute";
                        break;
                    case "D":
                        result = "départementale";
                        break;
                    case "N":
                        result = "nationale";
                        break;
                }
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

    public LinkedList<String> getVoisins(String sommets, String type){
        List<Aretes> aretesList = getVoisins(sommets);
        LinkedList<String> res = new LinkedList<>();
        for(Aretes aretes: aretesList)
            if(aretes.getSommetB().getType().equals(type)) {
                res.add(aretes.getSommetB().getName());
                System.out.println(aretes.getSommetB().getName());
            }
        return res;
    }

    public void afficheElt(String type){
        switch(type){
            case "A":
            case "D":
            case "N":
                List<Aretes> aretesLinkedList = getTypeAretes(type);
                for(Aretes aretes: aretesLinkedList)
                    System.out.println(aretes);
                break;
            case "V":
            case "L":
            case "R":
                List<Sommets> sommetsLinkedList = getTypeVille(type);
                for(Sommets sommets: sommetsLinkedList)
                    System.out.println(sommets);
                break;
        }
    }

    private List<Sommets> getTypeVille(String type) {
        List<Sommets> linkedList = new LinkedList<>();
        List<Sommets> sommetTraite = new LinkedList<>();
        for (ArrayList<Aretes> list : graphe)
            for (Aretes aretes : list)
                if (aretes.getSommetA().getType().equals(type) && !sommetTraite.contains(aretes.getSommetA())){
                    linkedList.add(aretes.getSommetA());
                    sommetTraite.add(aretes.getSommetA());
                }
        return linkedList;
    }

    private List<Aretes> getTypeAretes(String type){
        List<Aretes> linkedList = new LinkedList<>();
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
        List<Sommets> sommetsTraites = new LinkedList<>();
        for(ArrayList<Aretes> list : graphe)
            for(Aretes aretes: list)
                if(!sommetsTraites.contains(aretes.getSommetA())){
                    sommetsTraites.add(aretes.getSommetA());
                    System.out.println(aretes.getSommetA());
                }
    }

    public void affNbType(String type){
        System.out.println("Il y a " +getNbType(type)+" "+getTypeFull(type,2));
    }

    private int getNbType(String type) {
        int compteur = 0;
        switch(type){
            case "V":
            case "R":
            case "L":
                compteur = countType(type,2);
                break;
            case "N":
            case "A":
            case "D":
                compteur = countType(type,1);
                break;
        }return compteur;
    }

    private int countType(String type,int opt) {
        int cpt=0;
        switch(opt) {
            case 1:
                for (ArrayList<Aretes> list : graphe)
                    for (Aretes aretes : list)
                        if (aretes.getTyparete().equals(type))
                            cpt++;
                cpt=cpt/2;
                break;
            case 2:
                List<Sommets> sommetsTraite = new LinkedList<>();
                for(ArrayList<Aretes> list: graphe)
                    for(Aretes aretes:list)
                        if(!sommetsTraite.contains(aretes.getSommetA())&&aretes.getSommetA().getType().equals(type)) {
                            cpt++;
                            sommetsTraite.add(aretes.getSommetA());
                        }
                break;
        }
        return cpt;

    }

    public void infoAretes(Aretes aretes){
        for(ArrayList<Aretes> list: graphe)
            for(Aretes aretes1: list){
                if(aretes1.getSommetA().getName().equals(aretes.getSommetA().getName())&&aretes1.getSommetB().getName().equals(aretes.getSommetB().getName())) {
                    System.out.println(aretes1);
                }
            }
    }
}
