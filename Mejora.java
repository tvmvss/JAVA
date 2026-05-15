package componentes;

/**
 * Representa una mejora que Cloud puede comprar en la tienda de Sector 7.
 * Al aplicarse, aumenta permanentemente una estadística de Cloud.
 */
public class Mejora {
    private String nombre;
    private int costoChatarra;
    private TipoStat statAfectado;
    private int valorBono;
    
    /**
     * Crea una mejora disponible en la tienda.
     * @param nombre nombre de la mejora
     * @param costoChatarra precio en chatarra
     * @param statAfectado estadística que aumenta al comprarla
     * @param valorBono cantidad que aumenta la estadística
     */
    public Mejora(String nombre, int costoChatarra, TipoStat statAfectado, int valorBono) {
        this.nombre = nombre;
        this.costoChatarra = costoChatarra;
        this.statAfectado = statAfectado;
        this.valorBono = valorBono;
    }

    public String getNombre() { return nombre; }
    public int getCostoChatarra() { return costoChatarra; }
    public TipoStat getStatAfectado() { return statAfectado; }
    public int getValorBono() { return valorBono; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCostoChatarra(int costoChatarra) { this.costoChatarra = costoChatarra; }
    public void setStatAfectado(TipoStat statAfectado) { this.statAfectado = statAfectado; }
    public void setValorBono(int valorBono) { this.valorBono = valorBono; }
}
