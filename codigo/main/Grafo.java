/** 
 * MIT License
 *
 * Copyright(c) 2021 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

/**
 * Classe básica para um Grafo simples
 */
public abstract class Grafo {
    public final String nome;
    ABB<Vertice> vertices;

    /**
     * Construtor. Cria um grafo com nome dado por parametro
     * @param  nome nome do grafo
     */
    public Grafo(String nome){
        this.nome = nome;
        this.vertices = new ABB<>();
    }

    /**
     * Verifica se o vértice existe no grafo pelo seu ID
     * @param idVertice id do vértice
     * @return retorna o vértice. Caso não exista, retorna NULL
     */

    public Vertice existeVertice(int idVertice){
        return this.vertices.find(idVertice);
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
       return null;
    }
    
    /**
     * Verifica se este é um grafo completo. 
     * @return TRUE para grafo completo, FALSE caso contrário
     */
    public boolean completo(){
        int i;
        Vertice[] arrayvertices = this.vertices.allElements(new Vertice[this.vertices.size()]);
        for(i=0;i<arrayvertices.length;i++){
            if (arrayvertices[i].grau() == (ordem() - 1)) {
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se este é um grafo euleriano.
     * @return TRUE para grafo euleriano, FALSE caso contrário
     */
    public boolean euleriano(){
        int i;
        Vertice[] arrayvertices = this.vertices.allElements(new Vertice[this.vertices.size()]);
        for(Vertice vertice: arrayvertices){
            if(vertice.grau()%2!=0){
                return false;
            }
        }
        int tamanho=buscaProfundidade(arrayvertices[0].getId()).size();
        if(tamanho!=ordem()){
            return false;
        }
        return true;
    }

    /**
     * Realiza a busca em profundidade dentro de um grafo. a raiz é dada pelo ID do vértice.
     * @param id id do vértice raiz
     * @return retorna uma lista da ordem dos vértices visitados durante a busca.
     */

    public ArrayList<Integer> buscaProfundidade(int id){
        ArrayList<Integer> arrayid = new ArrayList<Integer>();
        arrayid = buscaProfundidade(id,arrayid);
        for(Vertice vertice:this.vertices.allElements(new Vertice[this.vertices.size()])){
            vertice.limparVisita();
            for(Aresta aresta: vertice.getArestas().allElements(new Aresta[vertice.getArestas().size()])){
                aresta.limparVisita();
            }
        }
        return arrayid;
    }

    private ArrayList<Integer> buscaProfundidade(int id, ArrayList<Integer> arrayid){
        if(this.vertices.find(id).visitado()!=true){
            this.vertices.find(id).visitar();
            arrayid.add(id);
        }
        Aresta[] arrayarestas = this.vertices.find(id).getArestas().allElements(new Aresta[ this.vertices.find(id).getArestas().size()]);
        for(Aresta aresta : arrayarestas){
            if(aresta.visitada()!=true){
                aresta.visitar();
                this.vertices.find(aresta.destino()).getArestas().find(id).visitar();
                buscaProfundidade(aresta.destino(),arrayid);
            }
        }
        return arrayid;
    }

    /**
     * Método que retorna um caminho entre dois vértices A e B dados pelos seus IDs.
     * @param origem id do vértice inicial
     * @param destino id do vértice final
     * @return retorna uma lista do caminho entre os vértices.
     */

    public ArrayList<Integer> caminhoEntreDoisVertices(int origem,int destino){
        ArrayList<Integer> arrayid = new ArrayList<Integer>();
        arrayid = caminhoEntreDoisVertices(origem,origem,destino,arrayid);
        for(Vertice vertice:this.vertices.allElements(new Vertice[this.vertices.size()])){
            vertice.limparVisita();
            for(Aresta aresta: vertice.getArestas().allElements(new Aresta[vertice.getArestas().size()])){
                aresta.limparVisita();
            }
        }
        Collections.reverse(arrayid);
        return arrayid;
    }

    private ArrayList<Integer> caminhoEntreDoisVertices(int id, int origem, int destino, ArrayList<Integer> arrayid){
        if(this.vertices.find(id).visitado()!=true){
            this.vertices.find(id).visitar();
        }
        Aresta[] arrayarestas = this.vertices.find(id).getArestas().allElements(new Aresta[ this.vertices.find(id).getArestas().size()]);
        for(Aresta aresta : arrayarestas){
            if(id==destino){
                arrayid.add(id);
                break;
            }
            else{
                if(aresta.visitada()!=true){
                    aresta.visitar();
                    this.vertices.find(aresta.destino()).getArestas().find(id).visitar();
                    caminhoEntreDoisVertices(aresta.destino(),origem,destino,arrayid);
                    if(arrayid.size()>0 && id!=destino && this.vertices.find(id).visitado()==true){
                        arrayid.add(id);
                        if(id!=origem){
                            break;
                        }
                        if(id==origem){
                            break;
                        }
                    }
                }
            }
        }
        return arrayid;
    }

    /**
     * Método abstrato que cria um subgrafo à partir de umas Lista de vértices.
     * @param vertices lista de vértices.
     * @return retorna um Grafo.
     */

    public abstract Grafo subGrafo(Lista<Vertice> vertices);

    /**
     * Calcula o tamanho do grafo
     * @return retorna o tamanho do grafo
     */
    
    public int tamanho(){
        int i;
        int tamanho = 0;
        Vertice[] arrayvertices = this.vertices.allElements(new Vertice[this.vertices.size()]); // cria um array dos vertices
        for(i=0;i<arrayvertices.length;i++){     //soma o grau dos vertices
            tamanho += arrayvertices[i].grau();
        }
        tamanho/=2;        //soma dos graus dividido 2 é o numero de arestas
        tamanho+=ordem();  //soma arestas + a ordem do grafo
        return tamanho;
    }

    /**
     * Calcula a ordem do grafo
     * @return retorna a ordem do grafo
     */

    public int ordem(){
        return this.vertices.size();
    }


}

