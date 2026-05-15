package componentes;

/**
 * Almacena y gestiona las estadísticas de combate de un personaje.
 */
public class Estadisticas {
    private int hpActual;
    private int hpMaximo;
    private int mpActual;
    private int mpMaximo;
    private int fuerza;
    private int magia;

    /**
     * Crea un conjunto de estadísticas con valores iniciales.
     * @param hpMaximo puntos de vida máximos
     * @param mpMaximo puntos de magia máximos
     * @param fuerza stat de ataque físico
     * @param magia stat de ataque mágico
     */
    public Estadisticas (int hpMaximo, int mpMaximo, int fuerza, int magia){
        this.hpMaximo = hpMaximo;
        this.hpActual = hpMaximo;
        this.mpMaximo = mpMaximo;
        this.mpActual = mpMaximo;
        this.fuerza = fuerza;
        this.magia = magia;
    }

      /**
     * Reduce el HP actual del personaje en la cantidad indicada.
     * El HP nunca baja de 0.
     * @param valor cantidad de daño a recibir
     */
    public void recibirDMG(int valor) {
        hpActual -= valor;
        if (hpActual < 0) hpActual = 0;
    }

    public int getHpActual() {return hpActual; }
    public int getHpMaximo() { return hpMaximo; }
    public int getMpActual() { return mpActual; }
    public int getMpMaximo() { return mpMaximo; }
    public int getFuerza() { return fuerza; }
    public int getMagia() { return magia; }
    
    public void setHpActual(int hpActual) { this.hpActual = hpActual; }
    public void setHpMaximo(int hpMaximo) { this.hpMaximo = hpMaximo; }
    public void setMpActual(int mpActual) { this.mpActual = mpActual; }
    public void setMpMaximo(int mpMaximo) { this.mpMaximo = mpMaximo; }
    public void setFuerza(int fuerza) { this.fuerza = fuerza; }
    public void setMagia(int magia) { this.magia = magia; }


}
