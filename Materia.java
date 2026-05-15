package componentes;
/**
 * Representa una materia que Cloud puede guardar en su mochila o equipar en su arma.
 */
public class Materia {
    private String nombre;
    private Elemento elemento;

    /**
     * Crea una materia con nombre y elemento definidos.
     * @param nombre nombre de la materia
     * @param elemento elemento mágico que representa
     */
    public Materia(String nombre, Elemento elemento) {
        this.nombre = nombre;
        this.elemento = elemento;
    }

    public String getNombre() { return nombre; }
    public Elemento getElemento() { return elemento; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setElemento(Elemento elemento) { this.elemento = elemento; }
}
