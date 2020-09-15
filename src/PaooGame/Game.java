package PaooGame;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Input.KeyManager;
import PaooGame.Input.MouseManager;
import PaooGame.Maps.GameCamera;
import PaooGame.Maps.Map;
import PaooGame.States.*;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable
{
    //! SINGLETON DESIGN PATTERN IMPLEMENTED, referinta statica catre joc accesibila doar cu metoda statica getGameInstance()
    private static Game game = new Game("Mirel and Mihai", 960, 832);


    private GameWindow      wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean         runState;   /*!< Flag ce starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    ///< Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    ///< flickering (palpaire) a ferestrei.
    ///< Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at
    ///<                         |------------------------------------------------>|
    ///<                         |                                                 |
    ///<                 ****************          *****************        ***************
    ///<                 *              *   Show   *               *        *             *
    ///< [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///<                 *              *          *               *        *             *
    ///<                 ****************          *****************        ***************


    private static Graphics        g;          /*!< Referinta catre un context grafic.*/

    //Available states

    private State playState;            /*!< Referinta catre joc.*/
    private State menuState;            /*!< Referinta catre menu.*/
    private State settingsState;        /*!< Referinta catre setari.*/
    private State highscoresState;      /*!< Referinta catre highscores state.*/

    private State gameEndState;         /*!< Referinta catre starea de la finalul meciului.*/

    private State aboutState;           /*!< Referinta catre about.*/
    private KeyManager keyManager;      /*!< Referinta catre obiectul care gestioneaza intrarile din partea utilizatorului din partea tastaturii.*/

    private MouseManager mouseManager;   /*!< Referinta catre obiectul care gestioneaza intrarile din partea utilizatorului din partea mouseului.*/
    private RefLinks refLink;            /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte pentru a fi usor accesibile.*/

    //Camera
    private GameCamera gameCamera;    /**< Referinta catre camera jocului (nu se vede toata tabla de joc, se misca camera dupa Hero). */

    /*!< \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    private Game(String title, int width, int height)
    {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
            ///Construirea obiectului de gestiune a evenimentelor de tastatura
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }
        /*! \fn private void InitGame()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.
        Aici are loc si initializarea starilor.

     */

    private void InitGame() throws IOException {

        InitDisplay();

        ///Se incarca toate elementele grafice (dale)
        Assets.Init();

        ///Se construieste obiectul de tip shortcut ce va retine o serie de referinte catre elementele importante din program.
        refLink = new RefLinks(this);


        gameCamera = new GameCamera(refLink,0,0);

            ///Definirea starilor programului
        gameEndState    = new GameEndState(refLink);
        highscoresState = new HighScoresState(refLink);
        playState       = new PlayState(refLink);
        menuState       = new MenuState(refLink);
        settingsState   = new SettingsState(refLink);
        aboutState      = new AboutState(refLink);


        //Seteaza starea implicita cu care va fi lansat programul in executie
        State.SetState(menuState);
    }


        /*! \fn private void InitDisplau()
        \brief  Metoda construieste fereastra jocului si ii adauga obiectele responsabile de inputul tastaturii/mouseului.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();

     */
    private void InitDisplay()
    {
        /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
        ///Se ataseaza ferestrei managerul de tastatura si de mouse pentru a primi evenimentele furnizate de fereastra.
        wnd.GetWndFrame().addKeyListener(keyManager);
        wnd.GetWndFrame().addMouseListener(mouseManager);
        wnd.GetWndFrame().addMouseMotionListener(mouseManager);
        wnd.GetCanvas().addMouseListener(mouseManager);
        wnd.GetCanvas().addMouseMotionListener(mouseManager);
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()   //apelata in StartGame automat la gameThread.start();

    //in aceasta functie sta jocul odata intrat in acel while
    {
            /// Initializeaza obiectul game
        try {
            InitGame();
        } catch (IOException e) {
            e.printStackTrace();
        }


        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

            /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
            /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

            /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState == true)
        {
                /// Se obtine timpul curent
            curentTime = System.nanoTime();
                /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime) > timeFrame)
            {
                /// Actualizeaza pozitiile elementelor
                Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }

    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame()
    {
        if(runState == false)
        {
                /// Se actualizeaza flagul de stare a threadului
            runState = true;

                /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this); //un nou thread pe care sa ruleze gameul

                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
        else
        {
                /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame()
    {
        if(runState == true)
        {
                /// Actualizare stare thread
            runState = false;
                /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try
            {
                    /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                    /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
            }
            catch(InterruptedException ex)
            {
                    /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else
        {
                /// Thread-ul este oprit deja.
            return;
        }
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update()
    {
            ///Determina starea tastelor
        wnd.GetWndFrame().requestFocusInWindow();
        keyManager.Update();

        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if(State.GetState() != null)
        {
                ///Actualizez starea curenta a jocului daca exista.
            State.GetState().Update();
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw()
    {
            /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
            /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
                /// Se executa doar la primul apel al metodei Draw()
            try
            {
                    /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
            /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
            /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        /// operatie de desenare
            ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
            if(State.GetState() != null)
            {
                ///Actualizez starea curenta a jocului daca exista.
                State.GetState().Draw(g);
            }
        /// end operatie de desenare

            /// Se afiseaza pe ecran
        bs.show();

            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }

    public  void RestartGame() {
        getPlayState().getMap().setItemManager(null);
        game.setPlayState(new PlayState(refLink));
    }



    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei
     */
    public int GetWidth()
    {
        return wnd.GetWndWidth();
    }


    /*! \fn public static Game getGameInstance()
        \brief Completeaza sablonul Singleton returnand instanta statica a jocului.
     */
    public static Game getGameInstance()
    {
        return game;
    }

    /*!
     \fn public int GetHeight()
     \brief Returneaza inaltimea ferestrei
  */
    public int GetHeight()
    {
        return wnd.GetWndHeight();
    }

    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza obiectul care gestioneaza tastatura.
     */
    public KeyManager GetKeyManager()
    {
        return keyManager;
    }

    /*! \fn public MouseManager GetMouseManager()
        \brief Returneaza obiectul care gestioneaza mouseul.
     */
    public MouseManager GetMouseManager() { return mouseManager;}


    public GameCamera GetGameCamera()
    {
        return gameCamera;
    }

    public PlayState getPlayState() {
        return (PlayState)playState;
    }
    public State getMenuState() {
        return menuState;
    }
    public State getSettingsState() {
        return settingsState;
    }
    public State getAboutState() {
        return aboutState;
    }

    public State getGameEndState() {
        return gameEndState;
    }

    public void setGameEndState(State gameEndState) {
        this.gameEndState = gameEndState;
    }

    public State getHighscoresState() {
        return highscoresState;
    }

    public void setHighscoresState(State highscoresState) {
        this.highscoresState = highscoresState;
    }

    public void setPlayState(State playState) {
        this.playState = playState;
    }
}

