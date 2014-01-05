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
    private String classID;
    private ArrayList<Elev> elevi;
    private ArrayList<Materie> materii;
    private HashMap<Materie, Profesor> profesori;
    private HashMap<Elev, HashMap<Materie, ? extends SituatieMaterieBaza>> Catalog;
    
    public Clasa(String classID)
    {
        this.classID = classID;
        elevi = new ArrayList<Elev>();
        materii = new ArrayList<Materie>();
        profesori = new HashMap<Materie, Profesor>();
        Catalog = new HashMap<Elev, HashMap<Materie, ? extends SituatieMaterieBaza>>();
        elevi.add(new Elev("andrei", "1234", "Vasilescu", "Andrei", "8765", "25.03.1993"));
        elevi.add(new Elev("costel", "1234", "Gigelino", "Andrei", "8765", "25.03.1993"));
        elevi.add(new Elev("bibel", "1234", "Costelino", "Andrei", "8765", "25.03.1993"));
        if (classID.equals("9B"))
        {
            elevi.add(new Elev("gibel", "1234", "Costelino", "Ion", "8765", "25.03.1993"));
        }
    }
    
    public String getClassID()
    {
        return classID;
    }
    
    public boolean addElev(Elev elev)
    {
        return true;
    }
    
    public void delElev(Elev elev)
    {
        elevi.remove(elev);
    }
    
    public Elev[] getElevNames()
    {
        return elevi.toArray(new Elev[elevi.size()]);
    }
    
    public HashMap<Materie, ? extends SituatieMaterieBaza> getSituatieElev(Elev elev)
    {
        return Catalog.get(elev);
    }
    
}
