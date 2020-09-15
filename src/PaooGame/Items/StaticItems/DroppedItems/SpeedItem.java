package PaooGame.Items.StaticItems.DroppedItems;

import PaooGame.Graphics.Assets;
import PaooGame.Items.StaticItems.StaticItem;
import PaooGame.Items.View;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;
import java.awt.*;
import java.awt.image.BufferedImage;

/*!
\class SpeedItem
\brief Aceasta clasa implementeaza notiunea de item bonus ce apare dupa distrugerea unui obiect destructibil din joc.
\warning Aceasta clasa implementeaza View de la sablonul Observer, dar momentan in aceasta versiune sunt redundante metodele specifice, prin urmare nu serveste rolul de view cu adevarat.
Momentan poate fi "luat" doar de Hero.
 */
public class SpeedItem extends StaticItem implements View {

    /*!
    \fn    public SpeedItem(RefLinks refLink, float x, float y, int width, int height)
    Constructorul clasei ce apeleaza constructorul din superclasa (SpeedItem extinde StaticItem si implementeaza View)
     */

    public SpeedItem(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x , y , width , height );
        ///Se seteaza obiectul ca fiind destructibil
        this.destructible = true;
        ///Se seteaza un dreptunghi de coliziune cu alte limite decat cele standard
        this.bounds = new Rectangle(15,15, Tile.TILE_WIDTH-30, Tile.TILE_HEIGHT- 30)   ;

    }


    /*!
    \fn    public boolean RemoveItem()
    Metoda suprascrisa ce e responsabila cu eliminarea obiectului curent din lista de items a jocului.
     */
    @Override
    public boolean RemoveItem() {

        return  refLink.GetMap().getItemManager().GetItems().remove(this);
    }


    /*!
    \fn     public void Update()
    Metoda suprascrisa ce e responsabila cu actualizarea obiectului SpeedItem
     */
    @Override
    public void Update() {
        ///Se verifica daca eroul are contact cu acest item si daca da, ii incrementeaza valoare de viteza, dupa care apeleaza RemoveItem() (Deoarece itemul a fost revendicat si e unic)
        if (refLink.GetMap().getItemManager().GetHero() != null) {
            if (refLink.GetMap().getItemManager().GetHero().getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))) {
                refLink.GetMap().getItemManager().GetHero().SetSpeed(refLink.GetMap().getItemManager().GetHero().GetSpeed() + 1);
                RemoveItem();

            }

        }
    }


/*
    @Override
    public void UpdateViews() {
        Unregister();  //odata creat obiectul da unsubscribe
    }

    @Override
    public void Register(Subject subject) {
        this.SetSubject(subject);
        this.GetSubject().AddView(this);

    }

    @Override
    public void Unregister() {
        if (GetSubject()!=null)
        {
            GetSubject().ReleaseView(this);
            this.SetSubject(null);
            }

    }

    @Override
    public void SetSubject(Subject subject) {
        if (GetSubject() == null )
            this.SetSubject(DestroyManager.GetInstance()); //pot face doar o data asignarea deoarece subiectul e unic
        else
             return;
    }


 */

    /*!
    \fn    public void Draw(Graphics g)
    Metoda suprascrisa responsabila cu randarea imaginii obiectului curent
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(Assets.speedItem, (int)(x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y - refLink.GetGame().GetGameCamera().getyOffset()), width, height, null);
       // g.setColor(Color.blue);
     //   g.fillRect((int)(x + bounds.x - refLink.GetGame().GetGameCamera().getxOffset()), (int)(y + bounds.y - refLink.GetGame().GetGameCamera().getyOffset()), bounds.width, bounds.height);

    }
}
