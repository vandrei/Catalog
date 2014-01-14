/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import liceu.Elev;
import liceu.Materie;
import liceu.SituatieMaterieBaza;
import liceu.SituatieMaterieBaza.Absenta;

/**
 *
 * @author andrei
 */
public class SkipsView extends CatalogView {
    private Elev user = null;
    private HomeView back = null;
    private HashMap <Materie, ? extends SituatieMaterieBaza> situatieElev;
    
    public SkipsView(Elev elev, final HomeView back)
    {
        super();
        this.user = elev;
        this.back = back;
        situatieElev = elev.getSituatie();
        Set<Materie> materii = situatieElev.keySet();
        Iterator<Materie> it = materii.iterator();
        JLayeredPane layers = new JLayeredPane();
        
        JLabel semLabel = new JLabel ("Semestrul 1");
        semLabel.setFont(new Font("JennaSue", Font.PLAIN, 35));
        semLabel.setForeground(Color.BLUE);
        semLabel.setBounds(250, 30, 150, 30);
        layers.add(semLabel, new Integer(2));
        
        semLabel = new JLabel ("Semestrul 2");
        semLabel.setFont(new Font("JennaSue", Font.PLAIN, 35));
        semLabel.setForeground(Color.BLUE);
        semLabel.setBounds(670, 30, 150, 30);
        layers.add(semLabel, new Integer(2));
        
        int height = 75;
        add(layers);
        while(it.hasNext())
        {
            Materie crr_mat = it.next();
            SituatieMaterieBaza sit = situatieElev.get(crr_mat);
            JLabel matNameLabel = new JLabel (crr_mat.toString());
            matNameLabel.setBounds(25, height, 150, 40);
            matNameLabel.setFont(new Font("JennaSue", Font.PLAIN, 35));
            layers.add(matNameLabel, new Integer(2));
            ArrayList<Absenta> absente = sit.getAbsente(1);
            
            int width = 175 + 5;
            Iterator<Absenta> absIt = absente.iterator();
            while (absIt.hasNext())
            {
                Absenta crr_abs = absIt.next();
                JLabel absLabel = new JLabel(crr_abs.getDate());
                absLabel.setFont(new Font ("JennaSue", Font.PLAIN, 25));
                if (crr_abs.isMotivata())
                    absLabel.setForeground(new Color(0, 153, 0));
                else
                    absLabel.setForeground(new Color(153,0,0));
                absLabel.setBounds(width, height, 35, 40);
                layers.add(absLabel, new Integer(2));
                width += 40;
                if (width + 40 > 600)
                    break;
            }
            
             absente = sit.getAbsente(2);
            
            width = 600 + 5;
            absIt = absente.iterator();
            while (absIt.hasNext())
            {
                Absenta crr_abs = absIt.next();
                JLabel absLabel = new JLabel(crr_abs.getDate());
                absLabel.setFont(new Font ("JennaSue", Font.PLAIN, 25));
                if (crr_abs.isMotivata())
                    absLabel.setForeground(new Color(0, 153, 0));
                else
                    absLabel.setForeground(new Color(153,0,0));
                absLabel.setBounds(width, height, 35, 40);
                layers.add(absLabel, new Integer(2));
                width += 40;
                if (width +40 > 1020)
                    break;
            }
            height += 50;
        }
        JButton backButton = new JButton ("back");
        backButton.setBounds(970, 30, 50, 20);
        layers.add(backButton, new Integer(2));
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
                back.setVisible(true);
            }
        });
        
    }
    
}
