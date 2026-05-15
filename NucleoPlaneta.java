package mapa;
import entidades.Jugador;

/**
 * Zona del jefe final. Requiere nivel 20 y mínimo 2 materias equipadas en el Arma.
 * Inicia el combate directo contra Sephiroth. No se puede huir.
 */
public class NucleoPlaneta extends Zona {

    private final int materiasMinimasRequeridas;

    /**
     * Crea el Núcleo del Planeta con nivel requerido 20 y mínimo 2 materias.
     */
    public NucleoPlaneta() {
        super("Núcleo del Planeta", 20);
        this.materiasMinimasRequeridas = 2;
    }

    /**
     * Acción principal: inicia el combate contra Sephiroth directamente.
     * @param Cloud el jugador que entra al combate
     */
    @Override
    public void accionZona(Jugador Cloud) {
        iniciarCombate(Cloud);
    }

    /**
     * Valida que Cloud tenga nivel 20 Y al menos 2 materias equipadas en el Arma.
     * @param Cloud el jugador a validar
     * @return true si cumple ambas condiciones
     */
    @Override
    public boolean validarAcceso(Jugador Cloud) {
        boolean verificarNivel = super.validarAcceso(Cloud);
        boolean verificarMaterias = Cloud.getBusterSword().getMateriasEquipadas().size() >= materiasMinimasRequeridas;
        return verificarNivel && verificarMaterias;
    }

    /**
     * Inicia el combate final contra Sephiroth.
     * Si Cloud muere, Sephiroth se restaura por completo para el próximo intento.
     * @param Cloud el jugador que combate
     */
    public void iniciarCombate(Jugador Cloud) {}

    // Getters
    public int getMateriasMinRequeridas() { return materiasMinimasRequeridas; }
}