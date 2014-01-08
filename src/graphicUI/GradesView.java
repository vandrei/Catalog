/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import liceu.Centralizator;
import liceu.Elev;
import liceu.Materie;
import liceu.SituatieMaterieBaza;
import liceu.SituatieMaterieCuTeza;
import liceu.Utilizator;

/**
 *
 * @author andrei
 */
public class GradesView extends CatalogView {
    private HashMap <Materie, ? extends SituatieMaterieBaza> situatieElev;
    private Utilizator user = null;
    private HomeView back;
    
    
    public GradesView(Elev elev, final HomeView back)
    {
        super();
        this.user = elev;
        this.back = back;
        situatieElev = elev.getSituatie();
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
            materieName.setFont(new Font("SweetlyBroken", Font.PLAIN, 50));
            materieName.setForeground(Color.BLACK);
            materieName.setBounds(50, heightNew, 200, 40);
            layers.add(materieName, new Integer(2));
            
            SituatieMaterieBaza situatieMat = situatieElev.get(crr_mat);
            
            JLabel materieNote1 = new JLabel(situatieMat.getGrades(1));
            materieNote1.setBounds(250, heightNew, 300, 40);
            materieNote1.setFont(new Font("SweetlyBroken", Font.PLAIN, 45));
            materieNote1.setForeground(Color.BLUE);
            layers.add(materieNote1, new Integer(2));
            
            JLabel materieNote2 = new JLabel(situatieMat.getGrades(2));
            materieNote2.setBounds(575,heightNew, 300, 40);
            materieNote2.setFont(new Font("SweetlyBroken", Font.PLAIN, 45));
            materieNote2.setForeground(Color.BLUE);
            layers.add(materieNote2, new Integer(2));
            
            if (situatieMat instanceof SituatieMaterieCuTeza)
            {
                JLabel teza = new JLabel(((SituatieMaterieCuTeza)situatieMat).getTeza(1));
                teza.setBounds(900, heightNew, 20,40);
                teza.setFont(new Font("SweetlyBroken", Font.PLAIN, 50));
                teza.setForeground(Color.RED);
                layers.add(teza, new Integer(2));
                
                teza = new JLabel(((SituatieMaterieCuTeza)situatieMat).getTeza(2));
                teza.setBounds(955, heightNew, 20, 40);
                teza.setFont(new Font("SweetlyBroken", Font.PLAIN, 50));
                teza.setForeground(Color.RED);
                layers.add(teza, new Integer(2));
            }
            
            heightNew += 55;
            
        }
        JButton backButton = new JButton("back");
        backButton.setBounds(990, 30, 50, 20);
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
                back.setVisible(true);
            }
        });
        
        layers.add(backButton, new Integer(2));
        
        add(layers);
    }
    
}
