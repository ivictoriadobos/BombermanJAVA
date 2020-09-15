package PaooGame.UserInterface;

import java.awt.*;
import java.awt.image.BufferedImage;

/*!
\class UIImageButton
\brief Implementare Concreta a UIObject. Reprezinta un buton.
 */
public class UIImageButton extends UIObject{

    /// Fiecare buton are 2 imagini : una cand mouseul nu este deasupra lui si una cand este. Cele doua imagini sunt stocate in aceasta variabila.
    private BufferedImage[] images;

    /// Referinta catre un obiect clicker.
    /// Pentru fiecare buton se poate suprascrie metoda clicler.CLicked() oferind astfel comportament dinamic pentru fiecare buton intr-un mod flexibil.
    private ClickListener clicker;


    /*!
    \fn public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker)
    \brief Constructor pentru un buton din UI. El seteaza coordonatele, inaltimea, latimea si referinta catre atributul ClickListener.
     */
    public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
        super(x, y, width, height);
        this.images = images;
        this.clicker = clicker;
    }

    /*!
    \fn public void Update()

    Metoda menita sa actualizeze butonul UI. Empty momentan.
     */
    @Override
    public void Update() {
    }


/*!
\fn public void Draw(Graphics g)

Metoda menita sa deseneze butonul.
In functie de pozitia mouseului se deseneaza una din doua imagini dispobile pentru buton.
 */
    @Override
    public void Draw(Graphics g) {

        if(overThis)
            g.drawImage(images[1], (int)x, (int) y, width, height, null);
        else
            g.drawImage(images[0], (int) x, (int) y, width, height, null);
       // g.setColor(Color.blue);
     //   g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /*!
    \fn public void Clicked()
    Metoda menita polimorfismului.
    Cand se construieste un buton se suprascrie metoda Clicked a referintei catre ClickListener din fiecare obiect UIImageButton.
     */
    @Override
    public void Clicked() {
        clicker.Clicked();
    }
}
