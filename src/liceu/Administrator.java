/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public class Administrator extends Utilizator implements IProfesor, ISecretar {

    public Administrator(String userName, String password, String nume, String prenume) {
        super(userName, password, nume, prenume);
    }

    @Override
    public void addClasa(String classID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
