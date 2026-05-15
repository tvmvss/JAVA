package entidades;
import componentes.Estadisticas;

/**
 * Clase abstracta que representa un enemigo del juego.
 * Sirve como plantilla para todos los tipos de enemigos.
 */
public abstract class Enemigo {
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
