/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import com.sun.tools.internal.xjc.api.ClassNameAllocator;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
                    setVisible(false);
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


    private void homeForProfesor() {
        
    }
   

    private void homeForSecretar() {
        final JFrame frame = this;
        final HomeView homeViewvar = this;
        
        Centralizator centralizator = Centralizator.getCentralizator();
        
       JMenuBar menuBar = new JMenuBar();
       JMenu claseMenu = new JMenu("Clase");
       JMenu profesoriMenu = new JMenu("Profesori");
       JMenuItem logOutItem = new JMenuItem("Deconectare");
       menuBar.add(claseMenu);
       menuBar.add(profesoriMenu);
       menuBar.add(logOutItem);
       logOutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
                Centralizator.getCentralizator().signOutUser();
            }
        });
       
       
       final JMenu clasa9 = new JMenu("Clasa a IX-a");
       final JMenu clasa10 = new JMenu("Clasa a X-a");
       final JMenu clasa11 = new JMenu("Clasa a XI-a");
       final JMenu clasa12 = new JMenu("Clasa a XII-a");
       
       final JLayeredPane classEditLayer = new JLayeredPane();
       final JList eleviList = new JList();
       eleviList.setBounds(150, 150, 250, 300);
       classEditLayer.add(eleviList, new Integer(2));
       
       JButton addElevButton = new JButton("Adauga");
       JButton removeElevButton = new JButton("Sterge");
       JButton editElev = new JButton("Modifica");
       addElevButton.setBounds(150, 470, 80, 40);
       removeElevButton.setBounds(235, 470, 80, 40);
       editElev.setBounds(320, 470, 80, 40);
       classEditLayer.add(addElevButton, new Integer(2));
       classEditLayer.add(removeElevButton, new Integer(2));
       classEditLayer.add(editElev, new Integer(2));
       
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
       
       makeMenu(clasa9, clasa10, clasa11, clasa12, eleviList, this, classEditLayer);
       
       editElev.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                final Elev selectedElev = (Elev) eleviList.getSelectedValue();
                
                final JTextField username = new JTextField(selectedElev.getUsername());
                username.setBounds(150, 530, 250, 30);
                
                final JTextField name = new JTextField(selectedElev.getLastName());
                name.setBounds(150, 570, 80, 30);
                
                final JTextField prenume = new JTextField(selectedElev.getFirstName());
                prenume.setBounds(235, 570, 80, 30);
                
                final JTextField clasa = new JTextField(selectedElev.getClassID());
                clasa.setBounds(150,610, 100, 30);
                
                final JTextField cnp = new JTextField(selectedElev.getCNP());
                cnp.setBounds(320, 570, 80, 30);
                
                final JButton addB = new JButton("Modifica!");
                addB.setBounds(150, 650, 250, 30);
                
                addB.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        selectedElev.changeInfo(username.getText(), name.getText(), prenume.getText(), cnp.getText());
                        selectedElev.changeClasa(clasa.getText());
                        eleviList.setListData(selectedClasa.getElevNames());
                        classEditLayer.remove(username);
                        classEditLayer.remove(name);
                        classEditLayer.remove(prenume);
                        classEditLayer.remove(cnp);
                        classEditLayer.remove(clasa);
                        classEditLayer.remove(addB);
                        frame.repaint();
                    }
                });
                
                classEditLayer.add(username, new Integer(2));
                classEditLayer.add(name, new Integer(2));
                classEditLayer.add(prenume, new Integer(2));
                classEditLayer.add(cnp, new Integer(2));
                classEditLayer.add(addB, new Integer(2));
                classEditLayer.add(clasa, new Integer(2));
                frame.repaint();
            }
        });
       
       addElevButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                final JTextField username = new JTextField();
                username.setBounds(150, 530, 250, 30);
                username.setText("utilizator");
                
                final JTextField name = new JTextField("Nume");
                name.setBounds(150, 570, 80, 30);
                
                final JTextField prenume = new JTextField("Prenume");
                prenume.setBounds(235, 570, 80, 30);
                
                final JTextField cnp = new JTextField("CNP");
                cnp.setBounds(320, 570, 80, 30);
                
                final JButton addB = new JButton("Adauga");
                addB.setBounds(150, 610, 250, 30);
                
                addB.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        Elev elevN = new Elev(username.getText(), "1234", name.getText(), prenume.getText(), cnp.getText());
                        elevN.setClasa(selectedClasa.getClassID());
                        selectedClasa.addElev(elevN);
                        
                        eleviList.setListData(selectedClasa.getElevNames());
                        classEditLayer.remove(username);
                        classEditLayer.remove(name);
                        classEditLayer.remove(prenume);
                        classEditLayer.remove(cnp);
                        classEditLayer.remove(addB);
                        frame.repaint();
                    }
                });
                
                classEditLayer.add(username, new Integer(2));
                classEditLayer.add(name, new Integer(2));
                classEditLayer.add(prenume, new Integer(2));
                classEditLayer.add(cnp, new Integer(2));
                classEditLayer.add(addB, new Integer(2));
                frame.repaint();
                
            }
        });
       
       
       
       JMenuItem addClasa = new JMenuItem("Adauga o clasa");
       final JLayeredPane addClassLayer = new JLayeredPane();
       final JTextField className = new JTextField();
       className.setBounds(250, 250, 150, 30);
       addClassLayer.add(className, new Integer(2));
       
       JButton addClasaButton = new JButton("Adauga clasa");
       addClasaButton.setBounds(250, 290, 150, 30);
       addClasaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ((Secretar) user).addClasa(className.getText());
                homeViewvar.remove(addClassLayer);
                makeMenu(clasa9, clasa10, clasa11, clasa12, eleviList, homeViewvar, classEditLayer);
                homeViewvar.repaint();
            }
        });
       addClassLayer.add(addClasaButton, new Integer(2));
       
       addClasa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (classEditLayer.getParent() != null)
                {
                    homeViewvar.remove(classEditLayer);
                }
                homeViewvar.add(addClassLayer);
                pack();
                homeViewvar.repaint();
            }
        });
       
       claseMenu.add(clasa9);
       claseMenu.add(clasa10);
       claseMenu.add(clasa11);
       claseMenu.add(clasa12);
       
       
       claseMenu.add(addClasa);
       
       setJMenuBar(menuBar);
       // add(layers);
       pack();
       setVisible(true);
    }
    
    private void addClassToMenu(String classID, JMenu clasax, final JList eleviList, final JLayeredPane classEditLayer, final JFrame frame)
    {
        JMenuItem crr_item = new JMenuItem(classID);
        crr_item.addActionListener(new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent ae) {
                   Centralizator c = Centralizator.getCentralizator();
                   Clasa cl = c.getClasa(ae.getActionCommand());
                   Elev[] elevi = (Elev[]) cl.getElevNames();
                   setSelectedClass(cl);
                   
                   eleviList.setListData(elevi);
                   if (classEditLayer.getParent() == null)
                   {
                       frame.add(classEditLayer);
                       frame.repaint();
                       pack();
                   }
               }
           });
        clasax.add(crr_item, clasax.getItemCount() - 1);
    }
    
    private void makeMenu(JMenu clasa9, JMenu clasa10, JMenu clasa11, JMenu clasa12, final JList eleviList, final HomeView frame, final JLayeredPane classEditLayer)
    {
        clasa9.removeAll();
        clasa10.removeAll();
        clasa11.removeAll();
        clasa12.removeAll();
        Centralizator centralizator = Centralizator.getCentralizator();
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
                   setSelectedClass(cl);
                   
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
    }

    private void homeForAdmin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
