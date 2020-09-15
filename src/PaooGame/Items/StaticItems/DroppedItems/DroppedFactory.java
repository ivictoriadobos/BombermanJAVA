package PaooGame.Items.StaticItems.DroppedItems;

import PaooGame.Items.StaticItems.StaticItem;
import PaooGame.RefLinks;

import java.util.Random;

/*!
\class DroppedFactory
\brief Clasa responsabila cu generarea de itemuri bonus. Completeaza implementarea sablonului Factory
 */
public class DroppedFactory {
    ///Generator random de numere, folosit la decizia tipului de item ce va "cadea" pe jos. Momentan exista doar un tip (SpeedItem)
    private static Random randomGenerator = new Random();

    ///Metoda publica statica folosita pentru dobandirea in mod aleatoriu a unui item bonus la distrugerea unui item din joc
    public static StaticItem getDroppedItem( RefLinks refLinks, float x, float y, int width, int height )
    {
        double index = randomGenerator.nextDouble() ; // genereaza intre 0,0 si 1,0
        if (index >=0 && index <= 0.05 ) // 5% sanse sa apara itemul viteza
        {
            return new SpeedItem( refLinks, x,y,width, height);
        }
        else  return null;
    }
}
