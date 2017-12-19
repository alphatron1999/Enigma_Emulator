package enigma_emulator;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Handles all keystroke related events in the program.
 * @author Alphatron99
 */
public class KeyInput extends KeyAdapter{
    Menu menu;
    Handler handler;
    Screen screen;
    Enigma en;
    int ctr=0;
    Random r;
    KeyInput(Menu menu,Handler handler,Screen sc)
    {
        this.handler=handler;
        this.menu=menu;
        screen=sc;
        r=new Random();
    }
    
    /**
     * Initializes an instance of Enigma based on setting stored in SVals
     */
    public void startMachine()
    {
        en= new Enigma(SVals.r1,SVals.r2,SVals.r3,SVals.rf,SVals.pb,SVals.ringSet,SVals.groundSet);
    }

    @Override
    /**
     * The key event which acts on key presses.
     * If the program is in Menu mode it translates the bars of MenuEffect and 
     * makes the rotors in the the menu rotate.
     * If the program is in Enigma mode it encodes the letter pressed.
     * If Escape key is pressed, the program shuts down.
     */
    public void keyPressed(KeyEvent e)
    {
        int key=e.getKeyCode();
        if(key==KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
        else
        {
            if(screen.state.equals(Screen.STATE.Menu))
            {
                menu.tick();
                r=new Random();
                char lc=Affiliate.intConv(r.nextInt(25)+1);
                Color colp;
                Color col=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
                col=col.brighter();
                    for(int i=0;i<handler.object.size();i++)
                    {
                        if(handler.object.get(i).getId()==ID.Effect)
                        {
                            MenuEffect m=(MenuEffect)handler.object.get(i);
                            char tc=Character.toUpperCase(m.getChar());
                            colp=m.col;
                            m.col=col;
                            m.setChar(lc);
                            lc=tc;
                            col=colp;
                        }
                    }  
            }
            else if(screen.state.equals(Screen.STATE.En))
            {
                if(menu.starter)
                {
                    startMachine();
                    ctr=0;
                    menu.starter=false;
                }
                char kc=e.getKeyChar();
                if(Character.isAlphabetic(kc))
                {
                    ctr++;
                    menu.addNtxt(Character.toString(Character.toUpperCase(kc)));
                    kc=en.EncodeLetter(Character.toUpperCase(kc));
                    menu.r3=menu.pushBack(menu.pushBack(SVals.r1.getCurrent()));
                    menu.r2=menu.pushBack(menu.pushBack(SVals.r2.getCurrent()));
                    menu.r1=menu.pushBack(menu.pushBack(SVals.r3.getCurrent()));
                    menu.addEntxt(Character.toString(Character.toUpperCase(kc)));
                    if(ctr%4==0)
                    {
                        menu.addNtxt(" ");
                        menu.addEntxt(" ");
                    }
                    for(int i=0;i<handler.object.size();i++)
                    {
                        if(handler.object.get(i).getId()==ID.mp&&kc==handler.object.get(i).getChar())
                        {
                            MenuLightKeys m=(MenuLightKeys)handler.object.get(i);
                            m.startGlow();
                        }
                    }
                }
            }
        }
    }
}
