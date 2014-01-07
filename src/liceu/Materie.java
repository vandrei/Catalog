/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public class Materie implements java.io.Serializable{
    private String name;
    private int nrOre;
    private boolean teza;
    
    public Materie(String name, int nrOre, boolean teza)
    {
        this.name = name;
        this.nrOre = nrOre;
        this.teza = teza;
    }
    
}
