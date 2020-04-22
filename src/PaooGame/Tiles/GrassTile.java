package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class public class GrassTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip iarba.
 */
public class GrassTile extends Tile
{
    /*! \fn public GrassTile(int id)
        \brief Constructorul de initializare al clasei

        \param id Id-ul dalei util in desenarea hartii.
     */

    public GrassTile(int id)
    {
        /// Apel al constructorului clasei
        // de baza
        super(Assets.grass, id);
    }

    //avand in vedere ca nu e suprascrisa isSolid
    //se duce in clasa de baza (Tile) si o executa,
    //deci grassTile e walkable
}
