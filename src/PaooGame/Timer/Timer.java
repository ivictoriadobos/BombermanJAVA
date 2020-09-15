package PaooGame.Timer;

/*!
\class Timer
\brief Clasa menita sa cronometreze un eveniment.

Obiectul de tip Timer are o limita de timp - timeLimit care odata atinsa returneaza true, altfel returneaza false.
Se foloseste pastrand o referinta catre un obiect de tip Timer (obj), iar cand se decide daca un eveniment sa aiba loc sau nu in functie de cat timp a trecut se apeleaza obj.isOverTimeLimit.
 */
public class Timer {

    /// Variabila destinata unor calcule. Retine cand a fost ultima data apelata o metoda a clasei (isOverTimeLimit).
    private long lastTime;

    ///Acest atribut reprezinta limita de timp a timerului. Este in milisecunde.
    private long timeLimit;

    /// Boolean ce retine daca e prima rulare a timerului (a metodei isOverTimeLimit mai exact) sau nu.
    private boolean firstRun;

    /// Variabila ce retine efectiv cat timp a trecut de la pornirea timerului pana in momentul curent.
    private long timer;


    /*!
    \fn     public Timer(long timeLimit)
    Constructor ce seteaza limita timerului si seteaza booleanul firsRun pe true, urmand ca in isOverTimeLimit() la prima rulare sa fie setat pe false.
     */
    public Timer(long timeLimit)
    {
        this.timeLimit = timeLimit;
        firstRun =true;
    }


/*!
\fn     public boolean isOverTimeLimit()
Metoda care implementeaza efectiv timerul.
Cronometrarea timpului scurs incepe de la 0 (nu returneaza true decat cand s-a atins limita de timp)
 */
    public boolean isOverTimeLimit()
    {

        // la prima rulare sa porneasca timerul de la 0
        if (firstRun)
        {
            timer = 0;
            firstRun  =false;
        }
        else
            timer += System.currentTimeMillis() - lastTime;

     //   System.out.println(" I : timer : " + timer + " timeLimit : " + timeLimit + " lastTime : " + lastTime);
        lastTime = System.currentTimeMillis();
       // System.out.println(" II : timer : " + timer + " timeLimit : " + timeLimit + " lastTime : " + lastTime);
        if(timer < timeLimit ) {
            return false;
        }
        else {
            timer =0;
            return true;
        }
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }
}
