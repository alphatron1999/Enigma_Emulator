package enigma_emulator;
/**
 * A class made for the storage of settings and configurations in a modular 
 * manner, separate from all code to maintain clarity.
 * @author Alphatron99
 */
public class SVals 
{
    static Rotor r1=new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ",Affiliate.intConv(17),Affiliate.intConv(17));;
    static Rotor r2=new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE",Affiliate.intConv(5),Affiliate.intConv(5));
    static Rotor r3=new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO",Affiliate.intConv(22),Affiliate.intConv(22));
    static String groundSet="ABC";
    static String ringSet="AAA";
    static Reflector rf=new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT",false);
    static Plugboard pb=new Plugboard("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
}
