/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import Function_Trig.Cosine;
import Function_Trig.Sine;
import Function_Trig.Tangent;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author jbannon
 */
public class Parse extends Function_Properties {
    
    /* Strings for common functions. */
    private static final String LOG = "log";
    private static final String SIN = "sin";
    private static final String COS = "cos";
    private static final String TAN = "tan";
    private static final String CSC = "csc";
    private static final String SEC = "sec";
    private static final String COT = "cot";
    
    
    /* List to parse and store functions within parenthesis. */
    private final ArrayList<Parse> parenthesisOperatorList = new ArrayList<>(); 
    
    /* List to store strings of separate functions within equation. */
    private final ArrayList<String> equationList = new ArrayList<>();
    
    
    /* */
    private final ArrayList<Function_Properties> functionList = new ArrayList<>();
    private final ArrayList<Variable> variableList = new ArrayList<>();
    private final ArrayList<Operator> operatorList = new ArrayList<>();
    
    private final String equationString;
    
    private int parenthesisBalance = 0;
    private int parenthesisOpenIndex = -1;
    private int functionParenthesisBalance = -1;
    private int functionParenthesisOpenIndex = -1;
    
    private boolean hasOperation = false;
    private boolean hasNegative = false;
    private boolean hasDecimal = false;
    private boolean hasExponent = false;
    
    private boolean isParenthesis = false;
    private boolean isFunction = false;
    
