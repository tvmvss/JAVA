import componentes.Combate;
import entidades.Enemigo;
import entidades.EnemigoSimulador;
import entidades.Jugador;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import mapa.Gongaga;
import mapa.NucleoPlaneta;
import mapa.Sector7;

public class main {

    public static void main(String[] args) {

        Jugador Cloud = new Jugador();
        Sector7 sector7 = new Sector7();
        Gongaga gongaga = new Gongaga();
        NucleoPlaneta nucleo = new NucleoPlaneta();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean jugando = true;

        System.out.println("=== La Amenaza de Sephiroth ===");
        System.out.println("Cloud despierta en el Sector 7...");

        while (jugando) {
            mostrarMenuPrincipal(Cloud);
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    menuSector7(Cloud, sector7, scanner);
                    break;
                case 2:
                    if (gongaga.validarAcceso(Cloud)) {
                        if (random.nextDouble() < 0.30) {
                            gongaga.accionZona(Cloud);
                        } else {
                            System.out.println("¡Emboscada!");
                            List<Enemigo> grupo = gongaga.generarGrupoEnemigo();
                            boolean gano = new Combate(Cloud, grupo, false, scanner).iniciar();
                            if (!gano && Cloud.getStats().getHpActual() <= 0) {
                                aplicarDerrota(Cloud);
                            }
                        }
                    } else {
                        System.out.println("Necesitas nivel " + gongaga.getNivelRequerido() + " para entrar a Gongaga.");
                    }
                    break;
                case 3:
                    if (nucleo.validarAcceso(Cloud)) {
                        List<Enemigo> grupoFinal = new ArrayList<>();
                        grupoFinal.add(new entidades.Sephiroth());
                        boolean victoria = new Combate(Cloud, grupoFinal, false, scanner).iniciar();
                        if (victoria) {
                            aplicarVictoria(Cloud);
                            jugando = false;
                        } else if (Cloud.getStats().getHpActual() <= 0) {
                            aplicarDerrota(Cloud);
                        }
                    } else {
                        if (Cloud.getNivel() < nucleo.getNivelRequerido()) {
                            System.out.println("Necesitas nivel 20 para entrar al Núcleo.");
                        } else {
                            System.out.println("Necesitas al menos 2 materias equipadas en el Arma.");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Hasta pronto, Cloud.");
                    jugando = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }

    /**
     * Muestra el menú principal con el estado actual de Cloud.
     * @param Cloud el jugador cuyo estado se muestra
     */
    private static void mostrarMenuPrincipal(Jugador Cloud) {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("HP: " + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo());
        System.out.println("MP: " + Cloud.getStats().getMpActual() + "/" + Cloud.getStats().getMpMaximo());
        System.out.println("Nivel: " + Cloud.getNivel() + " | XP: " + Cloud.getXpActual());
        System.out.println("Chatarra: " + Cloud.getChatarra());
        System.out.println("Límite: " + Cloud.getLimiteActual() + "/100");
        System.out.println("----------------------");
        System.out.println("1. Sector 7");
        System.out.println("2. Gongaga (Nivel 5)");
        System.out.println("3. Núcleo del Planeta (Nivel 20 + 2 materias)");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    /**
     * Muestra el submenú del Sector 7 y gestiona la elección del jugador.
     * @param Cloud el jugador que interactúa
     * @param sector7 la zona Sector 7
     * @param scanner lector de entrada
     */
    private static void menuSector7(Jugador Cloud, Sector7 sector7, Scanner scanner) {
        boolean enSector7 = true;
        while (enSector7) {
            System.out.println("\n=== SECTOR 7 ===");
            System.out.println("1. Simulador de combate");
            System.out.println("2. Tienda de chatarra");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    List<Enemigo> grupoSim = new ArrayList<>();
                    grupoSim.add(new EnemigoSimulador("Soldado Común"));
                    if (new Random().nextDouble() < 0.50) {
                        grupoSim.add(new EnemigoSimulador("Soldado Común"));
                    }
                    if (grupoSim.size() == 1) {
                        System.out.println("¡Apareció un Soldado Común!");
                    } else {
                        System.out.println("¡Aparecieron 2 Soldados Comunes!");
                    }
                    new Combate(Cloud, grupoSim, true, scanner).iniciar();
                    break;
                case 2:
                    menuTienda(Cloud, sector7, scanner);
                    break;
                case 0:
                    enSector7 = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Muestra el submenú de la tienda y gestiona las compras de mejoras.
     * @param Cloud el jugador que compra
     * @param sector7 la zona que contiene la tienda
     * @param scanner lector de entrada
     */
    private static void menuTienda(Jugador Cloud, Sector7 sector7, Scanner scanner) {
        boolean enTienda = true;
        while (enTienda) {
            System.out.println("\n=== TIENDA DE CHATARRA ===");
            System.out.println("Chatarra disponible: " + Cloud.getChatarra());
            System.out.println("----------------------");
            for (int i = 0; i < sector7.getTiendaLocal().size(); i++) {
                componentes.Mejora m = sector7.getTiendaLocal().get(i);
                System.out.println((i + 1) + ". " + m.getNombre() +
                    " | Costo: " + m.getCostoChatarra() +
                    " | +" + m.getValorBono() + " " + m.getStatAfectado());
            }
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            int opcion = scanner.nextInt();
            if (opcion == 0) {
                enTienda = false;
            } else if (opcion >= 1 && opcion <= sector7.getTiendaLocal().size()) {
                componentes.Mejora mejora = sector7.getTiendaLocal().get(opcion - 1);
                sector7.aplicarMejora(Cloud, mejora);
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Aplica la penalización de derrota en zonas de peligro.
     * Vacía la mochila, resetea la chatarra y restaura el HP al máximo.
     * @param Cloud el jugador derrotado
     */
    private static void aplicarDerrota(Jugador Cloud) {
        System.out.println("\n¡Cloud fue derrotado!");
        Cloud.getMochila().clear();
        Cloud.setChatarra(0);
        Cloud.getStats().setHpActual(Cloud.getStats().getHpMaximo());
        System.out.println("Cloud perdió toda su chatarra y las materias de su mochila.");
        System.out.println("Cloud regresa al Sector 7...");
    }

    /**
     * Muestra el mensaje de victoria final y las estadísticas de Cloud.
     * @param Cloud el jugador victorioso
     */
    private static void aplicarVictoria(Jugador Cloud) {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║     ¡SEPHIROTH FUE DERROTADO!    ║");
        System.out.println("║     ¡Cloud salvó el planeta!     ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("\n=== ESTADÍSTICAS FINALES ===");
        System.out.println("Nivel: "    + Cloud.getNivel());
        System.out.println("HP: "       + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo());
        System.out.println("MP: "       + Cloud.getStats().getMpActual() + "/" + Cloud.getStats().getMpMaximo());
        System.out.println("Fuerza: "   + Cloud.getStats().getFuerza());
        System.out.println("Chatarra: " + Cloud.getChatarra());
    }
}