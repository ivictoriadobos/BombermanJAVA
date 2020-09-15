package PaooGame.UserInterface;


import java.awt.*;
import java.awt.event.MouseEvent;

/*!
\class UIObject
\brief Clasa abstracta ce defineste caracteristicile unui obiect pentru UI.

Ea va fi extinsa de UIImageButton.
 */
public abstract class UIObject {
    /// Coordonata x a obiectului UI.
    protected float x;

    /// Coordonata y a obiectului UI.
    protected float  y;

    /// Dreptunghiul de coliziune pentru obiectul UI.
    protected Rectangle bounds;

    /// Latimea obiectului UI.
    protected int width;

    ///Inaltimea obiectului UI.
    protected int height;

    /// Boolean ce retine daca mouseul utilizatorului este deasupra obiectului UI sau nu.
    protected boolean overThis = false;

/*!
\fn UIObject( float x, float y, int width, int height)
\brief Constructor ce seteaza coordonatele obiectului UI si construieste si dreptunghiul de coliziune

 */
    public UIObject( float x, float y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int)x +25, (int) y + 75 , width/2, height/2);
    }

    /*!
    \fn public abstract void Update()
    Metoda abstracta menita sa fie suprascrisa in clasele concrete. Se va ocupa cu actualizarea obiectului la input.
     */
    public abstract void Update();

    /*!
\fn public abstract void Draw()
Metoda abstracta menita sa fie suprascrisa in clasele concrete. Se va ocupa cu desenarea obiectului.
 */
    public abstract void Draw(Graphics g);

    /*!
\fn public abstract void Clicked()
Metoda abstracta menita sa fie suprascrisa in clasele concrete. Se va ocupa cu accesarea metodei Clicked() din subclase (ele vor avea o referinta catre ClickListener )
 */
    public abstract void Clicked();



    /*!
\fn public void MouseMove()
Metoda ce e apelata la orice eveniment de miscare al mouseului.
Ea decide daca mouseul este deasupra obiectului UI sau nu.
 */
    public void MouseMove(MouseEvent e)
    {
        if(bounds.contains(e.getX(), e.getY()))
            overThis = true;
        else overThis = false;

    }


    /*!
\fn public void MouseRelease()
Metoda ce e apelata la orice eveniment de terminare al clickului al mouseului.
Ea apeleaza metoda Clicked() suprascrisa in fiecare subclasa, oferind raspuns versatil fiecarui obiect UI implementat.
*/
    public void MouseRelease(MouseEvent e)
    {
        if(overThis)
            Clicked();


    }
    // Getters and setters

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isOverThis() {
        return overThis;
    }

    public void setOverThis(boolean overThis) {
        this.overThis = overThis;
    }
}
