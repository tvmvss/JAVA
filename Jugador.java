package entidades;
import componentes.Elemento;
import componentes.Estadisticas;
import componentes.Materia;
import componentes.Vulnerable;
import java.util.ArrayList;
import java.util.List;
/**
 * Representa al jugador Cloud.
 * Contiene sus estadísticas, inventario y su arma Buster Sword.
 */
public class Jugador {
    public String nombre = "Cloud";
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private final List<Materia> mochila;
    public Arma busterSword;

    /**
     * Crea a Cloud con sus estadísticas base iniciales.
     */
    public Jugador() {
        this.nivel = 1;
        this.xpActual = 0;
        this.chatarra = 0;
        this.limiteActual = 0;
        this.stats = new Estadisticas(100, 50, 15, 15);
        this.mochila = new ArrayList<>();
        this.busterSword = new Arma();
    }

    /**
     * Otorga experiencia a Cloud y verifica si sube de nivel.
     * Por cada nivel ganado, aumentan las 4 estadísticas base.
     * @param xp cantidad de experiencia recibida
     */
    public void recibirXp(int xp) {
        xpActual += xp;
        int xpNecesaria = nivel * 10;
        if (xpActual >= xpNecesaria) {
            xpActual -= xpNecesaria;
            nivel++;
            subirNivel();
        }
    }

    /**
     * Aumenta las estadísticas base de Cloud al subir de nivel.
     * HP+10, MP+5, Fuerza+4, Magia+6.
     */
    private void subirNivel() {
        stats.setHpMaximo(stats.getHpMaximo() + 10);
        stats.setHpActual(stats.getHpMaximo());
        stats.setMpMaximo(stats.getMpMaximo() + 5);
        stats.setMpActual(stats.getMpMaximo());
        stats.setFuerza(stats.getFuerza() + 4);
        stats.setMagia(stats.getMagia() + 6);
        System.out.println("¡Cloud subió al nivel " + nivel + "!");
    }

    /**
     * Representa el arma Buster Sword de Cloud.
     * Al ser clase anidada, accede directamente a las estadísticas de Cloud.
     */
    public class Arma {

        public String nombre = "Buster Sword";
        private final List<Materia> materiasEquipadas;

        /**
         * Crea el Buster Sword sin materias equipadas.
         */
        public Arma() {
            this.materiasEquipadas = new ArrayList<>();
        }

        /**
         * Calcula el daño físico basado en la fuerza de Cloud.
         * Fórmula: daño fisico = (fuerza * 1.25)
         * @return daño físico como entero truncado
         */
        public int calcularDanoFisico() {
            return (int)(stats.getFuerza() * 1.25);
        }

        /**
         * Calcula el daño mágico según el elemento y las materias equipadas.
         * Fórmula: daño magico = (magia * (1.0 + (0.5 * n)))
         * Donde n es el número de materias del mismo elemento equipadas.
         * @param elemento elemento del ataque mágico
         * @return daño mágico como entero truncado
         */
        public int calcularDanoMagico(Elemento elemento) {
            int n = 0;
            for (Materia m : materiasEquipadas) {
                if (m.getElemento() == elemento) n++;
            }
            return (int)(stats.getMagia() * (1.0 + (0.5 * n)));
        }
        /**
        * Calcula el costo de MP del ataque mágico según las materias equipadas.
        * Fórmula: Costo MP = 10 + (5*n)
        * Donde n es el número de materias del mismo elemento equipadas.
        * @param elemento elemento del ataque
        * @return costo en MP
        */
        public int calcularCostoMP(Elemento elemento) {
        int n = 0;
        for (Materia m : materiasEquipadas) {
            if (m.getElemento() == elemento) n++;
            }
        return 10 + (5 * n);    
        }

        /**
         * Calcula el daño del ataque Límite de Cloud.
         * Fórmula: floor(fuerza * 3.0)
         * @return daño límite como entero truncado
         */
        public int calcularDanoLimite() {
            return (int)(stats.getFuerza() * 5.0);
        }
        /**
        * Equipa una materia en el Buster Sword.
        * Máximo 5 ranuras disponibles.
        * @param materia materia a equipar
        */
        public void equiparMateria(Materia materia) {
        if (materiasEquipadas.size() < 5) {
            materiasEquipadas.add(materia);
            System.out.println("Materia " + materia.getNombre() + " equipada.");
        } else {
            System.out.println("No hay ranuras disponibles.");
            }
        }

