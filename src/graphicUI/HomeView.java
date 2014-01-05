/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import liceu.Administrator;
import liceu.Centralizator;
import liceu.Clasa;
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
    private Clasa selectedClasa;
    
    public HomeView(Utilizator user)
    {
        super();
        this.user = user;
       // homeforElev();
        
        if (user instanceof Elev)
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
        }
    }

    private void homeforElev() {
        layers = new JLayeredPane();
        try
        {
            BufferedImage gradesImage = ImageIO.read(new File("img/reportcard.png"));
            JButton viewGrades = new JButton(new ImageIcon(gradesImage));
            viewGrades.setBounds(280, 500, gradesImage.getWidth(), gradesImage.getHeight());
            viewGrades.setOpaque(false);
            viewGrades.setContentAreaFilled(false);
            viewGrades.setBorderPainted(false);
            viewGrades.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    setVisible(false);
                    new GradesView((Elev)user);
                }
            });
            
            layers.add(viewGrades, new Integer(2));
            
            BufferedImage skipsImage = ImageIO.read(new File("img/absente2.png"));
            JButton viewSkips = new JButton(new ImageIcon(skipsImage));
            viewSkips.setBounds(630, 500, skipsImage.getWidth(), skipsImage.getHeight());
            viewSkips.setOpaque(false);
            viewSkips.setContentAreaFilled(false);
            viewSkips.setBorderPainted(false);
            layers.add(viewSkips, new Integer(1));
            
            BufferedImage smallBoard = ImageIO.read(new File("img/blackboard_small.png"));
            JLabel blackboard = new JLabel(new ImageIcon(smallBoard));
            blackboard.setBounds((getPreferredSize().width - smallBoard.getWidth())/2, 0, smallBoard.getWidth(), smallBoard.getHeight());
            
            layers.add(blackboard, new Integer(2));
            
            JLabel welcomeText = new JLabel("Bine ai revenit, " + user.getFirstName());
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
            signOut.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Centralizator.getCentralizator().signOutUser();
                }
            });
            
            layers.add(signOut, new Integer(2));
            
            BufferedImage infoImage = ImageIO.read(new File("img/infogreen.png"));
            JButton info = new JButton(new ImageIcon(infoImage));
            info.setBounds(520, 500, infoImage.getWidth(), infoImage.getHeight());
            info.setOpaque(false);
            info.setContentAreaFilled(false);
            info.setBorderPainted(false);
            
            layers.add(info, new Integer(2));
            
        
        add(layers);
        pack();
        setVisible(true);
        }
        catch (Exception e)
        {
            
        }
    }
    
    public void setSelectedClass(Clasa cls)
    {
        selectedClasa = cls;
    }
    
    public Clasa getSelectedClass()
    {
        return selectedClasa;
    }

    private void homeForProfesor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void homeForSecretar() {
       // layers = new JLayeredPane();
        
       /* JButton b = new JButton("sadcas");
        b.setBounds(50,50,50,50);
        layers.add(b);*/
        final JFrame frame = this;
        final HomeView homeViewvar = this;
        
        Centralizator centralizator = Centralizator.getCentralizator();
        
       JMenuBar menuBar = new JMenuBar();
       JMenu claseMenu = new JMenu("Clase");
       JMenu profesoriMenu = new JMenu("Profesori");
       menuBar.add(claseMenu);
       menuBar.add(profesoriMenu);
       
       
       JMenu clasa9 = new JMenu("Clasa a IX-a");
       JMenu clasa10 = new JMenu("Clasa a X-a");
       JMenu clasa11 = new JMenu("Clasa a XI-a");
       JMenu clasa12 = new JMenu("Clasa a XII-a");
       
       final JLayeredPane classEditLayer = new JLayeredPane();
       final JList eleviList = new JList();
       eleviList.setBounds(150, 150, 200, 300);
       classEditLayer.add(eleviList, new Integer(2));
       
       JButton addElevButton = new JButton("Adauga Elev");
       JButton removeElevButton = new JButton("Sterge Elev");
       addElevButton.setBounds(150, 470, 90, 40);
       removeElevButton.setBounds(260, 470, 90, 40);
       classEditLayer.add(addElevButton, new Integer(2));
       classEditLayer.add(removeElevButton, new Integer(2));
       
       removeElevButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Object[] toBeDel = eleviList.getSelectedValues();
                for (int i = 0; i < toBeDel.length; i++)
                {
                    selectedClasa.delElev((Elev) toBeDel[i]);
                }
                eleviList.setListData(selectedClasa.getElevNames());
            }
        });
       
       Set<String> classNames = centralizator.getClassNames();
       Iterator classIterator = classNames.iterator();
       
       while (classIterator.hasNext())
       {
           String cName = (String) classIterator.next();
           JMenuItem crr_item = new JMenuItem(cName);
           JMenu clasax;
           if (cName.startsWith("9"))
           {
               clasax = clasa9;
           }
           else if (cName.startsWith("10"))
           {
               clasax = clasa10;
           }
           else if (cName.startsWith("11"))
           {
               clasax = clasa11;
           }
           else
           {
               clasax = clasa12;
           }
           clasax.add(crr_item);
           crr_item.addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent ae) {
                   Centralizator c = Centralizator.getCentralizator();
                   Clasa cl = c.getClasa(ae.getActionCommand());
                   Elev[] elevi = (Elev[]) cl.getElevNames();
                   homeViewvar.setSelectedClass(cl);
                   
                   eleviList.setListData(elevi);
                   if (classEditLayer.getParent() == null)
                   {
                       frame.add(classEditLayer);
                       frame.repaint();
                       pack();
                   }
               }
           });
       }
       
       claseMenu.add(clasa9);
       claseMenu.add(clasa10);
       claseMenu.add(clasa11);
       claseMenu.add(clasa12);
       
       setJMenuBar(menuBar);
       // add(layers);
       pack();
       setVisible(true);
    }

    private void homeForAdmin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
