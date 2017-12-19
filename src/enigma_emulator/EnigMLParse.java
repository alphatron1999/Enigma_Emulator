package enigma_emulator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * EnigML XML file parser and validator.
 * @author Alphatron99
 */
public class EnigMLParse {
    Document doc;
    /**
     * Initializes the EnigML parser and readies the file for processing and
     * data extraction.
     * @param inputFile File to be processed.
     * @throws Exception Throws exception if XML file does not confirm to
     * general XML standards and rules.
     */
    EnigMLParse(File inputFile) throws Exception
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc=dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
    }

    /**
     * Parses file, extracts the data out of the EnigML XML File and validates 
     * the format. 
     * @return List with an array of array of Object where each array of Object
     * is of the format {type,position,data,notch1,notch2}. In cases except when
     * type is rotor, notch1, notch2 and position will be empty.
     * @throws Exception If the file does not confirm to the standards dictated,
     * an exception will be raised saying so.
     */
    public List<Object[]> Fetch() throws Exception
    {
        List<Object[]> li=new ArrayList<>();
        NodeList nList = doc.getElementsByTagName("config");
        
        for (int i = 0; i < nList.getLength(); i++) 
        {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) 
            {
               String ty;
               String nm;
               String dt;
               String nt1="";
               String nt2="";
               Element eElement = (Element) nNode;
               ty=eElement.getAttribute("type");
               nm="";
               if(ty.equals("rotor"))
               {
                   nm=eElement.getAttribute("position");
                   nt1=eElement.getAttribute("notch1");
                   nt2=eElement.getAttribute("notch2");
               }
               dt=eElement.getElementsByTagName("data").item(0).getTextContent();
               li.add(new Object[]{ty,nm.toUpperCase(),dt.toUpperCase(),nt1.toUpperCase(),nt2.toUpperCase()});
            }
        }
        
        int i=0;
        Exception e=new Exception("EnigML File Failed Validation");
        for(Object[] a:li)
        {
            if(a[0].equals("rotor"))
            {
                String n1=(String)a[3];
                String n2=(String)a[4];
                if(Rotor.isValidRotor((String)a[2])&&(n1.length()==1)&&(n1.length()==1)&&(n2.length()==1)&&(Affiliate.isAllLetter(n1))&&(Affiliate.isAllLetter(n2)))
                {
                    int ch=-1;
                    if(a[1].equals("L"))
                    {
                        ch=0;
                    }
                    else if(a[1].equals("M"))
                    {
                        ch=1;
                    }
                    else if(a[1].equals("R"))
                    {
                        ch=2;
                    }
                    else
                    {
                        throw e;
                    }
                    if((i&(1<<ch))==0)
                    {
                        i=(i|(1<<ch));
                    }
                    else
                    {
                        throw e;
                    }
                }
                else
                {
                    throw e;
                }
            }
            else if(a[0].equals("plugboard"))
            {
                if(Plugboard.isValidSwap((String)a[2])&&((i&(1<<3))==0))
                {
                    i=(i|(1<<3));
                }
                else
                {
                    throw e;
                }
            }
            else if(a[0].equals("reflector"))
            {
                 if(Reflector.isValidSet((String)a[2])&&((i&(1<<4))==0))
                 {
                    i=(i|(1<<4));
                 }
                 else
                 {
                     throw e;
                 }
            }
            else if(a[0].equals("setting"))
            {
                String st=(String)a[2];
                if((st.length()==6&&Affiliate.isAllLetter(st))&&((i&(1<<5))==0))
                {
                    i=(i|(1<<5));
                }
                else
                {
                    throw e;
                }
            }
            else
            {
                throw e;
            }
        }
        return li;
    }
}
