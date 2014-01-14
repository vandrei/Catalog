/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package liceu;

/**
 *
 * @author andrei
 */
public class Materie implements java.io.Serializable {

    private String name;
    private int nrOre;
    private boolean teza;
    private String oldName;

    public Materie(String name, int nrOre, boolean teza) {
        this.name = name;
        this.nrOre = nrOre;
        this.teza = teza;
    }

    @Override
    public String toString() {
        return name;
    }

    public String oldString() {
        return oldName;
    }

    public String getnrOre() {
        return Integer.toString(nrOre);
    }

    public boolean getTeza() {
        return teza;
    }

    public Profesor getProfesor(Clasa clasa) {
        return Centralizator.getCentralizator().getProfesor(this, clasa);
    }

    public void setInfo(String name, int nrOre, boolean teza) {
        this.oldName = this.name;
        this.name = name;
        this.nrOre = nrOre;
        this.teza = teza;
    }
}
