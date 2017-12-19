package enigma_emulator;

/**
 * This class file defines and dictates the behavior of a reflector in an 
 * Enigma Machine. The reflector, like the plugboard, swaps letters but the
 * main difference between the two is that while it is not compulsory for each
 * letter to be swapped in the plugboard, every letter that enter a reflector 
 * MUST BE SWAPPED.
 * @author Alphatron99
 */
public class Reflector 
{
    private int[] Setting= new int[26];
    /**
     * Initializes the 
     * @param set Reflector setting to be used.
     * @param chk true if you want to validate the setting before use, false 
     * otherwise.
     */
    Reflector(String set,boolean chk)
    {
        if(chk)
        {
            if(isValidSet(set))
            {
                for(int i=0;i<set.length();i++)
                {
                    Setting[i]=Affiliate.charConv(set.charAt(i));
                }
            }
        }
        else
        {
            for(int i=0;i<set.length();i++)
            {
                Setting[i]=Affiliate.charConv(set.charAt(i));
            }
        }
    }
    /**
     * Validates the reflector setting. A valid reflector setting must have
     * all the letters paired.
     * @param set Setting to be validated.
     * @return true if the setting is valid, false otherwise.
     */
    static boolean isValidSet(String set)
    {
        if(!Affiliate.doesNotRepeat(set))
        {
            return false;
        }
        if(set.length()==26)
        {
            StringBuffer s=new StringBuffer(set.toUpperCase());
            for(int i=0;i<s.length();i++)
            {
                if(s.charAt(i)!='<')
                {
                    char c2=s.charAt(Affiliate.charConv(s.charAt(i))-1);
                    if((i+1)==Affiliate.charConv(c2)&&c2!=s.charAt(i))
                    {
                        s.setCharAt(Affiliate.charConv(s.charAt(i))-1,'<');
                        s.setCharAt(i, '<');
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * This method carries out the swapping of letters.
     * @param entry The integer corresponding to the letter that has to be 
     * swapped.
     * @return Gives the integer corresponding to the swap.
     */
    public int reflect(int entry)
    {
        return(Setting[entry-1]);
    }
}
