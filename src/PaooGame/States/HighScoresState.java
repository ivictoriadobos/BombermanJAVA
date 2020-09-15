package PaooGame.States;

import PaooGame.Database.Database;
import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.UserInterface.ClickListener;
import PaooGame.UserInterface.UIImageButton;
import PaooGame.UserInterface.UIManager;
import java.awt.*;
import java.awt.image.BufferedImage;

/*!
\class HighScoresState
\brief Clasa ce implementeaza starea Highscores

Clasa este responsabila de afisarea valorilor de la sectiunea Highscores
 */
public class HighScoresState extends State {
    /// Referinta catre un manager pentru UI specific starii MenuState
    private UIManager uiManager;
    /// Referinta catre o coala alba de hartie ce va fi desenata peste poza de background, dar sub informatiile specifice sectiunii.
    private BufferedImage walpaper = null; // teoretic apartinea clasei Assets, practic ma simt prost la cate variabile am pus acolo si mi a parut potrivit sa o plasez aici pe aceasta
                                           // consecventa nu e un punct forte se pare

    ///\fn     public HighScoresState(RefLinks refLink)
    ///Constructorul clasei Highscores
    public HighScoresState(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        ///Incarca coala alba de hartie
        walpaper = ImageLoader.LoadImage("/textures/highscoresWalpaper.png");
        ///Construieste un nou manager de obiecte UI
        uiManager = new UIManager(refLink);
        ///Adauga diverse obiecte specifice starii Highscores
        uiManager.addObj(new UIImageButton(50, 50, 200, 200, Assets.backButton, new ClickListener() {
            @Override
            public void Clicked() {

                refLink.GetMouseManager().setUiManager(null);
                State.SetState(refLink.GetGame().getMenuState());
            }
        }));

    }
///\fn public void Update()
    ///Metoda destinata actualizarii starii Highscores
    @Override
    public void Update() {
        if (refLink.GetMouseManager().getUiManager() == null)
            refLink.GetMouseManager().setUiManager(uiManager);
        uiManager.Update();

    }
///\fn public void Draw(Graphics g)
    @Override
    public void Draw(Graphics g) {
        ///Se deseneaza imaginea de background
        g.drawImage(Assets.walpaper, 0,0,960, 832, null);
        ///Se deseneaza coala de hartie de sub informatiile legate de scor
        g.drawImage(walpaper, 230, 100, 520, 590, null);
        ///Se deseneaza informatiile bazei de date ce gestioneaza scorul
        Database.getInstance().Draw(g);
        ///Se deseneaza obiectele UI
        uiManager.Draw(g);

    }
}
