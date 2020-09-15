package PaooGame.Items.DynamicItems;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Item;
import PaooGame.Items.ItemManager;
import PaooGame.Items.StaticItems.DroppedItems.SpeedItem;
import PaooGame.Items.StaticItems.Flame;
import PaooGame.RefLinks;
import PaooGame.Score.Score;
import PaooGame.States.PlayState;
import PaooGame.Tiles.Tile;
import PaooGame.Timer.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.management.PlatformLoggingMXBean;
import java.util.List;
import java.util.Random;
/*!
\class Enemy
\brief Clasa Enemy implementeaza notiunea de inamic al jocului
 */
public class Enemy extends Character {

    private BufferedImage image;    /*!< Referinta catre imaginea curenta a inamicului.*/
    private ItemManager itemManager;  /*!< Referinta catre managerul de itemuri din joc.*/
    private boolean up = false, down = false, left = false, right = false;  /*!< Booleni care retin directia in care vrem sa plasam flama.*/
    private int direction ; /*!< 0 - stanga, 1 - sus, 2 - dreapta, 3 - jos */
    ///Obiect de tip Random folosit la stabilirea directiei de mers
    private Random random = new Random();
    private Timer timer;     /*!< Attack timer.*/
    ///Boolean ce transmite informatii despre starea inamicului (Dead sau alive)
    private static boolean isDead;


    /*! \fn     public Enemy(RefLinks refLink, float x, float y, ItemManager itemManager)
        \brief Constructorul de initializare al clasei Hero.
        \param itemManager Referinta catre managerul de items al jocului (memorat aici pentru comoditate, desi era accesibil si prin obiectul shortcut)
        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */


    public Enemy(RefLinks refLink, float x, float y, ItemManager itemManager) {
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
        ///Se seteaza o viteza mai mare decat cea standard
        this.SetSpeed(DEFAULT_SPEED + DEFAULT_SPEED / 2);
        image = Assets.enemyLeft[0];
        bounds.x = 18;
        bounds.y = 15;
        bounds.width = 56;
        bounds.height = 54;
        this.itemManager = itemManager;
        ///Se seteaza itemul ca fiind destructibil
        this.destructible = true;
        timer = new Timer(2000); /*!< Odata la x secunde (x parametru), eroul poate ataca, astfel el nu ataca non stop.*/
        isDead  = false;
    }

    /*! \fn private boolean checkNearbyItems()
         \brief Detecteaza elementele pe care le poate distruge (in toate 4 directii) la distanta de 1 pas.

         Adaug si Heroul in lista de posibile tinte deoarece in ItemManager Hero nu face parte din lista de itemuri, si daca vreau ca Enemy sa-l
         vada pe erou ca tinta trebuie sa il adaugam manual aici.
          Odata gasit un item, se seteaza booleanul de directie si se returneaza true.
          */
    private boolean checkNearbyItems()
    {
        List<Item> items = (List<Item>) itemManager.GetItems().clone();
        items.add(itemManager.GetHero());

        for (Item i : items) {

            if (i instanceof Enemy || i instanceof Flame || i instanceof SpeedItem)  // sa nu perceapa ca item de atacat flama sau pe sine.. :)))
                continue;

            if (i.getCollisionBounds(0, 0).intersects(getCollisionBounds(speed , 0)) && getCollisionBounds(speed,0).contains( (i.getCollisionBounds(0,0).getMinX() -16), (i.getCollisionBounds(0,0).getMinY() + 32))) {
                //            System.out.println("About to intersect right item");
                right = true;
                return true;
            }
            if (i.getCollisionBounds(0, 0).intersects(getCollisionBounds(-speed , 0)) && getCollisionBounds(-speed,0).contains( i.getCollisionBounds(0,0).getMaxX()+16, i.getCollisionBounds(0,0).getMaxY()-32)) {
                //              System.out.println("About to intersect left item");


                left = true;
                return true;
            }
            if (/*i.getCollisionBounds(0, 0).intersects(getCollisionBounds(0 , speed)) &&  */getCollisionBounds(0,speed).contains( i.getCollisionBounds(0,0).getMaxX()-32, i.getCollisionBounds(0,0).getMinY()-32)){
//                System.out.println("About to intersect down item");

                down = true;
                return true;
            }
            if (i.getCollisionBounds(0, 0).intersects(getCollisionBounds(0, -speed )) &&  getCollisionBounds(0,-speed).contains( i.getCollisionBounds(0,0).getMaxX()-32, i.getCollisionBounds(0,0).getMaxY()+32)
            ) {
                //       System.out.println("About to intersect up item");

                up = true;
                return true;
            }
        }
        //   System.out.println("no items found");

        return false;
    }


