/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function_Trig;

import Function.Function_Properties;
import Function.Parse;
import Function.Variable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jbannon
 */
public class Tangent extends Function_Properties {
    
    private Parse conents;
    private final double constant;
       
    private int parenthesisBalance = 0;
    private boolean verified;
    private boolean finished;
    private boolean constantNegative;
    private boolean constantDecimal; 
    
    
    public Tangent(final String theEquation) throws ParseException {
        final StringBuilder constantBuilder = new StringBuilder();
        final StringBuilder contentsBuilder = new StringBuilder();
        
        char[] logArray = theEquation.trim().toCharArray();
        
        int index = 0;
        
        for (char c : logArray) {
            
            if (Character.isDigit(c) && !verified) {
                constantBuilder.append(c);
            }
            
            else if (Character.isLetter(c) && verified 
                    && parenthesisBalance == 0 && !finished) {
                //Character within 'Log(' , do nothing
            }
            
            else if (parenthesisBalance != 0) {
                if (parenthesisBalance == 1 && c == ')') {
                    conents = new Parse(contentsBuilder.toString());
                    contentsBuilder.setLength(0);
                    --parenthesisBalance;
                    finished = true;
                } else {
                    if (c == '(')
                        ++parenthesisBalance;
                    else if (c == ')')
                        --parenthesisBalance;
                    
                    contentsBuilder.append(c);    
                }  
            } 
            
            else if ((c == 't' || c == 'T') && !verified && index + 3 < logArray.length
                && (logArray[index + 1] == 'a' || logArray[index + 1] == 'A')
                && (logArray[index + 2] == 'n' || logArray[index + 2] == 'N')
                && (logArray[index + 3] == '(')) 
                    verified = true;
            
            else if (c == '(' && verified && !finished)
                ++parenthesisBalance;
            
            else if (logArray[0] == '-' && !constantNegative) {
                constantNegative = true;
                constantBuilder.append(c);
            }
            
            else if (c == '.' && !verified && !constantDecimal) {
                constantDecimal = true;
                constantBuilder.append(c);
            }
            
            else if (finished) {
                throw new ParseException("Cannot have values after parenthesis", index);
            }
            
            else
                throw new ParseException("Invalid log at index " + index, index);
            
            ++index;
        }
        
        if (constantBuilder.toString().isEmpty())
            constant = 1;
        else 
            constant = Double.valueOf(constantBuilder.toString());
        
        if (parenthesisBalance != 0)
            throw new ParseException("Uneven amount of parenthesis", -1);
        
    }
    
    @Override
    public double value(final ArrayList<Variable> theVariable) {
        try {
            conents.interpretEquation();
            return constant * Math.tan(conents.solveEquation(theVariable));
        } catch (ParseException ex) {
            Logger.getLogger(Tangent.class.getName()).log(Level.SEVERE, null, ex);
            return Double.NaN;
        }
    }
    
    
    @Override
    public Function_Properties integrate() {
        return null;
    }
    
    @Override
    public String toString() {
        final StringBuilder log = new StringBuilder();
        
        if (constant != 1)
            log.append(FORMAT.format(constant));
        else if (constant == 0)
            return "0";
        else if (constant == 1) { /* Do nothing */ }
        
        log.append("tan(");
        log.append(conents.toString());
        log.append(")");
        
        return log.toString();
    }

    @Override
    public Function_Properties integrate(int nth_integral) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double integrate(double theLowerInterval, double theUpperInterval) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Function_Properties differentiate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Function_Properties differentiate(int nth_derivative) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Variable getVariable() {
        return null;
    }
}
