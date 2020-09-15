package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.UserInterface.ClickListener;
import PaooGame.UserInterface.UIImageButton;
import PaooGame.UserInterface.UIManager;

import java.awt.*;

/*! \class MenuState
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State
{
    /// Referinta catre un manager pentru UI specific starii MenuState
        private UIManager uiManager;
    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

     */
    public MenuState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        ///Construirea unui nou manager pentru obiectele UI.
        uiManager = new UIManager(refLink);
        refLink.GetMouseManager().setUiManager(uiManager);
        ///Adaugarea diverselor butoane specifice starii MenuState managerului de obiecte UI
        uiManager.addObj(new UIImageButton(200, 300, 200, 200, Assets.highscoresButton, new ClickListener() {
            @Override
            public void Clicked() {

                refLink.GetMouseManager().setUiManager(null);
                State.SetState(refLink.GetGame().getHighscoresState());

            }
        }));
        uiManager.addObj(new UIImageButton(200, 500, 200, 200, Assets.aboutButton, new ClickListener() {
            @Override
            public void Clicked() {
                refLink.GetMouseManager().setUiManager(null);
                State.SetState(refLink.GetGame().getAboutState());
            }
        }));
        uiManager.addObj(new UIImageButton(200, 100, 200, 200, Assets.playButton, new ClickListener() {
            @Override
            public void Clicked() {
                refLink.GetMouseManager().setUiManager(null);

                State.SetState(refLink.GetGame().getPlayState());
            }
        }));
    }


    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        if (refLink.GetMouseManager().getUiManager() == null)
            refLink.GetMouseManager().setUiManager(uiManager);

        uiManager.Update();
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        ///Desenarea imaginii de background
        g.drawImage(Assets.walpaper, 0,0,960, 832, null);
        ///Desenarea obiectelor UI
        uiManager.Draw(g);
    }
}
