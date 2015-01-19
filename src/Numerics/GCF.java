/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Numerics;

/**
 *
 * @author jbannon
 */
public class GCF {
    
    private GCF() { /* Do nothing */ }
    
    public static int getGCF(final int x, final int y) {
        if ((x % y) == 0)
            return y;
        else
            return getGCF(y, (x % y));
    }
}
