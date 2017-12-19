package enigma_emulator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * This class is a child of the Sprite abstract class and dictates the behavior
 * of a single lighting key 
 * @author Alphatron99
 */
public class MenuLightKeys extends Sprite{
    private int glow=0;
    private int gc;
    
    /**
     * Initializes a light key at the given position and with given character.
     * @param x x coordinate of the key
     * @param y y coordinate of the key
     * @param c Character to be displayed on key 
     */
    public MenuLightKeys(int x, int y, char c) {
        super(x, y, ID.mp);
        this.c=c;
    }

    /**
     *Ticker for the light key.
     */
    @Override
    public void tick() {
       Glow();
    }

    /**
     * Renderer for the light key.
     * @param g  Graphic entity upon which render takes place.
     */
    @Override
    public void render(Graphics g) 
    {
        g.setColor(Color.WHITE);
        g.setFont(new Font("type keys",1,80));
        g.drawString(""+c,(int)x+5,(int)y+40);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(makeTransparent((float)glow/20));
        g.setColor(Color.YELLOW);
        g.drawString(""+c,(int)x+5,(int)y+40);
        g2d.setComposite(makeTransparent(1));
    }
    
    /**
     * Starts the process of glowing of the light key.
     */
    public void startGlow()
    {
        if(gc!=1)
        {
            gc=1;
        }
    }
    /**
     * Manages the brightening and dimming of the keys.
     */
    private void Glow()
    {
        if(gc==1)
        {
            glow++;
            if(glow>=20)
            {
                gc= -1;
                glow=20;
            }
        }
        else if(gc== -1)
        {
            glow--;
            if(glow<=0)
            {
                gc=0;
                glow=0;
            }
        }
    }
    
    /**
     * Transparency overlay composite maker.
     * @param Alpha Alpha value for the transparency: 0 is completely 
     * transparent and 1.0 is fully opaque. All values in between can be taken. 
     * @return Returns a composite with the transparency set to the given alpha
     * value.
     */
    private Composite makeTransparent(float Alpha) {
        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type, Alpha);
    }
    
}
