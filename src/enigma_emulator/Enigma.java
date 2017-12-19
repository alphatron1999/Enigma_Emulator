package enigma_emulator;

/**
 * The class that puts together all the working components of the machine i.e.
 * the plugboard, reflector and three rotors along with setting the rotors up.
 * This class contains the core logic for the working of the machine.
 * @author Alphatron99
 */
public class Enigma 
{
    private Rotor RL;
    private Rotor RR;
    private Rotor RM;
    private Plugboard PB;
    private Reflector Ref;

    /**
     * Initializer for the Enigma Class. Sets up the encoding machine.
     * @param L Left Rotor
     * @param M Middle Rotor
     * @param R Right Rotor
     * @param Rf Reflector
     * @param P Plugboard
     * @param RingSet Ring Setting- Three letters, no spacing.
     * @param GroundSet Ground Setting- Three letters, no spacing.
     */
    public Enigma(Rotor L,Rotor M,Rotor R,Reflector Rf,Plugboard P,String RingSet,String GroundSet)
    {
        RL=L;
        RR=R;
        RM=M;
        Ref=Rf;
        PB=P;
        RL.setCurrent(GroundSet.charAt(0));
        RM.setCurrent(GroundSet.charAt(1));
        RR.setCurrent(GroundSet.charAt(2));
        RL.setRing(RingSet.charAt(0));
        RM.setRing(RingSet.charAt(1));
        RR.setRing(RingSet.charAt(2));
    }
    /**
     * Advances rotor positions based on whether a notch appears or not. 
     * The rotor to the left only advances if the rotor to its right is at a 
     * notch.
     * The rightmost rotor always advances.
     * There is also a special mechanism called the double stepping which makes 
     * the rotors deviate from an odometer like behavior. If the rightmost 
     * rotor is not at a notch but the middle rotor is, all three rotors will 
     * be advanced.
     */
    private void AdvancePositions()
    {
        if(RR.isAtNotch())
        {
            if(RM.isAtNotch())
            {
                RL.Advance();
            }
            RM.Advance();
        }
        else if(RM.isAtNotch())
        {
            RL.Advance();
            RM.Advance();
        }
        RR.Advance();
    }

    /**
     * Encodes a character at a time.
     * Enigma Process
     * 1. Convert input letter to number
     * 2. Rotate wheels
     * 3. Pass through plugboard
     * 4. Pass through right-hand wheel
     * 5. Pass through middle wheel
     * 6. Pass through left-hand wheel
     * 7. Pass through reflector
     * 8. Pass through left-hand wheel
     * 9. Pass through middle wheel
     * 10. Pass through right-hand wheel
     * 11. Pass through plugboard
     * 12. Convert to output letter
     * @param lt Letter/character to be encoded.
     * @return Returns the encoded character in upper case(Even if input was in
     * lower case).
     */
    public char EncodeLetter(char lt)
    {
        int let=Affiliate.charConv(lt);
        AdvancePositions();
        let=PB.pass(let);
        let=RR.mapLetter(let, true);
        let=RM.mapLetter(let, true);
        let=RL.mapLetter(let, true);
        let=Ref.reflect(let);
        let=RL.mapLetter(let, false);
        let=RM.mapLetter(let, false);
        let=RR.mapLetter(let, false);
        let=PB.pass(let);
        return Affiliate.intConv(let);
    }

    /**
     * Encodes entire string at once based on the current setting.
     * @param str String to be encoded
     * @return Returns entire encoded string, all the non-alphabetic characters
     * are skipped and all characters of returned string will be in upper case.
     */
    public String EncodeText(String str)
    {
        String ret="";
        for(int i=0;i<str.length();i++)
        {
            if(Character.isLetter(str.charAt(i)))
            {
                ret=ret+EncodeLetter(str.charAt(i));
            }
        }
        return ret;
    }
}
