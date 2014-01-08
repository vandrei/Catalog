/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import liceu.SituatieMaterieBaza.Absenta;

/**
 *
 * @author andrei
 */
public class Clasa implements java.io.Serializable {
    private String classID;
    private ArrayList<Elev> elevi;
    private ArrayList<Materie> materii;
    private HashMap<Elev, HashMap<Materie, ? extends SituatieMaterieBaza>> Catalog;
    
    public Clasa(String classID)
    {
        this.classID = classID;
        elevi = new ArrayList<Elev>();
        materii = new ArrayList<Materie>();
        //profesori = new HashMap<Materie, Profesor>();
        Catalog = new HashMap<Elev, HashMap<Materie, ? extends SituatieMaterieBaza>>();
    }
    
    public String toString()
    {
        return classID;
    }
    
    public void delGrade(Elev e, Materie m, int semestru, Integer grade)
    {
        Catalog.get(e).get(m).delGrade(semestru, grade);
    }
    
    public void addGrade(Elev elev, Materie materie, int semestru, Integer grade)
    {
        Catalog.get(elev).get(materie).addGrade(semestru, grade);
    }
    
    public Integer[] getSituatieAtMaterie(Materie materie, Elev elev, int sem)
    {
        SituatieMaterieBaza situatie = Catalog.get(elev).get(materie);
        return situatie.getGradesForProf(sem);
    }
    
    public Absenta[] getAbsenteAtMaterie(Materie materie, Elev elev, int sem)
    {
        SituatieMaterieBaza situatie = Catalog.get(elev).get(materie);
        return situatie.getAbsenteForProf(sem);
    }
    
    public void addMaterie(Materie materie, Profesor profesor)
    {
        materii.add(materie);
        Centralizator.getCentralizator().addMaterie(materie, profesor, this);
        Iterator<Elev> catalogIt = Catalog.keySet().iterator();
        while (catalogIt.hasNext())
        {
            Elev e = catalogIt.next();
            HashMap<Materie, SituatieMaterieBaza> map = (HashMap<Materie, SituatieMaterieBaza>) Catalog.get(e);
            if (materie.getTeza())
            {
                map.put(materie, new SituatieMaterieCuTeza());
            }
            else
            {
                map.put(materie, new SituatieMaterieBaza());
            }
        }
    }
    
    public void delMaterie(Materie materie)
    {
        materii.remove(materie);
        Centralizator.getCentralizator().delMaterie(materie, this);
        Iterator<Elev> catalogIt = Catalog.keySet().iterator();
        while (catalogIt.hasNext())
        {
            Elev e = catalogIt.next();
            HashMap<Materie, ? extends SituatieMaterieBaza> map = Catalog.get(e);
            map.remove(materie);
        }
    }
    
    public String getClassID()
    {
        return classID;
    }
    
    public void addElev(Elev elev)
    {
        elevi.add(elev);
        Centralizator.getCentralizator().addUtilizator(elev);
        HashMap<Materie, SituatieMaterieBaza> catalogElev = new HashMap<Materie, SituatieMaterieBaza>();
        Iterator<Materie> materiiIt = materii.iterator();
        while (materiiIt.hasNext())
        {
            Materie materie = materiiIt.next();
            if (materie.getTeza())
            {
                catalogElev.put(materie, new SituatieMaterieCuTeza());
            }
            else
            {
                catalogElev.put(materie, new SituatieMaterieBaza());
            }
        }
        Catalog.put(elev, catalogElev);
    }
    
    public void delElev(Elev elev)
    {
        elevi.remove(elev);
        Catalog.remove(elev);
    }
    
    public Materie[] getMaterii()
    {
        return materii.toArray(new Materie[materii.size()]);
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
