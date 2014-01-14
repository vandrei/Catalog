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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import liceu.Administrator;
import liceu.Centralizator;
import liceu.Clasa;
import liceu.Elev;
import liceu.Materie;
import liceu.Profesor;
import liceu.Secretar;
import liceu.SituatieMaterieBaza.Absenta;
import liceu.Utilizator;

/**
 *
 * @author andrei
 */
public class HomeView extends MainView {

    private Utilizator user;
    private JLayeredPane layers;
    private final JLayeredPane materiilayer = new JLayeredPane();
    private Clasa selectedClasa;
    private final JLayeredPane classEditLayer = new JLayeredPane();
    private final JLayeredPane profesoriLayer = new JLayeredPane();
    private final JLayeredPane addClassLayer = new JLayeredPane();
    private final JLayeredPane layer2 = new JLayeredPane();
    private final JLayeredPane layer3 = new JLayeredPane();
    private int semestru;
    private HomeView hView;

    public HomeView(Utilizator user) {
        super();
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/JennaSue.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Sweetly Broken.ttf")));
        } catch (IOException e) {
        } catch (FontFormatException e)
        {}
        
        this.user = user;
        this.hView = this;

        if (user instanceof Elev) {
            homeforElev();
        } else if (user instanceof Profesor) {
            homeForProfesor();
        } else if (user instanceof Secretar) {
            homeForSecretar();
        } else if (user instanceof Administrator) {
            homeForAdmin();
        }
    }

    private void makeClassLayer(final JLayeredPane classLayer, String cName) {
        classLayer.removeAll();
        final JList eleviList = new JList();
        final Elev[] elevName = selectedClasa.getElevNames();

        final JRadioButton sem1 = new JRadioButton("Semestrul 1");
        sem1.setBounds(100, 355, 150, 30);
        sem1.setForeground(Color.white);
        sem1.setSelected(true);
        classLayer.add(sem1, new Integer(2));

        final JRadioButton sem2 = new JRadioButton("Semestrul 2");
        sem2.setBounds(100, 390, 150, 30);
        sem2.setForeground(Color.white);
        classLayer.add(sem2, new Integer(2));

        ButtonGroup group = new ButtonGroup();
        group.add(sem1);
        group.add(sem2);

        final Comparator<Elev> alphaCompU = new Comparator<Elev>() {
            @Override
            public int compare(Elev t, Elev t1) {
                if (t.getLastName().compareTo(t1.getLastName()) == 0) {
                    return t.getFirstName().compareTo(t1.getFirstName());
                } else {
                    return t.getLastName().compareTo(t1.getLastName());
                }
            }
        };

        final Comparator<Elev> alphaCompD = new Comparator<Elev>() {
            @Override
            public int compare(Elev t, Elev t1) {
                if (t1.getLastName().compareTo(t.getLastName()) == 0) {
                    return t1.getFirstName().compareTo(t.getFirstName());
                } else {
                    return t1.getLastName().compareTo(t.getLastName());
                }
            }
        };

        final Comparator<Elev> meanCompU = new Comparator<Elev>() {
            @Override
            public int compare(Elev t, Elev t1) {
                int sem = 1;
                if (sem2.isSelected()) {
                    sem = 2;
                }
                int G1 = t.getSituatie().get(((Profesor) user).getMaterie()).getMean(sem);
                int G2 = t1.getSituatie().get(((Profesor) user).getMaterie()).getMean(sem);
                if (G1 < G2) {
                    return -1;
                }
                if (G1 > G2) {
                    return 1;
                }
                return 0;
            }
        };

        final Comparator<Elev> meanCompD = new Comparator<Elev>() {
            @Override
            public int compare(Elev t, Elev t1) {
                int sem = 1;
                if (sem2.isSelected()) {
                    sem = 2;
                }
                int G1 = t1.getSituatie().get(((Profesor) user).getMaterie()).getMean(sem);
                int G2 = t.getSituatie().get(((Profesor) user).getMaterie()).getMean(sem);
                if (G1 < G2) {
                    return -1;
                }
                if (G1 > G2) {
                    return 1;
                }
                return 0;
            }
        };

        Arrays.sort(elevName, alphaCompU);
        eleviList.setListData(elevName);
        eleviList.setBounds(100, 100, 150, 250);
        classLayer.add(eleviList, new Integer(2));
        final ImageIcon sortUp;
        final ImageIcon sortDown;
        final ImageIcon sortNone;
        try {
            sortUp = new ImageIcon(new File("img/sort-up-red.png").toURI().toURL());
            sortDown = new ImageIcon(new File("img/sort-down-red.png").toURI().toURL());
            sortNone = new ImageIcon(new File("img/sort-neutral-red.png").toURI().toURL());


            final JButton numeButton;
            final JButton medieButton;
            numeButton = new JButton("Nume", sortUp);
            numeButton.setBounds(100, 65, 150, 30);
            numeButton.setHorizontalAlignment(SwingConstants.LEFT);
            numeButton.setForeground(new Color(154, 59, 48));
            numeButton.setFont(new Font("JennaSue", Font.PLAIN, 25));
            numeButton.setOpaque(false);
            numeButton.setContentAreaFilled(false);
            numeButton.setBorderPainted(false);

            medieButton = new JButton("Medie", sortNone);
            medieButton.setBounds(100, 30, 150, 30);
            medieButton.setHorizontalAlignment(SwingConstants.LEFT);
            medieButton.setForeground(new Color(154, 59, 48));
            medieButton.setFont(new Font("JennaSue", Font.PLAIN, 25));
            medieButton.setOpaque(false);
            medieButton.setContentAreaFilled(false);
            medieButton.setBorderPainted(false);
            numeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (numeButton.getIcon() == sortUp) {
                        Arrays.sort(elevName, alphaCompD);
                        numeButton.setIcon(sortDown);
                    } else {
                        Arrays.sort(elevName, alphaCompU);
                        numeButton.setIcon(sortUp);
                    }
                    if (medieButton.getIcon() != sortNone) {
                        medieButton.setIcon(sortNone);
                    }
                    eleviList.setListData(elevName);
                }
            });

            medieButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (medieButton.getIcon() == sortDown) {
                        Arrays.sort(elevName, meanCompU);
                        medieButton.setIcon(sortUp);
                    } else {
                        Arrays.sort(elevName, meanCompD);
                        medieButton.setIcon(sortDown);
                    }
                    if (numeButton.getIcon() != sortNone) {
                        numeButton.setIcon(sortNone);
                    }
                    eleviList.setListData(elevName);
                }
            });

            classLayer.add(numeButton, new Integer(2));
            classLayer.add(medieButton, new Integer(2));
        } catch (MalformedURLException ex) {
            Logger.getLogger(HomeView.class.getName()).log(Level.SEVERE, null, ex);
        }

        final JButton viewElev = new JButton("Afiseaza situatie");
        viewElev.setBounds(100, 430, 150, 30);
        classLayer.add(viewElev, new Integer(2));

        viewElev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                layer2.removeAll();
                if (layer2.getParent() != null) {
                    layer2.getParent().remove(layer2);
                    layer2.removeAll();
                }

                if (layer3.getParent() != null) {
                    layer3.getParent().remove(layer3);
                    layer3.removeAll();
                }
                final JList gradeList = new JList();
                gradeList.setBounds(300, 100, 50, 250);
                final Materie m = Centralizator.getCentralizator().getProfsMaterie();
                if (sem2.isSelected()) {
                    semestru = 2;
                } else {
                    semestru = 1;
                }
                gradeList.setListData(selectedClasa.getSituatieAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru));
                layer2.add(gradeList, new Integer(2));

                if (selectedClasa.getMeanAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru) > 0) {
                    JLabel meanLabel = new JLabel(String.valueOf(selectedClasa.getMeanAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru)));
                    meanLabel.setBounds(300, 60, 50, 30);
                    meanLabel.setFont(new Font(meanLabel.getFont().getName(), Font.BOLD, 20));
                    if (selectedClasa.getMeanAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru) < 5) {
                        meanLabel.setForeground(Color.RED);
                    } else {
                        meanLabel.setForeground(new Color(0, 153, 76));
                    }
                    layer2.add(meanLabel, new Integer(2));
                } else {
                    JButton makeMean = new JButton("Inchide situatie");
                    makeMean.setBounds(300, 60, 150, 30);
                    layer2.add(makeMean, new Integer(2));
                    makeMean.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            selectedClasa.closeSituatie(m, (Elev) eleviList.getSelectedValue(), semestru);
                            ActionListener[] a = viewElev.getActionListeners();
                            a[0].actionPerformed(new ActionEvent(viewElev, 0, null));
                        }
                    });
                }
                final JList skipList = new JList();
                skipList.setBounds(400, 100, 200, 250);
                skipList.setListData(selectedClasa.getAbsenteAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru));
                layer2.add(skipList, new Integer(2));

                if (selectedClasa.getMeanAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru) == 0) {
                    JButton addSkip = new JButton("+");
                    addSkip.setBounds(400, 360, 50, 30);
                    layer2.add(addSkip, new Integer(2));

                    JButton motivateSkip = new JButton("motiveaza");
                    motivateSkip.setBounds(470, 360, 130, 30);
                    layer2.add(motivateSkip, new Integer(2));

                    JButton delGrade = new JButton("x");
                    delGrade.setBounds(300, 360, 24, 24);
                    layer2.add(delGrade, new Integer(2));


                    JButton addGrade = new JButton("+");
                    addGrade.setBounds(326, 360, 24, 24);
                    layer2.add(addGrade, new Integer(2));

                    addSkip.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            if (layer3.getParent() != null) {
                                layer3.getParent().remove(layer3);
                            }
                            layer3.removeAll();
                            final JTextField dateSkip = new JTextField();
                            dateSkip.setBounds(400, 410, 150, 30);
                            layer3.add(dateSkip, new Integer(2));

                            JButton confirmSkip = new JButton("Adauga");
                            confirmSkip.setBounds(400, 450, 150, 30);

                            confirmSkip.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    layer3.removeAll();
                                    layer3.getParent().remove(layer3);
                                    selectedClasa.addAbsenta((Elev) eleviList.getSelectedValue(), m, dateSkip.getText(), semestru);
                                    skipList.setListData(selectedClasa.getAbsenteAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru));
                                    pack();
                                    repaint();
                                }
                            });

                            layer3.add(confirmSkip, new Integer(2));
                            add(layer3);
                            pack();
                            repaint();
                        }
                    });

                    motivateSkip.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            if (layer3.getParent() != null) {
                                layer3.getParent().remove(layer3);
                            }
                            Object[] toBeMotivated = skipList.getSelectedValues();
                            for (int i = 0; i < toBeMotivated.length; i++) {
                                ((Absenta) toBeMotivated[i]).motivate();
                            }
                            skipList.setListData(selectedClasa.getAbsenteAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru));
                            pack();
                            repaint();
                        }
                    });

                    addGrade.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            final JTextField newNota = new JTextField();
                            newNota.setBounds(300, 390, 50, 30);
                            classLayer.add(newNota, new Integer(2));

                            final JButton acceptNota = new JButton("ok");
                            acceptNota.setBounds(300, 425, 50, 30);
                            classLayer.add(acceptNota, new Integer(2));

                            acceptNota.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    selectedClasa.addGrade((Elev) eleviList.getSelectedValue(), m, semestru, Integer.parseInt(newNota.getText()));
                                    gradeList.setListData(selectedClasa.getSituatieAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru));
                                    classLayer.remove(newNota);
                                    classLayer.remove(acceptNota);
                                    pack();
                                    repaint();
                                }
                            });

                        }
                    });

                    if (m.getTeza()) {
                        final JTextField tezaLabel = new JTextField(selectedClasa.getTezaatMaterie(m, (Elev) eleviList.getSelectedValue(), semestru));
                        tezaLabel.setBounds(800, 100, 80, 30);
                        tezaLabel.setForeground(Color.RED);
                        layer2.add(tezaLabel, new Integer(2));

                        JButton changeTeza = new JButton("Teza");
                        changeTeza.setBounds(800, 140, 80, 30);
                        layer2.add(changeTeza, new Integer(2));

                        changeTeza.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {
                                selectedClasa.setTezaatMaterie(m, ((Elev) eleviList.getSelectedValue()), semestru, tezaLabel.getText());
                                tezaLabel.setText(selectedClasa.getTezaatMaterie(m, (Elev) eleviList.getSelectedValue(), semestru));
                            }
                        });

                    }

                    delGrade.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            Object[] toBeDel = gradeList.getSelectedValues();
                            for (int i = 0; i < toBeDel.length; i++) {
                                selectedClasa.delGrade((Elev) eleviList.getSelectedValue(), m, semestru, (Integer) toBeDel[i]);
                            }
                            gradeList.setListData(selectedClasa.getSituatieAtMaterie(m, (Elev) eleviList.getSelectedValue(), semestru));
                        }
                    });
                }
                add(layer2);
                pack();
                repaint();
            }
        });

        super.add(classLayer);
        super.pack();
        super.repaint();

    }

    private void makeProfMenu(JMenu clase, final JLayeredPane classLayer) {
        JMenu clasa9 = new JMenu("Clasa a IX-a");
        JMenu clasa10 = new JMenu("Clasa a X-a");
        JMenu clasa11 = new JMenu("Clasa a XI-a");
        JMenu clasa12 = new JMenu("Clasa a XII-a");
        clase.add(clasa9);
        clase.add(clasa10);
        clase.add(clasa11);
        clase.add(clasa12);


        ArrayList<String> classList = Centralizator.getCentralizator().getProfsClasses();
        String[] classes = classList.toArray(new String[classList.size()]);
        Arrays.sort(classes);
        for (int i = 0; i < classes.length; i++)
        {
            final String cName = classes[i];
            JMenu clasax;
            if (cName.startsWith("9")) {
                clasax = clasa9;
            } else if (cName.startsWith("10")) {
                clasax = clasa10;
            } else if (cName.startsWith("11")) {
                clasax = clasa11;
            } else {
                clasax = clasa12;
            }
            JMenuItem crr_item = new JMenuItem(cName);
            clasax.add(crr_item);
            crr_item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    setSelectedClass(Centralizator.getCentralizator().getClasa(cName));
                    makeClassLayer(classLayer, cName);
                }
            });
        }

    }

    private void homeForProfesor() {
        JMenuBar menuBar = new JMenuBar();
        JMenu clase = new JMenu("Clase");
        JMenu contulMeu = new JMenu("Contul meu");
        JMenuItem logOut = new JMenuItem("Deconectare");
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
                Centralizator.getCentralizator().signOutUser();
            }
        });

        JMenuItem settings = new JMenuItem("Setari cont");
        menuBar.add(clase);
        menuBar.add(contulMeu);
        contulMeu.add(settings);
        contulMeu.add(logOut);
        final JLayeredPane classLayer = new JLayeredPane();
        makeProfMenu(clase, classLayer);

        final JMenu userItem = new JMenu(((Profesor) user).getDisplayName());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(userItem);
        userItem.setEnabled(false);

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //hView.removeAll();
                classLayer.removeAll();
                layer2.removeAll();
                layer3.removeAll();
                hView.remove(classLayer);
                hView.remove(layer2);
                hView.remove(layer3);
                final JTextField usernameField = new JTextField(((Profesor) user).getUsername());
                usernameField.setBounds(300, 110, 150, 30);
                classLayer.add(usernameField, new Integer(2));

                final JTextField numeField = new JTextField(((Profesor) user).getLastName());
                numeField.setBounds(300, 190, 150, 30);
                classLayer.add(numeField, new Integer(2));

                final JTextField prenumeField = new JTextField(((Profesor) user).getFirstName());
                prenumeField.setBounds(300, 230, 150, 30);
                classLayer.add(prenumeField, new Integer(2));

                final JPasswordField passwordField = new JPasswordField(((Profesor) user).getPassword());
                passwordField.setBounds(300, 150, 150, 30);
                classLayer.add(passwordField, new Integer(2));

                JLabel materieLabel = new JLabel(((Profesor) user).getMaterie().toString());
                materieLabel.setBounds(300, 270, 150, 30);
                classLayer.add(materieLabel, new Integer(2));

                JButton saveChanges = new JButton("Salveaza modificarile");
                saveChanges.setBounds(250, 310, 250, 30);
                classLayer.add(saveChanges, new Integer(2));

                saveChanges.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        user.changeInfo(usernameField.getText(), numeField.getText(), prenumeField.getText(), passwordField.getText());
                        classLayer.removeAll();
                        hView.remove(classLayer);
                        userItem.setText(((Profesor) user).getDisplayName());
                        pack();
                        repaint();
                    }
                });

                add(classLayer);
                pack();
                repaint();
            }
        });


        setJMenuBar(menuBar);
        pack();
        setVisible(true);

    }

    private void homeforElev() {
        layers = new JLayeredPane();
        try {
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
                    new GradesView((Elev) user, hView);
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
            viewSkips.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    setVisible(false);
                    new SkipsView((Elev) user, hView);
                }
            });

            BufferedImage smallBoard = ImageIO.read(new File("img/blackboard_small.png"));
            JLabel blackboard = new JLabel(new ImageIcon(smallBoard));
            blackboard.setBounds((getPreferredSize().width - smallBoard.getWidth()) / 2, 0, smallBoard.getWidth(), smallBoard.getHeight());

            layers.add(blackboard, new Integer(2));

            JLabel welcomeText = new JLabel("Bine ai revenit, " + user.getFirstName());
            welcomeText.setFont(new Font("JennaSue", Font.PLAIN, 39));
            welcomeText.setForeground(Color.white);

            welcomeText.setBounds(blackboard.getBounds().x + 30, blackboard.getBounds().y + 30, 340, 40);

            JLabel classText = new JLabel(((Elev) user).getClassID());
            classText.setFont(new Font("JennaSue", Font.PLAIN, 35));
            classText.setForeground(Color.white);
            classText.setBounds(blackboard.getBounds().x + 30, blackboard.getBounds().y + 80, 340, 40);

            DateFormat dateFormat = new SimpleDateFormat("dd-MM");
            Date date = new Date();
            String dateString = dateFormat.format(date);

            String elevBirthDate = ((Elev) user).getBirthdate();

            if (dateString.equals(elevBirthDate)) {
                JLabel messageText = new JLabel("La multi ani!");
                messageText.setFont(new Font("JennaSue", Font.PLAIN, 40));
                messageText.setForeground(Color.white);
                messageText.setBounds(blackboard.getBounds().x + 30, blackboard.getBounds().y + 130, 340, 40);
                layers.add(messageText, new Integer(3));
            }

            layers.add(welcomeText, new Integer(3));
            layers.add(classText, new Integer(3));

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

            final int height = smallBoard.getHeight() + 10;
            final int width = (1024 - 310) / 2;
            info.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (classEditLayer.getParent() != null) {
                        hView.remove(classEditLayer);
                        classEditLayer.removeAll();
                        pack();
                        repaint();
                    } else {
                        final JTextField usernameField = new JTextField(user.getUsername());
                        usernameField.setBounds(width, height, 150, 30);
                        classEditLayer.add(usernameField, new Integer(2));

                        final JTextField numeField = new JTextField(user.getLastName());
                        numeField.setBounds(width, height + 40, 150, 30);
                        numeField.setEditable(false);
                        numeField.setEnabled(false);
                        classEditLayer.add(numeField, new Integer(2));

                        final JTextField prenumeField = new JTextField(user.getFirstName());
                        prenumeField.setBounds(width + 160, height + 40, 150, 30);
                        prenumeField.setEditable(false);
                        prenumeField.setEnabled(false);
                        classEditLayer.add(prenumeField, new Integer(2));

                        final JPasswordField passwordField = new JPasswordField(user.getPassword());
                        passwordField.setBounds(width + 160, height, 150, 30);
                        classEditLayer.add(passwordField, new Integer(2));

                        final JTextField cnpField = new JTextField(((Elev) user).getCNP());
                        cnpField.setEditable(false);
                        cnpField.setEnabled(false);
                        cnpField.setBounds(width, height + 80, 310, 30);
                        classEditLayer.add(cnpField, new Integer(2));

                        JButton saveChanges = new JButton("Salveaza modificarile");
                        saveChanges.setBounds(width, height + 120, 310, 30);
                        classEditLayer.add(saveChanges, new Integer(2));

                        saveChanges.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {
                                ((Elev) user).changeInfobyStudent(usernameField.getText(), numeField.getText(), prenumeField.getText(), passwordField.getText());
                                classEditLayer.removeAll();
                                hView.remove(classEditLayer);
                                pack();
                                repaint();
                            }
                        });
                        hView.add(classEditLayer);
                        pack();
                        repaint();
                    }
                }
            });
            layers.add(info, new Integer(2));


            add(layers);
            pack();
            setVisible(true);
        } catch (Exception e) {
        }
    }

    public void setSelectedClass(Clasa cls) {
        selectedClasa = cls;
    }

    private void makeEditLayer(final JLayeredPane classEditLayer, final JFrame frame, final JList eleviList, final JList materiiList) {
        JLabel eleviLabel = new JLabel("ELEVI");
        eleviLabel.setBounds(220, 120, 85, 20);
        classEditLayer.add(eleviLabel, new Integer(2));

        JLabel materiiLabel = new JLabel("MATERII");
        materiiLabel.setBounds(600, 120, 95, 20);
        classEditLayer.add(materiiLabel, new Integer(2));
        eleviList.setBounds(150, 150, 250, 300);
        materiiList.setBounds(540, 150, 250, 300);
        classEditLayer.add(eleviList, new Integer(2));
        classEditLayer.add(materiiList, new Integer(2));
        JButton addElevButton = new JButton("Adauga");
        JButton removeElevButton = new JButton("Sterge");
        JButton editElev = new JButton("Modifica");
        addElevButton.setBounds(150, 470, 80, 40);
        removeElevButton.setBounds(235, 470, 80, 40);
        editElev.setBounds(320, 470, 80, 40);
        classEditLayer.add(addElevButton, new Integer(2));
        classEditLayer.add(removeElevButton, new Integer(2));
        classEditLayer.add(editElev, new Integer(2));
        final Comparator<Elev> alphaCompU = new Comparator<Elev>() {
            @Override
            public int compare(Elev t, Elev t1) {
                if (t.getLastName().compareTo(t1.getLastName()) == 0) {
                    return t.getFirstName().compareTo(t1.getFirstName());
                } else {
                    return t.getLastName().compareTo(t1.getLastName());
                }
            }
        };

        removeElevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object[] toBeDel = eleviList.getSelectedValues();
                for (int i = 0; i < toBeDel.length; i++) {
                    selectedClasa.delElev((Elev) toBeDel[i]);
                }
                Elev[] elevi = (Elev[]) selectedClasa.getElevNames();
                Arrays.sort(elevi, alphaCompU);
                eleviList.setListData(elevi);
            }
        });

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
                clasa.setBounds(150, 610, 100, 30);

                final JTextField cnp = new JTextField(selectedElev.getCNP());
                cnp.setBounds(320, 570, 80, 30);

                final JButton addB = new JButton("Modifica!");
                addB.setBounds(150, 650, 250, 30);

                addB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        selectedElev.changeInfo(username.getText(), name.getText(), prenume.getText(), cnp.getText());
                        selectedElev.changeClasa(clasa.getText());
                        Elev[] elevi = (Elev[]) selectedClasa.getElevNames();
                        Arrays.sort(elevi, alphaCompU);
                        eleviList.setListData(elevi);
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

                        Elev[] elevi = (Elev[]) selectedClasa.getElevNames();
                        Arrays.sort(elevi, alphaCompU);
                        eleviList.setListData(elevi);
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

    }

    private void homeForSecretar() {
        final JFrame frame = this;
        final HomeView homeViewvar = this;

        Centralizator centralizator = Centralizator.getCentralizator();

        JMenuBar menuBar = new JMenuBar();
        JMenu claseMenu = new JMenu("Clase");
        JMenu profesoriMenu = new JMenu("Profesori");
        JMenu materiiMenu = new JMenu("Materii");
        JMenu contulMeuMenu = new JMenu("Contul meu");
        JMenuItem logOutItem = new JMenuItem("Deconectare");
        JMenuItem settings = new JMenuItem("Setari cont");
        menuBar.add(claseMenu);
        menuBar.add(profesoriMenu);
        menuBar.add(materiiMenu);
        menuBar.add(contulMeuMenu);
        contulMeuMenu.add(settings);
        contulMeuMenu.add(logOutItem);
        final JMenu userItem = new JMenu(((Secretar) user).toString());
        userItem.setEnabled(false);
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(new JMenuItem());
        menuBar.add(userItem);


        JMenuItem addMaterie = new JMenuItem("Adauga Materie");
        JMenuItem delMaterie = new JMenuItem("Sterge Materie");
        materiiMenu.add(addMaterie);
        materiiMenu.add(delMaterie);

        JMenuItem addProfesor = new JMenuItem("Adauga un profesor");
        JMenuItem delProfesor = new JMenuItem("Sterge un profesor");

        profesoriMenu.add(addProfesor);
        profesoriMenu.add(delProfesor);

        delProfesor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (classEditLayer.getParent() != null);
                {
                    frame.remove(classEditLayer);
                    classEditLayer.removeAll();
                }
                if (profesoriLayer.getParent() != null) {
                    frame.remove(profesoriLayer);
                    profesoriLayer.removeAll();
                }
                if (addClassLayer.getParent() != null) {
                    frame.remove(addClassLayer);
                }
                if (materiilayer.getParent() != null) {
                    frame.remove(materiilayer);
                    materiilayer.removeAll();
                }
                final JList profesoriList = new JList(Centralizator.getCentralizator().getProfesori());
                profesoriList.setBounds(400, 200, 300, 400);
                classEditLayer.add(profesoriList, new Integer(2));

                JButton del = new JButton("Sterge");
                del.setBounds(400, 610, 300, 30);
                classEditLayer.add(del, new Integer(2));

                del.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        Object[] toBeDel = profesoriList.getSelectedValues();
                        for (int i = 0; i < toBeDel.length; i++) {
                            Centralizator.getCentralizator().delProf((Profesor) toBeDel[i]);
                        }
                        hView.remove(classEditLayer);
                        classEditLayer.removeAll();
                        pack();
                        repaint();
                    }
                });
                add(classEditLayer);
                pack();
                repaint();
            }
        });

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

        final JList eleviList = new JList();
        final JList materiiList = new JList();


        makeEditLayer(classEditLayer, frame, eleviList, new JList());

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //hView.removeAll();
                if (classEditLayer.getParent() != null);
                {
                    frame.remove(classEditLayer);
                    classEditLayer.removeAll();
                }
                if (profesoriLayer.getParent() != null) {
                    frame.remove(profesoriLayer);
                    profesoriLayer.removeAll();
                }
                if (addClassLayer.getParent() != null) {
                    frame.remove(addClassLayer);
                }
                if (materiilayer.getParent() != null) {
                    frame.remove(materiilayer);
                    materiilayer.removeAll();
                }
                final JTextField usernameField = new JTextField(user.getUsername());
                usernameField.setBounds(300, 150, 150, 30);
                classEditLayer.add(usernameField, new Integer(2));

                final JTextField numeField = new JTextField(user.getLastName());
                numeField.setBounds(300, 190, 150, 30);
                classEditLayer.add(numeField, new Integer(2));

                final JTextField prenumeField = new JTextField(user.getFirstName());
                prenumeField.setBounds(300, 230, 150, 30);
                classEditLayer.add(prenumeField, new Integer(2));

                final JPasswordField passwordField = new JPasswordField(user.getPassword());
                passwordField.setBounds(460, 150, 150, 30);
                classEditLayer.add(passwordField, new Integer(2));

                JButton saveChanges = new JButton("Salveaza modificarile");
                saveChanges.setBounds(250, 310, 250, 30);
                classEditLayer.add(saveChanges, new Integer(2));

                saveChanges.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        user.changeInfo(usernameField.getText(), numeField.getText(), prenumeField.getText(), passwordField.getText());
                        userItem.setText(((Secretar) user).toString());
                        classEditLayer.removeAll();
                        pack();
                        repaint();
                    }
                });
                hView.add(classEditLayer);
                pack();
                repaint();
            }
        });

        addMaterie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (classEditLayer.getParent() != null);
                {
                    frame.remove(classEditLayer);
                    classEditLayer.removeAll();
                }
                if (profesoriLayer.getParent() != null) {
                    frame.remove(profesoriLayer);
                    profesoriLayer.removeAll();
                }
                if (addClassLayer.getParent() != null) {
                    frame.remove(addClassLayer);
                }
                if (materiilayer.getParent() != null) {
                    frame.remove(materiilayer);
                    materiilayer.removeAll();
                }
                final JList materii2List = new JList();
                materii2List.setListData(Centralizator.getCentralizator().getMateriiNames());
                materii2List.setBounds(350, 150, 200, 300);
                materiilayer.add(materii2List, new Integer(2));

                final JTextField materieName = new JTextField("Nume materie");
                materieName.setBounds(350, 470, 200, 40);
                materiilayer.add(materieName, new Integer(2));

                final JTextField nrOre = new JTextField("Nr ore/saptamana");
                nrOre.setBounds(350, 520, 200, 40);
                materiilayer.add(nrOre, new Integer(2));

                final JCheckBox teza = new JCheckBox("Teza");
                teza.setBounds(350, 570, 200, 40);
                materiilayer.add(teza, new Integer(2));


                final JButton executeAdd = new JButton("Adauga materia");
                executeAdd.setBounds(350, 620, 200, 40);
                materiilayer.add(executeAdd, new Integer(2));

                executeAdd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        Centralizator.getCentralizator().addMaterie(new Materie(materieName.getText(), Integer.parseInt(nrOre.getText()), teza.isSelected()));
                        materii2List.setListData(Centralizator.getCentralizator().getMateriiNames());
                    }
                });
                frame.add(materiilayer);
                pack();
                repaint();
            }
        });

        delMaterie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (classEditLayer.getParent() != null);
                {
                    frame.remove(classEditLayer);
                    classEditLayer.removeAll();
                }
                if (profesoriLayer.getParent() != null) {
                    frame.remove(profesoriLayer);
                    profesoriLayer.removeAll();
                }
                if (addClassLayer.getParent() != null) {
                    frame.remove(addClassLayer);
                }
                if (materiilayer.getParent() != null) {
                    frame.remove(materiilayer);
                    materiilayer.removeAll();

                }
                final JList materii2List = new JList();
                materii2List.setListData(Centralizator.getCentralizator().getMateriiNames());
                materii2List.setBounds(350, 150, 200, 300);
                materiilayer.add(materii2List, new Integer(2));

                JButton executeDel = new JButton("Sterge");
                executeDel.setBounds(350, 470, 200, 30);
                materiilayer.add(executeDel, new Integer(2));

                executeDel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        Object[] toBeDel = materii2List.getSelectedValues();
                        for (int i = 0; i < toBeDel.length; i++) {
                            Centralizator.getCentralizator().delMaterie((Materie) toBeDel[i]);
                        }
                        materii2List.setListData(Centralizator.getCentralizator().getMateriiNames());
                    }
                });
                frame.add(materiilayer);
                pack();
                repaint();
            }
        });

        addProfesor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (classEditLayer.getParent() != null) {
                    frame.remove(classEditLayer);
                }

                if (profesoriLayer.getParent() != null) {
                    profesoriLayer.getParent().remove(profesoriLayer);
                }

                if (addClassLayer.getParent() != null) {
                    frame.remove(addClassLayer);
                }

                if (materiilayer.getParent() != null) {
                    frame.remove(materiilayer);
                }
                profesoriLayer.removeAll();

                final JList profs = new JList(Centralizator.getCentralizator().getProfesori());
                profs.setBounds(600, 200, 200, 250);
                profesoriLayer.add(profs, new Integer(2));

                final JTextField username = new JTextField("nume utilizator");
                username.setBounds(200, 200, 200, 40);
                profesoriLayer.add(username, new Integer(2));

                final JTextField nume = new JTextField("Nume");
                nume.setBounds(200, 245, 200, 40);
                profesoriLayer.add(nume, new Integer(2));

                final JTextField prenume = new JTextField("Prenume");
                prenume.setBounds(200, 290, 200, 40);
                profesoriLayer.add(prenume, new Integer(2));

                final JComboBox materie = new JComboBox(Centralizator.getCentralizator().getMateriiNames());
                materie.setBounds(200, 335, 200, 40);
                profesoriLayer.add(materie, new Integer(2));

                JButton addProfesorButton = new JButton("Adauga profesorul");
                addProfesorButton.setBounds(200, 380, 200, 40);
                profesoriLayer.add(addProfesorButton, new Integer(2));
                addProfesorButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {

                        Centralizator.getCentralizator().addProfesor(new Profesor(username.getText(), "1234", nume.getText(), prenume.getText(), (Materie) materie.getSelectedItem()));
                        if (profesoriLayer.getParent() != null) {
                            frame.remove(profesoriLayer);
                        }

                        if (classEditLayer.getParent() != null) {
                            frame.remove(classEditLayer);
                            classEditLayer.removeAll();
                        }

                        profesoriLayer.removeAll();
                        frame.pack();
                        frame.repaint();
                    }
                });

                frame.add(profesoriLayer);
                frame.pack();
                frame.repaint();
            }
        });


        makeMenu(clasa9, clasa10, clasa11, clasa12, eleviList, this, classEditLayer, materiiList, profesoriLayer);

        JMenuItem addClasa = new JMenuItem("Adauga o clasa");
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
                makeMenu(clasa9, clasa10, clasa11, clasa12, eleviList, homeViewvar, classEditLayer, materiiList, profesoriLayer);
                homeViewvar.repaint();
            }
        });
        addClassLayer.add(addClasaButton, new Integer(2));

        addClasa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (classEditLayer.getParent() != null) {
                    homeViewvar.remove(classEditLayer);
                }

                if (materiilayer.getParent() != null) {
                    homeViewvar.remove(materiilayer);
                }

                if (profesoriLayer.getParent() != null) {
                    homeViewvar.remove(profesoriLayer);
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
        claseMenu.addSeparator();
        claseMenu.add(addClasa);

        setJMenuBar(menuBar);
        // add(layers);
        pack();
        setVisible(true);
    }

    private void addClassToMenu(String classID, JMenu clasax, final JList eleviList, final JLayeredPane classEditLayer, final JFrame frame) {
        JMenuItem crr_item = new JMenuItem(classID);
        crr_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Centralizator c = Centralizator.getCentralizator();
                Clasa cl = c.getClasa(ae.getActionCommand());
                Elev[] elevi = (Elev[]) cl.getElevNames();
                setSelectedClass(cl);


                Arrays.sort(elevi, new Comparator<Elev>() {
                    @Override
                    public int compare(Elev t, Elev t1) {
                        if (t.getLastName().compareTo(t1.getLastName()) == 0) {
                            return t.getFirstName().compareTo(t1.getFirstName());
                        } else {
                            return t.getLastName().compareTo(t1.getLastName());
                        }
                    }
                });

                Arrays.sort(elevi, new Comparator<Elev>() {
                    @Override
                    public int compare(Elev t, Elev t1) {
                        if (t.getLastName().compareTo(t1.getLastName()) == 0) {
                            return t.getFirstName().compareTo(t1.getFirstName());
                        } else {
                            return t.getLastName().compareTo(t1.getLastName());
                        }
                    }
                });
                eleviList.setListData(elevi);
                if (classEditLayer.getParent() == null) {
                    frame.add(classEditLayer);
                    frame.repaint();
                    pack();
                }
            }
        });
        clasax.add(crr_item, clasax.getItemCount() - 1);
    }

    private void makeMenu(JMenu clasa9, JMenu clasa10, JMenu clasa11, JMenu clasa12, final JList eleviList, final HomeView frame,
            final JLayeredPane classEditLayer, final JList materiiList, final JLayeredPane profesoriLayer) {
        clasa9.removeAll();
        clasa10.removeAll();
        clasa11.removeAll();
        clasa12.removeAll();
        Centralizator centralizator = Centralizator.getCentralizator();
        Set<String> classNames = centralizator.getClassNames();
        String[] classes = classNames.toArray(new String[classNames.size()]);
        Arrays.sort(classes);

        for (int i = 0; i < classes.length; i++)
        {
            String cName = (String) classes[i];
            JMenuItem crr_item = new JMenuItem(cName);
            JMenu clasax;
            if (cName.startsWith("9")) {
                clasax = clasa9;
            } else if (cName.startsWith("10")) {
                clasax = clasa10;
            } else if (cName.startsWith("11")) {
                clasax = clasa11;
            } else {
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
                    classEditLayer.removeAll();
                    makeEditLayer(classEditLayer, frame, eleviList, materiiList);
                    pack();
                    repaint();
                    if (profesoriLayer.getParent() != null) {
                        profesoriLayer.getParent().remove(profesoriLayer);
                    }

                    if (materiilayer.getParent() != null) {
                        frame.remove(materiilayer);
                    }

                    if (addClassLayer.getParent() != null) {
                        frame.remove(addClassLayer);
                    }

                    if (classEditLayer.getParent() != null) {
                        classEditLayer.getParent().remove(classEditLayer);
                    }

                    Arrays.sort(elevi, new Comparator<Elev>() {
                        @Override
                        public int compare(Elev t, Elev t1) {
                            if (t.getLastName().compareTo(t1.getLastName()) == 0) {
                                return t.getFirstName().compareTo(t1.getFirstName());
                            } else {
                                return t.getLastName().compareTo(t1.getLastName());
                            }
                        }
                    });
                    eleviList.setListData(elevi);
                    materiiList.setListData(selectedClasa.getMaterii());

                    JButton addMaterie = new JButton("Adauga");
                    addMaterie.setBounds(540, 470, 80, 40);
                    classEditLayer.add(addMaterie, new Integer(2));
                    addMaterie.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {


                            final JButton executeAdd = new JButton("Adauga materia");
                            executeAdd.setBounds(540, 565, 250, 40);
                            classEditLayer.add(executeAdd, new Integer(2));

                            final JComboBox profesor = new JComboBox(Centralizator.getCentralizator().getProfesori());
                            profesor.setBounds(540, 520, 250, 40);
                            classEditLayer.add(profesor, new Integer(2));

                            executeAdd.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    selectedClasa.addMaterie((Profesor) profesor.getSelectedItem());
                                    //     selectedClasa.addMaterie(new Materie(materieName.getText(), Integer.parseInt(nrOre.getText()),
                                    //             teza.isSelected()), (Profesor) profesor.getSelectedItem());
                                    classEditLayer.remove(executeAdd);
                                    classEditLayer.remove(profesor);
                                    frame.pack();
                                    frame.repaint();
                                    materiiList.setListData(selectedClasa.getMaterii());
                                }
                            });
                        }
                    });

                    JButton delMaterie = new JButton("Sterge");
                    delMaterie.setBounds(710, 470, 80, 40);
                    classEditLayer.add(delMaterie, new Integer(2));
                    delMaterie.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            Object[] selectedMaterii = materiiList.getSelectedValues();
                            for (int i = 0; i < selectedMaterii.length; i++) {
                                selectedClasa.delMaterie((Materie) selectedMaterii[i]);
                            }
                            materiiList.setListData(selectedClasa.getMaterii());
                        }
                    });

                    if (classEditLayer.getParent() == null) {
                        frame.add(classEditLayer);
                        frame.repaint();
                        pack();
                    }
                }
            });
        }
    }

    private void homeForAdmin() {
        final JList userList = new JList();
        layers = new JLayeredPane();
        Object[] users = Centralizator.getCentralizator().getUsers();
        userList.setListData(users);
        userList.setBounds(300, 150, 300, 400);
        layers.add(userList, new Integer(2));

        JButton signOutButton = new JButton("Iesire");
        signOutButton.setBounds(970, 50, 40, 20);
        layers.add(signOutButton, new Integer(2));
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
                Centralizator.getCentralizator().signOutUser();
            }
        });

        final JTextField userNameField = new JTextField("utilizator");
        userNameField.setBounds(700, 150, 200, 30);
        layers.add(userNameField, new Integer(2));

        final JTextField numeField = new JTextField("nume");
        numeField.setBounds(700, 220, 200, 30);
        layers.add(numeField, new Integer(2));

        final JTextField prenumeField = new JTextField("prenume");
        prenumeField.setBounds(700, 255, 200, 30);
        layers.add(prenumeField, new Integer(2));

        final JComboBox userType = new JComboBox();
        userType.addItem("Secretar");
        userType.addItem("Administrator");
        userType.setBounds(700, 185, 200, 30);
        layers.add(userType, new Integer(2));

        JButton addUserButton = new JButton("Adauga utilizator");
        addUserButton.setBounds(700, 290, 200, 30);
        layers.add(addUserButton, new Integer(2));

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (userType.getSelectedItem().equals("Secretar")) {
                    Centralizator.getCentralizator().addSecretar(new Secretar(userNameField.getText(), "1234", numeField.getText(), prenumeField.getText()));
                } else {
                    Centralizator.getCentralizator().addAdministrator(new Administrator(userNameField.getText(), "1234", numeField.getText(), prenumeField.getText()));
                }
                userList.setListData(Centralizator.getCentralizator().getUsers());
            }
        });

        JButton delUserButton = new JButton("Sterge utilizator");
        delUserButton.setBounds(300, 560, 300, 30);
        layers.add(delUserButton, new Integer(2));
        delUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object[] toBeDel = userList.getSelectedValues();
                for (int i = 0; i < toBeDel.length; i++) {
                    Centralizator.getCentralizator().delUser((Utilizator) toBeDel[i]);
                }
                userList.setListData(Centralizator.getCentralizator().getUsers());
            }
        });

        add(layers);
        pack();
        setVisible(true);
    }
}
