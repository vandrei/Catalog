/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

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
    
    
    private class Absenta
    {
        String date;
        String status;
        
        Absenta(String date)
        {
            this.date = date;
            this.status = "nemotivata";
        }
    }
    
}
