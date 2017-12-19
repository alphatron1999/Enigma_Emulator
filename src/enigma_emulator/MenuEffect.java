package enigma_emulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * This class dictates the working of a single colored bar that is seen in the 
 * background. It is the child of the Sprite abstract class.
 * @author Alphatron99
 */
public class MenuEffect extends Sprite{
    private int ht,wd;
    private int hc=1;
    Color col;

    /**
     * Initializes a MenuEffect with the specified characteristics.
     * @param x The x coordinate of top-right corner of the bar
     * @param y The y coordinate of top-right corner of the bar
     * @param wd Width of the bar 
     * @param col Color of the bar
     * @param hc It is 1 if the bar is going down and -1 if it is going up
     */
    public MenuEffect(int x, int y, int wd, Color col, int hc) {
        super(x, y, ID.Effect);
        this.ht=Screen.HEIGHT-y;
        this.wd=wd;
        this.col=col;
        this.hc=hc;
        Random r=new Random();
        c=Affiliate.intConv(r.nextInt(25)+1);
    }

    /**
     * Ticker for the Menu Effect(Bar)
     */
    @Override
    public void tick() 
    {
            if(hc==1)
            {
                y+=2;
                ht=(Screen.HEIGHT-y);
                if(ht<=100)
                {
                    hc=-1;
                }
            }
            else if(hc==-1)
            {
                y-=2;
                ht=(Screen.HEIGHT-y);
                if(ht>=600)
                {
                    hc=1;
                }
            }
    }
    
    /**
     * Renderer for the Menu Effect(Bar).
     * @param g Graphic entity upon which render takes place.
     */
    @Override
    public void render(Graphics g) 
    {
        g.setColor(col);
        g.fillRect((int)x,(int)y, wd, ht);
        g.setFont(new Font("adequate",1,45));
        g.drawString(""+c, x+5, y-4);
    }    
}
