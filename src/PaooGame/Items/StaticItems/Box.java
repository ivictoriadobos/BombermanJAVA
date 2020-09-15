package PaooGame.Items.StaticItems;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Score.Score;
import PaooGame.Tiles.Tile;

import java.awt.*;
/*!
\class Box
\brief Aceasta clasa implementeaza notiunea de cutie care se poate distruge (nu dala de tip perete sau bloc indestructibil)
 */

public class Box  extends  StaticItem{
    /*!
\fn public     public Box(RefLinks refLink, float x, float y, int width, int height)
Constructor ce apeleaza constructorul clasei de baza si face obiectul destructibil
*/
    public Box(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        this.destructible = true;

    }

        /*!
\fn    public boolean RemoveItem()
Metoda suprascrisa ce elimina acest obiect din lista de items a jocului
Daca cel care a plasat flama ce a distrus acest obiect, scorul se altereaza cu o valoare proprie (aici 2)
 */

    @Override
    public boolean RemoveItem() {
        if (this.destroyerOrFather.equals("Hero"))
            Score.GetScoreInstance().alterScore(2);
        return  refLink.GetMap().getItemManager().GetItems().remove(this);

    }
    /*!
\fn public void Update()
Metoda suprascrisa. Empty in aceasta versiune.
 */
    @Override
    public void Update() {
      //  System.out.println("\n\nBox still here");

    }
    /*!
\fn public void Draw(Graphics g)
Metoda suprascrisa responsabila cu desenarea cutiei pe harta
Imaginea desenata apartine de clasa Assets
 */
    @Override
    public void Draw(Graphics g) {
    g.drawImage(Assets.box,(int)(x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y - refLink.GetGame().GetGameCamera().getyOffset()), width, height, null);
     // DIVERSE DESENE PENTRU DEBUG
        /*
        g.setColor(Color.MAGENTA);
         g.fillRect((int)(x + bounds.x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y + bounds.y - refLink.GetGame().GetGameCamera().getyOffset()), bounds.width, bounds.height);

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
