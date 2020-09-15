package PaooGame.Items.StaticItems;

import PaooGame.Items.Item;
import PaooGame.RefLinks;

///\class StaticItem
///\brief This class is like Character class, but for things that don't move
public abstract class StaticItem extends Item {

    ///\fn     public StaticItem(RefLinks refLink, float x, float y, int width, int height)
    /// Constructor ce apeleaza constructorul din superclasa.
    public StaticItem(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);
    }



}
