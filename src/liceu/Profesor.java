/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public class Profesor extends Utilizator implements IProfesor, java.io.Serializable {

    private Materie materie;

    public Profesor(String userName, String password, String nume, String prenume, Materie materie) {
        super(userName, password, nume, prenume);
        this.materie = materie;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + materie.toString();
    }

    public String getDisplayName() {
        return super.toString() + ", " + materie.toString();
    }

    public Materie getMaterie() {
        return materie;
    }
}
