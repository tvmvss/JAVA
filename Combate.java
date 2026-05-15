package componentes;
import entidades.Enemigo;
import entidades.Jugador;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase que gestiona el bucle de combate entre Cloud y un grupo de enemigos.
 * Maneja turnos, acciones, carga de límite y condiciones de victoria/derrota.
 */
public class Combate {

    private final Jugador Cloud;
    private final List<Enemigo> grupo;
    private final boolean esSimulador;
    private final Scanner scanner;
    private final Random random;

    /**
     * Crea una instancia de combate entre Cloud y un grupo de enemigos.
     * @param Cloud el jugador que combate
     * @param grupo lista de enemigos a enfrentar
     * @param esSimulador true si el combate es en el simulador
     * @param scanner lector de entrada del usuario
     */
    public Combate(Jugador Cloud, List<Enemigo> grupo, boolean esSimulador, Scanner scanner) {
        this.Cloud = Cloud;
        this.grupo = grupo;
        this.esSimulador = esSimulador;
        this.scanner = scanner;
        this.random = new Random();
    }

    /**
     * Ejecuta el bucle principal de combate.
     * Alterna turnos entre Cloud y los enemigos hasta que uno de los dos sea derrotado.
     * @return true si Cloud ganó, false si Cloud murió o huyó
     */
    public boolean iniciar() {
        int turno = 1;

        while (!grupo.isEmpty() && Cloud.getStats().getHpActual() > 0) {
            System.out.println("\n=== TURNO " + turno + " ===");
            mostrarEstadoCombate();

            int accion = menuCombate();

            switch (accion) {
                case 1:
                    Cloud.accionFisica(grupo.get(0));
                    break;
                case 2:
                    accionMagica();
                    break;
                case 3:
                    if (Cloud.getLimiteActual() >= 100) {
                        Cloud.accionLimite(grupo.get(0));
                    } else {
                        System.out.println("La barra de Límite no está llena.");
                        continue;
                    }
                    break;
                case 4:
                    if (esSimulador) {
                        System.out.println("No puedes huir del simulador.");
                        continue;
                    }
                    if (random.nextDouble() < 0.50) {
                        System.out.println("¡Cloud escapó!");
                        return false;
                    } else {
                        System.out.println("¡Cloud no pudo escapar! Pierde el turno.");
                    }
                    break;
                default:
                    System.out.println("Opción inválida.");
                    continue;
            }

            grupo.removeIf(e -> e.getStats().getHpActual() <= 0);
            if (grupo.isEmpty()) break;

            turnoEnemigos();

            if (Cloud.getStats().getHpActual() <= 0) {
                if (esSimulador) {
                    Cloud.getStats().setHpActual(1);
                    System.out.println("Derrota en el simulador. Cloud queda con 1 HP.");
                }
                return false;
            }
            turno++;
        }

        return grupo.isEmpty();
    }

    /**
     * Muestra el estado actual de Cloud y los enemigos durante el combate.
     */
    private void mostrarEstadoCombate() {
        System.out.println("Cloud — HP: " + Cloud.getStats().getHpActual() + "/" +
            Cloud.getStats().getHpMaximo() + " | MP: " + Cloud.getStats().getMpActual() +
            "/" + Cloud.getStats().getMpMaximo() + " | Límite: " + Cloud.getLimiteActual() + "/100");
        for (Enemigo e : grupo) {
            System.out.println(e.getNombre() + " — HP: " + e.getStats().getHpActual());
        }
    }

    /**
     * Muestra el menú de acciones de combate y retorna la opción elegida.
     * @return opción elegida por el jugador
     */
    private int menuCombate() {
        System.out.println("\n1. Ataque físico");
        System.out.println("2. Magia");
        System.out.println("3. Límite" + (Cloud.getLimiteActual() >= 100 ? " ¡DISPONIBLE!" : " (" + Cloud.getLimiteActual() + "/100)"));
        System.out.println("4. Huir");
        System.out.print("Acción: ");
        return scanner.nextInt();
    }

    /**
     * Gestiona la selección de elemento y ejecuta el ataque mágico de Cloud.
     * Solo muestra los elementos que Cloud tiene equipados en el Arma.
     */
    private void accionMagica() {
        List<Elemento> elementosDisponibles = new java.util.ArrayList<>();
        Jugador.Arma arma = Cloud.getBusterSword();
        for (Materia m : arma.getMateriasEquipadas()) {
            if (!elementosDisponibles.contains(m.getElemento())) {
                elementosDisponibles.add(m.getElemento());
            }
        }

        if (elementosDisponibles.isEmpty()) {
            System.out.println("No tienes materias equipadas en el Arma.");
            return;
        }

        System.out.println("Selecciona elemento:");
        for (int i = 0; i < elementosDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + elementosDisponibles.get(i));
        }
        System.out.print("Elemento: ");
        int idx = scanner.nextInt() - 1;

        if (idx < 0 || idx >= elementosDisponibles.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Cloud.accionMagica(grupo.get(0), elementosDisponibles.get(idx));
    }

    /**
     * Ejecuta el turno de ataque de los enemigos contra Cloud.
     * Aplica probabilidad de ataque conjunto según el tamaño del grupo.
     * 1 enemigo: ataca solo. 2 enemigos: 50% atacan juntos. 3 enemigos: 33% atacan juntos.
     */
    private void turnoEnemigos() {
        if (grupo.size() == 1) {
            atacarConEnemigo(grupo.get(0));
        } else if (grupo.size() == 2) {
            if (random.nextDouble() < 0.50) {
                for (Enemigo e : grupo) atacarConEnemigo(e);
            } else {
                atacarConEnemigo(grupo.get(0));
            }
        } else {
            if (random.nextDouble() < 0.33) {
                for (Enemigo e : grupo) atacarConEnemigo(e);
            } else {
                atacarConEnemigo(grupo.get(0));
            }
        }
    }

    /**
     * Ejecuta el ataque de un enemigo individual contra Cloud.
     * Calcula el daño recibido y carga la barra de límite de Cloud.
     * Carga += floor(dañoRecibido / 2).
     * @param enemigo el enemigo que ataca
     */
    private void atacarConEnemigo(Enemigo enemigo) {
        int hpAntes = Cloud.getStats().getHpActual();
        enemigo.atacar(Cloud);
        int danoRecibido = hpAntes - Cloud.getStats().getHpActual();
        if (danoRecibido > 0) {
            Cloud.setLimiteActual(Cloud.getLimiteActual() + danoRecibido / 2);
            if (Cloud.getLimiteActual() > 100) Cloud.setLimiteActual(100);
        }
    }
}