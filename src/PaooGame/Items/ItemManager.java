package PaooGame.Items;

import PaooGame.Items.DynamicItems.Enemy;
import PaooGame.Items.DynamicItems.Hero;
import PaooGame.Items.StaticItems.Bomb;
import PaooGame.Items.StaticItems.Flame;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.ArrayList;

/*!
\class ItemManager
\brief CLasa reprezinta un manager al itemurilor din joc; le gestioneaza actualizarea, randarea etc.

 */
public class ItemManager {

    ///Obiect shortcut catre referinte importante ale jocului
    private RefLinks reflinks;
    ///Referinta separata catre eroul jocului (Desi si el e un item)
    private Hero hero;

    ///Lista itemurilor din joc
    private ArrayList<Item> items;


/*!
\fn    public ItemManager(RefLinks refLinks, Hero hero)
Constructorul clasei ItemManager

 */
    public ItemManager(RefLinks refLinks, Hero hero)
    {
        ///Se asigneaza parametrii atributului shortcut si hero
        this.reflinks = refLinks;
        this.hero = hero;
        ///Se initializeaza lista de items
        this.items = new ArrayList<Item>();
    }

    /*!
    \fn    public void Update()
    Metoda ce apeleaza metoda Update a fiecarui item din joc, lasand eroul la final

     */
    public void Update()
    {

        for(int i=0;i<items.size();i++) { //nu i place ca in draw
           Item item =items.get(i);
           item.Update();
        }
        ///Deoarece eroul poate fi distrus pe parcusul jocului verificam existenta sa inainte de a ii apela metoda de actualizare
        ///Pentru itemurile din lista nu trebuie sa facem acest lucru deoarece cand un element din joc se distruge el isi face automat remove din aceasta lista

        if (hero != null)
            hero.Update();
    }

    /*!
\fn    public void Draw()
Metoda ce apeleaza metoda Draw a fiecarui item din joc, lasand eroul la final

 */
    public void Draw(Graphics g)
    {
        for(Item i: items)
            i.Draw(g);
        ///Deoarece eroul poate fi distrus pe parcusul jocului verificam existenta sa inainte de a ii apela metoda de desenare
        ///Pentru itemurile din lista nu trebuie sa facem acest lucru deoarece cand un element din joc se distruge el isi face automat remove din aceasta lista
        if (hero!= null)
        hero.Draw(g);

    }

    /*!
    \fn    public boolean itemsOverlapping(float x, float y)
        Metoda ce verifica daca coordonatele transmise ca parametru intersecteaza un obiect deja existent pe tabla de joc
        Metoda este apelata cu gandul ca acele coordonate sunt ale unei flacari
     */
    public boolean itemsOverlapping(float x, float y)
    {
        for (Item i : items)
        {
            if(  i instanceof Enemy || i instanceof Flame) // daca flama se duce peste Enemy
                continue;

            if(i.GetX() == x && i.GetY() == y) {
               // System.out.println("Item of " + i.getClass().getSimpleName() + " overlapped by flame");
                return true;
            }

        }
        return false;
    }
/*
    public boolean removeUnderThisFlame(Item item) // va fi apelata in Flame - Update pentru fiecare patratica ce contine un flame
            // e apelata doar de o instanta a flameului
    {
        // daca lucrez direct pe lista din clasa de itemuri imi da eroare in momentul in care sterg din ea, dar eu tot vreau s o parcurg in for
        ArrayList<Item> tempItems = (ArrayList<Item>) items.clone();

        for (Item i : tempItems) {

            if(i instanceof Flame ) {
              //  System.out.println("########################### is aici flame peste flame");
                continue;
            }

            if (i.getCollisionBounds(0,0).intersects(item.getCollisionBounds(0,0)) ) {

                items.remove(i);
                return !(i instanceof Bomb); // daca bomba a fost eliminata de un flame nu trebuie sa returnam true fiindca se va pune ca punct
                                            // functia aceasta daca returneaza true se pune pct pt jucator, altfel nu
            }
        }


        return false;
        // e facut boolean ca atunci cand intr-adevar sterg ceva sa pot verifica si sa adun puncte

    }


 */

    public RefLinks GetReflinks() {
        return reflinks;
    }

    public Hero GetHero() {
        return hero;
    }

    public ArrayList<Item> GetItems() {
        return items;
    }

    public void SetReflinks(RefLinks reflinks) {
        this.reflinks = reflinks;
    }

    public void SetHero(Hero hero) {
        this.hero = hero;
    }

    public void SetEntities(ArrayList<Item> entities) {
        this.items= entities;
    }

    public void AddItem(Item i)
    {
        items.add(i);
    }

/*
    public ArrayList<Pair> getExistingBombs() {
        return existingBombs;
    }

   */


}
