package PaooGame.Items.StaticItems;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Score.Score;
import PaooGame.Tiles.Tile;

import java.awt.*;
/*!
\class Portal
\brief Aceasta clasa implementeaza notiunea de portal al jocului ce (va) transpune eroul in alta lume
 */
public class Portal extends StaticItem{

    /*!
    \fn     public Portal(RefLinks refLink, float x, float y, int width, int height)
    Constructor ce apeleaza constructorul clasei de baza, seteaza dreptunghiul de coliziune mai mic si face obiectul destructibil
     */
    public Portal(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT );
        this.bounds = new Rectangle(Tile.TILE_WIDTH/3, Tile.TILE_HEIGHT/3,Tile.TILE_WIDTH/3, Tile.TILE_HEIGHT/3 );
        this.destructible = true;
    }

    /*!
    \fn    public boolean RemoveItem()
    Metoda suprascrisa ce elimina acest obiect din lista de items a jocului
    Daca cel care a plasat flama ce a distrus acest obiect, scorul se altereaza cu o valoare proprie (aici -10)
     */
    @Override
    public boolean RemoveItem() {
        if (this.destroyerOrFather.equals("Hero"))
            Score.GetScoreInstance().alterScore(-10);
        return refLink.GetMap().getItemManager().GetItems().remove(this);

    }

    /*!
    \fn public void Update()
    Metoda suprascrisa. Empty in aceasta versiune.
     */
    @Override
    public void Update() {

    }
/*!
\fn public void Draw(Graphics g)
Metoda suprascrisa responsabila cu desenarea portalului pe harta
Imaginea desenata apartine de clasa Assets
 */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(Assets.portal,(int)(x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y - refLink.GetGame().GetGameCamera().getyOffset()), width, height, null);
        //g.setColor(Color.blue);
       // g.fillRect((int)(x + bounds.x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y + bounds.y - refLink.GetGame().GetGameCamera().getyOffset()), bounds.width, bounds.height);


    }
}