    /*! \fn    public void Attack(float x, float y)

     In functie de directia detectata in checkNearbyItems() plaseaza o flama pe locul respectiv la distanta de o dala fata de pozitia inamicului.
     \warning Mai da rateuri uneori la calcularea coordonatelor potrivite
     */
    public void Attack(float x, float y) {
        if (up)
        {
            //   draw up a flame cycle
            refLink.GetMap().getItemManager().GetItems().add(new Flame(refLink, ((this.GetX()+32)/Tile.TILE_WIDTH)*Tile.TILE_WIDTH,(this.GetY()/Tile.TILE_HEIGHT)*Tile.TILE_HEIGHT - Tile.TILE_HEIGHT, Tile.TILE_WIDTH,Tile.TILE_HEIGHT,"Enemy"));
            //  make up item dissapear from ENemy's perspective
            up = false;
        }
        else if (down)
        {
            refLink.GetMap().getItemManager().GetItems().add(new Flame(refLink, ((this.GetX()+32)/Tile.TILE_WIDTH)*Tile.TILE_WIDTH,(this.GetY()/Tile.TILE_HEIGHT)*Tile.TILE_HEIGHT + Tile.TILE_HEIGHT, Tile.TILE_WIDTH,Tile.TILE_HEIGHT,"Enemy"));

            down = false;
            //draw down a flame cycle
            // make down item dissapear from ENemy's perspective
        }
        else if (left)
        {

            refLink.GetMap().getItemManager().GetItems().add(new Flame(refLink, ((this.GetX()+32)/Tile.TILE_WIDTH)*Tile.TILE_WIDTH - Tile.TILE_WIDTH,((this.GetY()+32)/Tile.TILE_HEIGHT)*Tile.TILE_HEIGHT, Tile.TILE_WIDTH,Tile.TILE_HEIGHT,"Enemy"));

            left = false;
            //draw down a flame cycle
            // make down item dissapear from ENnemy's perspective
        }
        else if (right)
        {
            refLink.GetMap().getItemManager().GetItems().add(new Flame(refLink, ((this.GetX()+32)/Tile.TILE_WIDTH)*Tile.TILE_WIDTH + Tile.TILE_WIDTH,((this.GetY()+32)/Tile.TILE_HEIGHT)*Tile.TILE_HEIGHT, Tile.TILE_WIDTH,Tile.TILE_HEIGHT,"Enemy"));

            right = false;
            //draw down a flame cycle
            // make down item dissapear
        }


    }


    /*! \fn    public void ImageActualization()
    Actualizeaza imaginea curenta a enemyului in functie de directia sa.*/
    public void ImageActualization()
    {
        if (up) {
            image = Assets.nextUpEnemy();
        }
        else if (down)
        {
            image = Assets.nextDownEnemy();
        }
        else if (left)
        {
            image = Assets.nextLeftEnemy();
        }
        else if ( right)
        {
            image = Assets.nextRightEnemy();
        }

    }


/*!
\fn    public void Destroy()
Metoda responsabila cu setarea directiei imaginii inamicului, actualizarea imaginii sale si atacarea itemului gasit in preajma sa
 */
    public void Destroy()
    {

        if (up)
        {
            direction = 1;
            ImageActualization();
            Attack(0,-speed);
            up = false;
        }
        else if (down)
        {
            direction = 3;
            ImageActualization();
            Attack(0,speed);

            down = false;
        }
        else if ( right)
        {
            direction = 2;
            ImageActualization();
            Attack(speed,0);

            right = false;
        }
        else if ( left)
        {
            direction = 0;
            ImageActualization();
            Attack(speed, 0);

            left = false;
        }
    }

