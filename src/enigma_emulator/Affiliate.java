package enigma_emulator;

/**
 * Contains essential functions used in many other classes and thus avoids
 * redundancy of code.
 * @author Alphatron99
 */
public class Affiliate
{

    /**
     * Converts character to integer based on the scheme that A is 1, B is 2,
     * and so on.
     * @param c Character to be converted to integer.
     * @return Returns an integer based on the scheme of conversion.
     */
    static public int charConv(char c)
    {
        return (((int)Character.toUpperCase(c))-64);
    }

    /**
     *
     * @param s
     * @return Gives true if String consists purely of letters and false
     * otherwise.
     */
    static public boolean isAllLetter(String s)
    {
        for(int i=0;i<s.length();i++)
        {
            if(Character.isLetter(s.charAt(i))==false)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that all letters in a string are unique
     * @param s String to be checked
     * @return Gives true if string
     */
    static public boolean doesNotRepeat(String s)
    {
        if(s.length()<=26&&isAllLetter(s))
        {
            s=s.toUpperCase();
            int checker = 0;
            for (int i = 0; i < s.length(); i++)
            {
                int val = s.charAt(i) - 'A';
                if ((checker & (1 << val)) > 0) return false;
                checker |= (1 << val);
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    /**
     * Converts integer to character based on the scheme that 1 is A, 2 is B,
     * and so on.
     * @param i Integer to be converted to character
     * @return Returns a character based on the scheme of conversion.
     */
    static public char intConv(int i)
    {
        char c='A';
        for(int j=1;j<i;j++)
        {
            c++;
        }
        return c;
    }
}
