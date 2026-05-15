package entidades;
import componentes.Estadisticas;
import java.util.Random;

/**
 * Jefe final del juego. No posee debilidades elementales.
 * Posee un contador que al llegar a 10 aniquila a Cloud instantáneamente.
 * El contador se reinicia solo si Cloud le golpea con un ataque Límite.
 */
public class Sephiroth extends Enemigo {

    private int contadorSuperNova;

    /**
     * Crea a Sephiroth con sus estadísticas base fijas.
     * No otorga XP ni chatarra al ser derrotado.
     */
    public Sephiroth() {
        super("Sephiroth", 0, 0, new Estadisticas(500, 0, 40, 0));
        this.contadorSuperNova = 0;
    }

    /**
     * Ataca a Cloud con daño físico. Incrementa el contadorSuperNova cada turno.
     * Al llegar a 10 lanza SuperNova. Tiene 90% de precisión.
     * @param Cloud el jugador que recibe el ataque
     */
    @Override
    public void atacar(Jugador Cloud) {
        contadorSuperNova++;
        System.out.println("Contador SuperNova: " + contadorSuperNova + "/10");

        if (contadorSuperNova >= 10) {
            lanzarSuperNova(Cloud);
            return;
        }

        if (new Random().nextDouble() > 0.90) {
            System.out.println("Sephiroth falló el ataque.");
            return;
        }

        int dmg = (int)(stats.getFuerza() * 1.25);
        Cloud.getStats().recibirDMG(dmg);
        System.out.println("Sephiroth infligió " + dmg + " de daño a Cloud.");
    }

    /**
     * Aniquila a Cloud instantáneamente seteando su HP a 0.
     * @param Cloud el jugador que recibe el ataque definitivo
     */
    public void lanzarSuperNova(Jugador Cloud) {
        Cloud.getStats().setHpActual(0);
        System.out.println("¡Sephiroth lanzó SuperNova!, Cloud fue aniquilado.");
    }

    /**
     * Reinicia el contadorSuperNova a 0.
     * Solo ocurre cuando Cloud golpea a Sephiroth con un ataque Límite.
     */
    public void resetearContador() {
        contadorSuperNova = 0;
        System.out.println("El contador de SuperNova fue reiniciado.");
    }

    // Getters y Setters
    public int getContadorSuperNova() { return contadorSuperNova; }
    public void setContadorSuperNova(int contador) { this.contadorSuperNova = contador; }
}