    public Parse(final String theEquation) throws ParseException {
        
        //Removes unnecessary parenthesis
        if (theEquation.trim().charAt(0) == '(' && theEquation.trim().charAt(theEquation.trim().length() - 1) == ')')
            equationString = theEquation.trim().substring(1, theEquation.trim().length() - 1);
        else
            equationString = theEquation.trim();
                            
        StringBuilder functionBuilder = new StringBuilder();
        
        int index = 0;
        for (char c : equationString.toCharArray()) {
                
            /* Always appends numbers */
            if (Character.isDigit(c)) {
                
                if (index == 0)
                    functionBuilder.append(c);
                
                else if (Character.isLetter(equationString.toCharArray()[index-1])
                        || equationString.toCharArray()[index-1] == ')')
                    throw new ParseException("Invalid Number: "
                            + "Numbers must precede everything.\n" 
                            + this.getErrorEquation(index), index);
                
                else 
                    functionBuilder.append(c);   
                
                if (hasOperation)
                    hasOperation = false;
            }
            
            /* Always appends letters */
            else if (Character.isLetter(c)) {
                
                if (index == 0)
                    functionBuilder.append(c); 
                
                /* If letters proceed make up existing function, add preceding
                   function to equationList and create new function */
                else if (index + 3 < equationString.toCharArray().length) {
                    final String function = equationString.substring(index, index + 3).toLowerCase();
                         
                    if (!isFunction && (function.equals(LOG) 
                            || function.equals(SIN) || function.equals(COS) 
                            || function.equals(TAN) || function.equals(CSC) 
                            || function.equals(SEC) || function.equals(COT))) {
                        isFunction = true;
                        equationList.add(functionBuilder.toString());
                        functionBuilder.setLength(0);
                        }
                        
                    functionBuilder.append(c);
                }
                
                /* If char follows another char and is not a function, 
                   add previous function to equation list and create a new 
                   function. For example: xy = x*y */
                else if (Character.isLetter(equationString.toCharArray()[index-1]) && !isFunction && !isParenthesis) {
                    equationList.add(functionBuilder.toString());
                    functionBuilder.setLength(0);
                    functionBuilder.append(c);
                }
                
                /* Throw exception if character that precedes variable is decimal */
                else if (equationString.toCharArray()[index-1] == '.'
                        || equationString.toCharArray()[index-1] == ')')
                    throw new ParseException("Invalid Variable: "
                            + "Cannot proceed non-numeric symbols.\n" 
                            + this.getErrorEquation(index), index);  
                
                else 
                    functionBuilder.append(c);
                    
      
                if (hasOperation)
                    hasOperation = false;
            }
            
            else if (c == '.') {
                if (!hasDecimal) {
                    
                    /* If no numbers precede, add 0 first */
                    if (index == 0)
                        functionBuilder.append(String.valueOf(0));
                    
                    /* If letter precedes decimal, throw exception */
                    else if (Character.isLetter(equationString.toCharArray()[index-1]))
                        throw new ParseException("Invalid Decimal: "
                                + "Decimals cannot proceed variables.\n" 
                                + this.getErrorEquation(index), index);
                    
                    else if (!Character.isDigit(equationString.toCharArray()[index-1]))
                        functionBuilder.append(String.valueOf(0));
                    
                    /* Append and set hasDecimal to true */
                    functionBuilder.append(c);
                    hasDecimal = true;
                } else 
                    throw new ParseException("Invalid Decimal: "
                            + "Numeric already contains a decimal.\n" 
                            + this.getErrorEquation(index), index);
            }
            
            else if (c == '-') {
                if (!hasNegative) {
                    
                    /* If the function has numbers/variables and is not in parenthesis, act as operator */
                    if (!functionBuilder.toString().isEmpty() && !isParenthesis && !isFunction && !hasOperation) {
                        if (hasOperation)
                            throw new ParseException("Invalid Subtraction Operator: "
                                    + "A subtraction operation already exists.\n" 
                                    + this.getErrorEquation(index), index);
                        
                        equationList.add(functionBuilder.toString());
                        equationList.add(String.valueOf(c));
                        functionBuilder.setLength(0); 
                        hasOperation = true;
                        
                    /* Otherwise append to function */
                    } else {
                        if (index == 0) {
                            functionBuilder.append(c);
                            hasNegative = true;
                        }
                        else if (equationString.toCharArray()[index-1] == '.')
                            throw new ParseException("Invalid Negative Operator: "
                                    + "Can't subtract decimal point\n" 
                                    + this.getErrorEquation(index), index);
                        else {
                            functionBuilder.append(c);
                            hasNegative = true;
                        }
                    }
                    
                /* If negative sign is present */    
                } else {
                    
                    /* If function already contains negative and not in parenthesis string, throw exception */
                    if (functionBuilder.toString().equals("-") && !isParenthesis && !isFunction) {
                        System.out.println(functionBuilder.toString());
                        
                        throw new ParseException("Invalid Negative: "
                                + "Negative symbol already exists.\n" 
                                + this.getErrorEquation(index), index);
                    }
                    
                    /* If no parenthesis, act as subtraction operation: add function and operator to list */
                    if (!isParenthesis && !isFunction) {
                        if (hasOperation)
                            throw new ParseException("Invalid Subtraction Operator: "
                                    + "A subtraction operation already exists.\n" 
                                    + this.getErrorEquation(index), index);
                        
                        equationList.add(functionBuilder.toString());
                        equationList.add(String.valueOf(c));
                        functionBuilder.setLength(0);
                        hasOperation = true;
                        
                    /* If parenthesis, append to function to deal with in recursion */
                    } else
                        functionBuilder.append(c);
                    
                   
                    /* Reset all numeric operators to false for new number/function */
                    hasNegative = false;
                    hasDecimal = false;
                    hasExponent = false;
                }     
            }
            
            else if (c == '^') {
                if (!hasExponent) {
                    
                    /* If exponent does not proceed number or variable, throw exception */
                    if (index == 0)
                        throw new ParseException("Invalid Exponent: "
                                + "Must proceed a number or variable.\n" 
                                + this.getErrorEquation(index), index);
                    
                    else if ((!Character.isDigit(equationString.toCharArray()[index-1]) 
                            && !Character.isLetter(equationString.toCharArray()[index-1])))
                        
                        throw new ParseException("Invalid Exponent: "
                                + "Must proceed a number or variable.\n" 
                                + this.getErrorEquation(index), index);
                    
                    /* Append to function and set exponent to true, 
                       negative and decimal to false for exponent number */
                    else {
                        functionBuilder.append(c);
                        hasOperation = true;
                        hasExponent = true;
                        hasNegative = false;
                        hasDecimal = false;
                    }
                    
                /* If it has exponent sign, throw exception */
                } else 
                    throw new ParseException("Invalid Exponent: Function already contains an exponent.\n" + this.getErrorEquation(index), index);
            }
            
            else if (c == '+' || c == '*' || c == '/') {
                
                if (hasOperation || index == 0)
                        throw new ParseException("Invalid " + c + " Operator: "
                                + "Does not operate on anything.\n" 
                                + this.getErrorEquation(index), index);
                
                /* If it is not in parenthesis and function, add function and operator to list */
                else if (!isParenthesis && !isFunction) {
                    
                    equationList.add(functionBuilder.toString());
                    equationList.add(String.valueOf(c));
                    
                    /* Reset all numeric operators to false for new number/function */
                    functionBuilder.setLength(0);
                    
                /* Otherwise append to function to deal with in recursion */
                } else
                    functionBuilder.append(c);
                
                hasOperation = true;
                hasNegative = false;
                hasDecimal = false;
                hasExponent = false;
            }
            
            else if (c == '(') {  
                if (!isParenthesis) {
                    
                    /* If the index can hold a three variables before opening parenthesis,
                       save it as fucntion to test against existing fucntions */
                    final String function;
                    if (index - 3 >= 0)
                        function = equationString.substring(index - 3, index ).toLowerCase();
                    else
                        function = "";
                    
                    System.out.println(function);
                    /* If three variables equals existing function, set as function */
                    if (function.equals(LOG) || function.equals(SIN)
                        || function.equals(COS) || function.equals(TAN)
                        || function.equals(CSC) || function.equals(SEC)
                        || function.equals(COT) || equationString.charAt(index) == '^') {
                        
                        if (!isFunction) {
                            isFunction = true;
                            
                            /* Stores the amount of parenthesis to know when the function parenthesis closes and index to parse */
                            functionParenthesisBalance = parenthesisBalance;
                            functionParenthesisOpenIndex = index;
                    
                            /* If the functionBuilder's length holds more than the function,
                               seperate it by adding the preceding numerics/variables to the list,
                               delete it from the existing function builder, and append the parenthesis */
                            if (functionBuilder.toString().length() > 3) {
                                equationList.add(functionBuilder.toString().substring(0, functionBuilder.toString().length()-3));
                                functionBuilder.delete(0, functionBuilder.toString().length()-3);
                            }
                        }
                        
                        functionBuilder.append(c);
                    
                    /* If it is not within the outer parenthesis */
                    } else {
                        
                        /* Marks the index to know what to parse separately */
                        parenthesisOpenIndex = index;
                        isParenthesis = true;
                        
                        /* If no numbers/variables precede it, append to functionBuilder */
                        if (index == 0 || functionBuilder.toString().isEmpty())
                            functionBuilder.append(c);
                        
                        /* Otherwise separate and add precedes to list */
                        else {
                            equationList.add(functionBuilder.toString());
                            functionBuilder.setLength(0);
                            functionBuilder.append(c);
                        }
                    }
                } 
                
                /* If it is in parenthesis appened to parse in recursion */
                else
                   functionBuilder.append(c);   
                
                if (hasOperation)
                    hasOperation = false;
                
                ++parenthesisBalance;
            }
            
            else if (c == ')') {
                --parenthesisBalance;
                
                if (parenthesisBalance < 0 || hasOperation)
                    throw new ParseException("Invalid Closing Parenthesis: "
                            + "Do not balance or has not finished an operation.\n" 
                            + this.getErrorEquation(index), index);
                
                /* If it is function and balances match, ends the function */
                else if (isFunction && (functionParenthesisBalance == parenthesisBalance)) {
                    isFunction = false;
                    parenthesisOperatorList.add(new Parse(equationString.substring(functionParenthesisOpenIndex + 1, index)));
                }
                
                /* If it is main parenthesis and balance is back to 0, 
                   ends the parenthesis and parses insides by adding to subEquationList */
                else if (isParenthesis && parenthesisBalance == 0) {
                    isParenthesis = false;
                    parenthesisOperatorList.add(new Parse(equationString.substring(parenthesisOpenIndex + 1, index)));
                }
                
                /* Appends closing parenthesis to builder */
                functionBuilder.append(c);  
                
                /* Reset all numeric operators to false for new number/function */
                hasNegative = false;
                hasDecimal = false;
                hasExponent = false;
            } 
            ++index;
        }
        
        /* After parsing, if anything is inside functionBuilder, add to list */
        if (!functionBuilder.toString().isEmpty())
            equationList.add(functionBuilder.toString());
        
        if (hasOperation)
            throw new ParseException("Invalid Operator: "
                    + "Last perator does not operate on a number or variable.\n"
                    + this.getEquation(), index);
        
        /* Parenthesis balance must be 0 at the end to match opening/closing parenthesis */
        if (parenthesisBalance != 0)
            throw new ParseException("Invalid Opening Parenthesis: Parenthesis do not balance.\n" 
                    + this.getEquation(), index);
        
        functionList.clear();
        variableList.clear();
        functionList.clear();
        
    }
    
