/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author andrei
 */
public class SituatieMaterieBaza implements java.io.Serializable {
    
    private ArrayList<Integer> note1;
    private ArrayList<Integer> note2;
    private ArrayList<Absenta> absente1;
    private ArrayList<Absenta> absente2;
    private float medie1, medie2;
    
    public SituatieMaterieBaza()
    {
        note1 = new ArrayList<Integer>();
        note2 = new ArrayList<Integer>();
        absente1 = new ArrayList<Absenta>();
        absente2 = new ArrayList<Absenta>();
    }
    
    public void delGrade(int semestru, Integer grade)
    {
        switch(semestru)
        {
            case 1:
                note1.remove(grade);
                break;
            case 2:
                note2.remove(grade);
                break;
        }
    }
    
    public void addGrade(int semestru, Integer grade)
    {
        switch(semestru)
        {
            case 1:
                note1.add(grade);
                break;
            case 2:
                note2.add(grade);
                break;
        }
    }
    
    public float getMean(int semestru)
    {
        float mean = 0;
        List<Integer> note = null;
        switch (semestru)
        {
            case 1: 
                note = note1;
                break;
            case 2:
                note = note2;
                break;
        }
        
        Iterator i = note.iterator();
        int k = 0;
        while(i.hasNext())
        {
            mean += ((Integer)i.next()).intValue();
            k++;
        }
        
        mean = mean / k;
        return mean;
    }
    
    public String getGrades(int semestru)
    {
        List<Integer> note = null;
        switch (semestru)
        {
            case 1:
                note = note1;
                break;
            case 2:
                note = note2;
                break;
        }
        
        Iterator i = note.iterator();
        String grades = "";
        if (i.hasNext())
        {
            grades += i.next().toString();
        }
        
        while(i.hasNext())
        {
            grades += ", " + i.next().toString();
        }
        return grades;
    }
    
    public Integer[] getGradesForProf(int semestru)
    {
        switch(semestru)
        {
            case 1:
                return (Integer[]) note1.toArray(new Integer[note1.size()]);
            case 2:
                return (Integer[]) note2.toArray(new Integer[note2.size()]);
        }
        return null;
    }
    
    public Absenta[] getAbsenteForProf(int semestru)
    {
        switch (semestru)
        {
            case 1:
                return absente1.toArray(new Absenta[absente1.size()]);
            case 2:
                return absente2.toArray(new Absenta[absente2.size()]);
        }
        return null;
    }
    
    public class Absenta {
        private String date;
        private String status;
        
        public Absenta(String date)
        {
            this.date = date;
            this.status = "nemotivata";
        }
        
        public String toString()
        {
            return null;
        }
    }
}
