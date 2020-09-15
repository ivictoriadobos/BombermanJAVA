package PaooGame.Items.DynamicItems;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Time;

import PaooGame.Items.StaticItems.Bomb;
import PaooGame.RefLinks;
import PaooGame.Graphics.Assets;
import PaooGame.Score.Score;
import PaooGame.Tiles.Tile;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul
        dreptunghiul de coliziune
 */
public class Hero extends Character
{
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
   // protected Score score;
    ///Boolean ce retine intentia de a plasa o bomba (nu este intotdeauna posibil)
    private boolean tryPlaceBomb;
    ///Intreg ce retine numarul de bombe active plasate de erou
    private int noOfActiveBombs =0;

    ///Limita numarului posibil de bombe active
    private int limitNoOfBombs = 1;

    ///Boolean ce transmite daca eroul a murit sau nu
    private static boolean isDead;



    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */

    public Hero(RefLinks refLink, float x, float y)
    {
            ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);

            ///Seteaza imaginea de start a eroului
        image = Assets.heroLeft[0];
            ///Stabilieste dimensiunea dreptunghiului de coliziune
        bounds.x = 22;
        bounds.y = 32;
        bounds.width = 46;
        bounds.height = 46;
            ///Se seteaza booleanul isDead pe false
        isDead = false;

    }
    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.

     */

    @Override
    public void Update() {
        ///Verifica daca a fost apasata o tasta
        GetInput();

        /// Daca este posibil si cerut, se plaseaza o bomba
        TryPlaceBomb();
    //    System.out.println("No of active bombs: " + noOfActiveBombs);
        ///Actualizeaza pozitia
        Move();


        ///Actualizeaza imaginea
        Illustrate();

        ///Se centreaza camera asupra acestui item
        refLink.GetGame().GetGameCamera().centerOnEntity(this);
    }


/*!
\fn    private void Illustrate()
Metoda responsabila cu gestionarea imaginii curente a caracterului in functie de directia sa
 */
    private void Illustrate()
    {
        if (refLink.GetKeyManager().left) {
            image = Assets.nextLeftHero();
        }

        if (refLink.GetKeyManager().right) {
            image = Assets.nextRightHero();
        }
        if (refLink.GetKeyManager().up) {
            image = Assets.nextUpHero();
        }
        if (refLink.GetKeyManager().down) {
            image = Assets.nextDownHero();
        }

///Daca heroul nu se misca deloc, el trebuie sa revina la imaginea initiala a directiei respective (daca nu facem asta, el ramane blocat in subimaginea x a spritesheetului pe directia pe care se deplasa)
        if(refLink.GetKeyManager().noKeyPressed) {
            switch (refLink.GetKeyManager().lastKeyTyped) {
                case 65:
                    image = Assets.heroLeft[0];
                    break;
                case 68:
                    image = Assets.heroRight[0];
                    break;
                case 83:
                    image = Assets.heroDown[0];
                    break;
                case 87:
                    image = Assets.heroUp[0];
                    break;
            }
        }
    }


        /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */

    private void GetInput()
    {
            ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
            ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().up)  //game.GetKeyManager()
        {
            yMove = -speed;
        }
            ///Verificare apasare tasta "jos"
        if(refLink.GetKeyManager().down)
        {
            yMove = speed;
        }
            ///Verificare apasare tasta "left"
        if(refLink.GetKeyManager().left)
        {
            xMove = -speed;
        }
            ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right)
        {
            xMove = speed;
        }

        ///Verificare apasare tasta "space"
        if (refLink.GetKeyManager().space)
        {
            tryPlaceBomb = true;
            refLink.GetKeyManager().space = false;

        }
            else return;
    }
/*!
\fn    private void TryPlaceBomb()
Metoda ce gestioneaza incercarea jucatorului de a plasa o bomba.
 */

    private void TryPlaceBomb()
    {
     //   System.out.println("No of active bombs " + noOfActiveBombs + " Limit of no of bombs " + limitNoOfBombs );
        ///Daca se incearca plasarea unei bombe (Apasarea tastei space)  si numarul de bombe active e mai mic decat limita numarului de bombe se trece la urmatoarea verificare
        if (tryPlaceBomb && noOfActiveBombs < limitNoOfBombs)
        {
            tryPlaceBomb = false;

            //cream noua bomba si verificam sa nu fie o bomba deja plasata pe locul acesta
            Bomb tempBomb = new Bomb(refLink, this.x, this.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            ///Bomba nu trebuie sa se plaseze direct peste alte itemuri din joc
            if(tempBomb.checkItemCollision(0,0))
                return;
            else {
          //      System.out.println("Hero.TryPlaceBomb");;

                noOfActiveBombs++;
                refLink.GetMap().getItemManager().AddItem(tempBomb);
            }
        }

    }

        /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul graficin care trebuie efectuata desenarea eroului.

       In aceasta metoda se deseneaza si scorul actual in partea de stanga sus.
     */

    @Override
    public void Draw(Graphics g)
    {

        g.drawImage(image, (int)(x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y - refLink.GetGame().GetGameCamera().getyOffset()), width, height, null);
        Score.GetScoreInstance().Draw(g);
        g.copyArea(0,0,199,199,2,2);
        System.out.println(this.x + "," + this.y);
            ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        //g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y + bounds.y - refLink.GetGame().GetGameCamera().getyOffset()), bounds.width, bounds.height);
    }

    /*!
    \fn    public boolean RemoveItem()
    Metoda suprascrisa menita elimine eroul din itemurile jocului.
    Altereaza scorul cu -200.
     */
    @Override
    public boolean RemoveItem() {
        Score.GetScoreInstance().alterScore(-200);
        refLink.GetMap().getItemManager().SetHero(null);
        System.out.println("You lose!");
        isDead = true;
        return true;
    }

    public static boolean isDead()
    {
        return isDead;
    }

    public int getNoOfActiveBombs() {
        return noOfActiveBombs;
    }

    public void alterNoOfActiveBombs(int noOfActiveBombs) {
        this.noOfActiveBombs += noOfActiveBombs;
    }

    public void HeroDied() //called when hero calls RemoveItem or when Time expires
    {

    }

}
