package mapa;
import entidades.Jugador;
import entidades.Enemigo;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase abstracta que representa una zona del mundo en el juego.
 */
public abstract class Zona {

    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;

    /**
     * Crea una zona con nombre y nivel requerido.
     * @param nombre nombre de la zona
     * @param nivelRequerido nivel mínimo para entrar
     */
    public Zona(String nombre, int nivelRequerido) {
        this.nombre = nombre;
        this.nivelRequerido = nivelRequerido;
        this.enemigosDisponibles = new ArrayList<>();
    }

    /**
     * Ejecuta la acción principal de la zona.
     * @param Cloud el jugador que realiza la acción
     */
    public abstract void accionZona(Jugador Cloud);

    /**
     * Verifica si Cloud cumple el nivel mínimo para entrar.
     * @param Cloud el jugador a validar
     * @return true si puede entrar, false si no
     */
    public boolean validarAcceso(Jugador Cloud) {
        return Cloud.getNivel() >= nivelRequerido;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getNivelRequerido() { return nivelRequerido; }
    public List<Enemigo> getEnemigosDisponibles() { return enemigosDisponibles; }
}