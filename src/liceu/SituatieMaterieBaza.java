/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import java.util.ArrayList;
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
    private boolean showMean1 = false;
    private boolean showMean2 = false;

    public SituatieMaterieBaza() {
        note1 = new ArrayList<Integer>();
        note2 = new ArrayList<Integer>();
        absente1 = new ArrayList<Absenta>();
        absente2 = new ArrayList<Absenta>();
    }

    public void delGrade(int semestru, Integer grade) {
        switch (semestru) {
            case 1:
                note1.remove(grade);
                break;
            case 2:
                note2.remove(grade);
                break;
        }
    }

    public void addGrade(int semestru, Integer grade) {
        switch (semestru) {
            case 1:
                note1.add(grade);
                break;
            case 2:
                note2.add(grade);
                break;
        }
    }

    public int getMean(int semestru) {
        float mean = 0;
        List<Integer> note = null;
        switch (semestru) {
            case 1:
                if (showMean1 == false) {
                    return 0;
                }
                note = note1;
                break;
            case 2:
                if (showMean2 == false) {
                    return 0;
                }
                note = note2;
                break;
            case 0:
                float x = getMean(1) + getMean(2) / 2;
                return Math.round(x);
        }

        Iterator<Integer> i = note.iterator();
        while (i.hasNext()) {
            mean += ((Integer) i.next()).intValue();
        }

        mean = mean / note.size();
        return Math.round(mean);
    }

    public void showMean(int semestru) {
        switch (semestru) {
            case 1:
                showMean1 = true;
                break;
            case 2:
                showMean2 = true;
                break;
        }
    }

    public String getGrades(int semestru) {
        List<Integer> note = null;
        switch (semestru) {
            case 1:
                note = note1;
                break;
            case 2:
                note = note2;
                break;
        }

        Iterator i = note.iterator();
        String grades = "";
        if (i.hasNext()) {
            grades += i.next().toString();
        }

        while (i.hasNext()) {
            grades += ", " + i.next().toString();
        }
        return grades;
    }

    public Integer[] getGradesForProf(int semestru) {
        switch (semestru) {
            case 1:
                return (Integer[]) note1.toArray(new Integer[note1.size()]);
            case 2:
                return (Integer[]) note2.toArray(new Integer[note2.size()]);
        }
        return null;
    }

    public Absenta[] getAbsenteForProf(int semestru) {
        switch (semestru) {
            case 1:
                return absente1.toArray(new Absenta[absente1.size()]);
            case 2:
                return absente2.toArray(new Absenta[absente2.size()]);
        }
        return null;
    }

    public ArrayList<Absenta> getAbsente(int semester) {
        switch (semester) {
            case 1:
                return absente1;
            case 2:
                return absente2;
        }
        return null;
    }

    public void addAbsenta(String date, int semester) {
        switch (semester) {
            case 1:
                absente1.add(new Absenta(date));
                break;
            case 2:
                absente2.add(new Absenta(date));
                break;
        }
    }

    public class Absenta implements java.io.Serializable {

        private String date;
        private String status;

        public Absenta(String date) {
            this.date = date;
            this.status = "nemotivata";
        }

        public void motivate() {
            this.status = "motivata";
        }

        public boolean isMotivata() {
            return status.equals("motivata");
        }

        @Override
        public String toString() {
            return date + " - " + status;
        }

        public String getDate() {
            return this.date;
        }
    }
}
