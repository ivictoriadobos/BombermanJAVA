package PaooGame.Items.StaticItems;

import PaooGame.Graphics.Assets;
import PaooGame.Items.DestroyManager;
import PaooGame.Items.DynamicItems.Hero;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!
\class Flame
\brief Clasa ce implementeaza notiunea de flaca a jocului
 */
public class Flame extends StaticItem {
    ///Imaginea curenta ce se randeaza (ea se schimba odata cu trecerea timpului)
    private BufferedImage currimage;

    ///Data la care se creeaza flama
    private final java.util.Date createdTime ;

    ///Variabila ce retina numarul de flacari pornind de la flama centrala (Exclusiv) pe fiecare directie. In curand vor exista DroppedItems ce pot incrementa aceasta valoare
    public static int powerOfFLame=1; //length from center to one extreme,

    /*!
    \fn    public Flame(RefLinks refLink, float x, float y, int width, int height, String father)
    Constructorul clasei ce apeleaza constructorul din superclasa
    A se observa ca obiectul nu este destructibil
    \param father "Tatal" flacarii (Enemy sau Hero)
     */
    public Flame(RefLinks refLink, float x, float y, int width, int height, String father) {
        super(refLink, x, y, width, height);
        ///Se adjusteaza dreptunghiul de coliziune
        this.bounds = new Rectangle(16,16,32,32);
        ///Se seteaza data crearii flacarii
        this.createdTime = new java.util.Date();
        ///Se seteaza "tata" flacarii
        this.destroyerOrFather = father;
    }

    /*!
    \fn    public double getIndexByTime()
    In functie de cat timp a trecut de la crearea flacarii pana in momentul actual se transmite un index care sa decida imaginea curenta a flacarii care se randeaza
     */
    public double getIndexByTime() {
        java.util.Date now = new java.util.Date();
        ///Valoarea 50 nu are nimic special, doar satisface viteza cu care se doreste a fi schimbata imaginea curenta a flamei
        return (int)((now.getTime() - this.createdTime.getTime()) / 50);
    }

    /*!
    \fn public void Update()
    Metoda suprascrisa ce are responsabilitatea de a schimba imaginea actuala randata in functie de cat timp a trecut de la crearea flacarii si,
    in caz de timpul a expirat pentru aceasta instanta , sa isi apeleze metoda RemoveItem
    Ea mai functioneaza si ca trigger pentru DestroyManager
     */
    @Override
    public void Update() {
      //  if (this.father.equals("Enemy"))
        //    System.out.println("&&&&&&&&&&&&&&&&&&&&  Flame from enemy");
        double index = getIndexByTime();
        if(index >= 8.001 ){  // time has expired for flame
            RemoveItem();
            DestroyManager.GetInstance().Notify();
                return;
        }

        DestroyManager.GetInstance().HandleItemRemoving(this);
        currimage = Assets.flame[(int)index];
    }

    /*!
    \fn public void Draw(Graphics g)
    Metoda suprascrisa ce are responsabilitatea de a randa imaginea curenta a flamei
     */
    @Override
    public void Draw(Graphics g) {

        g.drawImage(currimage, (int)(x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y - refLink.GetGame().GetGameCamera().getyOffset()), width, height, null);
       // g.setColor(Color.ORANGE);
        //g.fillRect((int)(x + bounds.x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y + bounds.y - refLink.GetGame().GetGameCamera().getyOffset()), bounds.width, bounds.height);

    }


/*!  \fn  public boolean RemoveItem()
Metoda suprascrisa ce are rolul de a elimina obiectul curent din lista de items a jocului si atat.
 */
    @Override
    public boolean RemoveItem() {

        //System.out.println("\n\nFlame.RemoveItem");
    //    System.out.println("Flame.RemoveItem");
      //  System.out.println("Is this item removed ? " + refLink.GetMap().getItemManager().GetItems().remove(this));

        return refLink.GetMap().getItemManager().GetItems().remove(this);
    }


}



