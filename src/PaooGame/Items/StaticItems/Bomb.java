package PaooGame.Items.StaticItems;

import PaooGame.Graphics.Assets;
import PaooGame.Items.ItemManager;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

import PaooGame.Timer.*;
/*!
\class Bomb
\brief Aceasta clasa implementeaza notiunea de bomba a jocului.  Ea explodeaza si in functie de parametrii jocului lasa in urma un numar de flacari

Aceasta este similara lui Flame in sensul ca se ocupa cu gestionarea imaginilor randate in functie de timpul scurs de la crearea bombei
Pe langa acestea odata cu scurgerea timpului acest obiect este responsabil si de crearea flacarilor specifice acestei bombe (not the best design, i know, but things are going to change :D )
 */
public class Bomb extends StaticItem {
    ///Data la care a fost creat acest obiect
    java.util.Date createdTime ;
    int callCOunt = 0;
    int callRemoveBombcount = 0;

    ///Imaginea curenta randata a bombei
    private BufferedImage currimage;

    /*!
    \fn    public Bomb(RefLinks refLink, float x, float y, int width, int height)
    Constructor ce apeleaza constructorul din superclasa
    Modifica limitele dreptunghiului de coliziune
    Seteaza ora crearii bombei
    Adauga acest obiect la lista de items a jocului
     */
    public Bomb(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, ((int)x/Tile.TILE_WIDTH +1 )*Tile.TILE_WIDTH , ((int)y/Tile.TILE_HEIGHT+1)*Tile.TILE_HEIGHT , Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        bounds.x=16;
        bounds.y = 16;
        bounds.height=32;
        bounds.width = 32;
        createdTime = new java.util.Date();
        refLink.GetMap().getItemManager().AddItem(this);
        }


                /*!
\fn    public boolean RemoveItem()
Metoda suprascrisa ce elimina acest obiect din lista de items a jocului
De fiecare data cand dispare o bomba stiu sigur datorita implementarii ca poate fi doar a heroului, prin urmare ii scad numarul de active bombs */

    @Override
    public boolean RemoveItem() {
        // de fiecare data cand dispare o bomba stiu sigur datorita implementarii
        // ca poate fi doar a heroului, prin urmare ii scad numarul de active bombs

            refLink.GetMap().getItemManager().GetHero().alterNoOfActiveBombs(-1);
            return RemoveBomb();

        }


    /*!
\fn    public double getIndexByTime()
In functie de cat timp a trecut de la crearea bombei pana in momentul actual se transmite un index care sa decida imaginea curenta a bombei care se randeaza
 */
    public int getIndexByTime() {
        java.util.Date now = new java.util.Date();
        return (int)((now.getTime() - this.createdTime.getTime()) / 250); // valoare hardcodata... am ales acest raport fiindca imi satisface
                                                                          // viteza cu care vreau sa se schimbe spriteurile pt bomba ;
                                                                          // Apelata aici in Update
    }

        /*!
    \fn public void Update()
    Metoda suprascrisa ce are responsabilitatea de a schimba imaginea actuala randata in functie de cat timp a trecut de la crearea bombei si,
    in caz de timpul a expirat pentru aceasta instanta , sa isi apeleze metoda RemoveItem dupa care apeleaza AddFlames care in functie de pozitia bombei adauga flacari pe cele 4 directii
    */
    @Override
    public void Update() {

        if(getIndexByTime()>=10){
            RemoveItem();
            AddFlames();
            return;
        }
        currimage = Assets.bomb[getIndexByTime()];
    }

    /*!
    \fn    public boolean RemoveBomb()
Metoda responsabila doar cu eliminarea acestui obiect din lista de items a jocului
     */
    public boolean RemoveBomb()
    {
       return refLink.GetMap().getItemManager().GetItems().remove(this);
    }


