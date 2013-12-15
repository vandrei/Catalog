/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import liceu.Utilizator;

/**
 *
 * @author andrei
 */
public class LogInView extends MainView implements ActionListener{
    private JLabel blackboardLabel;
    private Timer t;
    private JLabel userLabel;
    private JLayeredPane layers;
    private JLabel passwordLabel;
    private BufferedImage blackboard;
    private JLabel professorLabel;
    Utilizator user;
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        layers.setLocation(layers.getLocation().x, layers.getLocation().y + 10);
        layers.repaint();
        super.repaint();
        if (layers.getLocation().y >= (getPreferredSize().height - blackboard.getHeight())/2)
        {
            t.stop();
            
        } 
    }
    
    public LogInView(Utilizator user)
    {
        super();
        this.user = user;
        try
        {
           
            layers = new JLayeredPane();
            
            blackboard = ImageIO.read(new File("img/blackboard.png"));
            blackboardLabel = new JLabel(new ImageIcon(blackboard));
            blackboardLabel.setBounds((super.getPreferredSize().width - blackboard.getWidth())/2, 0, blackboard.getWidth(), blackboard.getHeight());
            
            userLabel = new JLabel("Utilizator:");
            userLabel.setFont(new Font("SweetlyBroken", Font.PLAIN, 39));
            userLabel.setForeground(Color.WHITE);
            userLabel.setBounds(blackboardLabel.getBounds().x + 45, blackboardLabel.getBounds().y + 50, 200, 100);
            
            passwordLabel = new JLabel("Parola:");
            passwordLabel.setFont(new Font("SweetlyBroken", Font.PLAIN, 39));
            passwordLabel.setForeground(Color.WHITE);
            passwordLabel.setBounds(userLabel.getBounds().x, userLabel.getBounds().y + 80, 200, 100);
            
            BufferedImage profImg = ImageIO.read(new File("img/professor_clipart_left.png"));
                professorLabel = new JLabel (new ImageIcon(profImg));
                professorLabel.setBounds(getPreferredSize().width - profImg.getWidth() - 50,
                        blackboard.getHeight() - profImg.getHeight() / 2 - 25, profImg.getWidth(), profImg.getHeight());
                Color c = professorLabel.getBackground();
                layers.add(professorLabel, new Integer(3));
            
            JTextField usernameTextField = new JTextField();
            usernameTextField.setBounds(userLabel.getBounds().x + 120, userLabel.getBounds().y, 400, 100);
            usernameTextField.setFont(new Font("JennaSue", Font.PLAIN, 40));
            usernameTextField.setForeground(new Color(102, 178, 255));
            usernameTextField.setBackground(new Color(0,0,0,0));
            usernameTextField.setCaretColor(new Color(255,255,255,120));
            usernameTextField.setSelectionColor(new Color(0,0,0,70));
            usernameTextField.setSelectedTextColor(new Color(102, 178, 255));
            usernameTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            
            JTextField passwordTextField = new JPasswordField();
            passwordTextField.setBounds(passwordLabel.getBounds().x + 120, passwordLabel.getBounds().y, 400, 100);
            passwordTextField.setFont(new Font("JennaSue", Font.PLAIN, 40));
            passwordTextField.setForeground(new Color(102, 178, 255));
            passwordTextField.setBackground(new Color(0,0,0,0));
            passwordTextField.setCaretColor(new Color(255,255,255,120));
            passwordTextField.setSelectionColor(new Color(0,0,0,70));
            passwordTextField.setSelectedTextColor(new Color(102, 178, 255));
            passwordTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            
            BufferedImage arrowImage = ImageIO.read(new File("img/arrow.png"));
            JButton logInButton = new JButton(new ImageIcon(arrowImage));
            logInButton.setBounds(professorLabel.getBounds().x - arrowImage.getWidth() + 10, 
                    passwordTextField.getBounds().y + passwordTextField.getBounds().height, arrowImage.getWidth(), arrowImage.getHeight());
            logInButton.setOpaque(false);
            logInButton.setContentAreaFilled(false);
            logInButton.setBorderPainted(false);
            
            layers.add(blackboardLabel, new Integer(2));
            layers.add(userLabel, new Integer(3));
            layers.add(passwordLabel, new Integer(3));
            layers.add(usernameTextField, new Integer(3));
            layers.add(passwordTextField, new Integer(3));
            layers.add(logInButton, new Integer(3));
            
            add(layers);
            t = new Timer(2, this);
            pack();
            setVisible(true);
            t.start();
            
            
        }
        catch (Exception e)
        {
            System.out.println("err");
        }
    }
    
    private void setInputBoxes()
    {
    }
    
}
