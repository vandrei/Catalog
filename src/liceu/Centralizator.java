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
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
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
    private HashMap<String, Materie> materiiNames;
    private static volatile Centralizator centralizator = new Centralizator();
    private Utilizator loggedInUser;

    protected Centralizator() {
        readFromFile(0, 4);
    }

    public void delProf(Profesor prof) {
        Materie mat = prof.getMaterie();
        Iterator<Clasa> it = materii.get(mat).keySet().iterator();
        while (it.hasNext()) {
            Clasa cls = it.next();
            if (materii.get(mat).get(cls) == prof) {
                materii.get(mat).remove(cls);
            }
        }
        profesori.remove(prof);
        users.remove(prof.getUsername());
    }

    public void delUser(Utilizator u) {
        users.remove(u.getUsername());
    }

    public Materie[] getMateriiNames() {
        return materii.keySet().toArray(new Materie[materii.size()]);
    }

    public void putProfesorinClass(Profesor profesor, Clasa cls) {
        materii.get(profesor.getMaterie()).put(cls, profesor);
    }

    private void readFromFile(int i, int limit) {
        try {
            FileInputStream fileIn = new FileInputStream("data/Centralizator.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (HashMap<String, Utilizator>) in.readObject();
            classes = (HashMap<String, Clasa>) in.readObject();
            materii = (HashMap<Materie, HashMap<Clasa, Profesor>>) in.readObject();
            profesori = (ArrayList<Profesor>) in.readObject();
            materiiNames = (HashMap<String, Materie>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            /*if (i >= limit)
             System.exit(-1);
             readFromFile(i+1, limit);*/
            users = new HashMap<String, Utilizator>();
            classes = new HashMap<String, Clasa>();
            materii = new HashMap<Materie, HashMap<Clasa, Profesor>>();
            profesori = new ArrayList<Profesor>();
            materiiNames = new HashMap<String, Materie>();
            users.put("administrator", new Administrator("administrator", "1234", "Nume", "Prenume"));
        }
    }

    public Materie getProfsMaterie() {
        return ((Profesor) loggedInUser).getMaterie();
    }

    public ArrayList<String> getProfsClasses() {
        HashMap<Clasa, Profesor> map = materii.get(getProfsMaterie());
        ArrayList<String> clase = new ArrayList<String>();
        if (map == null) {
            return clase;
        }
        Iterator<Clasa> it = map.keySet().iterator();
        while (it.hasNext()) {
            Clasa cls = it.next();
            if (map.get(cls) == (Profesor) loggedInUser) {
                clase.add(cls.toString());
            }
        }
        return clase;
    }

    public void addMaterie(Materie materie) {
        materii.put(materie, new HashMap<Clasa, Profesor>());
        materiiNames.put(materie.toString(), materie);
    }

    public void addMaterietoClasa(Clasa clasa, Profesor profesor) {
        materii.get(profesor.getMaterie()).put(clasa, profesor);
    }

    public Profesor getProfesor(Materie materie, Clasa clasa) {
        return materii.get(materie).get(clasa);
    }

    public Profesor[] getProfesori() {
        return profesori.toArray(new Profesor[profesori.size()]);
    }

    public Object[] getUsers() {
        ArrayList<Utilizator> userList = new ArrayList<Utilizator>();
        Iterator<String> it = this.users.keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            Utilizator u = users.get(name);
            if (u instanceof Secretar) {
                userList.add(u);
            }
            if (u instanceof Administrator) {
                userList.add(u);
            }
        }

        return userList.toArray();
    }

    public void addProfesor(Profesor profesor) {
        profesori.add(profesor);
        users.put(profesor.getUsername(), profesor);
    }

    public void addSecretar(Secretar secretar) {
        users.put(secretar.getUsername(), secretar);
    }

    public void addAdministrator(Administrator administrator) {
        users.put(administrator.getUsername(), administrator);
    }

    public void delMaterie(Materie materie) {
        materii.remove(materie);
        materiiNames.remove(materie.toString());
    }

    public void addUtilizator(Utilizator user) {
        users.put(user.getUsername(), user);
    }

    public void changeUsername(String oldUsername, Utilizator user) {
        users.remove(oldUsername);
        users.put(user.getUsername(), user);
    }

    public void moveElev(String oldClassID, String newClassID, Elev elev) {
        ((Clasa) classes.get(oldClassID)).delElev(elev);
        ((Clasa) classes.get(newClassID)).addElev(elev);
    }

    public Set<String> getClassNames() {
        return classes.keySet();
    }

    public Utilizator authenticate(String username, String password) {
        if (users.containsKey(username)) {
            if (users.get(username).passwordIsCorrect(password)) {
                loggedInUser = users.get(username);
                return loggedInUser;
            }
        }
        return null;
    }

    public Clasa getClasa(String classID) {
        return classes.get(classID);
    }

    public void addClasa(String classID) {
        classes.put(classID, new Clasa(classID));
    }

    public void signOutUser() {
        if (loggedInUser != null) {
            loggedInUser = null;
        }
        new LogInView();
    }

    public void saveCentralizator() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/Centralizator.obj");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.writeObject(classes);
            out.writeObject(materii);
            out.writeObject(profesori);
            out.writeObject(materiiNames);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public static Centralizator getCentralizator() {
        return centralizator;
    }
}
