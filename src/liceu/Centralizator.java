/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import graphicUI.LogInView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author andrei
 */
public class Centralizator implements java.io.Serializable {
    private transient HashMap<String, Utilizator> users;
    private transient HashMap<String, Clasa> classes;
    private transient HashMap<Materie, HashMap<Clasa, Profesor>> materii;
    private static volatile Centralizator centralizator = new Centralizator();
    private Utilizator loggedInUser;
    
    protected Centralizator()
    {
        users = new HashMap<String, Utilizator>();
        classes = new HashMap<String, Clasa>();
        materii = new HashMap<Materie, HashMap<Clasa, Profesor>>();
        users.put("andrei", new Elev("andrei", "1234", "Vasilescu", "Andrei", "12345", "25 martie 1993"));
    }
    
    public Utilizator authenticate(String username, String password)
    {
        if (users.containsKey(username))
        {
            if(users.get(username).passwordIsCorrect(password))
            {
                loggedInUser = users.get(username);
                return loggedInUser;
            }
        }
        return null;
    }
    
    public void signOutUser()
    {
        if (loggedInUser != null)
        {
            loggedInUser = null;
        }
        new LogInView();
    }
    public void saveCentralizator()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("data/Centralizator.obj");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        }
        catch (Exception e)
        {
            System.exit(-1);
        }
    }
    
    public static Centralizator getCentralizator()
    {
        return centralizator;
    }    
}
