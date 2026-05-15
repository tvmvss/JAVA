package entidades;
import componentes.Elemento;
import componentes.Estadisticas;
import componentes.Vulnerable;
import java.util.List;
import java.util.Random;

/**
 * Enemigo salvaje que habita en Gongaga.
 * Posee debilidades, resistencias e inmunidades elementales.
 * Implementa Vulnerable para evaluar multiplicadores de daño mágico.
 */
public class EnemigoSalvaje extends Enemigo implements Vulnerable {
    private final List<Elemento> debilidades;
    private List<Elemento> resistencias;
    private final List<Elemento> inmunidades;

    /**
     * Crea un EnemigoSalvaje con sus estadísticas y listas elementales.
     * XP aleatoria entre 80 y 100, chatarra aleatoria entre 50 y 75.
     * @param nombre nombre del enemigo
     * @param stats estadísticas de combate
     * @param debilidades elementos que le hacen x2 de daño
     * @param resistencias elementos que le hacen x0.5 de daño
     * @param inmunidades elementos que le hacen x0.0 de daño
     */
    public EnemigoSalvaje (String nombre, Estadisticas stats, List <Elemento> debilidades, List <Elemento> resistencias, List <Elemento> inmunidades) {
        super (nombre, new Random().nextInt(21) + 80, new Random().nextInt(26) + 50, stats);
        this.debilidades = debilidades;
        this.resistencias = resistencias;
        this.inmunidades = inmunidades;
    }
    /**
     * Ataca a Cloud con daño físico basado en su fuerza.
     * Tiene 85% de probabilidad de acertar.
     * @param Cloud el jugador que recibe el ataque
     */
    @Override
    public void atacar(Jugador Cloud) {
        if (new Random().nextDouble() > 0.85) {
            System.out.println(nombre + " falló el ataque.");
            return;
        }
        int dmg = (int)(stats.getFuerza() * 1.25);
        Cloud.getStats().recibirDMG(dmg);
        System.out.println(nombre + " infligió " + dmg + " de daño a Cloud.");
    }

    /**
     * Evalúa el multiplicador de daño según el elemento recibido.
     * @param elementoMagia elemento del ataque mágico
     * @return 2.0 si es debilidad, 0.5 si es resistencia, 0.0 si es inmunidad, 1.0 si es neutral
     */
    @Override
    public double evaluarDebilidad(Elemento elementoMagia) {
        if (debilidades.contains(elementoMagia))  return 2.0;
        if (inmunidades.contains(elementoMagia))  return 0.0;
        if (resistencias.contains(elementoMagia)) return 0.5;
        return 1.0;
    }

    /**
     * Otorga la chatarra del enemigo a Cloud al ser derrotado.
     * @param Cloud el jugador que recibe la chatarra
     */
    public void giveChatarraRecompensa(Jugador Cloud) {
        Cloud.setChatarra(Cloud.getChatarra() + chatarraRecompensa);
        System.out.println("Cloud obtuvo " + chatarraRecompensa + " de chatarra.");
    }

    public List<Elemento> getDebilidades() { return debilidades; }
    public List<Elemento> getResistencias() { return resistencias; }
    public List<Elemento> getInmunidades() { return inmunidades; }
}
