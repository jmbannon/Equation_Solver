package Numerics;

/**
 *
 * @author jbannon
 */
public class Fraction {

    private static double VALUE;
    
    private static String VALUE_STRING;
    private static String DECIMAL_STRING;
    
    private static int WHOLE_NUMBER;
    private static int DECIMAL_NUMERATOR;
    private static int DECIMAL_DENOMINATOR;
    
    private static boolean NEGATIVE_DECIMAL = false;
    
    private Fraction() { /* Do nothing */ }
    
    private static void calculateFraction(final double theValue) {
        VALUE = theValue;
        
        if (VALUE == 0) {
            WHOLE_NUMBER = 0;
            DECIMAL_NUMERATOR = 0;
            DECIMAL_DENOMINATOR = 0;
            return;
        }
        
        if (VALUE < 0 && VALUE > -1)
            NEGATIVE_DECIMAL = true;
        
        VALUE_STRING = String.valueOf(VALUE);
        DECIMAL_STRING = VALUE_STRING.substring(VALUE_STRING.indexOf(".") + 1);
        
        WHOLE_NUMBER = (int)VALUE;
        DECIMAL_NUMERATOR = Integer.valueOf(DECIMAL_STRING);
        
        DECIMAL_DENOMINATOR = 1;
        for (int i = 0; i < DECIMAL_STRING.length(); ++i)
            DECIMAL_DENOMINATOR *= 10;
        
        final int gcf = GCF.getGCF(DECIMAL_NUMERATOR, DECIMAL_DENOMINATOR);
        
        DECIMAL_NUMERATOR /= gcf;
        DECIMAL_DENOMINATOR /= gcf;
        
        if (NEGATIVE_DECIMAL)
            DECIMAL_NUMERATOR *= -1;  
    }     
    
    public static String getMixedFraction(final double theValue) {
        calculateFraction(theValue);
        if (WHOLE_NUMBER != 0)
            if (DECIMAL_NUMERATOR != 0)
                return WHOLE_NUMBER + " " 
                        + DECIMAL_NUMERATOR + "/" + DECIMAL_DENOMINATOR;
            else
                return String.valueOf(WHOLE_NUMBER);
        
        else 
            if (DECIMAL_NUMERATOR != 0)
                return DECIMAL_NUMERATOR + "/" + DECIMAL_DENOMINATOR;
            else
                return "0";
    }
    
    public static String getMixedFraction(final double numerator, 
            final double denominator) {
        
        return getMixedFraction(numerator/denominator);
    }
    
    public static String getImproperFraction(final double theValue) {
        calculateFraction(theValue);
        
        if (WHOLE_NUMBER != 0)
            if (DECIMAL_NUMERATOR != 0)
                return (WHOLE_NUMBER*DECIMAL_DENOMINATOR) + DECIMAL_NUMERATOR 
                        + "/" + DECIMAL_DENOMINATOR;
            else
                return String.valueOf(WHOLE_NUMBER);
        
        else 
            if (DECIMAL_NUMERATOR != 0)
                return DECIMAL_NUMERATOR + "/" + DECIMAL_DENOMINATOR;
            else
                return "0";
    }
    
    public static String getImproperFraction(final double numerator, 
            final double denominator) {
        
        return getImproperFraction(numerator/denominator);
    }
    
    public static double getValue(final double numerator, 
            final double denominator) {
        
        return numerator/denominator;
    }
}