    public final String getEquation() {
        return equationString;
    }
    
    public final String getErrorEquation(final int index) {
        return equationString.substring(0, index - 1) + " [" 
                + equationString.charAt(index) + "] " 
                + equationString.substring(index + 1);
    }
    
    public void printInfo() {
        
        for (Parse parts2: parenthesisOperatorList) {
            parts2.printInfo();
        }
        
        
        System.out.println("\n--- RECURSION");
        System.out.println("- FUNCTIONS:");
        for (Function_Properties f : functionList) {
            System.out.println(f.toString());
        }
        
        System.out.println("- OPERATORS:");
        for (Operator o : operatorList) {
            System.out.println(o.toString());
        }
        
        System.out.println("- VARIABLES:");
        for (Variable v : variableList)
            System.out.println(v.toString());
    }
    
    public final void interpretEquation() throws ParseException {
        hasOperation = true;
        
        //Recurses through equations within parenthesis first
        for (Parse parts2: parenthesisOperatorList)
            try {
            parts2.interpretEquation();
            } catch (ParseException pe) {
                System.err.println(pe.getMessage());
            }

        
        outerloop:
        for (String parts : equationList) {
            System.out.println(parts);
            //Operators
            if (!hasOperation) {
                if (parts.equals("+") || parts.equals("-")
                        || parts.equals("*") || parts.equals("/")) { 
                    operatorList.add(Operator.getOperator(parts));
                    hasOperation = true;
                    continue;
                }
                
                //If implicitly multiplied
                else {
                    operatorList.add(Operator.getOperator("*"));    
                    hasOperation = true;
                }
            }
            
            
            //Finds equations with parenthesis included that have already been
            //parsed in the recursion
            for (Parse inner : parenthesisOperatorList) {
                if (parts.equals("(" + inner.toString().trim() + ")")) {
                    functionList.add(new Parse(inner.toString()));
                    hasOperation = false;
                    continue outerloop;
                }
            }
            
            //Used to test if a trig/log function exists as the operand
            final String function;
            boolean isExistingFunction = true;
            
            if (parts.length() > 3) {
                function = parts.substring(0, 3).toLowerCase();
                for (char c : function.toCharArray())
                    if (!Character.isLetter(c))
                        isExistingFunction = false;
                        
            } else {
                function = "";
                isExistingFunction = false;
            }
            
            
            if (isExistingFunction) {
                if (function.equals(LOG))
                    functionList.add(new Logarithm(parts));
                
                else if (function.equals(SIN))
                    functionList.add(new Sine(parts));
                
                else if (function.equals(COS))
                    functionList.add(new Cosine(parts));
                
                else if (function.equals(TAN))
                    functionList.add(new Tangent(parts));

                else if (function.equals(CSC) || function.equals(SEC) 
                        || function.equals(COT))
                    System.err.println("Not implemented yet");
                /*
                if (function.equals(LOG)
                    || function.equals(SIN) || function.equals(COS)
                    || function.equals(TAN) || function.equals(CSC)
                    || function.equals(SEC) || function.equals(COT))
                System.err.println("Not implemented yet");
                */
            }
            //All else create a new polynomial
            else {
                functionList.add(new Polynomial(parts));
                hasOperation = false;
            }
        }
        
        for (Function_Properties f : functionList) 
            if (f.getVariable() != null && !variableList.contains(f.getVariable()))
                variableList.add(f.getVariable());
        
    }
    
