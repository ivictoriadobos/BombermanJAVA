package PaooGame.Items.StaticItems;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Score.Score;

import java.awt.*;
/*!
\class DestructibleWall
\brief Aceasta clasa implementeaza notiunea de perete care se poate distruge (nu dala de tip perete sau bloc indestructibil)
 */


public class DestructibleWall extends StaticItem {

    /*!
\fn public DestructibleWall(RefLinks refLink, float x, float y, int width, int height)
Constructor ce apeleaza constructorul clasei de baza si face obiectul destructibil
*/
    public DestructibleWall(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
        this.destructible = true;
    }

    /*!
\fn    public boolean RemoveItem()
Metoda suprascrisa ce elimina acest obiect din lista de items a jocului
Daca cel care a plasat flama ce a distrus acest obiect, scorul se altereaza cu o valoare proprie (aici 3)
 */
    @Override
    public boolean RemoveItem() {
        if (this.destroyerOrFather.equals("Hero"))
            Score.GetScoreInstance().alterScore(3);
        return  refLink.GetMap().getItemManager().GetItems().remove(this);

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
Metoda suprascrisa responsabila cu desenarea peretelui pe harta
Imaginea desenata apartine de clasa Assets
 */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(Assets.destructibleWall,(int)(x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y - refLink.GetGame().GetGameCamera().getyOffset()), width, height, null);
        //g.setColor(Color.MAGENTA);
        //g.fillRect((int)(x + bounds.x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y + bounds.y - refLink.GetGame().GetGameCamera().getyOffset()), bounds.width, bounds.height);

/*
        g.fillOval((int)(this.x + Tile.TILE_WIDTH + 16 - refLink.GetGame().GetGameCamera().getxOffset()), (int)(this.y +32 -refLink.GetGame().GetGameCamera().getyOffset()), 8,8); // drt
        g.setColor(Color.GREEN);
        g.fillOval((int)(this.x -16 - refLink.GetGame().GetGameCamera().getxOffset()), (int)(this.y +32 -refLink.GetGame().GetGameCamera().getyOffset()), 8,8); // stg
        g.setColor(Color.BLUE);
        g.fillOval((int)(this.x +32 - refLink.GetGame().GetGameCamera().getxOffset()), (int)(this.y +Tile.TILE_HEIGHT + 16 -refLink.GetGame().GetGameCamera().getyOffset()), 8,8); // jos
        g.setColor(Color.YELLOW);
        g.fillOval((int)(this.x +32 - refLink.GetGame().GetGameCamera().getxOffset()), (int)(this.y  - 16 -refLink.GetGame().GetGameCamera().getyOffset()), 8,8); // sus


 */
    }
}
