package PaooGame.States;

import PaooGame.Database.Database;
import PaooGame.Items.DynamicItems.Enemy;
import PaooGame.Items.DynamicItems.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Timer.Timer;

import java.awt.*;

/*! \class PlayState
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{


    private Map map;    /*!< Referinta catre harta curenta.*/
    ///Timerul jocului ( gameplayul este contracronometru)
    Timer timer ;

    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

     */
    public PlayState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza
        super(refLink);

            ///Se construieste harta jocului
        map = new Map(refLink, "res/maps/map1.txt");

            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
           /// jocul este contra timp, iar timerul este initializat
        timer = new Timer(60000);

    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {
        /// Se cerceteaza daca timpul meciului a expirat
        boolean time = timer.isOverTimeLimit();
     //   System.out.println("Time ? " + time);
      //  System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
        ///Daca : timpul a expirat SAU eroul a murit SAU enemyul a murit, se termina meciul SI:
        if(time || Hero.isDead() || Enemy.isDead()) {
            //game ended
            // switch to GameEnd
            //System.out.println("Hero dead? \nEnemy dead? " + Hero.isDead() + Enemy.isDead());
            ///Se decide cine a castigat (hero sau enemy)
            GameEndState.setGameWon(!Hero.isDead() && !time);
            ///Se actualizeaza baza de date
            Database.getInstance().UpdateDatabase();
            /// Se seteaza starea jocului in GameEndState (Starea de joc terminat)
            State.SetState(refLink.GetGame().getGameEndState());
        }
            else
            ///Daca meciul nu s-a terminat se actualizeaza harta cu componentele jocului
            map.Update();

    }

    public static void ForceUpdate()
    {
        State.GetState().Update();
    }


    private String getTimer()
    {
        int timer = (int)(this.timer.getTimer()/1000); //aici avem timpul trecut in secunde
        int minutes = timer/60;   // calculam minutele
        int seconds = timer%60;    // calculam secundele
        return minutes + " : " + seconds;
    }

    /*! \fn public void Draw(Graphics g)
    \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

    \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
 */
    @Override
    public void Draw(Graphics g)
    {
        ///Se deseneaza harta
        map.Draw(g);
        ///Se deseneaza timerul meciului
        g.setColor(Color.BLACK);
        g.fillRect(880, 0, 80,  20);
        g.setColor(Color.ORANGE);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString(getTimer(), 900,15);

    }


    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
