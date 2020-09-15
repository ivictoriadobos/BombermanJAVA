package PaooGame.Maps;

import PaooGame.Items.Item;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

/*!
\class GameCamera
\brief Clasa implementeaza notiunea de camera. Tabla de joc e mult mai mare fata de ceea ce vede jucatorul.
 */
public class GameCamera {
    ///Atribut ce retine valoarea abscisei ce ajuta la centrarea unui item in fereastra
    private float xOffset;
    ///Atribut ce retine valoarea ordonatei ce ajuta la centrarea unui item in fereastra
    private float yOffset;
    private RefLinks refLinks;

    /*!
    \fn public GameCamera(RefLinks refLinks, float xOffset, float yOffset)
        Constructorul clasei GameCamera
     */
    public GameCamera(RefLinks refLinks, float xOffset, float yOffset)
    {
        this.refLinks = refLinks;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /*!
    \fn     public void centerOnEntity(Item e)
    \param e Itemul asupra caruia se centreaza fereastra
    Metoda are rolul de a calcula un offset (Atat pe orizontala cat si pe ordonata) astfel incat fiecare item la randare sa foloseaca aceste valori pentru a pune in evidenta Itemul e dat ca parametru
     */
    public void centerOnEntity(Item e)
    {
        xOffset = e.GetX() - refLinks.GetWidth()/2 + e.GetWidth()/2;
        yOffset = e.GetY() - refLinks.GetHeight()/2 + e.GetHeight()/2;
        checkCameraLimits();
    }

    /*!
    \fn    public void checkCameraLimits()
       Metoda ce are rolul de a verifica extremitatile camerei
     */
    public void checkCameraLimits()
    {
        //Daca camera ar vrea sa mearga prea in stanga sau prea in dreapta (Analog si pe verticala) ea e oprita (practic cand se intampla acest lucru Itemul asupra caruia se incearca centrarea nu mai e in centrul camerei )

        if(xOffset < 0 )
            xOffset = 0;
        else if( xOffset > refLinks.GetMap().getWidth()* Tile.TILE_WIDTH - refLinks.GetWidth())
            xOffset =refLinks.GetMap().getWidth()* Tile.TILE_WIDTH - refLinks.GetWidth();
        if(yOffset<0)
            yOffset = 0;
        if(yOffset > refLinks.GetMap().getHeight()*Tile.TILE_HEIGHT  - refLinks.GetHeight())
        {
            yOffset = refLinks.GetMap().getHeight()*Tile.TILE_HEIGHT - refLinks.GetHeight();
        }
   }
    public float getxOffset() {
        return xOffset;
    }
    public float getyOffset()
    {
        return yOffset;
    }
    public void setxOffset(float x)
    {
        xOffset = x;
    }

    public void setyOffset(float y)
    {
        yOffset = y;
    }

}
