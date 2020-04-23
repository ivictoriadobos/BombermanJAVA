package PaooGame.Items;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

/*! \class public abstract class Character extends Item
    \brief Defineste notiunea abstracta de caracter/individ/fiinta din joc.

    Notiunea este definita doar de viata, viteza de deplasare si distanta cu care trebuie sa se
    miste/deplaseze in urma calculelor.
 */
public abstract class Character extends Item
{
    public static final int DEFAULT_LIFE            = 10;   /*!< Valoarea implicita a vietii unui caracter.*/
    public static final float DEFAULT_SPEED         = 2.0f; /*!< Viteza implicita a unu caracter.*/
    public static final int DEFAULT_CREATURE_WIDTH  = 56;   /*!< Latimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_CREATURE_HEIGHT = 56;   /*!< Inaltimea implicita a imaginii caracterului.*/

    protected int life;     /*!< Retine viata caracterului.*/
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
        life    = DEFAULT_LIFE;
        speed   = DEFAULT_SPEED;
        xMove   = 0;
        yMove   = 0;
    }

    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */
    public void Move()
    {
            ///Modifica pozitia caracterului pe axa X.
            ///Modifica pozitia caracterului pe axa Y.
        MoveX();  //x+=xMove
        MoveY(); // analog
    }

    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */
    public void MoveX() {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa X.
        if (xMove > 0) {
            //moving right
            int tx = (int)(x+xMove + bounds.x+bounds.width)/ Tile.TILE_WIDTH;
            if(!collisionWithTile(tx, (int) (y+bounds.y)/Tile.TILE_HEIGHT) && !collisionWithTile(tx, (int)(y+bounds.y + bounds.height)/Tile.TILE_HEIGHT))
                x+=xMove;
        }
        else if (xMove <0) {
            int tx = (int)(x+xMove + bounds.x)/ Tile.TILE_WIDTH;
            if(!collisionWithTile(tx, (int) (y+bounds.y)/Tile.TILE_HEIGHT) && !collisionWithTile(tx, (int)(y+bounds.y + bounds.height)/Tile.TILE_HEIGHT))
                 x += xMove;
        }
    }

    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */
    public void MoveY()
    {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa Y.

        if(yMove > 0) //in jos
        {
            int ty = (int)(y+yMove + bounds.y+bounds.height)/ Tile.TILE_HEIGHT;
            if(!collisionWithTile((int)(x+bounds.x)/Tile.TILE_WIDTH, ty) && !collisionWithTile((int)(x + bounds.x + bounds.width)/Tile.TILE_WIDTH, ty))
                y+=yMove;
        }
        else if (yMove < 0) { //in sus
            int ty = (int)(y+yMove + bounds.y)/ Tile.TILE_HEIGHT;
            if(!collisionWithTile((int)(x+bounds.x)/Tile.TILE_WIDTH, ty) && !collisionWithTile((int)(x + bounds.x + bounds.width)/Tile.TILE_WIDTH, ty))
                y+=yMove;
        }
    }

    protected  boolean collisionWithTile(int x, int y)
    {
        return refLink.GetMap().GetTile(x,y).IsSolid();
    }

    /*! \fn public int GetLife()
        \brief Returneaza viata caracterului.
     */
    public int GetLife()
    {
        return life;
    }

    /*! \fn public int GetSpeed()
        \brief Returneaza viteza caracterului.
     */
    public float GetSpeed()
    {
        return speed;
    }

    /*! \fn public void SetLife(int life)
        \brief Seteaza viata caracterului.
     */
    public void SetLife(int life)
    {
        this.life = life;
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

