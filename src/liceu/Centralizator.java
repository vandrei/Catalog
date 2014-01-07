/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import graphicUI.LogInView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author andrei
 */
public class Centralizator implements java.io.Serializable {
    private HashMap<String, Utilizator> users;
    private HashMap<String, Clasa> classes;
    private HashMap<Materie, HashMap<Clasa, Profesor>> materii;
    private static volatile Centralizator centralizator = new Centralizator();
    private Utilizator loggedInUser;
    
    protected Centralizator()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("data/Centralizator.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (HashMap<String, Utilizator>) in.readObject();
            classes = (HashMap<String, Clasa>) in.readObject();
            materii = (HashMap<Materie, HashMap<Clasa, Profesor>>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i)
        {
         users = new HashMap<String, Utilizator>();
        classes = new HashMap<String, Clasa>();
        materii = new HashMap<Materie, HashMap<Clasa, Profesor>>();
        users.put("andrei", new Elev("andrei", "1234", "Vasilescu", "Andrei", "1234567"));
        users.put("miki", new Secretar("miki", "1234", "Miki", "Mihaela"));
        classes.put("9A", new Clasa("9A"));
            return;
        }
         catch (ClassNotFoundException e)
         {
             users = new HashMap<String, Utilizator>();
        classes = new HashMap<String, Clasa>();
        materii = new HashMap<Materie, HashMap<Clasa, Profesor>>();
        users.put("andrei", new Elev("andrei", "1234", "Vasilescu", "Andrei", "1234567"));
        users.put("miki", new Secretar("miki", "1234", "Miki", "Mihaela"));
        classes.put("9A", new Clasa("9A"));
            return;
         }
    }
    
    
    public void addUtilizator (Utilizator user)
    {
        users.put(user.getUsername(), user);
    }
    
    public void changeUsername (String oldUsername, Utilizator user)
    {
        users.remove(oldUsername);
        users.put(user.getUsername(), user);
    }
    
    public void moveElev(String oldClassID, String newClassID, Elev elev)
    {
        ((Clasa)classes.get(oldClassID)).delElev(elev);
        ((Clasa)classes.get(newClassID)).addElev(elev);
    }
    
    public Set<String> getClassNames()
    {
        return classes.keySet();
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
    
    public Clasa getClasa(String classID)
    {
        return classes.get(classID);
    }
    
    public void addClasa(String classID)
    {
        classes.put(classID, new Clasa(classID));
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
            out.writeObject(users);
            out.writeObject(classes);
            out.writeObject(materii);
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
