/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import apple.awt.CColor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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
        
        JLabel s1Expl = new JLabel ("Semetrul 1");
        s1Expl.setBounds(250, 50, 250, 40);
        s1Expl.setForeground(Color.BLUE);
        s1Expl.setFont(new Font("JennaSue", Font.PLAIN, 38));
        layers.add(s1Expl, new Integer(2));
        
        JLabel s2Expl = new JLabel ("Semetrul 2");
        s2Expl.setBounds(525, 50, 250, 40);
        s2Expl.setForeground(Color.BLUE);
        s2Expl.setFont(new Font("JennaSue", Font.PLAIN, 38));
        layers.add(s2Expl, new Integer(2));
        
        JLabel t1Expl = new JLabel ("Teze");
        t1Expl.setBounds(790, 50, 80, 40);
        t1Expl.setForeground(Color.RED);
        t1Expl.setFont(new Font("JennaSue", Font.PLAIN, 38));
        layers.add(t1Expl, new Integer(2));

        JLabel m1Expl = new JLabel ("Medii");
        m1Expl.setBounds(900, 50, 65, 40);
        m1Expl.setForeground(new Color(153, 0, 0));
        m1Expl.setFont(new Font("JennaSue", Font.PLAIN, 38));
        layers.add(m1Expl, new Integer(2));
        
        
        int heightNew = 105;
        for (int i = 0; i < nr_materii; i++)
        {
            Materie crr_mat = (Materie) it.next();
            JLabel materieName = new JLabel(crr_mat.toString());
            materieName.setFont(new Font("JennaSue", Font.PLAIN, 38));
            materieName.setForeground(Color.BLACK);
            materieName.setBounds(50, heightNew, 180, 40);
            layers.add(materieName, new Integer(2));
            
            SituatieMaterieBaza situatieMat = situatieElev.get(crr_mat);
            
            JLabel materieNote1 = new JLabel(situatieMat.getGrades(1));
            materieNote1.setBounds(250, heightNew, 250, 40);
            materieNote1.setFont(new Font("JennaSue", Font.PLAIN, 40));
            materieNote1.setForeground(Color.BLUE);
            layers.add(materieNote1, new Integer(2));
            
            JLabel materieNote2 = new JLabel(situatieMat.getGrades(2));
            materieNote2.setBounds(525,heightNew, 250, 40);
            materieNote2.setFont(new Font("JennaSue", Font.PLAIN, 40));
            materieNote2.setForeground(Color.BLUE);
            layers.add(materieNote2, new Integer(2));
            
            if (situatieMat instanceof SituatieMaterieCuTeza)
            {
                if (Integer.parseInt(((SituatieMaterieCuTeza)situatieMat).getTeza(1)) > 0)
                {
                JLabel teza = new JLabel(((SituatieMaterieCuTeza)situatieMat).getTeza(1));
                teza.setBounds(790, heightNew, 25,40);
                teza.setFont(new Font("JennaSue", Font.PLAIN, 40));
                teza.setForeground(Color.RED);
                layers.add(teza, new Integer(2));
                }
                
                if (Integer.parseInt(((SituatieMaterieCuTeza)situatieMat).getTeza(2)) > 0)
                {
                JLabel teza = new JLabel(((SituatieMaterieCuTeza)situatieMat).getTeza(2));
                teza.setBounds(830, heightNew, 25, 40);
                teza.setFont(new Font("JennaSue", Font.PLAIN, 40));
                teza.setForeground(Color.RED);
                layers.add(teza, new Integer(2));
                }
            }
            
            if (situatieMat.getMean(1) > 0)
            {
            JLabel materieMedie1 = new JLabel(String.valueOf(situatieMat.getMean(1)));
            materieMedie1.setBounds(900, heightNew, 25, 40);
            materieMedie1.setFont(new Font("JennaSue", Font.PLAIN, 40));
            materieMedie1.setForeground(new Color(153, 0, 0));
            layers.add(materieMedie1, new Integer(2));
            }
            
            if (situatieMat.getMean(2) > 0)
            {
            JLabel materieMedie2 = new JLabel(String.valueOf(situatieMat.getMean(2)));
            materieMedie2.setBounds(935, heightNew, 25, 40);
            materieMedie2.setFont(new Font("JennaSue", Font.PLAIN, 40));
            materieMedie2.setForeground(new Color(153, 0, 0));
            layers.add(materieMedie2, new Integer(2));
            }
            heightNew += 55;
            
        }
        JButton backButton = new JButton("back");
        backButton.setBounds(970, 30, 50, 20);
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
