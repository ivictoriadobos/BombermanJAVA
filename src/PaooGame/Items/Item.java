package PaooGame.Items;

import PaooGame.Items.DynamicItems.Enemy;
import PaooGame.Items.DynamicItems.Hero;
import PaooGame.Items.StaticItems.Bomb;
import PaooGame.Items.StaticItems.DroppedItems.SpeedItem;
import PaooGame.Items.StaticItems.Flame;
import PaooGame.RefLinks;

import java.awt.*;

/*! \class Item
    \brief. Implementeaza notiunea abstracta de entitate activa din joc, "element cu care se poate interactiona: monstru, turn etc.".

    Coordonatele x si y sunt de tip float pentru a se elimina erorile de rotunjire ce pot sa apara in urma calculelor, urmand a se converti la intreg doar in momentul desenarii.
 */
public abstract class Item
{

    protected float x;                  /*!< Pozitia pe axa X a "tablei" de joc a imaginii entitatii.*/
    protected float y;                  /*!< Pozitia pe axa Y a "tablei" de joc a imaginii entitatii.*/
    protected int width;                /*!< Latimea imaginii entitatii.*/
    protected int height;               /*!< Inaltimea imaginii entitatii.*/
    protected Rectangle bounds;         /*!< Dreptunghiul curent de coliziune.*/
    //   protected Rectangle normalBounds;   /*!< Dreptunghiul de coliziune aferent starii obisnuite(spatiul ocupat de entitate in mod normal), poate fi mai mic sau mai mare decat dimensiunea imaginii sale.*/
//    protected Rectangle attackBounds;   /*!< Dreptunghiul de coliziune aferent starii de atac.*/
    protected RefLinks refLink;         /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/

    ///Boolean ce retine daca obiectul curent se poate distruge sau nu
    protected boolean destructible = false;

    ///String ce retine ori caracterul care a distrus acest obiect, ori tatal (de exemplu la Flame poate fi atat Hero cat si Enemy)
    protected String destroyerOrFather = null;


    /*! \fn public Item(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei

        \param  reflink Referinte "shortcut" catre alte referinte
        \param  x   Pozitia pe axa X a "tablei" de joc a imaginii entitatii.
        \param  y   Pozitia pe axa Y a "tablei" de joc a imaginii entitatii.
        \param  width   Latimea imaginii entitatii.
        \param  height  Inaltimea imaginii entitatii.
     */
    public Item(RefLinks refLink, float x, float y, int width, int height)
    {
        this.x = x;             /*!< Retine coordonata pe axa X.*/
        this.y = y;             /*!< Retine coordonata pe axa X.*/
        this.width = width;     /*!< Retine latimea imaginii.*/
        this.height = height;   /*!< Retine inaltimea imaginii.*/
        this.refLink = refLink; /*!< Retine the "shortcut".*/

        ///Dreptunghiul de coliziune este creat.
        bounds = new Rectangle(0, 0, width, height);
    }

        ///Metoda abstracta destinata actualizarii starii curente
    public abstract void Update();
    ///Metoda abstracta destinata desenarii starii curente
    public abstract void Draw(Graphics g);

    /*!
    \fn    public boolean checkItemCollision(float xoff, float yoff)
        Metoda ce se ocupa cu detectare coliziunilor intre itemurile jocului (Deci nu dale)

     */
    public boolean checkItemCollision(float xoff, float yoff)
    {

        for(Item i: refLink.GetMap().getItemManager().GetItems())
        {
            // daca se compara cu el insusi, treci peste
            if(i.equals(this))
                continue;

            /// Se detecteaza sau nu o posibila coliziune
            if(i.getCollisionBounds(0f,0f).intersects(getCollisionBounds(xoff,yoff)))
            {


            ///Daca este vorba de un SpeedItem, se returneaza false ( se doreste a pasi peste astfel de itemuri astfel incat sa poata fi culese)
                if (i instanceof SpeedItem && (this instanceof Enemy || this instanceof Hero))
                {
                    return false;
                }
                ///Daca se detecteaza o coliziune intre Hero si Bomb sau Flame, se returneaza false pentru ca Hero poate trece peste ambele (e greseala jucatorului daca o face)
                ///Enemy nu e tratat la fel fiindca se doreste ca el sa nu intre peste o flaca sau o bomba
                if(((i instanceof Bomb || i instanceof Flame ) && (this instanceof Hero || this instanceof Enemy)))
                    return false;

                return true;
            }
        }
        return false;
    }

    /*!
    \fn    public Rectangle getCollisionBounds(float xoff, float yoff)
        Metoda ce returneaza un dreptunghi de coliziune shiftat in functie de valorile parametrilor
     */
    public Rectangle getCollisionBounds(float xoff, float yoff)
    {

        return new Rectangle((int) (x+bounds.x + xoff), (int)(y+bounds.y + yoff), bounds.width, bounds.height);
    }

    /*!
    \fn    public abstract boolean RemoveItem()
    Metoda destinata suprascrierii in subclase, necesara polimorfismului
     */
    public abstract boolean RemoveItem();

    public boolean isDestructible() {
        return destructible;
    }

    /*! \fn public float GetX()
        \brief Returneaza coordonata pe axa X.
     */
    public float GetX()
    {
        return x;
    }

    /*! \fn public float GetY()
        \brief Returneaza coordonata pe axa Y.
     */
    public float GetY()
    {
        return y;
    }

    /*! \fn public float GetWidth()
        \brief Returneaza latimea entitatii.
     */
    public int GetWidth()
    {
        return width;
    }

    /*! \fn public float GetHeight()
        \brief Returneaza inaltimea entitatii.
     */
    public int GetHeight()
    {
        return height;
    }

    /*! \fn public float SetX()
        \brief Seteaza coordonata pe axa X.
     */
    public void SetX(float x)
    {
        this.x = x;
    }

    /*! \fn public float SetY()
        \brief Seteaza coordonata pe axa Y.
     */
    public void SetY(float y)
    {
        this.y = y;
    }

    /*! \fn public float SetWidth()
        \brief Seteaza latimea imaginii entitatii.
     */
    public void SetWidth(int width)
    {
        this.width = width;
    }

    /*! \fn public float SetHeight()
        \brief Seteaza inaltimea imaginii entitatii.
     */
    public void SetHeight(int height)
    {
        this.height = height;
    }
    public String getDestroyerOrFather() {
        return destroyerOrFather;
    }

    public void setDestroyerOrFather(String destroyerOrFather) {
        this.destroyerOrFather = destroyerOrFather;
    }


    /*! \fn public void SetAttackMode()
        \brief Seteaza modul de atac de interactiune
     */

}
