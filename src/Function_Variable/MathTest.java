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
import Function.Polynomial;
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
        

        
        Parse k = new Parse("sin(32)*(3x-7^3*5/3)/3+3");
        k.interpretEquation();
        k.printInfo();
        System.out.println(k.solveEquation_v3(test));
        
        Polynomial l = new Polynomial("43x^-1");
        Parse m = new Parse(l.integrate().toString());
        m.interpretEquation();
        System.out.println(m.toString());
        System.out.println(m.solveEquation_v3(test));
    }
}
