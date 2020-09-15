package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class IndestructibleWallTile
    \brief Abstractizeaza notiunea de dala de tip perete.
 */public class IndestructibleWallTile extends Tile
{
    /*! \fn public IndestructibleWallTile(int id)
       \brief Constructorul de initializare al clasei

       \param id Id-ul dalei util in desenarea hartii.
    */
    public IndestructibleWallTile(int id)
    {
        super(Assets.indestructibleWall, id);
    }

    /*! \fn public boolean IsSolid()
       \brief Suprascrie metoda IsSolid() din clasa de baza in sensul ca va fi luat in calcul in caz de coliziune.
    */
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}
