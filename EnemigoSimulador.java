package entidades;
import componentes.Estadisticas;
import java.util.Random;

/**
 * Enemigo de entrenamiento del simulador de Sector 7.
 * Su ataque nunca puede matar a Cloud, lo deja mínimo con 1 HP.
 */
public class EnemigoSimulador extends Enemigo {

    /**
     * Crea un EnemigoSimulador con stats fijas y recompensa aleatoria de XP (entre 15-20 puntos de XP).
     * @param nombre nombre del simulador
     */
    public EnemigoSimulador(String nombre) {
        super(nombre, new Random().nextInt(6) + 15, 0, new Estadisticas(50, 0, 15, 0));
    }

    /**
     * Ataca a Cloud con daño basado en su fuerza.
     * Si el daño mataría a Cloud, lo reduce para dejarlo con 1 HP.
     * @param Cloud el jugador que recibe el ataque
     */
    @Override
    public void atacar(Jugador Cloud) {
        if (new Random().nextDouble() > 0.85) {
        System.out.println(nombre + " falló el ataque.");
        return;
        }   
        int dmg = (int) (stats.getFuerza()* 1.25);
        if (!checkDanoSeguro(Cloud, dmg)) {
            dmg = Cloud.getStats().getHpActual() - 1;
        }
        if (dmg > 0) {
            Cloud.getStats().recibirDMG(dmg);
            System.out.println(nombre + " inflingió " + dmg + " de daño a Cloud.");
        }
    }

    /**
     * Verifica si el daño calculado no reduciría el HP de Cloud a 0 o menos.
     * @param Cloud el jugador a verificar
     * @param dmg el daño que se aplicaría
     * @return true si el daño es seguro, false si mataría a Cloud
     */
    public boolean checkDanoSeguro(Jugador Cloud, int dmg) {
        return Cloud.getStats().getHpActual() - dmg >= 1;
    }
}