package PaooGame;
/*!
\mainpage PAOO TILE GAME (in JAVA)
* \author Dobos Ioana-Victoria
*
* \version 1.0
*
* \date 2020-9-10
*
* REGULI: Eroul trebuie sa creeze un plan prin care sa elimine inamicul care are parte de avantaje fizice (rapiditate atat in comportament cat si ca viteza de deplasare)
* Acest, eroul, nu se poate apropia prea mult de inamic deoarece din cauza vitezei de reactie a celui din urma, jucatorul nu are nicio sansa de castig, va fi eliminat instant.
* Eroul poate distruge prin plasarea de bombe diverse obiecte din universul jocului si poate culege itemuri ce ii sporesc aptitudinile.
*
* POVESTE: Dincolo de interfata de joc, acesta aplicatie reda lupta activa din societate dintre un individ (Mirel) care ia decizii logice bazate pe analiza contextului si un alt individ ("Mihai") care
* are implementat in comportament un sistem mecanizat, prin urmare mult mai rapid fata de primul, care nu pune pret neaparat pe logica ci mai degraba pe actiune. Depinde de tine cine va castiga!
*
* Caractere:
* White player ("Mirel") : jucatorul real.
* Black player ("Mihai") : jucatorul programat.

\section SPRITESHEETS

\subsection Butoane
\image latex buttons.pdf
\image html buttons.png

\subsection Dale
\image latex spritesheet.pdf
\image html spritesheet.png


\subsection Bombe
\image latex bombeSprite.pdf
\image html bombeSprite.png

\subsection Inamic
\image latex enemy.pdf
\image html enemy.png

\subsection Erou
\image latex player.pdf
\image html player.png

\subsection Flacara
\image latex flame.pdf
\image html flame.png

\subsection AboutSheet
\image latex about.pdf
\image html about.png

\subsection HighscoresSheet
\image latex highscoresWalpaper.pdf
\image html highscoresWalpaper.png

\subsection Win Screen
\image latex YouWin.pdf
\image html YouWin.png

\subsection Lost Screen
\image latex LostGame.pdf
\image html LostGame.png

\subsection Walpaper
\image latex walpaper.pdf
\image html walpaper.png

\subsection SpeedItem
\image latex speed.pdf
\image html speed.png


\section DB

\subsection Structura
\image latex db1.pdf
\image html db1.png

\subsection DATA
\image latex db2.pdf
\image html db2.png


 */

/*!
 \class Main
 \brief Clasa clientului ce apeleaza Game.getInstance().StartGame();
 */
public class Main
{
    public static void main(String[] args)
    {
        // utilizarea modelului singleton de client
        Game.getGameInstance().StartGame();
    }
}