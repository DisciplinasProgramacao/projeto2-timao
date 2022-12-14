import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class GrafoMutavel extends Grafo{

    /**
     * Construtor. Cria um grafo com nome dado por parametro
     * @param  nome nome do grafo
     */
    GrafoMutavel(String nome){
        super(nome);
    }

    /**
     * Carrega um arquivo txt e cria um grafo com base nos dados lidos.
     * @param nomeArquivo nome do arquivo txt
     */
    public void carregar(String nomeArquivo) throws FileNotFoundException {
        int i;
        int origem, destino;
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
            addAresta(origem,destino);
        }
    }

    /**
     * Salva um grafo em um arquivo txt
     * @param nomeArquivo nome do arquivo txt
     */

    public void salvar(String nomeArquivo) throws IOException {
        int i,j;
        FileWriter myWriter = new FileWriter(nomeArquivo);
        myWriter.write(Integer.toString(ordem()));
        myWriter.write("\n");
        Vertice[] arrayvertices = this.vertices.allElements(new Vertice[this.vertices.size()]);
        for(i=0;i<=this.vertices.size();i++){
            for(j=i+1;j<this.vertices.size();j++){
                if(existeAresta(arrayvertices[i].getId(),arrayvertices[j].getId())!=null){
                    myWriter.write(Integer.toString(arrayvertices[i].getId()));
                    myWriter.write(";");
                    myWriter.write(Integer.toString(arrayvertices[j].getId()));
                    myWriter.write("\n");
                }
            }
        }
        myWriter.close();

    }

    /**
     * Adiciona, se poss??vel, um v??rtice ao grafo.
     * @param id id do v??rtice
     * @return retorna TRUE se adicionado com sucesso e FALSE caso contr??rio.
     */
    public boolean addVertice(int id){
        Vertice novo = new Vertice(id);
        return this.vertices.add(id, novo);
    }

    /**
     * (M??todo abstrato) Adiciona uma aresta entre dois v??rtices do grafo.
     * @param origem V??rtice de origem
     * @param destino V??rtice de destino
     */
    public abstract boolean addAresta(int origem, int destino);

    /**
     * Deleta uma aresta entre dois v??rtices do grafo.
     * @param origem V??rtice de origem
     * @param destino V??rtice de destino
     */

    public boolean delAresta(int origem, int destino){
        boolean deletou = false;
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);
        if(saida!=null && chegada !=null){
            saida.getArestas().remove(destino);
            chegada.getArestas().remove(origem);
        }
        return deletou;
    }

    /**
     * Adiciona, se poss??vel, um v??rtice ao grafo.
     * @param id id do v??rtice
     * @return retorna TRUE se adicionado com sucesso e FALSE caso contr??rio.
     */
    public boolean delVertice(int id){
        boolean deletou = false;
        if(this.vertices.find(id)!=null){
            this.vertices.remove(id);
            deletou = true;
        }
        return deletou;
    }


}
