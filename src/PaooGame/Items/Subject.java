package PaooGame.Items;

import PaooGame.Items.StaticItems.DroppedItems.DroppedFactory;
import PaooGame.Items.StaticItems.StaticItem;
import PaooGame.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;
/*!
\class Subject
\brief Aceasta Clasa implementeaza notiunea de Subject a sablonului Observer

\warning Sablonul nu este implementat corect (incomplet de fapt). Sunt cateva metode pe care nu are sens sa le tin momentan, dar nu le sterg deoarece voi dezvolta jocul in continuare

Aceasta clasa este responsabila de crearea itemurilor care cad pe jos dupa ce un obiect este distrus (DroppedItem)
 */
public abstract class Subject {

    /// Propriul manager de items
    protected ItemManager itemManager;
    ///Lista de views. Practic nu o folosesc (inca :) )
    private List<View> views = new ArrayList<>();

    ///Lista itemurilor ce au fost distruse in urma flameurilor
    private List<Item> itemList = new ArrayList<>();


    ///\fn     public void AddView(View view)
    ///Metoda specifica sablonului Observer. Nefolosita Momentan
    public void AddView(View view){
        views.add(view);
    }

    ///\fn     public void ReleaseView(View view)
    ///Metoda specifica sablonului Observer. Nefolosita Momentan
    public void ReleaseView(View view)
    {
        views.remove(view);
    }

    ///\fn    public void UpdateData( Item data)
    ///Metoda ce adauga listei de obiecte ce au fost sterse si obiectul trimis ca parametru
    ///\param data Item ce a fost distrus in joc
    public void UpdateData( Item data)
    {
        itemList.add(data);
    }


    /*!
    \fn    private void CreateViews()
        Metoda ce creeaza obiecte DroppedItems prin intermediul unui Factory
     */
    private void CreateViews()
    {
        for (Item i : itemList)
        {
            StaticItem newItem = DroppedFactory.getDroppedItem(i.refLink, i.GetX(), i.GetY(), Tile.TILE_WIDTH, Tile.TILE_HEIGHT); ///Usage of factory pattern
            if(newItem != null)
            {
                if (i instanceof StaticItem)
                    newItem.setDestroyerOrFather(i.getDestroyerOrFather());
                itemManager.GetItems().add(newItem);
            }


        }

    }

/*!
\fn     public void Notify()
Metoda ce creeaza(sau nu, exista o probabilitate la mijloc) views (dropped items) pentru fiecare element distrus din joc, dupa care efectueaza lucrari de intretinere a listelor (resetarea lor)
 */
    public void Notify()
    {
        CreateViews();
      /*  for ( View view : views)
        {
            view.UpdateViews();
        }

       */
        ResetLists();

    }
/*!
\fn    private void ResetLists()
Metoda reseteaza listele astfel incat fiecare apel va trata strict obiectele distruse in frameul curent al jocului, nu si cele anterioare
 */
    private void ResetLists()
    {
        itemList.clear();
        views.clear();
    }



    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

}
