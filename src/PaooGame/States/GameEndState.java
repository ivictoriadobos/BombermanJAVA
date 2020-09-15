package PaooGame.States;

import PaooGame.Game;
import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.UserInterface.ClickListener;
import PaooGame.UserInterface.UIImageButton;
import PaooGame.UserInterface.UIManager;

import java.awt.*;
///\class GameEndState
///\brief Clasa GameEndState reprezinta starea in care trece jocul odata ce meciul a luat sfarsit
public class GameEndState extends State {
    /// Referinta catre un manager pentru UI specific starii MenuState
    private UIManager uiManager;
    /// Boolean ce decide daca jocul a fost castigat (Enemy a fost omorat) sau nu
    public static boolean gameWon;

    ///\fn    public GameEndState(RefLinks refLink)
    ///Constructorul clasei GameEndState
    public GameEndState(RefLinks refLink) {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        ///Se construieste un nou manager de obiecte UI
        uiManager = new UIManager(refLink);
        ///Adauga diverse obiecte specifice starii Highscores
        uiManager.addObj(new UIImageButton(50, 450, 200, 200, Assets.backButton, new ClickListener() {
            @Override
            public void Clicked() {

                refLink.GetMouseManager().setUiManager(null);
                State.SetState(refLink.GetGame().getMenuState());
                Game.getGameInstance().RestartGame();
            }
        }));
    }
    ///\fn public void Update()
    ///Metoda destinata actualizarii starii GameEndState
    @Override
    public void Update() {
        if (refLink.GetMouseManager().getUiManager() == null)
            refLink.GetMouseManager().setUiManager(uiManager);

        uiManager.Update();
    }

    ///\fn public void Draw(Graphics g)
    @Override
    public void Draw(Graphics g) {
        ///Daca jocul a fost castigat se deseneaza o poza specifica unui meci castigat
        if (gameWon)
        g.drawImage(Assets.gameWon, 0,0,960, 832, null);
        else
            ///Daca jocul a fost pierdut se deseneaza o poza specifica unui meci pierdut
        g.drawImage(Assets.gameLost, 0, 0, 960, 832, null);
        ///Se deseneaza obiectele UI
        uiManager.Draw(g);

    }


    public static boolean isGameWon() {
        return gameWon;
    }

    public static void setGameWon(boolean gameWon) {
        GameEndState.gameWon = gameWon;
    }
}
