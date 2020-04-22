package PaooGame.Graphics;

import PaooGame.Input.KeyManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    private static int idxL =0;
    private static int idxR =0;
    private static int idxU =0;
    private static int idxD =0;
    public static BufferedImage heroLeft[];
    public static BufferedImage heroRight[];
    public static BufferedImage heroUp[];
    public static BufferedImage heroDown[];

    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage mountain;
    public static BufferedImage townGrass;
    public static BufferedImage townGrassDestroyed;
    public static BufferedImage townSoil;
    public static BufferedImage water;
    public static BufferedImage rockUp;
    public static BufferedImage rockDown;
    public static BufferedImage rockLeft;
    public static BufferedImage rockRight;
    public static BufferedImage tree;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        SpriteSheet mySheet = new SpriteSheet(ImageLoader.LoadImage("/textures/spritesheetCustom.png"));
        SpriteSheet playerSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/player.png"));
            /// Se obtin subimaginile corespunzatoare elementelor necesare.
        grass =mySheet.crop (131, 1, 64, 64 );
      //  grass =sheet.crop (0,0 );
        soil = mySheet.crop(131, 67, 64, 64);
        water = mySheet.crop(72, 133, 64, 64);
        System.out.println("hey");
       // mountain = sheet.crop(3, 0);
       // townGrass = sheet.crop(0, 1);
      //  townGrassDestroyed = sheet.crop(1, 1);
      //  townSoil = sheet.crop(2, 1);
       // tree = sheet.crop(3, 1);
        heroLeft = new BufferedImage[7];
        heroRight = new BufferedImage[7];
        heroUp= new BufferedImage[7];
        heroDown = new BufferedImage[7];
        for(int i=0;i<7;i++)
            heroLeft[i] = playerSheet.crop(i, 3);
        for(int i=0;i<7;i++)
            heroRight[i] = playerSheet.crop(i, 1);
        for(int i=0;i<7;i++)
            heroUp[i] = playerSheet.crop(i, 0);
        for(int i=0;i<7;i++)
            heroDown[i] = playerSheet.crop(i, 2);

      //  heroRight = sheet.crop(48, 96, 48, 48);
       // rockUp = sheet.crop(2, 2);
        //rockDown = sheet.crop(3, 2);
       // rockLeft = sheet.crop(0, 3);
       // rockRight = sheet.crop(1, 3);
    }
    public static BufferedImage nextLeftHero()
    {

        if(idxL==24)
            idxL=0;
        else
            idxL++;
        return heroLeft[idxL/4];

    }
    public static BufferedImage nextRightHero()
    {

        if(idxR==24)
            idxR=0;
        else
            idxR++;
        return heroRight[idxR/4];

    }
    public static BufferedImage nextUpHero()
    {

        if(idxU==24)
            idxU=0;
        else
            idxU++;
        return heroUp[idxU/4];

    }
    public static BufferedImage nextDownHero()
    {

        if(idxD==24)
            idxD=0;
        else
            idxD++;
        return heroDown[idxD/4];

    }

}
