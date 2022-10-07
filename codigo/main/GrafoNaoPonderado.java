public class GrafoNaoPonderado extends GrafoMutavel {


    /**
     * Construtor. Cria um grafo com nome dado por parametro
     * @param  nome nome do grafo
     */
    GrafoNaoPonderado(String nome){
        super(nome);
    }

    /**
     * Adiciona uma aresta entre dois vértices do grafo.
     * @param origem Vértice de origem
     * @param destino Vértice de destino
     * @return retorna TRUE caso aresta foi adicionada e FALSE caso contrário
     */

    @Override
    public boolean addAresta(int origem, int destino){
        boolean adicionou = false;
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);
        if(saida!=null && chegada !=null){
            saida.addAresta(destino);
            chegada.addAresta(origem);
            adicionou = true;
        }
        return adicionou;
    }

    /**
     * Método que cria um subgrafo à partir de umas Lista de vértices.
     * @param vertices lista de vértices.
     * @return retorna um Grafo.
     */
    @Override
    public Grafo subGrafo(Lista<Vertice> vertices){
        GrafoPonderado subgrafo = new GrafoPonderado("Subgrafo de "+this.nome);
        Vertice[] arrayvertices = vertices.allElements(new Vertice[vertices.size()]);
        int i;
        for(i=0;i<arrayvertices.length;i++){
            Vertice verticeAtual =  (this.existeVertice(arrayvertices[i].getId()));
            if(verticeAtual !=null){
                subgrafo.addVertice(verticeAtual.getId());
                for(Vertice candidato : this.vertices.allElements(new Vertice[this.vertices.size()])){
                    if(verticeAtual.existeAresta(candidato.getId())!=null && candidato.getId()!=verticeAtual.getId()){
                        if(subgrafo.existeAresta(verticeAtual.getId(), candidato.getId())==null){
                            subgrafo.addAresta(verticeAtual.getId(), candidato.getId());
                        }
                    }
                }
            }
        }
        return subgrafo;
    }

}
