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
    
    public void addAbsenta(Elev e, Materie m, String abs, int semester)
    {
        Catalog.get(e).get(m).addAbsenta(abs, semester);
    }
    
    public int getMeanAtMaterie(Materie m, Elev elev, int semester)
    {
        return Catalog.get(elev).get(m).getMean(semester);
    }
    
    public void closeSituatie(Materie m, Elev elev, int semester)
    {
        Catalog.get(elev).get(m).showMean(semester);
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
    
    public void addMaterie(Profesor profesor)
    {
        Materie materie = profesor.getMaterie();
        materii.add(materie);
        if (materie.getTeza())
        {
            Iterator<Elev> it = Catalog.keySet().iterator();
            while (it.hasNext())
            {
                Elev crr_elev = it.next();
                HashMap<Materie, SituatieMaterieBaza> map = (HashMap<Materie, SituatieMaterieBaza>) Catalog.get(crr_elev);
                SituatieMaterieCuTeza sit = new SituatieMaterieCuTeza();
                map.put(materie, sit);
            }
        }
        else
        {
            Iterator<Elev> it = Catalog.keySet().iterator();
            while (it.hasNext())
            {
                Elev crr_elev = it.next();
                HashMap<Materie, SituatieMaterieBaza> map = (HashMap<Materie, SituatieMaterieBaza>) Catalog.get(crr_elev);
                SituatieMaterieBaza sit = new SituatieMaterieBaza();
                map.put(materie, sit);
            }
        }
        Centralizator.getCentralizator().addMaterietoClasa(this, profesor);
    }
    
    public void delMaterie(Materie materie)
    {
        materii.remove(materie);
        Iterator<Elev> it = Catalog.keySet().iterator();
        while (it.hasNext())
        {
            Elev e = it.next();
            Catalog.get(e).remove(materie);
        }
    }
    
        public void changeMaterie(Materie materie, String materieName, int nrOre, boolean Teza, Profesor profesor)
    {
        materie.setInfo(materieName, nrOre, Teza);
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
    
    public void setTezaatMaterie(Materie m, Elev elev, int semestru, String grade)
    {
        ((SituatieMaterieCuTeza)Catalog.get(elev).get(m)).addTeza(grade, semestru);
    }

    public String getTezaatMaterie(Materie m, Elev elev, int semestru) {
        return ((SituatieMaterieCuTeza) Catalog.get(elev).get(m)).getTeza(semestru);
    }
    
}
