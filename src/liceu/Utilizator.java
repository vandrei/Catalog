/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public abstract class Utilizator implements java.io.Serializable {
    private String userName, password, nume, prenume;
    
    public String getUsername()
    {
        return userName;
    }
    
    public String getLastName()
    {
        return nume;
    }
    
    public Utilizator(String userName, String password, String nume, String prenume)
    {
        this.userName = userName;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void changeInfo(String username, String nume, String prenume, String passwd)
    {
        Centralizator.getCentralizator().changeUsername(username, this);
        this.userName = username;
        this.nume = nume;
        this.prenume = prenume;
        this.password = passwd;
    }
    
    public void changeInfo (String username, String nume, String prenume)
    {
        this.userName = username;
        this.nume = nume;
        this.prenume = prenume;
    }
    
    public boolean passwordIsCorrect(String password)
    {
        if (this.password.equals(password))
            return true;
        return false;
    }
    
    @Override
    public String toString()
    {
        return nume + " " + prenume;
    }
    
    public String getFirstName()
    {
        return prenume;
    }
    
}
