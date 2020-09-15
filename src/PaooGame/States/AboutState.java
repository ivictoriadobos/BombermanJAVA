package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.UserInterface.ClickListener;
import PaooGame.UserInterface.UIImageButton;
import PaooGame.UserInterface.UIManager;

import java.awt.*;

/*! \class AboutState
    \brief Implementeaza notiunea de credentiale (about)
 */
public class AboutState extends State
{
    /// Referinta catre un manager pentru UI specific starii MenuState
    private UIManager uiManager;

    /*! \fn public AboutState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public AboutState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        ///Se construieste un nou manager de obiecte UI
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
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniu about.
     */
    @Override
    public void Update()
    {
        if (refLink.GetMouseManager().getUiManager() == null)
            refLink.GetMouseManager().setUiManager(uiManager);
        uiManager.Update();

    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniu about.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        ///Se deseneaza imaginea de background
        g.drawImage(Assets.walpaper, 0,0,960, 832, null);
        ///Se deseneaza imaginea specifica starii AboutState
        g.drawImage(Assets.about, 230, 100, 520, 590, null);
        ///Se deseneaza obiectele UI
        uiManager.Draw(g);
        

    }
}
