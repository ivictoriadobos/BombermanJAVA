package PaooGame.Maps;

import PaooGame.Game;
import PaooGame.Items.Item;

public class GameCamera {
    private float xOffset, yOffset;
    private Game game;
    public GameCamera(Game game, float xOffset, float yOffset)
    {
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centerOnEntity(Item e)
    {
        xOffset = e.GetX() - game.GetWidth()/2 + e.GetWidth()/2;
        yOffset = e.GetY() - game.GetHeight()/2 + e.GetHeight()/2;
    }
    public float getxOffset() {
        return xOffset;
    }
    public float getyOffset()
    {
        return yOffset;
    }
    public void setxOffset(float x)
    {
        xOffset = x;
    }

    public void setyOffset(float y)
    {
        yOffset = y;
    }

    public void move(float xAmt, float yAmt)
    {
        xOffset+=xAmt;
        yOffset+=yAmt;
    }
}
