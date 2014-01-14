/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author andrei
 */
public class CatalogView extends JFrame {

    public CatalogView() {
        super("Catalog");
        BufferedImage img = null;
        setBackground(new Color(0, 0, 0, 0));

        try {

            img = ImageIO.read(new File("img/papyrus.png"));

            super.setContentPane(new ImagePane((img)));

            setPreferredSize(new Dimension(1024, 768));
            setUndecorated(true);
            setLayout(new BorderLayout());
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation((dim.width - 1024) / 2, (dim.height - 768) / 2);

            pack();
            setVisible(true);
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
            //setOpaque(false);
        }
    }
}
