package PaooGame.Input;

import PaooGame.UserInterface.UIManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

    private int xmouse, ymouse;
    private boolean left, right;

    private UIManager uiManager;

    public void Update()
    {

    }

    public UIManager getUiManager() {
        return uiManager;
    }

    public void setUiManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }



    @Override
    public void mouseMoved(MouseEvent e) {
        xmouse = e.getX();
        ymouse = e.getY();
        if (uiManager != null)
            uiManager.MoveMouse(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) //left MB
        {
            left = true;
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            right = true;
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) //left MB
        {
            left = false;
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            right = false;
        }

        if (uiManager != null)
            uiManager.MouseRelease(e);

    }

    public boolean isLeft()
    {
        return left;
    }
    public boolean isRight()
    {
        return right;
    }


    public int getXmouse() {
        return xmouse;
    }

    public int getYmouse() {
        return ymouse;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
