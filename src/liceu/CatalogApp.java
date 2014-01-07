/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import graphicUI.CatalogView;
import graphicUI.HomeView;
import graphicUI.LogInView;
import graphicUI.MainView;

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