    public double solveEquation(final ArrayList<Variable> theVariable) throws ParseException {
        
        double MDsum = 0;
        double sum = getValue(functionList.get(0), theVariable);;
        
        boolean hasMDsum = false;
        
        for (int i = 1; i < functionList.size(); i++) {
            double value = getValue(functionList.get(i), theVariable);
            
            //There are always i-1 operators in a function List
            switch(operatorList.get(i-1)) {
                case MULTIPLY:
                    sum *= value;
                    break;
                    
                case DIVIDE:
                    sum /= value;
                    break;
                    
                case ADDITION:
                    while (i < operatorList.size() && i + 1 < functionList.size()) {
                        if (operatorList.get(i) == Operator.ADDITION ||
                                operatorList.get(i) == Operator.SUBTRACTION)
                            break;
                        
                        else if (operatorList.get(i) == Operator.MULTIPLY) {
                            if (!hasMDsum)
                                MDsum = value;
                            
                            MDsum *= getValue(functionList.get(i + 1), theVariable);
                            hasMDsum = true;
                            
                            i++;
                        }
                        else if (operatorList.get(i) == Operator.DIVIDE) {
                            if (!hasMDsum)
                                MDsum = value;
                            
                            MDsum /= getValue(functionList.get(i + 1), theVariable);
                            hasMDsum = true;
                            
                            i++;
                        }
                    }
                
                    if (!hasMDsum)
                        sum += value;
                    else
                        sum += MDsum;
                    
                    break;
                        
                case SUBTRACTION:
                    while (i < operatorList.size() && i + 1 < functionList.size()) {
                        if (operatorList.get(i) == Operator.ADDITION ||
                                operatorList.get(i) == Operator.SUBTRACTION)
                            break;
                        
                        else if (operatorList.get(i) == Operator.MULTIPLY) {
                            if (!hasMDsum)
                                MDsum = value;
                            
                            MDsum *= getValue(functionList.get(i + 1), theVariable);
                            hasMDsum = true;
                            
                            i++;
                        }
                        else if (operatorList.get(i) == Operator.DIVIDE) {
                            if (!hasMDsum)
                                MDsum = value;
                            
                            MDsum /= getValue(functionList.get(i + 1), theVariable);
                            hasMDsum = true;
                            
                            i++;
                        }
                    }
                
                    if (!hasMDsum)
                        sum -= value;
                    else
                        sum -= MDsum;
                    
                    break;
            }
        }
        
        return Double.valueOf(FORMAT.format(sum));
    }
    
    public double getValue(Function_Properties f, final ArrayList<Variable> theVariable) throws ParseException {
            for (Parse p : parenthesisOperatorList) 
                if (f.toString().equals(p.toString())) 
                    return p.solveEquation(theVariable);
                
            return f.value(theVariable);       
    }
    
    @Override
    public String toString() {
        return equationString;
    }

    @Override
    public double value(final ArrayList<Variable> theVariable) {
        
        return 0;
    }

    @Override
    public Function_Properties integrate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
