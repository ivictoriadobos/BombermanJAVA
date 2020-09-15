package PaooGame.Graphics;

import PaooGame.Input.KeyManager;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private static int idxLE =0;
    private static int idxRE =0;
    private static int idxUE =0;
    private static int idxDE =0;
    public static BufferedImage heroLeft[];
    public static BufferedImage heroRight[];
    public static BufferedImage heroUp[];
    public static BufferedImage heroDown[];
    public static BufferedImage bomb[];
    public static BufferedImage box;
    public static BufferedImage background;
    public static BufferedImage indestructibleBlock;
    public static BufferedImage destructibleWall;
    public static BufferedImage portal;
    public static BufferedImage indestructibleWall;
    public static BufferedImage flame[];
    public static BufferedImage enemyLeft[], enemyRight[], enemyUp[], enemyDown[];
    public static BufferedImage playButton[];
    public static BufferedImage aboutButton[];
    public static BufferedImage backButton[];
    public static BufferedImage highscoresButton[];
    public static BufferedImage walpaper;
    public static BufferedImage about;
    public static BufferedImage speedItem;
    public static BufferedImage gameLost;
    public static BufferedImage gameWon;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init() throws IOException {
            /// Se creaza temporar obiecte SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/spritesheet.png"));
        SpriteSheet playerSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/player.png"));
        SpriteSheet bombsheet = new SpriteSheet(ImageLoader.LoadImage("/textures/bombeSprite.png"));
        SpriteSheet flameSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/flame.png"));
        SpriteSheet enemySheet = new SpriteSheet(ImageLoader.LoadImage("/textures/enemy.png"));
        SpriteSheet buttonsSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/buttons.png"));

        walpaper = ImageLoader.LoadImage("/textures/walpaper.png");
        about = ImageLoader.LoadImage("/textures/about.png");
        speedItem = ImageLoader.LoadImage("/textures/speed.png");
        gameLost = ImageLoader.LoadImage("/textures/LostGame.png");
        gameWon = ImageLoader.LoadImage("/textures/YouWin.png");
            /// Se obtin subimaginile corespunzatoare elementelor necesare.
        background =sheet.crop (64, 0, 64, 64 );
        box = sheet.crop(0, 64, 64, 64);
        indestructibleWall = sheet.crop(0, 0, 64, 64);
        indestructibleBlock = sheet.crop(2, 0);
        destructibleWall = sheet.crop(2, 1);
        portal = sheet.crop(1, 1);

        // bomb
        bomb = new BufferedImage[10];
        bomb[0] = bombsheet.crop(0,0); bomb[1] = bombsheet.crop(1,0); bomb[2] = bombsheet.crop(2,0); bomb[3] = bombsheet.crop(3,0); bomb[4] = bombsheet.crop(4,0);
        bomb[5] = bombsheet.crop(0,1); bomb[6] = bombsheet.crop(1,1); bomb[7] = bombsheet.crop(2,1); bomb[8] = bombsheet.crop(3,1); bomb[9] = bombsheet.crop(4,1);

        // flame
        flame = new BufferedImage[9];
        flame[0] = flameSheet.crop(2,2); flame[1] = flameSheet.crop(1,2);flame[2] = flameSheet.crop(0,2);flame[3] = flameSheet.crop(2,1);flame[4] = flameSheet.crop(1,1);
        flame[5] = flameSheet.crop(0,1);flame[6]=flameSheet.crop(2,0);flame[7] = flameSheet.crop(1,0);flame[8] = flameSheet.crop(0,0);

        // hero
        heroLeft = new BufferedImage[7];
        heroRight = new BufferedImage[7];
        heroUp= new BufferedImage[7];
        heroDown = new BufferedImage[7];

        // enemy
        enemyLeft = new BufferedImage[7];
        enemyRight = new BufferedImage[7];
        enemyDown = new BufferedImage[7];
        enemyUp = new BufferedImage[7];
        for(int i=0;i<7;i++)
        {
            heroLeft[i] = playerSheet.crop(i, 3);
            enemyLeft[i] = enemySheet.crop(i, 3);

            heroRight[i] = playerSheet.crop(i, 1);
            enemyRight[i] = enemySheet.crop(i, 1);

            heroUp[i] = playerSheet.crop(i, 0);
            enemyUp[i] = enemySheet.crop(i,0);

            heroDown[i] = playerSheet.crop(i, 2);
            enemyDown[i] = enemySheet.crop(i,2);
        }

        // buttons
        playButton = new BufferedImage[2];
        aboutButton = new BufferedImage[2];
        backButton = new BufferedImage[2];
        highscoresButton = new BufferedImage[2];


        aboutButton[0] = buttonsSheet.crop(200,0,200,200);
        aboutButton[1] = buttonsSheet.crop(0,0,200,200);

        playButton[0] = buttonsSheet.crop(200,400,200,200);
        playButton[1] = buttonsSheet.crop(0,400,200,200);

        backButton[0] = buttonsSheet.crop(200, 200, 200, 200);
        backButton[1] = buttonsSheet.crop(0, 200,200,200);

        highscoresButton[0] = buttonsSheet.crop(400, 200, 200, 200);
        highscoresButton[1] = buttonsSheet.crop(400, 0, 200, 200);



    }
    public static BufferedImage nextLeftHero()
    {

        if(idxL==18)
            idxL=0;
        else
            idxL++;
        return heroLeft[idxL/3];

    }
    public static BufferedImage nextRightHero()
    {

        if(idxR==18)
            idxR=0;
        else
            idxR++;
        return heroRight[idxR/3];

    }
    public static BufferedImage nextUpHero()
    {

        if(idxU==18)
            idxU=0;
        else
            idxU++;
        return heroUp[idxU/3];

    }
    public static BufferedImage nextDownHero()
    {

        if(idxD==18)
            idxD=0;
        else
            idxD++;
        return heroDown[idxD/3];

    }
    public static BufferedImage nextLeftEnemy()
    {

        if(idxLE==18)
            idxLE=0;
        else
            idxLE++;
        return enemyLeft[idxLE/3];

    }
    public static BufferedImage nextRightEnemy()
    {

        if(idxRE==18)
            idxRE=0;
        else
            idxRE++;
        return enemyRight[idxRE/3];

    }
    public static BufferedImage nextUpEnemy()
    {

        if(idxUE==18)
            idxUE=0;
        else
            idxUE++;
        return enemyUp[idxUE/3];

    }
    public static BufferedImage nextDownEnemy()
    {

        if(idxDE==18)
            idxDE=0;
        else
            idxDE++;
        return enemyDown[idxDE/3];

    }


}
