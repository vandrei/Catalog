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
    public Administrator(String userName, String password, String nume, String prenume)
    {
        super(userName, password, nume, prenume);
    }
    
}
