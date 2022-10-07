public class GrafoCompleto extends Grafo{
    private int ordem;

    /**
     * Construtor. Cria um grafo completo com o nome e a ordem recebida por parametro.
     * @param nome nome do grafo
     * @param ordem ordem do grafo
     */
    GrafoCompleto(String nome,int ordem){
        super(nome);
        this.ordem = ordem;
        int i,j;
        for(i=1;i<=ordem;i++) {
            Vertice novo = new Vertice(i);
            this.vertices.add(i, novo);
        }
        for(i=1;i<=ordem;i++){
            for(j=1;j<=ordem;j++){
                if(i!=j){
                    this.vertices.find(i).addAresta(j);
                }
                continue;
            }
        }
    }
    /**
     * Verifica se este é um grafo completo.
     * @return TRUE para grafo completo, FALSE caso contrário
     */
    public boolean completo(){
        int i;
        Vertice[] arrayvertices = this.vertices.allElements(new Vertice[this.vertices.size()]);
        for(i=0;i<arrayvertices.length;i++){
            if (arrayvertices[i].grau() == (this.ordem - 1)) {
            } else {
                return false;
            }
        }
        return true;
    }
    /**
     * Verifica se existe uma aresta entre o vértice A e B, identificados pelos seus IDs
     * @param verticeA id do vértice A
     * @param verticeB id do vértice B
     * @return retorna a aresta. Caso não exista, retorna NULL
     */
    public Aresta existeAresta(int verticeA, int verticeB){
        if(this.vertices.find(verticeA).existeAresta(verticeB)!=null){
            return this.vertices.find(verticeA).existeAresta(verticeB);
        }
        else {
            return null;
        }
    }
    /**
     * Verifica se o vértice existe no grafo pelo seu ID
     * @param idVertice id do vértice
     * @return retorna o vértice. Caso não exista, retorna NULL
     */
    public Vertice existeVertice(int idVertice){
        if(this.vertices.find(idVertice)!=null){
            return this.vertices.find(idVertice);
        }
        else {
            return null;
        }
    }
    /**
     * Verifica se este é um grafo euleriano.
     * @return TRUE para grafo euleriano, FALSE caso contrário
     */
    public boolean euleriano(){
        int i;
        for(i=1;i<=ordem;i++){
            if(this.vertices.find(i).grau()%2!=0){
                return false;
            }
        }
        return true;
    }

    /**
     * Método que cria um subgrafo à partir de umas Lista de vértices.
     * @param vertices lista de vértices.
     * @return retorna um Grafo.
     */
    @Override
    public GrafoCompleto subGrafo(Lista<Vertice> vertices){
        GrafoCompleto subgrafo = new GrafoCompleto("Subgrafo de "+super.nome, vertices.size());
        return subgrafo;
    }

}