        public List<Materia> getMateriasEquipadas() { return materiasEquipadas; }
        public String getNombre() { return nombre; }
    
    }

    /**
     * Ejecuta el ataque físico de Cloud contra un enemigo.
     * Carga la barra de límite según el daño realizado.
    * @param objetivo el enemigo que recibe el ataque
    */
   public void accionFisica(Enemigo objetivo) {
        int dmg = busterSword.calcularDanoFisico();
        objetivo.getStats().recibirDMG(dmg);
        limiteActual += dmg / 5;
        if (limiteActual > 100) limiteActual = 100;
        System.out.println("Cloud atacó a " + objetivo.getNombre() + " por " + dmg + " de daño.");
        if (objetivo.getStats().getHpActual() <= 0) {
            System.out.println(objetivo.getNombre() + " fue derrotado.");
            objetivo.giveXpRecompensa(this);
            objetivo.giveChatarraRecompensa(this);
        }
    }

    /**
    * Ejecuta el ataque mágico de Cloud o la curación con CURA.
    * Descuenta MP y aplica multiplicadores de debilidad si el enemigo es Vulnerable.
    * Si el elemento es CURA restaura HP de Cloud sin superar el máximo.
    * @param objetivo el enemigo que recibe el ataque
    * @param elemento el elemento mágico seleccionado
    */
    public void accionMagica(Enemigo objetivo, Elemento elemento) {
        int costo = busterSword.calcularCostoMP(elemento);

        if (stats.getMpActual() < costo) {
            System.out.println("MP insuficiente. Costo: " + costo + " | MP actual: " + stats.getMpActual());
            return;
        }

        stats.setMpActual(stats.getMpActual() - costo);
        int dmgBase = busterSword.calcularDanoMagico(elemento);

        if (elemento == Elemento.CURA) {
            int curacion = Math.min(dmgBase, stats.getHpMaximo() - stats.getHpActual());
            stats.setHpActual(stats.getHpActual() + curacion);
            System.out.println("Cloud se curó " + curacion + " HP.");
            return;
        }

        double multiplicador = 1.0;
        if (objetivo instanceof Vulnerable) {
            multiplicador = ((Vulnerable) objetivo).evaluarDebilidad(elemento);
        }
        int dmgFinal = (int)(dmgBase * multiplicador);
        objetivo.getStats().recibirDMG(dmgFinal);
        limiteActual += dmgFinal / 5;
        if (limiteActual > 100) limiteActual = 100;
        System.out.println("Cloud lanzó " + elemento + " a " + objetivo.getNombre() + " por " + dmgFinal + " de daño. (x" + multiplicador + ")");

        if (objetivo.getStats().getHpActual() <= 0) {
            System.out.println(objetivo.getNombre() + " fue derrotado.");
            objetivo.giveXpRecompensa(this);
            objetivo.giveChatarraRecompensa(this);
        }
    }

    /**
     * Ejecuta el ataque Límite de Cloud contra un enemigo.
    * Ignora multiplicadores de debilidad y resetea la barra a 0.
    * Si el objetivo es Sephiroth resetea su contadorSuperNova.
    * @param objetivo el enemigo que recibe el ataque
    */
    public void accionLimite(Enemigo objetivo) {
        int dmg = busterSword.calcularDanoLimite();
        objetivo.getStats().recibirDMG(dmg);
        limiteActual = 0;
        objetivo.resetearContador();
        System.out.println("¡Cloud usó Ataque Límite sobre " + objetivo.getNombre() + " por " + dmg + " de daño!");
        if (objetivo.getStats().getHpActual() <= 0) {
            System.out.println(objetivo.getNombre() + " fue derrotado.");
            objetivo.giveXpRecompensa(this);
        }
    }

    public int getNivel() { return nivel; }
    public int getXpActual() { return xpActual; }
    public int getChatarra() { return chatarra; }
    public int getLimiteActual() { return limiteActual; }
    public Estadisticas getStats() { return stats; }
    public List<Materia> getMochila() { return mochila; }
    public Jugador.Arma getBusterSword() { return busterSword; }
    
    public void setNivel(int nivel) { this.nivel = nivel; }
    public void setChatarra(int chatarra) { this.chatarra = chatarra; }
    public void setLimiteActual(int limiteActual) { this.limiteActual = limiteActual; }
    public void setStats(Estadisticas stats) { this.stats = stats; }

    
}
