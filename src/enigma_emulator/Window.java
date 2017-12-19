package enigma_emulator;

import java.awt.Dimension;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class makes a frame and then sets the Screen into it so that it can be 
 * viewed and interacted with by the user.
 * @author Alphatron99
 */
public class Window extends Canvas
{
    /**
     * Creates a frame with the given characteristics and set the screen into 
     * it. Also imports the fonts into the environment because the font may or
     * may not be installed in the host machine.
     * @param width Width of the frame
     * @param height Height of the frame
     * @param title Title that should appear in the title bar of the frame
     * @param screen The screen entity that is to be set into the frame.
     */ 
    public Window(int width, int height, String title,Screen screen)
    {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(screen);
        frame.setVisible(true);
        try 
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,this.getClass().getClassLoader().getResourceAsStream("res/C.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,this.getClass().getClassLoader().getResourceAsStream("res/F.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,this.getClass().getClassLoader().getResourceAsStream("res/D.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,this.getClass().getClassLoader().getResourceAsStream("res/cour.ttf")));
        } 
        catch (IOException|FontFormatException e) 
        {
            JOptionPane.showMessageDialog(null,e);
        }
        screen.start();
    }
}
