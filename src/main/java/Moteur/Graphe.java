package Moteur;

import Interface.MainWindow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.sound.midi.SysexMessage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Graphe {
    private ArrayList<ArrayList<Aretes>> graphe;
    private static int status;
    private MainWindow window;
    private String fileName;

    public Graphe(MainWindow window,File file){
        status = 0;
        fileName = file.getName();
        this.graphe = load(file.getName());
        this.window = window;
    }

    public String getFileName() {return fileName;}

    public int getStatus() {
        return status;
    }

    public Optional<String> getExtension(String filename){
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private ArrayList load(String filename){
        ArrayList<ArrayList<Aretes>> stock = new ArrayList<>();
        Aretes aretes;

        try {
            if(getExtension(filename).get().equals("json")){
                JSONParser parser = new JSONParser();
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
            }else{
                File file = new File(filename);
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                String line = "";
                String[] tempArr;
                while((line = br.readLine())!=null){
                    ArrayList<Aretes> list = new ArrayList<>();
                    tempArr = line.split(":",2);
                    String type = tempArr[0].split(",")[0];
                    String name = tempArr[0].split(",")[1];
                    Sommets sommetA = new Sommets(type,name);
                    String[] sommetsB= tempArr[1].split(";");
                    for(String sommetB: sommetsB){
                        String[] infoSommet= sommetB.split("::");
                        String typeArr = infoSommet[0].split(",")[0];
                        int distArr = Integer.valueOf(infoSommet[0].split(",")[1]);
                        String typeSommetB = infoSommet[1].split(",")[0];
                        String nomSommetB = infoSommet[1].split(",")[1];
                        Sommets VsommetB = new Sommets(typeSommetB,nomSommetB);
                        Aretes aretes1 = new Aretes(typeArr,distArr,sommetA,VsommetB);
                        list.add(aretes1);
                    }
                    stock.add(list);
                }status = 1;
            }

        } catch (FileNotFoundException e){
            System.out.println("Fichier pas trouv??");
            return stock;
        } catch (IOException e) {
            return stock;
        } catch (ParseException e) {
            return stock;
        }catch (ArrayIndexOutOfBoundsException e){
            return stock;
        }return stock;
    }

    public void JSONintoCSV(String name){
        List<Sommets> sommetsTraite = new ArrayList<Sommets>();
        try {
            if(!name.endsWith(".csv")) name = name+".csv";
            PrintWriter writer = new PrintWriter(name);
            for (ArrayList<Aretes> list : graphe) {
                for (Aretes aretes : list) {
                    if (!sommetsTraite.contains(aretes.getSommetA())) {
                        LinkedList<Aretes> linkedList = getAretesOfSommet(aretes.getSommetA());
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

    public void clearEverything(){
        graphe.clear();
    }



    // /(~~~~~*****~~~~~)\
    // --- Utilitaires ---
    // \(~~~~~*****~~~~~)/

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
        StringBuffer result = new StringBuffer();
        if(opt == 1) {
            switch (type) {
                case "L":
                case "R":
                    result.append("un ");
                    break;
                default:
                    result.append("une ");
            }
        }
        switch(type) {
            case "V":
                result.append("ville.s");
                break;
            case "L":
                result.append("centre.s de loisir");
                break;
            case "R":
                result.append("restaurant.s");
                break;
            case "A":
                result.append("autoroute.s");
                break;
            case "D":
                result.append("d??partementale.s");
                break;
            case "N":
                result.append("nationale.s");
                break;
        }
        return result.toString();
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

    public boolean isDifferent(Sommets sA, Sommets sB){
        return !sA.getName().equals(sB.getName());
    }

    public Sommets getSommet(String name){
        for(ArrayList<Aretes> list: graphe)
            for(Aretes aretes: list)
                if(aretes.getSommetA().getName().equals(name)) return aretes.getSommetA();
        return null;
    }

    // /(~~~~~****~~~~~)\
    // --- O-distance ---
    // \(~~~~~****~~~~~)/

    public ArrayList<Sommets> getAllSommet(){
        ArrayList<Sommets> resultat = new ArrayList<>();
        for(ArrayList<Aretes> list : graphe)
            for(Aretes arete : list)
                if (!resultat.contains(arete.getSommetA()))
                    resultat.add(arete.getSommetA());
        return resultat;
    }
    
    public ArrayList<Aretes> getAllArete()
    {
        int count = 0; //DEBUG
        ArrayList<Aretes> resultat = new ArrayList<>();
        Aretes current;
        for(ArrayList<Aretes> list : graphe)
            for(Aretes arete : list)
            {
                current = arete;
                System.out.println(current.toString()); //DEBUG
                if(!(current.getSommetA() == arete.getSommetB() && current.getSommetB() == arete.getSommetA()) && !resultat.contains(current))
                {
                    count++; //DEBUG
                    resultat.add(current);
                }
            }
        System.out.println(count); //DEBUG
        return resultat;
    }

    public String affNbType(String type){
        return "Il y a " +getNbType(type)+" "+getTypeFull(type,2);
    }

    public List<Object> afficheElt(String type) {
        List<Object> objectList = null;
        switch (type) {
            case "A":
            case "D":
            case "N":
                objectList = getAretesOfType(type);
                break;
            case "V":
            case "L":
            case "R":
                objectList = getSommetsOfType(type);
                break;
        }
        return objectList;
    }

    private List<Object> getSommetsOfType(String type) {
        List<Object> linkedList = new LinkedList<>();
        List<Sommets> sommetTraite = new LinkedList<>();
        for (ArrayList<Aretes> list : graphe)
            for (Aretes aretes : list)
                if (aretes.getSommetA().getType().equals(type) && !sommetTraite.contains(aretes.getSommetA())){
                    linkedList.add(aretes.getSommetA());
                    sommetTraite.add(aretes.getSommetA());
                }
        return linkedList;
    }

    private List<Object> getAretesOfType(String type){
        List<Object> linkedList = new LinkedList<>();
        List<String> sommetsTraite = new LinkedList<>();
        for(ArrayList<Aretes> list: graphe) {
            for (Aretes aretes : list) {
                if (aretes.getTyparete().equals(type) && !sommetsTraite.contains(aretes.getSommetB().getName())) {
                    linkedList.add(aretes);
                    sommetsTraite.add(aretes.getSommetA().getName());
                }
            }
        }
        return linkedList;
    }

    public int getNbType(String type) {
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

    // /(~~~~~****~~~~~)\
    // --- 1-distance ---
    // \(~~~~~****~~~~~)/
    
    public List<String> voisinSommet(String sommets){
        List<String> strings = new LinkedList();
        if(sommetExiste(sommets)) {
//            System.out.println(sommets + " est li?? avec:");
            LinkedList<Aretes> linkedList = getAretesOfSommet(sommets);
            for (Aretes aretes: linkedList) {
                if (!strings.contains(aretes.getSommetB().getName())&&aretes.getSommetA().getName().equals(sommets)) {
                    Aretes found = aretes;
//                    System.out.println("    " + found.getSommetB().getName() + " qui est "+getTypeFull(found.getSommetB().getType(),1)+" par " + getTypeFull(found.getTyparete(),1) + " de distance " + found.getDistance() + " km");
                    strings.add(found.getSommetB().getName());
                }
            }
        }else {
            System.out.println("Aucun sommet de ce nom n'existe");
            strings = null;
        }return strings;
    }

    public List<Sommets> SvoisinSommet(String sommets){
        List<Sommets> strings = new LinkedList();
        if(sommetExiste(sommets)) {
            LinkedList<Aretes> linkedList = getAretesOfSommet(sommets);
            for (Aretes aretes: linkedList) {
                if (!strings.contains(aretes.getSommetB().getName())&&aretes.getSommetA().getName().equals(sommets)) {
                    Aretes found = aretes;
                    strings.add(found.getSommetB());
                }
            }
        }else {
            System.out.println("Aucun sommet de ce nom n'existe");
            strings = null;
        }return strings;
    }

    public List<String> voisinSommet(Sommets sommets){
        return voisinSommet(sommets.getName());
    }

    private LinkedList<Aretes> getAretesOfSommet(String sommets){
        LinkedList<Aretes> aretesList = new LinkedList<>();
        for(ArrayList<Aretes> list: graphe)
            for(Aretes aretes : list)
                if(aretes.getSommetA().getName().equals(sommets))
                    aretesList.add(aretes);
        return aretesList;
    }

    private LinkedList<Aretes> getAretesOfSommet(Sommets sommets){
        return getAretesOfSommet(sommets.getName());
    }

    public LinkedList<String> getAretesOfSommet(String sommets, String type){
        List<Aretes> aretesList = getAretesOfSommet(sommets);
        LinkedList<String> res = new LinkedList<>();
        for(Aretes aretes: aretesList)
            if(aretes.getSommetB().getType().equals(type)) {
                res.add(aretes.getSommetB().getName());
            }
        return res;
    }

    /*public void infoAretes(Aretes aretes){
        for(ArrayList<Aretes> list: graphe)
            for(Aretes aretes1: list){
                if(aretes1.getSommetA().getName().equals(aretes.getSommetA().getName())&&aretes1.getSommetB().getName().equals(aretes.getSommetB().getName())) {
                    System.out.println(aretes1);
                }
            }
    }*/

    // /(~~~~~****~~~~~)\
    // --- 2-distance ---
    // \(~~~~~****~~~~~)/

    public boolean isTwoDistance(String sommetA, String sommetB){
        boolean verdict = false;
        for(ArrayList<Aretes> aretes: graphe)
            for(Aretes a: aretes){
                if(a.getSommetA().getName().equals(sommetA)) {
                    List<Aretes> voisin = getAretesOfSommet(a.getSommetB());
                    for(Aretes arete: voisin){
                        if(arete.getSommetB().getName().equals(sommetB)){
                            verdict = true;
                        }
                    }
                }
            }
        return verdict;
    }

    public boolean isMore(int type,Sommets sA, Sommets sB){
        boolean b = false;
        int compteurA = 0;
        int compteurB = 0;
        List<Sommets> sommetsA = SvoisinSommet(sA.getName());
        List<Sommets> sommetsB = SvoisinSommet(sB.getName());
        List<String> sommetTraiteA = new LinkedList<>();
        List<String> sommetTraiteB = new LinkedList<>();
        switch (type){
            case 1: // ouverte
                sommetTraiteA.add(sA.getName());
                for(Sommets s: sommetsA) {
                    List<Sommets> autresSommets = SvoisinSommet(s.getName());
                    for (Sommets s1 : autresSommets) {
                        if (s1.getType().equals("V") && !sommetsA.contains(s1) && !s1.getName().equals(sA.getName()) && !sommetTraiteA.contains(s1.getName())) {
                            compteurA++;
                            sommetTraiteA.add(s1.getName());
                        }
                    }
                }
                for(Sommets s: sommetsB) {
                    List<Sommets> autresSommets = SvoisinSommet(s.getName());
                    for(Sommets s1: autresSommets){
                        if (s1.getType().equals("V")&&!sommetsB.contains(s1)&&!s1.getName().equals(sB.getName())&&!sommetTraiteB.contains(s1.getName())){
                            compteurB++;
                            sommetTraiteB.add(s1.getName());
                        }
                    }
                }
                if(compteurA>compteurB) b = true;
                break;
            case 2: // gastronomie
                sommetTraiteA.add(sA.getName());
                for(Sommets s: sommetsA) {
                    List<Sommets> autresSommets = SvoisinSommet(s.getName());
                    for (Sommets s1 : autresSommets) {
                        if (s1.getType().equals("R") && !sommetsA.contains(s1) && !s1.getName().equals(sA.getName()) && !sommetTraiteA.contains(s1.getName())) {
                            compteurA++;
                            sommetTraiteA.add(s1.getName());
                        }
                    }
                }
                for(Sommets s: sommetsB) {
                    List<Sommets> autresSommets = SvoisinSommet(s.getName());
                    for(Sommets s1: autresSommets){
                        if (s1.getType().equals("R")&&!sommetsB.contains(s1)&&!s1.getName().equals(sB.getName())&&!sommetTraiteB.contains(s1.getName())){
                            compteurB++;
                            sommetTraiteB.add(s1.getName());
                        }
                    }
                }
                if(compteurA>compteurB) b = true;
                break;
            case 3: // culturelle
                sommetTraiteA.add(sA.getName());
                for(Sommets s: sommetsA) {
                    List<Sommets> autresSommets = SvoisinSommet(s.getName());
                    for (Sommets s1 : autresSommets) {
                        if (s1.getType().equals("L") && !sommetsA.contains(s1) && !s1.getName().equals(sA.getName()) && !sommetTraiteA.contains(s1.getName())) {
                            compteurA++;
                            sommetTraiteA.add(s1.getName());
                        }
                    }
                }
                for(Sommets s: sommetsB) {
                    List<Sommets> autresSommets = SvoisinSommet(s.getName());
                    for(Sommets s1: autresSommets){
                        if (s1.getType().equals("L")&&!sommetsB.contains(s1)&&!s1.getName().equals(sB.getName())&&!sommetTraiteB.contains(s1.getName())){
                            compteurB++;
                            sommetTraiteB.add(s1.getName());
                        }
                    }
                }
                if(compteurA>compteurB) b = true;
                break;
        }
        return b;
    }
}
