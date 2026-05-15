package entidades;
import componentes.Elemento;
import componentes.Estadisticas;
import componentes.Vulnerable;

/**
 * Clase abstracta que representa un enemigo del juego.
 * Sirve como plantilla para todos los tipos de enemigos.
 */
public abstract class Enemigo implements Vulnerable {
    public String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;

        /**
     * Crea un enemigo con sus estadísticas base.
     * @param nombre nombre del enemigo
     * @param xpRecompensa experiencia que otorga al morir
     * @param chatarrARecompensa chatarra que otorga al morir
     * @param stats estadísticas de combate del enemigo
     */
    public Enemigo(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats) {
        this.nombre = nombre;
        this.xpRecompensa = xpRecompensa;
        this.chatarraRecompensa = chatarraRecompensa;
        this.stats = stats;
   }

       /**
     * Ejecuta el ataque del enemigo contra Cloud.
     * Cada subclase define su propia lógica de ataque.
     * @param Cloud el jugador que recibe el ataque
     */
    public abstract void atacar(Jugador Cloud);

       /**
     * Otorga la experiencia del enemigo a Cloud al ser derrotado.
     * @param Cloud el jugador que recibe la experiencia
     */
    public void giveXpRecompensa(Jugador Cloud) {
        Cloud.recibirXp(xpRecompensa);
    }

    /**
     * Comportamiento por defecto para todos los enemigos.
     * Si no tienen debilidades específicas, el daño se multiplica por 1.0 (daño normal).
     * @param elementoMagia el elemento del ataque mágico de Cloud
     * @return el multiplicador de daño basado en la debilidad del enemigo
     */
    @Override
    public double evaluarDebilidad(Elemento elementoMagia) {
        return 1.0; 
    }

    /**
     * Resetea el contador de SuperNova del enemigo.
    * Por defecto no hace nada. Sobreescrito por Sephiroth.
    */
    public void resetearContador() {};

    /**
    * Otorga la chatarra del enemigo a Cloud al ser derrotado.
    * Por defecto no hace nada. Sobreescrito por EnemigoSalvaje.
    * @param Cloud el jugador que recibe la chatarra
    */
    public void giveChatarraRecompensa(Jugador Cloud) {};

    public String getNombre () { return nombre; }
    public int getXpRecompensa() { return xpRecompensa; }
    public int getChatarraRecompensa() { return chatarraRecompensa; }
    public Estadisticas getStats() { return stats; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setStats(Estadisticas stats) { this.stats = stats; }  
    
}
