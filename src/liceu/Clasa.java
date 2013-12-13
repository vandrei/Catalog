/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import java.util.ArrayList;

/**
 *
 * @author andrei
 */
public class Clasa {
    private int classID;
    private ArrayList<Elev> elevi;
    private ArrayList<Materie> materii;
    
    public Clasa(int classID, ArrayList<Elev> elevi, ArrayList<Materie> materii)
    {
        this.classID = classID;
        this.elevi = elevi;
        this.materii = materii;
    }
    
    public boolean addElev(Elev elev)
    {
        return true;
    }
    
    public boolean delElev(Elev elev)
    {
        return true;
    }
    
}
