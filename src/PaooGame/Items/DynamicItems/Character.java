package PaooGame.Items.DynamicItems;

import PaooGame.Items.Item;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

/*! \class public abstract class Character extends Item
    \brief Defineste notiunea abstracta de caracter/individ/fiinta din joc.

    Notiunea este definita doar de viata, viteza de deplasare si distanta cu care trebuie sa se
    miste/deplaseze in urma calculelor.
 */
public abstract class Character extends Item
{
    public static final float DEFAULT_SPEED         = 3.0f; /*!< Viteza implicita a unu caracter.*/
    public static final int DEFAULT_CREATURE_WIDTH  = 88;   /*!< Latimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_CREATURE_HEIGHT = 88;   /*!< Inaltimea implicita a imaginii caracterului.*/

    protected float speed;  /*!< Retine viteza de deplasare caracterului.*/
    protected float xMove;  /*!< Retine noua pozitie a caracterului pe axa X.*/
    protected float yMove;  /*!< Retine noua pozitie a caracterului pe axa Y.*/


    /*! \fn public Character(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei Character

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pa axa X a caracterului.
        \param y Pozitia de start pa axa Y a caracterului.
        \param width Latimea imaginii caracterului.
        \param height Inaltimea imaginii caracterului.
     */

    public Character(RefLinks refLink, float x, float y, int width, int height)
    {
            ///Apel constructor la clasei de baza
        super(refLink, x,y, width, height);
            //Seteaza pe valorile implicite pentru viata, viteza si distantele de deplasare
        speed   = DEFAULT_SPEED;
        xMove   = 0;
        yMove   = 0;
    }
    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */

    public boolean Move() {
        ///Modifica pozitia caracterului pe axa X.
        ///Modifica pozitia caracterului pe axa Y.

        /*
        Baically we have not actually changed our X or Y position yet, so we have the offset telling the colliision box
        where we will be moving to
         */

        ///Utilizarea acestui boolean ret foloseste la depistarea situatiei cand inamicul nu se mai misca din cauza elementelor solide din jur.

        boolean ret;
        if (!checkItemCollision(xMove, 0)) {
         //   if(this instanceof Enemy)
       //     System.out.println("No item collision detected on x axis");
            ///ENEMY DOCUMENTATION:
            ///Daca nu au loc coliziuni cu items pe abscisa, verifica dalele
            ret = MoveX();  //x+=xMove
        }
        ///ENEMY DOCUMENTATION:
        ///Au avut loc coliziuni pe abscisa, prin urmare returnam false (chiar daca pe ordonata ne putem misca) si schimbam directa inamicului. Analog ordonata.
        else ret = false;
        if (!checkItemCollision(0, yMove)) {
           // if(this instanceof Enemy)
       //     System.out.println("No item collision detected on y axis");
            ret = ret && MoveY(); // analog
        }
        else ret = false;
        return ret;
//
    }

    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */


    public boolean MoveX() {   //grija de tileuri solide
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa X.
        boolean success = false; // daca se misca enemyul pe OX va returna daca se poate face asta sau nu in ceea ce priveste tileurile, nu itemele
        if (xMove!=0)
        {
            if (xMove > 0)
            {
                //moving right
                int tx = (int)(x+xMove + bounds.x+bounds.width)/ Tile.TILE_WIDTH;
                if(!collisionWithTile(tx, (int) (y+bounds.y)/Tile.TILE_HEIGHT) && !collisionWithTile(tx, (int)(y+bounds.y + bounds.height)/Tile.TILE_HEIGHT))
                {
                    x += xMove;
                    success = true;
                }
            }
            else if (xMove <0)
            {
                int tx = (int)(x+xMove + bounds.x)/ Tile.TILE_WIDTH;
                if(!collisionWithTile(tx, (int) (y+bounds.y)/Tile.TILE_HEIGHT) && !collisionWithTile(tx, (int)(y+bounds.y + bounds.height)/Tile.TILE_HEIGHT))
                {
                     x += xMove;
                     success = true;

                }
            }
        }
        else success = true;
        return success;


    }
    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */

    public boolean MoveY() {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa Y.
        boolean success = false; // daca se misca enemyul pe OY va returna daca se poate face asta sau nu in ceea ce priveste tileurile, nu itemele
        if (yMove != 0)
        {
            if (yMove > 0) //in jos
            {
                int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
                if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
                {
                    y += yMove;
                    success = true;
                }
            } else if (yMove < 0) { //in sus
                int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;
                if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
                {
                    y += yMove;
                    success = true;
                }
            }
        }
        else success = true;
        return success;
    }
    protected  boolean collisionWithTile(int x, int y)
    {
        return refLink.GetMap().GetTile(x,y).IsSolid();
    }

    /*! \fn public int GetSpeed()
        \brief Returneaza viteza caracterului.
     */

    public float GetSpeed()
    {
        return speed;
    }


    /*! \fn public void SetSpeed(float speed)
        \brief
     */

    public void SetSpeed(float speed) {
        this.speed = speed;
    }
    /*! \fn public float GetXMove()
        \brief Returneaza distanta in pixeli pe axa X cu care este actualizata pozitia caracterului.
     */


    public float GetXMove()
    {
        return xMove;
    }

    /*! \fn public float GetYMove()
        \brief Returneaza distanta in pixeli pe axa Y cu care este actualizata pozitia caracterului.
     */
    public float GetYMove()
    {
        return yMove;
    }

    /*! \fn public void SetXMove(float xMove)
        \brief Seteaza distanta in pixeli pe axa X cu care va fi actualizata pozitia caracterului.
     */
    public void SetXMove(float xMove)
    {
        this.xMove = xMove;
    }

    /*! \fn public void SetYMove(float yMove)
        \brief Seteaza distanta in pixeli pe axa Y cu care va fi actualizata pozitia caracterului.
     */
    public void SetYMove(float yMove)
    {
        this.yMove = yMove;
    }
}


