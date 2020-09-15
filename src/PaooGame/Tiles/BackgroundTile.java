package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class BackgroundTile
    \brief Abstractizeaza notiunea de dala de tip podea.
 */
public class BackgroundTile extends Tile
{
    /*
    ///Variabila ce retine daca asupra acestei dale se afla o bomba sau nu
    private boolean hasBomb=false;

     */
    /*! \fn public BackgroundTile(int id)
        \brief Constructorul de initializare al clasei

        \param id Id-ul dalei util in desenarea hartii.
     */


    public BackgroundTile(int id)
    {
        /// Apel al constructorului clasei
        // de baza
        super(Assets.background, id);
    }



    /*
    public void setHasBomb()
    {
        if (hasBomb)
        hasBomb = false;
        else hasBomb = true;
    }
    public boolean getHasBomb()
    {
        return hasBomb;
    }


     */

    //avand in vedere ca nu e suprascrisa isSolid
    //se duce in clasa de baza (Tile) si o executa,
    //deci backgroundTile e walkable
}
