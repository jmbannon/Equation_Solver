/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Integration;

import Function.Function_Properties;

/**
 *
 * @author jbannon
 */
public class By_Parts {
    
    private Function_Properties f;
    private Function_Properties g;
    private String f_String;
    private String g_String;
    
    private Function_Properties df;
    private Function_Properties dg;
    private String df_String;
    private String dg_String;
    
    By_Parts(final Function_Properties i, final Function_Properties j) {
        f = i;
        dg = j;
        
        //df_String = i.differentiate();
        //g_String = i.integrate();
        
        //df = new Function(df_String);
    
    
    }
}
