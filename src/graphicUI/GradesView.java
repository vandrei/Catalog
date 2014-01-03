/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import liceu.Materie;
import liceu.SituatieMaterieBaza;
import liceu.SituatieMaterieCuTeza;
import liceu.Utilizator;

/**
 *
 * @author andrei
 */
public class GradesView extends CatalogView {
    private Utilizator user;
    HashMap <Materie, ? extends SituatieMaterieBaza> situatieElev;
    
    public GradesView(Utilizator user)
    {
        super();
        this.user = user;
        int nr_materii = situatieElev.size();
        Set<Materie> materii = null;
        materii = situatieElev.keySet();
        Iterator it = materii.iterator();
        JLayeredPane layers = new JLayeredPane();
        int heightNew = 25;
        for (int i = 0; i < nr_materii; i++)
        {
            Materie crr_mat = (Materie) it.next();
            JLabel materieName = new JLabel(crr_mat.toString());
            materieName.setBounds(25, heightNew, 200, 40);
            layers.add(materieName, new Integer(2));
            
            SituatieMaterieBaza situatieMat = situatieElev.get(crr_mat);
            
            JLabel materieNote1 = new JLabel(situatieMat.getGrades(1));
            materieNote1.setBounds(250, heightNew, 300, 40);
            layers.add(materieNote1, new Integer(2));
            
            JLabel materieNote2 = new JLabel(situatieMat.getGrades(2));
            materieNote2.setBounds(575,heightNew, 300, 40);
            layers.add(materieNote2, new Integer(2));
            
            if (situatieMat instanceof SituatieMaterieCuTeza)
            {
                JLabel teza = new JLabel(((SituatieMaterieCuTeza)situatieMat).getTeza(1));
                teza.setBounds(900, heightNew,10,40);
                teza.setForeground(Color.RED);
                layers.add(teza, new Integer(2));
                
                teza = new JLabel(((SituatieMaterieCuTeza)situatieMat).getTeza(2));
                teza.setBounds(955, heightNew, 10, 40);
                teza.setForeground(Color.RED);
                layers.add(teza, new Integer(2));
            }
            
            heightNew += 15 + 40;
            
        }
        add(layers);
    }
    
}
