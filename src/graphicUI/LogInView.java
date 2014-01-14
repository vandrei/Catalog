/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import liceu.Centralizator;
import liceu.Utilizator;

/**
 *
 * @author andrei
 */
public class LogInView extends MainView implements ActionListener {

    private JLabel blackboardLabel;
    private JLabel userLabel;
    private JLayeredPane layers;
    private JLabel passwordLabel;
    private BufferedImage blackboard;
    private JLabel professorLabel;
    private JButton logInButton;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JLabel warning;

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (warning != null) {
            layers.remove(warning);
            warning = null;
        }
        Centralizator ct = Centralizator.getCentralizator();
        Utilizator user = Centralizator.getCentralizator().authenticate(usernameTextField.getText(), passwordTextField.getText());
        if (user != null) {
            new boardAnimation(layers, this).boardUp(user);
        } else {
            if (usernameTextField.getText().length() != 0 && passwordTextField.getText().length() != 0) {
                warning = new JLabel("Date de autentificare incorecte.");
                warning.setFont(new Font("SweetlyBroken", Font.PLAIN, 45));
                warning.setForeground(Color.RED);
                warning.setBounds(blackboardLabel.getBounds().x + 130, blackboardLabel.getBounds().y + 30, 400, 50);
                layers.add(warning, new Integer(5));
            } else {
                warning = new JLabel("Introduceti datele de autentificare");
                warning.setFont(new Font("SweetlyBroken", Font.PLAIN, 45));
                warning.setForeground(Color.RED);
                warning.setBounds(blackboardLabel.getBounds().x + 130, blackboardLabel.getBounds().y + 30, 400, 50);
                layers.add(warning, new Integer(5));
            }
        }
    }

    public LogInView() {
        super();
        //this.user = user;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/JennaSue.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Sweetly Broken.ttf")));
        } catch (IOException e) {
        } catch (FontFormatException e)
        {}
        
        try {

            layers = new JLayeredPane();

            blackboard = ImageIO.read(new File("img/blackboard.png"));
            blackboardLabel = new JLabel(new ImageIcon(blackboard));
            blackboardLabel.setBounds((super.getPreferredSize().width - blackboard.getWidth()) / 2, 0, blackboard.getWidth(), blackboard.getHeight());

            userLabel = new JLabel("Utilizator:");
            userLabel.setFont(new Font("SweetlyBroken", Font.PLAIN, 39));
            userLabel.setForeground(Color.WHITE);
            userLabel.setBounds(blackboardLabel.getBounds().x + 45, blackboardLabel.getBounds().y + 50, 200, 100);

            passwordLabel = new JLabel("Parola:");
            passwordLabel.setFont(new Font("SweetlyBroken", Font.PLAIN, 39));
            passwordLabel.setForeground(Color.WHITE);
            passwordLabel.setBounds(userLabel.getBounds().x, userLabel.getBounds().y + 80, 200, 100);

            BufferedImage profImg = ImageIO.read(new File("img/professor_clipart_left.png"));
            professorLabel = new JLabel(new ImageIcon(profImg));
            professorLabel.setBounds(getPreferredSize().width - profImg.getWidth() - 50,
                    blackboard.getHeight() - profImg.getHeight() / 2 - 25, profImg.getWidth(), profImg.getHeight());
            Color c = professorLabel.getBackground();
            layers.add(professorLabel, new Integer(3));

            usernameTextField = new JTextField();
            usernameTextField.setBounds(userLabel.getBounds().x + 120, userLabel.getBounds().y, 400, 100);
            usernameTextField.setFont(new Font("JennaSue", Font.PLAIN, 40));
            usernameTextField.setForeground(new Color(102, 178, 255));
            usernameTextField.setBackground(new Color(0, 0, 0, 0));
            usernameTextField.setCaretColor(new Color(255, 255, 255, 120));
            usernameTextField.setSelectionColor(new Color(0, 0, 0, 70));
            usernameTextField.setSelectedTextColor(new Color(102, 178, 255));
            usernameTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    usernameTextField.requestFocus();
                }
            });
            usernameTextField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    passwordTextField.requestFocus();
                }
            });

            passwordTextField = new JPasswordField();
            passwordTextField.setBounds(passwordLabel.getBounds().x + 120, passwordLabel.getBounds().y, 400, 100);
            passwordTextField.setFont(new Font("JennaSue", Font.PLAIN, 40));
            passwordTextField.setForeground(new Color(102, 178, 255));
            passwordTextField.setBackground(new Color(0, 0, 0, 0));
            passwordTextField.setCaretColor(new Color(255, 255, 255, 120));
            passwordTextField.setSelectionColor(new Color(0, 0, 0, 70));
            passwordTextField.setSelectedTextColor(new Color(102, 178, 255));
            passwordTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            passwordTextField.addActionListener(this);

            BufferedImage arrowImage = ImageIO.read(new File("img/arrow.png"));
            logInButton = new JButton(new ImageIcon(arrowImage));
            logInButton.setBounds(professorLabel.getBounds().x - arrowImage.getWidth() + 10,
                    passwordTextField.getBounds().y + passwordTextField.getBounds().height, arrowImage.getWidth(), arrowImage.getHeight());
            logInButton.setOpaque(false);
            logInButton.setContentAreaFilled(false);
            logInButton.setBorderPainted(false);
            logInButton.addActionListener(this);

            BufferedImage leaveImage = ImageIO.read(new File("img/exit.png"));
            JButton signOut = new JButton(new ImageIcon(leaveImage));
            signOut.setBounds(960, 10, leaveImage.getWidth(), leaveImage.getHeight());
            signOut.setOpaque(false);
            signOut.setContentAreaFilled(false);
            signOut.setBorderPainted(false);
            signOut.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Centralizator.getCentralizator().saveCentralizator();
                    System.exit(0);
                }
            });

            layers.add(blackboardLabel, new Integer(2));
            layers.add(signOut, new Integer(2));
            layers.add(userLabel, new Integer(3));
            layers.add(passwordLabel, new Integer(3));
            layers.add(usernameTextField, new Integer(3));
            layers.add(passwordTextField, new Integer(3));
            layers.add(logInButton, new Integer(3));

            add(layers);
            pack();
            setVisible(true);
            new boardAnimation(layers, this).boardDown();


        } catch (Exception e) {
            System.out.println("err");
        }
    }

    private class boardAnimation implements ActionListener {

        private Timer downTimer;
        private Timer upTimer;
        private JLayeredPane layers;
        private LogInView frame;
        private Utilizator user;

        public boardAnimation(JLayeredPane layers, LogInView frame) {
            this.layers = layers;
            this.frame = frame;
        }

        public void boardDown() {
            downTimer = new Timer(2, this);
            pack();
            setVisible(true);
            downTimer.start();
        }

        public void boardUp(Utilizator user) {
            upTimer = new Timer(20, this);
            this.user = user;
            upTimer.start();
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == downTimer) {
                layers.setLocation(layers.getLocation().x, layers.getLocation().y + 10);
                layers.repaint();
                frame.repaint();
                if (layers.getLocation().y >= (getPreferredSize().height - blackboard.getHeight()) / 2) {
                    downTimer.stop();
                }
            } else {
                layers.setLocation(layers.getLocation().x, layers.getLocation().y - 10);
                layers.repaint();
                frame.repaint();
                if (layers.getLocation().y <= 9) {
                    upTimer.stop();
                    setVisible(false);
                    new HomeView(user);
                }
            }
        }
    }
}
