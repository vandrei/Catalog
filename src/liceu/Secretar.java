/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public class Secretar extends Utilizator implements ISecretar {

    public Secretar(String userName, String password, String nume, String prenume) {
        super(userName, password, nume, prenume);
    }

    @Override
    public void addClasa(String classID) {
        Centralizator.getCentralizator().addClasa(classID);
    }

    public String toString() {
        return super.getFirstName() + " " + super.getLastName();
    }
}
