/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function_Variable;

import Function.Variable;
import Function.Operator;
import Function.Logarithm;
import static Function.Variable.*;
import Function.Parse;
import Function_Trig.Sine;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbannon
 */
public class MathTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        
        
        x.setValue(2.0);
        y.setValue(2.2);
        ArrayList<Variable> test = new ArrayList<>();
        
        test.add(x);
        test.add(y);
        

        Sine logTest = new Sine("Sin(cos(3.1415))");
        System.out.println(logTest.toString() +  " = " + logTest.value(test) + " -------------- \n");
        
        Parse k = new Parse("xSin(" + String.valueOf(Math.PI) + ")");
        k.interpretEquation();
        System.out.println(k.solveEquation(test));
    }
}
