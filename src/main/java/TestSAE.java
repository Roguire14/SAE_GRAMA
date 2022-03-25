import java.util.ArrayList;

public class TestSAE {
    public static void main(String args[]){

        Sommets ambe = new Sommets('V',"Ambérieu-en-Bugey");
        Sommets bourg = new Sommets('V',"Bourg-en-Bresse");
        Sommets vaux = new Sommets('V',"Vaux-en-Bugey");
        Sommets argis = new Sommets('V',"Argis");
        Sommets montluel = new Sommets('V',"Montluel");
        Sommets villars = new Sommets('V',"Villars-les-Dombes");

        Sommets bcube = new Sommets('L',"BCube Bowling");
        Sommets lagnieu = new Sommets('V',"Lagnieu");


        Aretes d_ambe_villars = new Aretes('D',31,ambe,villars);
        Aretes d_ambe_bourg = new Aretes('D',32,ambe,bourg);
        Aretes a_ambe_bourg = new Aretes('A',37,ambe,bourg);
        Aretes d_ambe_vaux = new Aretes('D',5,ambe,vaux);
        Aretes d_ambe_montluel = new Aretes('A',35,ambe,montluel);
        Aretes d_ambe_argis = new Aretes('D',17,ambe,argis);

        Aretes d_vaux_bcube = new Aretes('D',1,vaux,bcube);
        Aretes d_vaux_lagnieu = new Aretes('D',3,vaux,lagnieu);
        Aretes d_langieu_bcube = new Aretes('D',3,lagnieu,bcube);


        ArrayList<Sommets> ls_sommet = new ArrayList<Sommets>();
        ls_sommet.add(ambe);
        ls_sommet.add(bourg);
        ls_sommet.add(vaux);
        ls_sommet.add(argis);
        ls_sommet.add(montluel);
        ls_sommet.add(villars);
        ls_sommet.add(bcube);
        ls_sommet.add(lagnieu);

        ArrayList<Aretes> ls_aretes = new ArrayList<>();
        ls_aretes.add(d_ambe_bourg);
        ls_aretes.add(d_ambe_argis);
        ls_aretes.add(d_ambe_montluel);
        ls_aretes.add(d_ambe_vaux);
        ls_aretes.add(d_ambe_montluel);
        ls_aretes.add(a_ambe_bourg);
        ls_aretes.add(d_langieu_bcube);
        ls_aretes.add(d_vaux_bcube);
        ls_aretes.add(d_vaux_lagnieu);
        Graphe graphe = new Graphe(ls_sommet,ls_aretes);

        graphe.getVoisins(vaux);

    }
}
