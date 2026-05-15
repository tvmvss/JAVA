package mapa;
import componentes.Elemento;
import componentes.Materia;
import entidades.Enemigo;
import entidades.EnemigoSalvaje;
import entidades.Jugador;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Zona salvaje. Al explorar: 30% de encontrar una Materia, 70% de emboscada.
 * Requiere nivel 5 para entrar.
 */
public class Gongaga extends Zona {

    private final List<Materia> poolMaterias;
    private final Random random;

    /**
     * Crea Gongaga con su pool de materias y catálogo de enemigos.
     */
    public Gongaga() {
        super("Gongaga", 5);
        this.poolMaterias = new ArrayList<>();
        this.random = new Random();
        inicializarEnemigos();
    }

    /**
     * Acción principal: 30% materia, 70% emboscada.
     * @param Cloud el jugador que explora
     */
    @Override
    public void accionZona(Jugador Cloud) {
        if (random.nextDouble() < 0.30) {
            encontrarMateria(Cloud);
        } else {
            System.out.println("¡Emboscada!");
            generarGrupoEnemigo();
        }
    }

    /**
     * Genera un grupo de 1, 2 o 3 EnemigoSalvaje aleatorios.
     * 60% un enemigo, 30% dos, 10% tres.
     * @return lista con los enemigos del grupo
     */
    public List<Enemigo> generarGrupoEnemigo() {
        List<Enemigo> grupo = new ArrayList<>();
        double probabilidad = random.nextDouble();
        int cantidad;

        if (probabilidad < 0.60)      cantidad = 1;
        else if (probabilidad < 0.90) cantidad = 2;
        else                          cantidad = 3;

        for (int i = 0; i < cantidad; i++) {
            grupo.add(crearEnemigoAleatorio());
        }
        switch (cantidad) {
            case 1:
                System.out.println("¡Apareció " + grupo.get(0).getNombre() + "!");
                break;
            case 2:
                System.out.println("¡Aparecieron 2 enemigos!");
                break;
            default:
                System.out.println("¡Aparecieron 3 enemigos!");
                break;
        }

        return grupo;
    }

    /**
     * Crea un EnemigoSalvaje aleatorio del catálogo de Gongaga.
     * Planta Carnívora, Sapo de la Jungla o Robot Centinela con igual probabilidad.
     * @return una nueva instancia del enemigo seleccionado
     */
    private Enemigo crearEnemigoAleatorio() {
        int indice = random.nextInt(enemigosDisponibles.size());
        Enemigo base = enemigosDisponibles.get(indice);
        return base;
    }

    /**
     * Añade a la mochila de Cloud una Materia con elemento aleatorio.
     * Igual probabilidad entre FUEGO, HIELO, RAYO y CURA.
     * @param Cloud el jugador que recibe la materia
     */
    private void encontrarMateria(Jugador Cloud) {
        Elemento[] elementos = {Elemento.FUEGO, Elemento.HIELO, Elemento.RAYO, Elemento.CURA};
        Elemento elementoAleatorio = elementos[random.nextInt(elementos.length)];
        Materia nuevaMateria = new Materia("Materia " + elementoAleatorio, elementoAleatorio);
        Cloud.getMochila().add(nuevaMateria);
        System.out.println("¡Encontraste una Materia de " + elementoAleatorio + "!");
    }

    /**
     * Inicializa el catálogo de enemigos salvajes de Gongaga.
     */
    private void inicializarEnemigos() {
        List<Elemento> vacio = new ArrayList<>();
        List<Elemento> debPlanta = new ArrayList<>();
        debPlanta.add(Elemento.FUEGO);
        debPlanta.add(Elemento.HIELO);
        List<Elemento> inmPlanta = new ArrayList<>();
        inmPlanta.add(Elemento.RAYO);
        enemigosDisponibles.add(new EnemigoSalvaje(
            "Planta Carnívora",
            new componentes.Estadisticas(80, 0, 15, 0),
            debPlanta, vacio, inmPlanta
        ));

        List<Elemento> debSapo = new ArrayList<>();
        debSapo.add(Elemento.RAYO);
        debSapo.add(Elemento.HIELO);
        List<Elemento> resSapo = new ArrayList<>();
        resSapo.add(Elemento.FUEGO);
        enemigosDisponibles.add(new EnemigoSalvaje(
            "Sapo de la Jungla",
            new componentes.Estadisticas(60, 0, 12, 0),
            debSapo, resSapo, vacio
        ));

        List<Elemento> debRobot = new ArrayList<>();
        debRobot.add(Elemento.RAYO);
        List<Elemento> resRobot = new ArrayList<>();
        resRobot.add(Elemento.FISICO);
        resRobot.add(Elemento.HIELO);
        enemigosDisponibles.add(new EnemigoSalvaje(
            "Robot Centinela",
            new componentes.Estadisticas(100, 0, 20, 0),
            debRobot, resRobot, vacio
        ));
    }

    public List<Materia> getPoolMaterias() { return poolMaterias; }
}