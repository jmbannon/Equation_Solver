/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

/**
 *
 * @author jbannon
 */
public enum Variable {
    a("a"), A("A"),
    b("b"), B("B"),
    c("c"), C("C"),
    d("d"), D("D"),
    f("f"), F("F"),
    g("g"), G("G"), 
    h("h"), H("H"),
    i("i"), I("I"),
    j("j"), J("J"),
    k("k"), K("K"),
    l("l"), L("L"),
    m("m"), M("M"),
    n("n"), N("N"),
    o("o"), O("O"),
    p("p"), P("P"),
    q("q"), Q("Q"),
    r("r"), R("R"), 
    s("s"), S("S"), 
    t("t"), T("T"),
    u("u"), U("U"),
    v("v"), V("V"),
    w("w"), W("W"), 
    x("x"), X("X"),
    y("y"), Y("Y"),
    z("z"), Z("Z");
    
    private final String variable;
    private double value;
    
    private Variable(final String var) {
        variable = var;
        value = Double.NaN;
    }
    
    /**
     *
     * @param theVariable
     * @return
     */
    public static Variable getVariable(final char theVariable) {
        switch(theVariable) {
            
            /* Most popular variables at top */
            
            case 'x': return x; case 'X': return X;
            case 'y': return y; case 'Y': return Y;
            case 'z': return z; case 'Z': return Z;
                
            case 't': return t; 
            case 'u': return u; 
            case 'v': return v; 
            case 'w': return w; 
                
            case 'a': return a; 
            case 'b': return b; 
            case 'c': return c; 
            case 'd': return d; 
                
            /*===================================*/
                
                                case 'A': return A;
                                case 'B': return B;
                                case 'C': return C;
                                case 'D': return D;
            case 'f': return f; case 'F': return F;
            case 'g': return g; case 'G': return G;
            case 'h': return h; case 'H': return H;
            case 'i': return i; case 'I': return I;
            case 'j': return j; case 'J': return J;
            case 'k': return k; case 'K': return K;
            case 'l': return l; case 'L': return L;
            case 'm': return m; case 'M': return M;
            case 'n': return n; case 'N': return N;
            case 'o': return o; case 'O': return O;
            case 'p': return p; case 'P': return P;
            case 'q': return q; case 'Q': return Q;
            case 'r': return r; case 'R': return R;
            case 's': return s; case 'S': return S;
                                case 'T': return T;
                                case 'U': return U;
                                case 'V': return V;
                                case 'W': return W;
            default: 
                throw new IllegalArgumentException("Invalid variable " + theVariable);
        }
    }
    
    public static Variable getVariable(final String theVariable) {
        if (theVariable.length() > 1)
            throw new IllegalArgumentException("Invalid variable.");
        
        return getVariable(theVariable.charAt(0));
    }
    
    public void setValue(final double theValue) {
        value = theValue;
    }
    
    public double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return variable;
    }
}
