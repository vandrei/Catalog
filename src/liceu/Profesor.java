/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public class Profesor extends Utilizator implements IProfesor, java.io.Serializable{
    private String materie;
    
    public Profesor(String userName, String password, String nume, String prenume, String materie)
    {
        super(userName, password, nume, prenume);
        this.materie = materie;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + " - " + materie;
    }
    
    public String getMaterie()
    {
        return materie;
    }
    
}
