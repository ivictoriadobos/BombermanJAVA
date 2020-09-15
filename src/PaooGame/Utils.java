package PaooGame;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/*!
\class Utils
\brief Clasa ce usureaza citirea fisierelor text a caror continut urmeaza sa fie exploatat ca input pentru diverse aspecte ale jocului, spre
exemplu incarcarea unui mesaj codat prin cifre care redau o harta.
 */

public class Utils {
    /*! \fn public static String loadFIleAsString(String path)
    \param path Calea catre fisier

    Functie ce returneaza ca string continutul unui fisier.
     */
    public static String loadFIleAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String currLine;
            while ((currLine = br.readLine()) != null) {
                builder.append(currLine + "\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /*! \fn public static int parseInt(String number)

     Functie ce incearca sa returneze ca intreg numarul din stringul number dat ca parametru.
     */
    public static int parseInt(String number)
    {
        try{
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
}

