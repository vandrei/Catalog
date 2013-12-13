/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public abstract class Utilizator {
    private String userName, password, nume, prenume;
    
    public Utilizator(String userName, String password, String nume, String prenume)
    {
        this.userName = userName;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
    }
    
    @Override
    public String toString()
    {
        return nume + " " + prenume;
    }
    
}
