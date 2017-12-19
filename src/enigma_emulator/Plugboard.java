package enigma_emulator;

/**
 * Handles all the working that was done by the Enigma Plugboard. Swaps letters
 * on entry and exit from the machine.
 * @author Alphatron99
 */
public class Plugboard 
{
    private int[] Swapper=new int[26];
    /**
     * Initializes a plugboard based on the given setting.
     * @param set Setting that is to be used 
     */
    Plugboard(String set)
    {
        for(int i=0;i<set.length();i++)
        {
            Swapper[i]=Affiliate.charConv(set.charAt(i));
        }
    }

    /**
     * Validates that a string refers to a correct format of plugboard setting.
     * @param set String to be validated
     * @return Returns true if valid and false if not.
     */
    public static boolean isValidSwap(String set)
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
                    if((i+1)==Affiliate.charConv(c2))
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
    public int pass(int entry)
    {
        return(Swapper[entry-1]);
    }
    
    /**
     * Used to get the setting that is being used in integer array format.
     * @return An array of integer where the index 0 corresponds to the 
     * association of letter A, 1 corresponds to the association of letter B 
     * and so on.
     */
    public int[] getAssociation()
    {
           return Swapper;
    }
}
