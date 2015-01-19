/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import Numerics.Inverse;
import java.util.ArrayList;

/**
 *
 * @author jbannon
 */
public class Polynomial extends Function_Properties {
    
    //private static final DecimalFormat FORMAT = new DecimalFormat("#.########");
    
    /* Polynomial parts */
    private double coefficient;
    private double exponent;
    private Variable variable = null;

    /* Booleans for parsing equation */
    private boolean hasExponent;
    private boolean hasExponentDecimal;
    private boolean hasExponentNegative;
    private boolean hasConstantDecimal;
    private boolean hasVariable;
    
    public Polynomial() {
        coefficient = 0;
        exponent = 1;
        variable = Variable.getVariable('x');
    }
    
    public Polynomial(final String theEquation) throws IllegalArgumentException {
        super(theEquation);
        final StringBuilder coeffBuilder = new StringBuilder();
        final StringBuilder exponentBuilder = new StringBuilder();
        
        int index = 0;
        
        for (char c: theEquation.trim().toCharArray()) {
            
            if (Character.isDigit(c) && !hasVariable && !hasExponent)
                coeffBuilder.append(c);
            
            else if (Character.isDigit(c) && hasExponent)
                exponentBuilder.append(c);
            
            else if (Character.isLetter(c) && !hasVariable && !hasExponent) {
                hasVariable = true;
                variable = Variable.getVariable(c);              
            }
            
            else if (c == '-') {
                if (index == 0) {
                    coeffBuilder.append(c);
                }
                else if (hasVariable && hasExponent && !hasExponentNegative) {
                    hasExponentNegative = true;
                    exponentBuilder.append(c);
                }
                else
                    throw new IllegalArgumentException("Negative sign invalid "
                            + "at index " + index);
            }
            
            else if (c == '.') {
                if (!hasVariable && !hasExponent && !hasConstantDecimal) {
                    hasConstantDecimal = true;
                    coeffBuilder.append(c);
                }
                else if (hasVariable && hasExponent && !hasExponentDecimal) {
                    hasExponentDecimal = true;
                    exponentBuilder.append(c);
                }
                else
                    throw new IllegalArgumentException("Decimal point invalid "
                            + "at index " + index);
            }
            
            else if (c == '^' && !hasExponent)
                hasExponent = true;
                
            else {
                final String errorMessage;
                
                if (c == '^' && hasExponent)
                    errorMessage = "Exponent invalid";
                else if (Character.isLetter(c) && !hasVariable)
                    errorMessage = "Variable invalid";
                else if (Character.isDigit(c) && ((hasVariable && !hasExponent) 
                        || (!hasVariable && hasExponent)))
                    errorMessage = "Digit invalid";
                else
                    errorMessage = "Equation invalid";
                
                throw new IllegalArgumentException(errorMessage + " at index " 
                        + index + ": " + theEquation.trim().subSequence(0, index));
            }
            index++;
        }
        
        
        if (coeffBuilder.toString().isEmpty())
            coefficient = 1;
        else if (coeffBuilder.toString().equals("-"))
            coefficient = -1;
        else
            coefficient = Double.valueOf(coeffBuilder.toString());
            
        
        if (!exponentBuilder.toString().isEmpty())
            exponent = Double.valueOf(exponentBuilder.toString());
        else
            exponent = 1;
        
        if (hasExponent && !hasVariable) {
            coefficient = Math.pow(coefficient, exponent);
            exponent = 1;
        }
    }
    
    @Override
    public double value(final ArrayList<Variable> theVariable) {  
        if (variable == null)
            return Math.pow(coefficient, exponent);
        
        for (Variable v : theVariable) 
            if (v.toString().equals(variable.toString()))
                return coefficient * Math.pow(v.getValue(), exponent); 
        
        
        
        return Double.NaN;
    }
    
    @Override
    public Function_Properties integrate() {
        
        if (coefficient == 0)
            return new Polynomial("0");
        
        else if (variable == null)
            return new Polynomial(buildPolynomial(coefficient, Variable.getVariable('x'), 1));
        
        else if (exponent == -1)
            throw new IllegalArgumentException("Logs not yet implemented");
            //return new Log(FORMAT.format(coefficient) + "log(" + String.valueOf(variable) + ")");
        
        else {
            final double integratedExponent = exponent + 1;
            final double integratedCoefficient = Inverse.getInverse(integratedExponent) * coefficient;
               
            return new Polynomial(buildPolynomial(integratedCoefficient, variable, integratedExponent));
        }
    }
    
