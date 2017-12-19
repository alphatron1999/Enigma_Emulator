package enigma_emulator;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * This class called handler is used to make the ticking and rendering of 
 * multiple objects much easier and without much clutter in the main code. 
 * @author Alphatron99
 */
public class Handler {
    LinkedList<Sprite> object = new LinkedList<Sprite>();

    /**
     * Ticks all the Sprites present in the handler.
     */
    public void tick() {
        for(int i = 0; i< object.size(); i++) {
            Sprite tempObject = object.get(i);

            tempObject.tick();
        }
    }

    /**
     * Renders every object in list on the passed Graphic entity passed
     * @param g Graphic entity upon which the rendering takes place.
     */
    public void render(Graphics g) {
        for(int i = 0; i < object.size(); i++) {
            Sprite tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    /**
     * Adds a specific Sprite to the handler
     * @param object Sprite to be added
     */
    public void addObject(Sprite object) {
        this.object.add(object);
    }

    /**
     * Removes a specific Sprite from the handler
     * @param object Sprite to be removed
     */
    public void removeObject(Sprite object) {
        this.object.remove(object);
    }
    
    /**
     * Empties the handler.
     */
    public void empty()
    {
        object= new LinkedList<>();
    }
}
