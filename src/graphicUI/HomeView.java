/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import liceu.Administrator;
import liceu.Elev;
import liceu.Profesor;
import liceu.Secretar;
import liceu.Utilizator;

/**
 *
 * @author andrei
 */
public class HomeView extends MainView {
    private Utilizator user;
    private JLayeredPane layers;
    
    public HomeView(Utilizator user)
    {
        super();
        this.user = user;
        homeforElev();
        
    /*    if (user instanceof Elev)
        {
            homeforElev();
        }
        else if (user instanceof Profesor)
        {
            homeForProfesor();
        }
        else if (user instanceof Secretar)
        {
            homeForSecretar();
        }
        else if (user instanceof Administrator)
        {
            homeForAdmin();
        }*/
    }

    private void homeforElev() {
        layers = new JLayeredPane();
        try
        {
            BufferedImage gradesImage = ImageIO.read(new File("img/reportcard.png"));
            JButton viewGrades = new JButton(new ImageIcon(gradesImage));
            viewGrades.setBounds(300, 500, gradesImage.getWidth(), gradesImage.getHeight());
            viewGrades.setOpaque(false);
            viewGrades.setContentAreaFilled(false);
            viewGrades.setBorderPainted(false);
            
            layers.add(viewGrades, new Integer(2));
            
            BufferedImage skipsImage = ImageIO.read(new File("img/absente2.png"));
            JButton viewSkips = new JButton(new ImageIcon(skipsImage));
            viewSkips.setBounds(610, 500, skipsImage.getWidth(), skipsImage.getHeight());
            viewSkips.setOpaque(false);
            viewSkips.setContentAreaFilled(false);
            viewSkips.setBorderPainted(false);
            layers.add(viewSkips, new Integer(1));
            
            BufferedImage smallBoard = ImageIO.read(new File("img/blackboard_small.png"));
            JLabel blackboard = new JLabel(new ImageIcon(smallBoard));
            blackboard.setBounds((getPreferredSize().width - smallBoard.getWidth())/2, 0, smallBoard.getWidth(), smallBoard.getHeight());
            
            layers.add(blackboard, new Integer(2));
            
            JLabel welcomeText = new JLabel("Bine ai revenit, " + "Costel");//user.getFirstName());
            welcomeText.setFont(new Font("SweetlyBroken", Font.PLAIN, 39));
            welcomeText.setForeground(Color.white);
            welcomeText.setBounds(blackboard.getBounds().x + 30, blackboard.getBounds().y + 30, 340, 40);
            
            layers.add(welcomeText, new Integer(3));
            
            BufferedImage leaveImage = ImageIO.read(new File("img/exit.png"));
            JButton signOut = new JButton(new ImageIcon(leaveImage));
            signOut.setBounds(960, 10, leaveImage.getWidth(), leaveImage.getHeight());
            signOut.setOpaque(false);
            signOut.setContentAreaFilled(false);
            signOut.setBorderPainted(false);
            
            layers.add(signOut, new Integer(2));
            
            
        
        add(layers);
        pack();
        setVisible(true);
        }
        catch (Exception e)
        {
            
        }
    }

    private void homeForProfesor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void homeForSecretar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void homeForAdmin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
