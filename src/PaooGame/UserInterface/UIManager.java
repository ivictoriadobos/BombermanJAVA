package PaooGame.UserInterface;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*!
\class UIManager
\brief Clasa responsabila de gestionarea UI

Aici se retin toate obiectele de tip UIObject din joc.
Clasa contine metode existente in UIObject (Update, Draw, MoveMouse, MouseRelease) in care se apeleaza pentru toate obiectele retinute aceeasi metoda, plus inca doua metode
de intretinere (addObj si deleteObj);
 */

public class UIManager {
    /// Referinta catre obiectul cu referinte importante ale jocului.
    private RefLinks refLinks;

    ///Lista de obiecte UI.
    private ArrayList<UIObject> uiObjects;

    /*!
    \fn public UIManager ( RefLinks refLinks )
    Constructor ce seteaza atrinutul refLinks al clasei si initializeaza lista de obiecte
     */
    public UIManager ( RefLinks refLinks)
    {
        this.refLinks = refLinks;
        uiObjects = new ArrayList<UIObject>();
    }

    ///Update pentru toate obiectele UI ale jocului
    public void Update(){
        for (UIObject obj : uiObjects)
            obj.Update();

    }

    ///Draw pentru toate obiectele UI ale jocului
    public void Draw(Graphics g)
    {
        for(UIObject obj : uiObjects)
            obj.Draw(g);
    }

    public void MoveMouse(MouseEvent e)
    {
        for(UIObject obj : uiObjects)
            obj.MouseMove(e);
    }

    public void MouseRelease( MouseEvent e)
    {
        for(UIObject obj : uiObjects)
            obj.MouseRelease(e);
    }

    public void addObj ( UIObject obj )
    {
        uiObjects.add(obj);
    }

    public void deleteObj( UIObject obj)
    {
        uiObjects.remove(obj);
    }
}
