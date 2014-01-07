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
public class Clasa implements java.io.Serializable {
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
    }
    
    public String getClassID()
    {
        return classID;
    }
    
    public void addElev(Elev elev)
    {
        elevi.add(elev);
        Centralizator.getCentralizator().addUtilizator(elev);
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
