/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import graphicUI.LogInView;

/**
 *
 * @author andrei
 */
public class CatalogApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Centralizator.getCentralizator();
        new LogInView();
        //HomeView view = new HomeView(null);
        //new CatalogView();
    }
}
