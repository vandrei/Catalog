/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public class SituatieMaterieCuTeza extends SituatieMaterieBaza implements java.io.Serializable {
    private int teza1;
    private int teza2;
    
    public SituatieMaterieCuTeza()
    {
        super();
    }
    
    public void addTeza(String grade, int semester)
    {
        switch(semester)
        {
            case 1:
                teza1 = Integer.parseInt(grade);
                break;
            case 2:
                teza2 = Integer.parseInt(grade);
                break;
        }
    }
    
    public String getTeza(int i) {
        switch(i)
        {
            case 1:
                return String.valueOf(teza1);
            case 2:
                return String.valueOf(teza2);
        }
        return null;
    }
    
    
}
