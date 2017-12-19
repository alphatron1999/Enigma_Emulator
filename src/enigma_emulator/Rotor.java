package enigma_emulator;

/**
 * This class defines and dictates the behavior of a single rotor.
 * @author Alphatron99
 */
public class Rotor
{
    private char RingSetting='A';
    private char CurrSetting='A';
    final private char Notch1;
    final private char Notch2;
    private int[] Wiring=new int[26];

    /**
     * Initializes the rotor with the given settings.
     * @param wir Wiring to be used.
     * @param NT1 Character to set as the first notch.
     * @param NT2 Character to be set as the second notch.
     */
    public Rotor(String wir,char NT1,char NT2)
    {
        for(int i=0;i<wir.length();i++)
        {
            Wiring[i]=Affiliate.charConv(wir.charAt(i));
        }
        Notch1=NT1;
        Notch2=NT2;
    }

    /**
     * Sets the current/ground setting of the rotor.
     * @param a Character to be used as the current setting.
     */
    public void setCurrent(char a)
    {
        CurrSetting=a;
    }

    /**
     * Sets the ring setting of the rotor.
     * @param a Character to be used as the ring setting.
     */
    public void setRing(char a)
    {
        RingSetting=a;
    }
    /**
     * It is a function which ensures that in the internal calculations, the 
     * integer value never goes out of the 1 to 26 range which corresponds to 
     * the alphabets.
     * @param a The integer corresponding to the letter that needs to be 
     * corrected.
     * @return Returns corrected integer 
     */
    private int correctLetter(int a)
    {
        if(a>26)
        {
            a-=26;
        }
        else if(a<1)
        {
            a+=26;
        }
        return a;
    }

    /**
     * Validates the rotor wiring.
     * @param rtr Wiring to be validated
     * @return true is the rotor wiring is valid, false otherwise.
     */
    public static boolean isValidRotor(String rtr)
    {
        return(Affiliate.doesNotRepeat(rtr)&&(rtr.length()==26));
    }

    /**
     * Checks whether the rotor is at a notch or not.
     * @return true if rotor is a a notch, false otherwise.
     */
    public boolean isAtNotch()
    {
        if(CurrSetting==Notch1||CurrSetting==Notch2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Advances the current setting of the rotor.
     */
    public void Advance()
    {
        if(CurrSetting=='Z')
        {
            CurrSetting='A';
        }
        else
        {
            CurrSetting++;
        }
    }

    /**
     * Getter method for the ring setting of the rotor.
     * @return The ring setting in use.
     */
    public char getRing()
    {
        return RingSetting;
    }

    /**
     * Getter method for the current/ground setting of the rotor.
     * @return The current/ground setting in use.
     */
    public char getCurrent()
    {
        return CurrSetting;
    }

    /**
     * This function is the working mechanism for the rotor.
     * It substitutes the given letter with another based on the wiring, 
     * current setting and ring setting. 
     * @param let Letter to be substituted.
     * @param FPass true if the signal/letter enters from right of the rotor, 
     * otherwise false.
     * @return The substituted letter.
     */
    public int mapLetter(int let,boolean FPass)
    {
        let-=(Affiliate.charConv(RingSetting)-1);
        let=correctLetter(let);
        let+=(Affiliate.charConv(CurrSetting)-1);
        let=correctLetter(let);
        if(FPass)
        {
            let=Wiring[let-1];
        }
        else
        {
            for(int i=0;i<26;i++)
            {
                if(Wiring[i]==let)
                {
                    let=i+1;
                    break;
                }
            }
        }
        let-=(Affiliate.charConv(CurrSetting)-1);
        let=correctLetter(let);
        let+=(Affiliate.charConv(RingSetting)-1);
        let=correctLetter(let);
        return let;
    }
}
