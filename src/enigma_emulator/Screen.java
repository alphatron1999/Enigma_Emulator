package enigma_emulator;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * This is the project's main class. It is the class that will run first. It
 * manages all the tick and render cues. 
 * @author Alphatron99
 */
public class Screen extends Canvas implements Runnable{

    public static final int WIDTH = 900, HEIGHT = WIDTH / 12 * 9+50;
    private Thread thread;
    private boolean running = false;
    private Menu menu;
    private Handler handler;
    public enum STATE
    {
        Menu,
        En
    };
    
    STATE state=STATE.Menu;
    
    /**
     * Initializes the screen by adding the event listeners and then setting it
     * in a frame through Window.
     */
    public Screen()
    {
        handler = new Handler();
        menu=new Menu(this,handler);
        this.addMouseListener(menu);
        this.addKeyListener(new KeyInput(menu,handler,this));
        new Window(WIDTH, HEIGHT, "World's Greatest Enemy", this);
    }
    
    @Override
    /**
     * This handles the tick and the render cues. The render is done as many 
     * times as possible but ticking will happen at a constant rate of 60 ticks 
     * per second. Also grabs the mouse focus directly on starting.
     */
    public void run() 
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        //long timer = System.currentTimeMillis();
        //int frames = 0;
        while(running) 
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) 
            {
                tick();
                delta--;
            }
            if(running)
                render();
            /*
            Following code was used to test FPS while developing:
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
            frames = 0;
            }*/
        }
        stop();
    }
    
    /**
     * Starts the thread which will run the code
     */
    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    /**
     * Kills the thread running the code. Stops the execution of the program.
     */
    public synchronized void stop() {
        try 
        {
            thread.join();
            running = false;
        }
        catch (Exception e) 
        {
            javax.swing.JOptionPane.showMessageDialog(null,e);
        }
    }
    
    /**
     * The master ticker of the project.
     */
    private void tick()
    {
        handler.tick();
    }
    
    /**
     * The master renderer of the project. All render call go through here.
     * It uses triple buffer strategy to render the graphics.
     */
    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) 
        {
           this.createBufferStrategy(3);
           return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);
        menu.render(g);
        g.dispose();
        bs.show();
    }
    
    public static void main (String [] args) 
    {
        //Giving enough time for the splashscreen to appear.
        try
        {
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
        
        }
        new Screen();
    }
}
