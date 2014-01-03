/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author andrei
 */
public class Clasa {
    private int classID;
    private ArrayList<Elev> elevi;
    private ArrayList<Materie> materii;
    private HashMap<Elev, HashMap<Materie, ? extends SituatieMaterieBaza>> Catalog;
    
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
    
    public HashMap<Materie, ? extends SituatieMaterieBaza> getSituatieElev(Elev elev)
    {
        return Catalog.get(elev);
    }
    
}
