/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;
import java.util.ArrayList;

/**
 *
 * @author jbannon
 */
public enum Operator {
    
    ADDITION("+") {
        @Override 
        public double apply(final Function_Properties fx, final Function_Properties fy, final ArrayList<Variable> variables) {
            return fx.value(variables) + fy.value(variables);
        }            

        @Override
        public double apply(final Function_Properties fx, final ArrayList<Variable> variables, final double value) {
            return fx.value(variables) + value;
        }
    },
    
    SUBTRACTION("-") {
        @Override 
        public double apply(final Function_Properties fx, final Function_Properties fy, final ArrayList<Variable> variables) {
            return fx.value(variables) - fy.value(variables);
        }            

        @Override
        public double apply(Function_Properties fx, ArrayList<Variable> variables, final double value) {
            return fx.value(variables) - value;
        }

        
        public double apply(double value, Function_Properties fx, ArrayList<Variable> variables) {
            return value - fx.value(variables);
        }
    },
    
    MULTIPLY("*") {
        @Override 
        public double apply(final Function_Properties fx, final Function_Properties fy, final ArrayList<Variable> variables) {
            return fx.value(variables) * fy.value(variables);
        }            

        @Override
        public double apply(final Function_Properties fx, final ArrayList<Variable> variables, final double value) {
            return fx.value(variables) * value;
        }
    },
    
    DIVIDE("/") {
        @Override 
        public double apply(final Function_Properties fx, final Function_Properties fy, final ArrayList<Variable> variables) {
            return fx.value(variables) / fy.value(variables);
        }            

        @Override
        public double apply(Function_Properties fx, ArrayList<Variable> variables, final double value) {
            return fx.value(variables) / value;
        }

        
        public double apply(double value, Function_Properties fx, ArrayList<Variable> variables) {
            return value / fx.value(variables);
        }
    };
    
    private final String text;
    
    private Operator(final String text) {
        this.text = text;
    }
    
    public static Operator getOperator(final String text) {
        switch (text) {
            case "+": return ADDITION;
            case "-": return SUBTRACTION;
            case "*": return MULTIPLY;
            case "/": return DIVIDE;
            default: return null; 
        }
    }
    
    public abstract double apply(final Function_Properties fx, final Function_Properties fy, final ArrayList<Variable> variables);
    public abstract double apply(final Function_Properties fx, final ArrayList<Variable> variables, final double value);
}