    /*! \fn     public void Update() {
    Se incearca depistarea unor items din imprejurimi si daca se gasesc, in functie de cat timp a trecut de la ultimul atac ataca sau nu.
    Daca nu gaseste itemuri de distrus in jur, se plimba.
     */
    @Override
    public void Update() {
        if (checkNearbyItems()) {

            if(!timer.isOverTimeLimit()) {
             //   System.out.println("Timer " + timer.getTimer() );
                return;
            }
            Destroy();
        }
        else {
            //centerOnTile();
            MoveEnemy();
        }

    }


    /*! \fn     public void MoveEnemy()
     Metoda ce decide directia pe care merge enemyul. */
    public void MoveEnemy()
    {
        switch (direction)
        {
            case 0 :
                this.xMove -=speed;
                this.yMove = 0;
                left = true;
                ImageActualization();
                left = false;
                break;
            case 1:
                this.yMove -=speed;
                this.xMove = 0;
                up = true;
                ImageActualization();
                up = false;
                break;
            case 2:
                this.xMove +=speed;
             //   System.out.println("xMove = " + xMove);
                this.yMove = 0;
                right = true;
                ImageActualization();
                right = false;
                break;
            case 3:
                this.yMove +=speed;
                this.xMove = 0;
                down = true;
                ImageActualization();
                down = false;
                break;
        }
        try {

            if (!Move()) {
                /*! Daca enemyul nu a reusit sa se miste facem undo manual la directia pe care am stabilit o inainte si incercam iar cu o directie Random
            (pentru a evita previzibilitatea miscarilor) */
                switch (direction) {
                    case 0:
                        this.xMove += speed;
                        this.yMove = 0;
                        break;
                    case 1:
                        this.yMove += speed;
                        this.xMove = 0;
                        break;
                    case 2:
                        this.xMove -= speed;
                        //  System.out.println("xMove from second switch " + xMove);
                        this.yMove = 0;
                        break;
                    case 3:
                        this.yMove -= speed;
                        this.xMove = 0;
                        break;
                }
                direction = random.nextInt(4);
                //   System.out.println("\nDirection: " + direction + "\n");
                MoveEnemy();
            }
        }
        catch (StackOverflowError e)
        {
            Score.GetScoreInstance().alterScore(200);
            isDead = true;
           itemManager.GetItems().remove(this);
            PlayState.ForceUpdate();

                        /* Deoarece nu e cea mai eficienta implementare uneori (rar) primesc aceasta eroare si o rezolv prin eliminarea enemyului. */
        }
        this.xMove = 0;
        this.yMove = 0;
    }


    /*!
    \fn    public void Draw(Graphics g) {
Metoda suprascrisa reponsabila cu randarea imaginii curente a inamicului.
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(image, (int)(x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y - refLink.GetGame().GetGameCamera().getyOffset()), width, height, null);
        g.setColor(Color.CYAN);
   //     g.fillRect((int)(x + bounds.x - refLink.GetGame().GetGameCamera().getxOffset() ) , (int)(y + bounds.y - refLink.GetGame().GetGameCamera().getyOffset()), bounds.width, bounds.height);
     //   System.out.println("Enemy coords : "+ this.x + " , " + this.y);
    }

    /*!< Metoda statica si publica ce o apelez in PlayState - Update ca sa verific daca Enemyul a murit, deci meciul e castigat. */
    public static boolean isDead()
    {
       // System.out.println("Is enemy dead? " + isDead);
        return isDead;
    }

    /*! Metoda ce elimina enemyul din lista de iteme a jocului si forteaza apelarea PlayState->Update() (unde se decide ca meciul e terminat) pentru a evita unele erori
    precum NullPtrException (oricum scopul jocului s-a indeplinit cand moare enemyul) .*/
    @Override
    public boolean RemoveItem() {


        if ( this.getDestroyerOrFather().equals("Hero"))
        Score.GetScoreInstance().alterScore(200);
        if (this.getDestroyerOrFather().equals("Enemy"))
            return false;
        isDead = true;
        PlayState.ForceUpdate();
      //  System.out.println("Is enemy dead? " + isDead());
       return refLink.GetMap().getItemManager().GetItems().remove(this);
    }
}
