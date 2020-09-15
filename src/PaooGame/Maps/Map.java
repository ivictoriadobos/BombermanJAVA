package PaooGame.Maps;

import PaooGame.Items.DestroyManager;
import PaooGame.Items.DynamicItems.Enemy;
import PaooGame.Items.DynamicItems.Hero;
import PaooGame.Items.ItemManager;
import PaooGame.Items.StaticItems.Box;
import PaooGame.Items.StaticItems.DestructibleWall;
import PaooGame.Items.StaticItems.Portal;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;
import PaooGame.Utils;
import jdk.jshell.execution.Util;

import java.awt.*;

/*! \class Map
    \brief Implementeaza notiunea de harta a jocului.
    Contine toate obiectele jocului. (statice sau dinamice)
 */
public class Map
{
    private RefLinks refLink;   /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/



    private int width;          /*!< Latimea hartii in numar de dale.*/
    private int height;         /*!< Inaltimea hartii in numar de dale.*/

    private int [][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/

    ///Atribut ce retine coordonata x unde trebuie spawnat heroul
    private int spawnX ;
    ///Atribut ce retine coordonata y unde trebuie spawnat heroul
    private int spawnY;


    ///Managerul de items al jocului
    private ItemManager itemManager;

/*!
\fn     public Map(RefLinks refLink, String path)
Constructorul clasei Map.
Aici se seteaza obiectul shortcut, se transmite calea catre fisierul ce contine formatul hartii dpdv al dalelor

 */
    public Map(RefLinks refLink, String path)
    {
        this.refLink = refLink;
        ///Se construieste un nou manager de Items
        ///Se construieste un nou Hero
        itemManager = new ItemManager(refLink, new Hero(refLink, 0,0));

        ///Se seteaza noul manager de items si managerului de distrugeri de itemuri
        DestroyManager.GetInstance().setItemManager(itemManager);

        ///Se adauga diverse itemuri pe harta
        itemManager.AddItem(new Box(refLink, 128,192, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
        itemManager.AddItem(new Box(refLink, 128,128, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
        itemManager.AddItem(new Box(refLink, 64,320, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
        itemManager.AddItem((new DestructibleWall(refLink, 128,448,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 256,128,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem(new Portal(refLink,192,448,Tile.TILE_WIDTH, Tile.TILE_HEIGHT ));
        itemManager.AddItem((new DestructibleWall(refLink, 192,12*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 192,13*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
      //  itemManager.AddItem((new DestructibleWall(refLink, 192,14*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 192,16*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 192,17*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));

        itemManager.AddItem((new DestructibleWall(refLink, 320,12*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 320,13*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 320,17*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));

        itemManager.AddItem((new DestructibleWall(refLink, 384,14*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 384,16*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));

        itemManager.AddItem((new DestructibleWall(refLink, 448,14*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 448,16*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));
        itemManager.AddItem((new DestructibleWall(refLink, 256,15*64,Tile.TILE_WIDTH, Tile.TILE_HEIGHT)));

        ///Se incarca harta folosind calea transmisa ca parametru
        LoadWorld(path);
        itemManager.AddItem(new Enemy(refLink,256, 64,  itemManager));
        itemManager.GetHero().SetX(spawnX);
        itemManager.GetHero().SetY(spawnY);
    }


    public int[][] getTiles() {
        return tiles;
    }

/*!
\fn public void Update()
Metoda ce e responsabila cu actualizaea hartii (in consecinta actualizarea managerului de items).
 */
    public  void Update()
    {

        itemManager.Update();
    }



    /*! \fn public void Draw(Graphics g)
        \brief Functia de desenare a hartii.

        \param g Contextl grafi in care se realizeaza desenarea.
     */

    public void Draw(Graphics g)
    {
        ///Se calculeaza coordonatele extreme ale camerei jocului (nu e disponibila spre  vizualizare toata tabla de joc, ci in functie de pozitia lui Hero se servesc cadre)

        int xStart =(int)Math.max(0, refLink.GetGame().GetGameCamera().getxOffset()/Tile.TILE_WIDTH);
        int xEnd = (int)Math.min(width, (refLink.GetGame().GetGameCamera().getxOffset()+ refLink.GetGame().GetWidth())/Tile.TILE_WIDTH +1);
        int yStart =(int)Math.max(0, refLink.GetGame().GetGameCamera().getyOffset()/Tile.TILE_HEIGHT);
        int yEnd =(int)Math.min(height, (refLink.GetGame().GetGameCamera().getyOffset()+ refLink.GetGame().GetWidth())/Tile.TILE_HEIGHT +1);
        for(int y = yStart; y < yEnd; y++)
        {
            for(int x = xStart; x < xEnd; x++)
            {
                GetTile(x, y).Draw(g, (int)(x * Tile.TILE_WIDTH-refLink.GetGame(). GetGameCamera().getxOffset()), (int)(y * Tile.TILE_HEIGHT - refLink.GetGame().GetGameCamera().getyOffset()));
            }
        }
        ///Se deseneaza itemurile jocului
        itemManager.Draw(g);

    }


    /*! \fn public Tile GetTile(int x, int y)
        \brief Intoarce o referinta catre dala aferenta codului din matrice de dale.

        In situatia in care dala nu este gasita datorita unei erori ce tine de cod dala, coordonate gresite etc se
        intoarce o dala predefinita (ex. grassTile, mountainTile)
     */

    public Tile GetTile(int x, int y)
    {
        if(x < 0 || y < 0 || x >= width || y >= height)
        {
            return Tile.bckgTile;
        }
        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null)
        {
            return Tile.indestructibleWallTile;
        }
        return t;
    }


    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta.
        \param path Calea catre fisierul codat ce contine compozitia hartii
     */

    private void LoadWorld(String path)
    {

        ///Se incarca continutul fisierului oferot de parametru
        String file = Utils.loadFIleAsString(path);
        String[] tokens = file.split("\\s+");

             ///Se stabileste latimea hartii in numar de dale.
        width = Utils.parseInt(tokens[0]);
            ///Se stabileste inaltimea hartii in numar de dale
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);
            ///Se construieste matricea de coduri de dale
        tiles = new int[width][height];
            ///Se incarca matricea cu coduri
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                tiles[x][y] = Utils.parseInt(tokens[(x+y*width + 4)]);
            }

        }
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
    public ItemManager getItemManager() {
        return itemManager;
    }
    public int getSpawnX()
    {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public int getWidth()
    {
        return  width;
    }

    public int getHeight()
    {
        return height;
    }

}