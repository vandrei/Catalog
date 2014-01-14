/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author andrei
 */
public class MainView extends JFrame {

    public MainView() {
        super("Title");
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("img/bckground.jpg"));

            super.setContentPane(new ImagePane((img)));

            setPreferredSize(new Dimension(1024, 768));
            super.setUndecorated(true);
            setLayout(new BorderLayout());
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation((dim.width - 1024) / 2, (dim.height - 768) / 2);
        } catch (Exception e) {
        }
    }

    private class ImagePane extends JComponent {

        private Image image;

        public ImagePane(Image image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }
}
