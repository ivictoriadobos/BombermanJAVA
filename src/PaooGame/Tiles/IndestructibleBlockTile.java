package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class IndestructibleBlockTile
    \brief Abstractizeaza notiunea de dala de tip stalp.
 */
public class IndestructibleBlockTile extends Tile {

    /*! \fn public IndestructibleBlockTile(int id)
       \brief Constructorul de initializare al clasei

       \param id Id-ul dalei util in desenarea hartii.
    */
    public IndestructibleBlockTile(int id)
    {
            /// Apel al constructorului clasei de baza
        super(Assets.indestructibleBlock, id);
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
