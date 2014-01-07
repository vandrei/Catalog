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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrei
 */
public class Centralizator implements java.io.Serializable {
    private HashMap<String, Utilizator> users;
    private HashMap<String, Clasa> classes;
    private HashMap<Materie, HashMap<Clasa, Profesor>> materii;
    private ArrayList<Profesor> profesori;
    private static volatile Centralizator centralizator = new Centralizator();
    private Utilizator loggedInUser;
    
    protected Centralizator()
    {
        readFromFile(0, 4);
        
                /* users = new HashMap<String, Utilizator>();
                 classes = new HashMap<String, Clasa>();
                 materii = new HashMap<Materie, HashMap<Clasa, Profesor>>();
                 profesori = new ArrayList<Profesor>();
                 users.put("andrei", new Elev("andrei", "1234", "Vasilescu", "Andrei", "1234567"));
                 users.put("miki", new Secretar("miki", "1234", "Miki", "Mihaela"));
                 classes.put("9A", new Clasa("9A"));*/
    }
    
    private void readFromFile(int i, int limit)
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("data/Centralizator.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (HashMap<String, Utilizator>) in.readObject();
            classes = (HashMap<String, Clasa>) in.readObject();
            materii = (HashMap<Materie, HashMap<Clasa, Profesor>>) in.readObject();
            profesori = (ArrayList<Profesor>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e)
        {
            /*if (i >= limit)
                System.exit(-1);
            readFromFile(i+1, limit);*/
            users = new HashMap<String, Utilizator>();
                 classes = new HashMap<String, Clasa>();
                 materii = new HashMap<Materie, HashMap<Clasa, Profesor>>();
                 profesori = new ArrayList<Profesor>();
                 users.put("andrei", new Elev("andrei", "1234", "Vasilescu", "Andrei", "1234567"));
                 users.put("miki", new Secretar("miki", "1234", "Miki", "Mihaela"));
                 classes.put("9A", new Clasa("9A"));
        }
    }
    
    public Profesor getProfesor(Materie materie, Clasa clasa)
    {
        return materii.get(materie).get(clasa);
    }
    
    public void delProfesor (Profesor profesor)
    {
        profesori.remove(profesor);
        Collection<HashMap<Clasa, Profesor>> col = materii.values();
        
    }
    
    public Profesor[] getProfesori()
    {
        return profesori.toArray(new Profesor[profesori.size()]);
    }
    
    public void addProfesor(Profesor profesor)
    {
        profesori.add(profesor);
    }
    
    public void addMaterie(Materie materie, Profesor profesor, Clasa clasa)
    {
        if (! materii.containsKey(materie))
        {
            materii.put(materie, new HashMap<Clasa, Profesor>());
        }
        materii.get(materie).put(clasa, profesor);
    }
    
    public void delMaterie(Materie materie, Clasa clasa)
    {
        materii.get(materie).remove(clasa);
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
            out.writeObject(profesori);
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
