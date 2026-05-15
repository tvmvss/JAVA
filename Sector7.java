package mapa;
import componentes.Mejora;
import componentes.TipoStat;
import entidades.Enemigo;
import entidades.EnemigoSimulador;
import entidades.Jugador;
import java.util.ArrayList;
import java.util.Random;

/**
 * Zona segura inicial. Contiene el simulador de combate y la tienda de chatarra.
 * Cloud no puede morir aquí — si pierde queda con 1 HP sin penalización.
 */
public class Sector7 extends Zona {

    private ArrayList<Mejora> tiendaLocal;

    /**
     * Crea el Sector 7 con su inventario de mejoras inicializado.
     */
    public Sector7() {
        super("Sector 7", 1);
        this.tiendaLocal = new ArrayList<>();
        inicializarTienda();
        inicializarEnemigos();
    }

    /**
     * Acción principal de Sector 7: delega al simulador o la tienda.
     * @param Cloud el jugador que interactúa
     */
    @Override
    public void accionZona(Jugador Cloud){};

    /**
     * Inicia un combate de entrenamiento contra 1 o 2 EnemigoSimulador.
     * 50% de probabilidad de que aparezca un segundo enemigo.
     * Si Cloud pierde, queda con 1 HP sin penalización.
     * @param Cloud el jugador que entrena
     */
    public void iniciarSimulador(Jugador Cloud) {
    ArrayList<Enemigo> grupo = new ArrayList<>();
    grupo.add(new EnemigoSimulador("Soldado Común"));
    if (new Random().nextDouble() < 0.50) {
        grupo.add(new EnemigoSimulador("Soldado Común"));
    }
    if (grupo.size() == 1) {
        System.out.println("¡Apareció un Soldado Común!");
    } else {
        System.out.println("¡Aparecieron 2 Soldados Comunes!");
    }
}

    /**
     * Abre la tienda donde Cloud gasta chatarra en mejoras permanentes.
     * Descuenta la chatarra y aplica el bono inmediatamente a las stats de Cloud.
     * @param Cloud el jugador que compra
     */
    public void abrirTienda(Jugador Cloud){};
    
    /**
     * Aplica una mejora comprada a las estadísticas de Cloud.
     * Descuenta la chatarra y aumenta el stat correspondiente permanentemente.
     * @param Cloud el jugador que recibe la mejora
     * @param mejora la mejora a aplicar
     */
    public void aplicarMejora(Jugador Cloud, Mejora mejora) {
        if (Cloud.getChatarra() < mejora.getCostoChatarra()) {
            System.out.println("No tienes suficiente chatarra.");
            return;
        }
        Cloud.setChatarra(Cloud.getChatarra() - mejora.getCostoChatarra());
        switch (mejora.getStatAfectado()) {
            case HP_MAX:
                Cloud.getStats().setHpMaximo(Cloud.getStats().getHpMaximo() + mejora.getValorBono());
                Cloud.getStats().setHpActual(Cloud.getStats().getHpMaximo());
                break;
            case MP_MAX:
                Cloud.getStats().setMpMaximo(Cloud.getStats().getMpMaximo() + mejora.getValorBono());
                Cloud.getStats().setMpActual(Cloud.getStats().getMpMaximo());
                break;
            case FUERZA:
                Cloud.getStats().setFuerza(Cloud.getStats().getFuerza() + mejora.getValorBono());
                break;
        }
        System.out.println("¡" + mejora.getNombre() + " aplicada!");
    }

    /**
     * Inicializa el catálogo de mejoras disponibles en la tienda.
     */
    private void inicializarTienda() {
        tiendaLocal.add(new Mejora("Mejora de Vitalidad", 100, TipoStat.HP_MAX,  20));
        tiendaLocal.add(new Mejora("Mejora de Éter",      120, TipoStat.MP_MAX,  10));
        tiendaLocal.add(new Mejora("Mejora Física",       150, TipoStat.FUERZA,  10));
    }

    /**
     * Inicializa el enemigo disponible en el simulador.
     */
    private void inicializarEnemigos() {
        enemigosDisponibles.add(new EnemigoSimulador("Soldado Común"));
    }

    // Getters
    public ArrayList<Mejora> getTiendaLocal() { return tiendaLocal; }
}