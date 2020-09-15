package PaooGame.Items;

import PaooGame.Items.DynamicItems.Enemy;
import PaooGame.Items.DynamicItems.Hero;
import PaooGame.Items.StaticItems.Flame;

import java.util.ArrayList;
/*!
\class DestrouyManager
\brief Clasa responsabila cu eliminarea obiectelor de pe harta in urma unui atac cu flama fie din parte Hero sau Enemy

Clasa completeaza sablonul Observer, fiind Subject (incomplet implementat in versiunea actuala)
Clasa de asemenea foloseste si sablonul Singleton
 */

// SINGLETON PATTERN IMPLEMENTED
public class DestroyManager extends Subject {
    ///Referinta statica catre instanta propriei clase
    private static DestroyManager destroyManager = new DestroyManager();
    ///Pentru comoditate se retine si o referinta catre eroul jocului
    private Hero hero;
    ///\fn    private DestroyManager()
    ///Constructor privat vid
    private DestroyManager()
    {
    }

    ///\fn    public static DestroyManager GetInstance()
    /// Metoda publica statica pentru a accesa instanta clasei
    public static DestroyManager GetInstance()
    {
        return destroyManager;
    }

    ///\fn public void HandleItemRemoving(Flame flame)
    /// Metoda responsabila de eliminarea obiectelor ce intra in coliziune cu o instanta a Flame
    ///Este folosita in clasa Flame, mai exact in metoda Update atunci cand se decide ca timpul animatiei flacarii s-a scurs si e timpul ca ea sa dispara
    ///\param flame Pentru flacara ce apeleaza aceasta metoda sunt verificare coliziunile cu celelalte obiecte ale programului
    public void HandleItemRemoving(Flame flame)
    {
        ///Daca eroul nu a murit intre timp el se adauga listei de elemente ce vor fi analizate in vederea detectarii coliziunii acestora cu flacara ce apeleaza aceasta metoda
        if(hero == null)
        this.hero = itemManager.GetHero();

            ArrayList<Item> tempItems = (ArrayList<Item>) itemManager.GetItems().clone();
            tempItems.add(hero);
       //     System.out.println(tempItems.stream().filter(p -> p instanceof Flame).count());
            for (Item i : tempItems) {

                if (i instanceof Flame) {
                    continue;
                }
                ///Daca se gaseste un item ce trebuie distrus
                if (i.getCollisionBounds(0, 0).intersects(flame.getCollisionBounds(0, 0))) { // am gasit un item de distruuuus
                    ///I se seteaza variabila setDestroyerOrFather cu tatal flamei (acest lucru ne ajuta sa tinem evidenta unui scor)
                    i.setDestroyerOrFather(flame.getDestroyerOrFather());

                    ///Se apeleaza metoda publica RemoveItem suprascrisa de fiecare subclasa concreta a Item care
                    ///decide pentru fiecare obiect un comportament aparte (in principal valoarea cu care se altereaza scorul difera)
                    i.RemoveItem();  //distrug itemul (il scot din lista de itemuri de pe harta practic)

                    ///Se adauga itemul distrus in lista de items a subiectului sub care va trebui sa cada un DroppedItem (doar SpeedItem este disponibil momentan)
                    this.UpdateData(i);  // am gasit un item sub care va trebui pus un SpeedItem sau ce vreau eu pe viitor
                    ///Se iese din ciclu intrucat o flama nu poate distruge decat un obiect, chiar cel gasit
                    break;
                }
            }
            ///Daca s-au parcurs toate elementele jocului si nu s-a gasit niciun de distrus, se iese din functie fara vreun efect
    }


}

