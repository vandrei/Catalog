/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public class Elev extends Utilizator implements IElev{
    private String CNP, dataNastere;
    
    public Elev (String userName, String password, String nume, String prenume, String CNP, String dataNastere)
    {
        super(userName, password, nume, prenume);
        this.CNP = CNP;
        this.dataNastere = dataNastere;
    }
    
    
}
