package enigma_emulator;
import javax.swing.JTextArea;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;
/**
 * This is the class for the rendering, ticking and other visual functionality 
 * associated with the menu and the machine. This class has code which dictates
 * the look and behavior of the entire UI in this project. It also doubles as
 * a mouse event listener and handler.
 * @author Alphatron99
 */
public class Menu extends MouseAdapter{
    private Screen screen;
    private Handler handler;
    public char r3='C',r2='L',r1='G';
    JTextArea a=new JTextArea();
    boolean starter=false;
    String ntxt="";
    String entxt="";
    String msg="Default Setting in Use   Ground Setting: ABC   Ring Setting: AAA";
    Menu(Screen screen,Handler handler)
    {
        this.screen=screen;
        this.handler=handler;
        addParticles();
    }
    
    /**
     * Copies the encoded text to clipboard.
     */
    public void cpToCb()
    {
        a.setText(entxt);
        a.selectAll();                                                                                                                                                
        a.copy();
    }

    /**
     * Adds a string to the string entxt(Encoded text)
     * @param n String to be added
     */
    public void addEntxt(String n)
    {
        entxt=entxt+n;
    }
    
    /**
     * Adds a string to the string ntxt(Normal text)
     * @param n String to be added
     */
    public void addNtxt(String n)
    {
        ntxt=ntxt+n;
    }
    @Override
    /**
     * Handles the mouse press events based on position of mouse on screen
     * where it was clicked.
     */
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if(screen.state==Screen.STATE.Menu)
        {
        
        if(mouseOver(mx,my,screen.getWidth()/2-100,130, 200, 50))
        {
            //BEGIN
            starter=true;
            r3=SVals.groundSet.charAt(0);
            r2=SVals.groundSet.charAt(1);
            r1=SVals.groundSet.charAt(2);
            for(int i=1;i<=2;i++)
            {
                r3=pushBack(r3);
                r2=pushBack(r2);
                r1=pushBack(r1);
            }
            screen.state=Screen.STATE.En;
            handler.empty();
            String letr="QWERTYUIOPASDFGHJKLZXCVBNM";
            int x=20;
            int y=400;
            for(int i=0;i<10;i++)
            {
                handler.addObject(new MenuLightKeys(x,y,letr.charAt(i)));
                x+=87;
            }
            x=60;
            y=500;
            for(int i=10;i<19;i++)
            {
                handler.addObject(new MenuLightKeys(x,y,letr.charAt(i)));
                x+=87;
            }
            x=140;
            y=600;
            for(int i=19;i<26;i++)
            {
                handler.addObject(new MenuLightKeys(x,y,letr.charAt(i)));
                x+=87;
            }
        }
        else if(mouseOver(mx,my,screen.getWidth()/2-140,230, 280, 50))
        {
            //SETTINGS
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter;
            filter = new FileNameExtensionFilter("EnigML XML File", "xml");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
                File f=chooser.getSelectedFile();
                try
                {
                    EnigMLParse pr=new EnigMLParse(f);
                    List<Object[]> li = pr.Fetch();
                    for(Object[] a:li)
                    {
                        if(a[0].equals("rotor"))
                        {
                            char n1=((String)a[3]).charAt(0);
                            char n2=((String)a[4]).charAt(0);
                            if(a[1].equals("L"))
                            {
                                SVals.r1=new Rotor((String)a[2],n1,n2);
                            }
                            else if(a[1].equals("M"))
                            {
                                SVals.r2=new Rotor((String)a[2],n1,n2);
                            }
                            else if(a[1].equals("R"))
                            {
                                SVals.r3=new Rotor((String)a[2],n1,n2);
                            }
                        }
                        else if(a[0].equals("plugboard"))
                        {
                            SVals.pb=new Plugboard((String)a[2]);
                        }
                        else if(a[0].equals("reflector"))
                        {
                            SVals.rf=new Reflector((String)a[2],false);
                        }
                        else if(a[0].equals("setting"))
                        {
                            String s=(String)a[2];
                            SVals.groundSet=s.substring(0,3);
                            SVals.ringSet=s.substring(3,6);
                        }
                    }
                    msg=f.getName()+" is in Use"+"   Ground Setting: "+SVals.groundSet+"   Ring Setting: "+SVals.ringSet;
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
            
        }
        else if(mouseOver(mx,my,screen.getWidth()/2-100,330, 200, 50))
        {
            //EXIT
            System.exit(0);
        }
        }
        else if(screen.state==Screen.STATE.En)
        {
            if(mouseOver(mx,my,20,90,130,50))
            {
                //BACK
                screen.state=Screen.STATE.Menu;
                ntxt="";
                entxt="";
                handler.empty();
                addParticles();
            }
            else if(mouseOver(mx,my,740,90,130,50))
            {
                //COPY
                cpToCb();
            }
        }
    }
    //Adds the bars(MenuEffect) to the handler 
    private void addParticles()
    {
        int xc=5,yc=200,g=1;
        Color col;
        Random r=new Random();
        for(int i=1;i<=8;i++)
        {
            col=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
            col=col.brighter();
            handler.addObject(new MenuEffect(xc,yc,65,col,g));
            xc+=75;
            yc+=50;
        }
        g=-1;
        col=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        col=col.brighter();
        handler.addObject(new MenuEffect(xc,yc,65,col,g));
        g=-1;
        yc-=50;
        xc+=75;
        for(int i=10;i<=12;i++)
        {
            col=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
            col=col.brighter();
            handler.addObject(new MenuEffect(xc,yc,65,col,g));
            xc+=75;
            yc-=50;
        }
    }
    //Checks if mouse is within the dictated region
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height)
    {
        if (mx > x && mx < x + width)
        {
            if (my > y && my < y + height)
            {
                return true;
            }
            else return false;
        }
        else return false;

    }
    
    /**
     * 
     * @param a Character whose following alphabet is required
     * @return Returns the required character
     */
    private char advance(char a)
    {
        if(a=='Z')
        {
            a='A';
        }
        else
        {
            a++;
        }
        return a;
    }
    
    /**
     * Goes reverse in alphabetical sequence by 1 move
     * @param c Character whose previous alphabet is required
     * @return Returns the required character
     */
    public char pushBack(char c)
    {
        if(c=='A')
        {
            c='Z';
        }
        else
        {
            c=Affiliate.intConv(Affiliate.charConv(c)-1);
        }
        return c;
    }

    /**
     * Changes the variables r1,r2 and r3 according to pattern followed by 
     * the Enigma machine when Screen is in Menu mode.
     */
    public void tick()
    {
        if(r1=='V')
        {
            if(r2=='E')
            {
                r3=advance(r3);
            }
            r2=advance(r2);
        }
        else if(r2=='E')
        {
            r3=advance(r3);
            r2=advance(r2);
        }
        r1=advance(r1);
    }

    /**
     * Renders a frame of the buttons, effects, etc. that we see.
     * @param g Graphic object to be used to render the changes on
     */
    public void render(Graphics g)
    {
        int lcx,lcy,losx,losy;
        lcx=MouseInfo.getPointerInfo().getLocation().x;
        lcy=MouseInfo.getPointerInfo().getLocation().y;
        losx=screen.getLocationOnScreen().x;
        losy=screen.getLocationOnScreen().y;
        if(screen.state==Screen.STATE.Menu)
        {
            g.setColor(Color.black);
            g.fillRect(screen.getWidth()/2-100,130, 200, 50);
            g.fillRect(screen.getWidth()/2-140,230, 280, 50);
            g.fillRect(screen.getWidth()/2-100,330, 200, 50);
            g.fillRect(260,430,70,250);
            g.fillRect(410,430,70,250);
            g.fillRect(560,430,70,250);
            g.setColor(Color.white);
            g.setFont(new Font("veteran typewriter",1,70));
            g.drawString("ENIGMA EMULATOR", screen.getWidth()/2-320, 80);
            g.setFont(new Font("adequate",1,45));
            if(mouseOver(lcx,lcy,screen.getWidth()/2-100+losx,130+losy, 200, 50))
            {
                g.setColor(Color.yellow);
            }
            else
            {
                g.setColor(Color.white);
            }
            g.drawRect(screen.getWidth()/2-100,130, 200, 50);
            g.drawString("BEGIN", screen.getWidth()/2-83, 173);
            if(mouseOver(lcx,lcy,screen.getWidth()/2-140+losx,230+losy, 280, 50))
            {
                g.setColor(Color.yellow);
            }
            else
            {
                g.setColor(Color.white);
            }
            g.drawRect(screen.getWidth()/2-140,230, 280, 50);
            g.drawString("SETTINGS", screen.getWidth()/2-134, 273);
            if(mouseOver(lcx,lcy,screen.getWidth()/2-100+losx,330+losy,200, 50))
            {
                g.setColor(Color.red);
            }
            else
            {
                g.setColor(Color.white);
            }
            g.drawString("EXIT", screen.getWidth()/2-55, 373);
            g.setColor(Color.red);
            g.drawRect(screen.getWidth()/2-100,330, 200, 50);
            g.setColor(Color.BLUE);
            g.setFont(new Font("adequate",1,40));
            g.drawRect(260,430,70,250);
            g.drawRect(410,430,70,250);
            g.drawRect(560,430,70,250);
            char a=r3,b=r2,c=r1;
            int xc=275,yc=472;
            for(int i=1;i<=5;i++)
            {
                switch (i) {
                    case 5:
                    case 1:
                        g.setColor(Color.DARK_GRAY);
                        break;
                    case 3:
                        g.setColor(Color.WHITE);
                        break;
                    default:
                        g.setColor(Color.GRAY);
                        break;
                }
                g.drawString(""+a, xc,yc);
                g.drawString(""+b, xc+150,yc);
                g.drawString(""+c, xc+300,yc);
                yc=yc+50;
                a=advance(a);
                b=advance(b);
                c=advance(c);
            }
    }
        else if(screen.state.equals(Screen.STATE.En))
        {
            g.setColor(Color.YELLOW.darker());
            g.setFont(new Font("courier new",1,30));
            g.drawRoundRect(260, 30, 70, 250, 50, 50);
            g.drawRoundRect(410,30,70,250,50,50);
            g.drawRoundRect(560,30,70,250,50,50);
            
            if(mouseOver(lcx,lcy,20+losx,90+losy,130,50))
            {
                g.setColor(Color.red);
            }
            else
            {
                g.setColor(Color.YELLOW.darker());
            }
            g.drawRect(20,90,130,50);
            g.drawString("BACK",50,125);
            if(mouseOver(lcx,lcy,740+losx,90+losy,130,50))
            {
                g.setColor(Color.red);
            }
            else
            {
                g.setColor(Color.YELLOW.darker());
            }
            g.drawRect(740,90,130,50);
            g.drawString("COPY",770,125);
            g.setColor(Color.YELLOW.darker());
            if(ntxt.length()>49)
            {
                g.drawString(ntxt.substring(ntxt.length()-49),8,320);
                g.drawString(entxt.substring(entxt.length()-49),8,360);
            }
            else
            {
                g.drawString(ntxt,8,320);
                g.drawString(entxt,8,360);
            }
            char a=r3,b=r2,c=r1;
            int xc=275,yc=72;
            g.setFont(new Font("adequate",1,40));
            for(int i=1;i<=5;i++)
            {
                switch (i) {
                    case 5:
                    case 1:
                        g.setColor(Color.DARK_GRAY);
                        break;
                    case 3:
                        g.setColor(Color.WHITE);
                        break;
                    default:
                        g.setColor(Color.GRAY);
                        break;
                }
                g.drawString(""+a, xc,yc);
                g.drawString(""+b, xc+150,yc);
                g.drawString(""+c, xc+300,yc);
                yc=yc+50;
                a=advance(a);
                b=advance(b);
                c=advance(c);
            }
            g.setColor(Color.GREEN);
            g.setFont(new Font("adequate",1,15));
            g.drawString(msg,10,680);
        }
}

}
