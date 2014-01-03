/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author andrei
 */
public class SituatieMaterieBaza {
    
    private Materie materie;
    private List<Integer> note1;
    private List<Integer> note2;
    private List<Absenta> absente1;
    private List<Absenta> absente2;
    private float medie1, medie2;
    
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
}
