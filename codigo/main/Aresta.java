

public class Aresta {

    private int peso;
    private int destino;
    private boolean visitada;

    /**
     * Construtor para arestas com ou sem peso
     * @param peso Peso da aresta
     * @param destino Vértice de destino
     */
    public Aresta(int peso, int destino){
        this.peso = peso;
        this.destino = destino;
        this.visitada = false;
    }

    /**
     * Método de acesso para o peso da aresta
     * @return the peso
     */
    public int peso() {
        return this.peso;
    }
   
    /**
     * Método de acesso para o destino da aresta
     * @return the destino
     */
    public int destino() {
        return this.destino;
    }

    /**
     * Visita uma aresta
     */

    public void visitar(){
        this.visitada = true;
    }

    /**
     * Limpa a visita de uma aresta
     */

    public void limparVisita(){
        this.visitada = false;
    }

    /**
     * Retorna se uma aresta foi visitada
     * @return retorna TRUE caso tenha sido visitada e FALSE caso contrário
     */

    public boolean visitada(){
        return this.visitada;
    }

    

}