    @Override
    public Function_Properties integrate(final int nth_integral) {
        if (nth_integral < 0)
            throw new IllegalArgumentException("Cannot have negative integrals.");
        else if (nth_integral == 0)
            return this;
        else if (exponent < 0 && exponent + nth_integral > 0) {
            throw new IllegalArgumentException("Sorry, integrals of logarithms are not yet implemented.");
        }
        
        Function_Properties p = this;
        
        for (int i = 0; i < nth_integral - 1; ++i) {
            p = p.integrate();
        }
        
        return p.integrate();
    }
    
    @Override
    public double integrate(final double theLowerInterval, 
            final double theUpperInterval) 
                throws IllegalArgumentException {
        
        if (exponent == -1 && (theLowerInterval < 0 || theUpperInterval < 0))
            throw new IllegalArgumentException("Log(-n) does not exist");
            
        if (theLowerInterval == theUpperInterval)
            return 0;
        
        else {
            final double upperInterval;
            final double lowerInterval;
            
            if (exponent != -1) {
                final double integratedExponent = exponent + 1;
                final double integratedCoefficient = Inverse.getInverse(integratedExponent) * coefficient;
                upperInterval = integratedCoefficient 
                        * Math.pow(theUpperInterval, integratedExponent);
                
                lowerInterval = integratedCoefficient 
                        * Math.pow(theLowerInterval, integratedExponent);
            } else {
                upperInterval = coefficient * Math.log(theUpperInterval);
                lowerInterval = coefficient * Math.log(theLowerInterval);
            }      
            
            return Double.valueOf(FORMAT.format(upperInterval - lowerInterval));
        }
    }
    
    @Override
    public Function_Properties differentiate() {
        
        if (exponent == 0 || variable == null|| coefficient == 0) {
            return new Polynomial("0");
        } else {
        
            final double differentiatedCoefficient = coefficient * exponent;
            final double differentiatedExponent = exponent - 1;
        
            return new Polynomial(buildPolynomial(differentiatedCoefficient, variable, differentiatedExponent));
        }
    }
    
    @Override
    public Function_Properties differentiate(final int nth_derivative) {
        if (nth_derivative < 0)
            throw new IllegalArgumentException("Cannot have negative derivatives.");
        else if (nth_derivative == 0)
            return this;
            
        Function_Properties p = this;
        
        for (int i = 0; i < nth_derivative - 1; ++i) {
            p = p.differentiate();
        }
        
        return p.differentiate();
    }
    
    private String buildPolynomial(final double theCoefficient, 
            final Variable theVariable, final double theExponent) {
           
        final StringBuilder equation = new StringBuilder();
        
        if (theExponent == 0)
            return FORMAT.format(theCoefficient);
        
        if (theCoefficient != 1)
            equation.append(FORMAT.format(theCoefficient));
        
        if (theExponent != 0 && theVariable != null) {
            equation.append(theVariable.toString());
            if (theExponent != 1) {
                equation.append('^');
                equation.append(FORMAT.format(theExponent));
            }
        }

        return equation.toString();
    }
    
    @Override
    public Variable getVariable() {
        return variable;
    }
    
    public double getCoefficient() {
        return coefficient;
    }
    
    public double getExponent() {
        return exponent;
    }
    
    public boolean isNumeric() {
        return ((variable == null && exponent == 1) || exponent == 0);
    }
    
    @Override
    public String toString() {
        
        final StringBuilder equation = new StringBuilder();
        
        if (coefficient == 0)
            return "0";
        
        if (coefficient != 1)
            equation.append(FORMAT.format(coefficient));
        
        if (exponent != 0 && variable != null) {
            equation.append(variable.toString());
            
            if (exponent != 1) {
                equation.append('^');
                equation.append(FORMAT.format(exponent));
            }
        }

        return equation.toString();
    }
}
