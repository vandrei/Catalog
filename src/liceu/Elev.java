/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author andrei
 */
public class Elev extends Utilizator implements IElev {

    private String CNP, dataNastere;
    private String classID;

    public String getCNP() {
        return CNP;
    }

    public void changeClasa(String classID) {
        Centralizator centralizator = Centralizator.getCentralizator();
        centralizator.moveElev(this.classID, classID, this);
        this.classID = classID;
    }

    public void setClasa(String classID) {
        this.classID = classID;
    }

    public Elev(String userName, String password, String nume, String prenume, String CNP) {
        super(userName, password, nume, prenume);
        this.CNP = CNP;
        setDataNastere();
    }

    public void changeInfo(String username, String nume, String prenume, String CNP) {
        this.CNP = CNP;
        setDataNastere();
        String oldUsername = super.getUsername();
        super.changeInfo(username, nume, prenume);
        Centralizator.getCentralizator().changeUsername(oldUsername, this);
    }

    public void changeInfobyStudent(String username, String nume, String prenume, String password) {
        super.changeInfo(username, nume, prenume, password);
    }

    public String getBirthdate() {
        return dataNastere.substring(0, 5);
    }

    private void setDataNastere() {
        String year = CNP.substring(1, 3);
        String month = CNP.substring(3, 5);
        String day = CNP.substring(5, 7);
        this.dataNastere = day + "-" + month + "-19" + year;
    }

    public String getClassID() {
        return classID;
    }

    @Override
    public HashMap<Materie, ? extends SituatieMaterieBaza> getSituatie() {
        Centralizator centralizator = Centralizator.getCentralizator();
        return centralizator.getClasa(classID).getSituatieElev(this);
    }
}
