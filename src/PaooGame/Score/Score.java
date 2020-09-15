package PaooGame.Score;

import PaooGame.RefLinks;

import java.awt.*;
/*!
\class Score
\brief Clasa Score implementeaza cu ajutorul sablonului SINGLETON notiunea de scor
 */
public class Score {

    ///Variabila ce retine scorul jucatorului
    private int score;
    ///Variabila statica instanta a clasei ce completeaza sablonul Singleton
    static Score scoreObj = new Score();

    ///\fn private Score()
    ///Constructor privat al clasei ce completeaza sablonul Singleton. Scorul e initializat cu 0
    private Score()
    {
        score = 0;
    }

    /*!
    \fn     static public Score GetScoreInstance()
    Metoda statica de a obtine instanta clasei
     */
    static public Score GetScoreInstance()
    {
        return scoreObj;
    }

/*!
\fn    public void Draw(Graphics g)
Metoda destinata desenarii scorului in stanga sus a ferestrei pe durata meciului
 */
    public void Draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,70,20);
        g.setColor(Color.ORANGE);;
        g.drawString("Score : " + score, 3,14);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /*!
    \fn    public void alterScore( int score)
    Metoda ce se ocupa cu alterarea valorii scorului
    \param score Valoarea ( pozitiva sau negativa) pe care a acumulat-o jucatorul distrugand diverse obiecte ce trebuie adunata la scorul sau.
     */
    public void alterScore( int score)
    {
       // System.out.println("Score.alterScore");
        this.score += score;
    }



}
