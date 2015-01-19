/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author jbannon
 */
public abstract class Function_Properties {
    
    public static final DecimalFormat FORMAT = new DecimalFormat("#.##########");
    private final String function_String;
    
    public Function_Properties() {
        function_String = "";
    }
    
    public Function_Properties(final String function) {
        function_String = function; 
    }
    
    public abstract double value(final ArrayList<Variable> theVariable);
    public abstract Function_Properties integrate();
    public abstract Function_Properties integrate(final int nth_integral);
    public abstract double integrate(final double theLowerInterval, 
            final double theUpperInterval) throws IllegalArgumentException;
    
    public abstract Function_Properties differentiate();
    public abstract Function_Properties differentiate(final int nth_derivative);
    public abstract Variable getVariable();
    
    @Override
    public String toString() {
        return function_String;
    }
    
}