   /*!
\fn     public void AddFlames()
Functie responsabila de a adauga flacari in urma exploziei bombei (obiectului curent)
    */
    public void AddFlames()
    {
            ///Adaugarea flacarii centrale (unde a fost bomba)
            Flame flame = new Flame(refLink, this.x, this.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, "Hero");
            refLink.GetMap().getItemManager().GetItems().add(flame);

            /// Adaugarea flacarilor aditionale evitand dalele solide
            BewareSolidTiles();
            ///Adaugarea flacarilor aditionale evitand succesiunea de itemuri (o directie de flacari se opreste in primul item intalnit)
            BewareItemsSuccesion();

    }
/*!
\fn    private void BewareItemsSuccesion()
Metoda gestioneaza crearea flacarilor daca directiile pe care se extind acestea intalnesc itemuri ( o directie se va opri la prima flacara ce intalneste un item)
 */
    private void BewareItemsSuccesion() {
        int counterSus = 0, counterJos =0, counterStanga = 0, counterDreapta = 0;
        ItemManager im = refLink.GetMap().getItemManager();
        for ( int i  =1 ; i<=  Flame.powerOfFLame; i++)
        {
            ///Daca pe directia curenta (Dreapta) se intalneste un item, se incrementeaza un contor de directie si se incearca a se plasa o flacara
            if(im.itemsOverlapping(this.GetX() + i*64, this.GetY())) // flame overlapps existing item on right direction
            {
                // increase counterDreapta
                // if counterDreapta!= 2, place flame (the flame should stop after first obstacle)
                // don't reset counter
                counterDreapta++;
                System.out.println("\nCounter dreapta : " + counterDreapta);
                ///Daca itemul gasit este altul decat primul, se abandoneaza plasarea flacarii
                if(counterDreapta>= 2)
                    break;
                ///Altfel, se plaseaza flama pe primul item gasit in directia dreapta
                ///Analog celelalte directii
                else
                  im.GetItems().add(new Flame(refLink, this.x + i*64, this.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, "Hero"));

            }

            if(im.itemsOverlapping(this.GetX() - i*64, this.GetY()))
            {
                counterStanga++;
                System.out.println("\nCounter stanga : " + counterStanga);

                if(counterStanga>= 2)
                    break;
                else
                    im.GetItems().add(new Flame(refLink, this.x - i*64, this.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, "Hero"));

            }
            if(im.itemsOverlapping(this.GetX() , this.GetY()+ i*64))
            {
                counterJos++;
                System.out.println("\nCounter jos : " + counterJos);
                if(counterJos>= 2)
                    break;

                else
                    im.GetItems().add(new Flame(refLink, this.x , this.y+ i*64, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, "Hero"));

            }


            if(im.itemsOverlapping(this.GetX() , this.GetY()- i*64))
            {
                counterSus++;
                System.out.println("\nCounter sus : " + counterSus);
                if(counterSus>= 2)
                    break;
                else
                    im.GetItems().add(new Flame(refLink, this.x , this.y- i*64, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, "Hero"));
            }
        }

    }

/*!
\fn    public void BewareSolidTiles()
Metoda responsabila de a gestiona crearea flacarilor avand in vedere dalele solide sau walkable
Aceasta metoda in momentul in care intalneste orice fel de item (nu dala) se opreste, urmand ca BewareItemsSuccesion() sa se ocupe de aspectul flamelor peste items
 */
    public void BewareSolidTiles()
    {
        ItemManager im = refLink.GetMap().getItemManager();


        ///Se seteaza cate un boolean pentru fiecare directie pe false.
        boolean flagSus = false, flagJos = false, flagStanga = false, flagDreapta = false;
        for ( int i  =1 ; i<=  Flame.powerOfFLame; i++)
        {
            ///Se itereaza peste numarul de flacari pe o directie (1,2,3... )
            if ( refLink.GetMap().GetTile( (int)(this.x + i * 64)/Tile.TILE_WIDTH, (int)this.y/Tile.TILE_HEIGHT ) != Tile.bckgTile || im.itemsOverlapping(this.GetX() + i*64, this.GetY())) //  pun flag, de asemenea daca dau de un Item
                flagDreapta =true ;
            ///Daca se intalneste dala solida sau items, se seteaza flagul directiei pe true
            else if ( refLink.GetMap().GetTile( (int)(this.x + i * 64)/Tile.TILE_WIDTH, (int)this.y/Tile.TILE_HEIGHT ) == Tile.bckgTile && !flagDreapta) //pot crea flama doar daca e bckg tile si n am anterior vreun stalp sau perete in cale
                refLink.GetMap().getItemManager().GetItems().add(new Flame(refLink, this.x + i*64, this.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT , "Hero"));
            ///Se verifica daca se poate plasa flama pe locul verificat (Trebuie sa nu fie actuala dala sau una din cele anterioare de tip solid si nici nu trebuie sa fi colizionat cu vreun items acum sau inainte ca
            ///acest lucru sa se realizeze)
            ///Booleanul de directie nu se reseteaza pe false, astfel incat odata depistata o dala solida sau item in cale, directia nu va mai putea fi populata de flames
            if ( refLink.GetMap().GetTile( (int)(this.x - i * 64)/Tile.TILE_WIDTH, (int)this.y/Tile.TILE_HEIGHT ) != Tile.bckgTile || im.itemsOverlapping(this.GetX() - i*64, this.GetY())) // daca dau de un perete ceva, pun flag
                flagStanga =true ;

            else if ( refLink.GetMap().GetTile( (int)(this.x - i * 64)/Tile.TILE_WIDTH, (int)this.y/Tile.TILE_HEIGHT ) == Tile.bckgTile && !flagStanga) //pot crea flama doar daca e bckg tile si n am anterior vreun stalp sau perete in cale
                refLink.GetMap().getItemManager().GetItems().add(new Flame(refLink, this.x - i*64, this.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT ," Hero"));

            if ( refLink.GetMap().GetTile( (int)(this.x )/Tile.TILE_WIDTH, ((int)this.y+ i * 64)/Tile.TILE_HEIGHT ) != Tile.bckgTile || im.itemsOverlapping(this.GetX() , this.GetY() + i*64)) // daca dau de un perete ceva, pun flag
                flagJos =true ;

            else if ( refLink.GetMap().GetTile( (int)(this.x )/Tile.TILE_WIDTH, ((int)this.y+ i * 64)/Tile.TILE_HEIGHT ) == Tile.bckgTile && !flagJos) //pot crea flama doar daca e bckg tile si n am anterior vreun stalp sau perete in cale
                refLink.GetMap().getItemManager().GetItems().add(new Flame(refLink, this.x, this.y + i*64, Tile.TILE_WIDTH, Tile.TILE_HEIGHT , "Hero"));

            if ( refLink.GetMap().GetTile( (int)(this.x )/Tile.TILE_WIDTH, ((int)this.y - i*64)/Tile.TILE_HEIGHT ) != Tile.bckgTile || im.itemsOverlapping(this.GetX() , this.GetY() - i*64)) // daca dau de un perete ceva, pun flag
                flagSus =true ;

            else if ( refLink.GetMap().GetTile( (int)(this.x)/Tile.TILE_WIDTH, ((int)this.y - i*64)/Tile.TILE_HEIGHT ) == Tile.bckgTile && !flagSus) //pot crea flama doar daca e bckg tile si n am anterior vreun stalp sau perete in cale
                refLink.GetMap().getItemManager().GetItems().add(new Flame(refLink, this.x, this.y - i*64, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, "Hero"));

        }
    }


    /*!
    \fn    public void Draw(Graphics g) {
Metoda suprascrisa responsabila cu randarea imaginii curente a bombei.
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(currimage, (int)(x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y - refLink.GetGame().GetGameCamera().getyOffset()), width, height, null);
    //    g.setColor(Color.blue);
      //  g.fillRect((int)(x + bounds.x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y + bounds.y - refLink.GetGame().GetGameCamera().getyOffset()), bounds.width, bounds.height);

    }
}
