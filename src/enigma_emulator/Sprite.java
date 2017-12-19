package enigma_emulator;

import java.awt.Graphics;

/**
 * Abstract class containing the basic definition of a how a Sprite should 
 * should be structured.
 * @author Alphatron99
 */
public abstract class Sprite {
  

    protected int x, y;
    protected ID id;
    protected char c;

    public Sprite (int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    
    public char getChar()
    {
        return c;
    }
    
    public void setChar(char c)
    {
        this.c=c;
    }
    
    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
