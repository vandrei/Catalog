/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author andrei
 */
public interface IElev {
    
    public HashMap<Materie, ? extends SituatieMaterieBaza> getSituatie();
    
}
