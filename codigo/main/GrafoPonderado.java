import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GrafoPonderado extends GrafoMutavel{

    /**
     * Construtor. Cria um grafo com nome dado por parametro
     * @param  nome nome do grafo
     */

    GrafoPonderado(String nome){
        super(nome);
    }

    /**
     * Carrega um arquivo txt e cria um grafo com base nos dados lidos.
     * @param nomeArquivo nome do arquivo txt
     */

    public void carregar(String nomeArquivo) throws FileNotFoundException {
        int i;
        int origem, destino, peso;
        int numvertices;
        Scanner leitor = new Scanner(new File(nomeArquivo));
        String linha = leitor.nextLine();
        numvertices = Integer.parseInt(linha);
        for(i=1;i<=numvertices;i++){
            addVertice(i);
        }
        while(leitor.hasNextLine()){
            linha = leitor.nextLine();
            String [] detalhes = linha.split(";");
            origem = Integer.parseInt(detalhes[0]);
            destino = Integer.parseInt(detalhes[1]);
            peso = Integer.parseInt(detalhes[2]);
            addAresta(origem,destino,peso);
        }
    }

    /**
     * Adiciona uma aresta entre dois vértices do grafo com seu respectivo peso.
     * @param origem Vértice de origem
     * @param destino Vértice de destino
     * @return retorna TRUE caso aresta foi adicionada e FALSE caso contrário
     */

    public boolean addAresta(int origem, int destino, int peso) {
        boolean adicionou = false;
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);
        if(saida!=null && chegada !=null){
            saida.addAresta(destino,peso);
            chegada.addAresta(origem,peso);
            adicionou = true;
        }
        return adicionou;
    }

    @Override
    public boolean addAresta(int origem, int destino) {
        return addAresta(origem,destino,1);
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
        for(i=0;i< arrayvertices.length;i++){
            Vertice verticeAtual =  (this.existeVertice(arrayvertices[i].getId()));
            if(verticeAtual !=null){
                subgrafo.addVertice(verticeAtual.getId());
                for(Vertice candidato : this.vertices.allElements(new Vertice[this.vertices.size()])){
                    if(verticeAtual.existeAresta(candidato.getId())!=null && candidato.getId()!=verticeAtual.getId()){
                        if(subgrafo.existeAresta(verticeAtual.getId(), candidato.getId())==null){
                            subgrafo.addAresta(verticeAtual.getId(), candidato.getId(),verticeAtual.existeAresta(candidato.getId()).peso());
                        }
                    }
                }
            }
        }
        return subgrafo;
    }
}
