import java.util.*;

public class Grafo {
    private Map<Integer, Map<Integer, Integer>> hashMapAdjacencia;

    public void setHashMapAdjacencia(Map<Integer, Map<Integer, Integer>> hashMapAdjacencia) {
        this.hashMapAdjacencia = hashMapAdjacencia;
    }

    private static class VerticeInfo {
        int vertice;
        int distancia;
        Integer predecessor; // aqui usamos Integer pq ele permite null

        // construtor da classe
        VerticeInfo(int vertice, int distancia, Integer predecessor) {
            this.vertice = vertice;
            this.distancia = distancia;
            this.predecessor = predecessor;
        }
    }

    public List<Integer> identificarMenorCaminho(int origem, int destino) {
        /*Algoritmo de Dijkstra, aqui não usamos recursividade*/
        // primeiro verifica se o grafo está vazio
        if (hashMapAdjacencia == null || hashMapAdjacencia.isEmpty()) {
            return Collections.emptyList();
        }

        // mapa que armazena as distancias e predecessores
        // armazena o melhor caminho conhecido pra cada vértice até agora
        Map<Integer, VerticeInfo> infoVertices = new HashMap<>();

        // fila de prioridade que ordena de forma crescente a partir da distancia de cada vertice até a origem
        PriorityQueue<VerticeInfo> filaPrioridade = new PriorityQueue<>(
                Comparator.comparingInt(v -> v.distancia));

        // inicializa todos os vertices com distancia infinita e predecessor nulo
        for (Integer vertice : hashMapAdjacencia.keySet()) {
            infoVertices.put(vertice, new VerticeInfo(vertice, Integer.MAX_VALUE, null));
        }

        // define a distancia da origem pra ela mesma como 0
        infoVertices.get(origem).distancia = 0;

        // adiciona a origem na fila de prioridade
        filaPrioridade.add(new VerticeInfo(origem, 0, null));

        // parte principal:
        while (!filaPrioridade.isEmpty()) {
            /* Pega e remove o vertice de maior prioridade
            * ou seja, o de menor distância*/
            VerticeInfo atual = filaPrioridade.poll();

            // caso cheguemos ao destino, ja interrompe o loop
            if (atual.vertice == destino) {
                break;
            }

            /* quando atualizamos a distância de um vértice, é inserida uma nova
            * entrada na fila, nao removemos a antiga aparição desse vértice,
            * então o mesmo vértice acaba aparecendo várias vezes na lista.
            * A verificação a seguir serve para ignorar essas versões antigas,
            * processando somente as versões do vértice com a menor distância
            * encontrada. Ela não é necessária pro funcionamento do algoritmo,
            * mas é uma otimização que evita trabalho desnecessário */
            if (atual.distancia > infoVertices.get(atual.vertice).distancia) {
                continue; // vai pra proxima iteração do loop while
            }

            // obtendo os vizinhos do vertice atual:
            Map<Integer, Integer> vizinhos = hashMapAdjacencia.getOrDefault(atual.vertice, new HashMap<>());

            // processando cada vizinho:
            for (Map.Entry<Integer, Integer> entrada : vizinhos.entrySet()) {
                int vizinho = entrada.getKey(); // id do vertice vizinho
                int peso = entrada.getValue(); // peso da aresta (atual -> vizinho)

                // calcula a nova distância da origem até o vizinho
                int novaDistancia = atual.distancia + peso;

                // pega a informação atual do vizinho:
                VerticeInfo infoVizinho = infoVertices.get(vizinho);

                // se for encontrado um caminho menor, atualizamos tudo
                if (novaDistancia < infoVizinho.distancia) {
                    infoVizinho.distancia = novaDistancia; // atualiza a distância mínima
                    infoVizinho.predecessor = atual.vertice; // atualiza o predecessor

                    // adiciona a versão atualizada desse vizinho na fila de prioridade
                    filaPrioridade.add(new VerticeInfo(vizinho, novaDistancia, atual.vertice));
                }
            }
        }
        // função auxiliar que volta o caminho a partir do destino e retorna
        // o caminho correto da origem -> destino
        return reconstruirCaminho(infoVertices, origem, destino);
    }

    private List<Integer> reconstruirCaminho(Map<Integer, VerticeInfo> infoVertices, int origem, int destino) {
        List<Integer> caminho = new ArrayList<>();

        // verifica se o destino está no alcance
        if (infoVertices.get(destino).distancia == Integer.MAX_VALUE) {
            return caminho; // retorna uma lista vazia
        }

        // começa do destino, vai voltando pelos predecessores e adicionando na lista
        Integer atual = destino;
        while (atual != null) { // para quando chega na origem
            caminho.add(atual);
            atual = infoVertices.get(atual).predecessor;
        }

        // inverte a lista pra ter a ordem origem -> destino
        Collections.reverse(caminho);

        if (!caminho.isEmpty() && caminho.getFirst() == origem) {
            return caminho;
        } else {
            return Collections.emptyList();
        }
    }

    public void identificarMaiorCaminho(int origem, int destino) {
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
    }

    public void identificarTodosCaminhos(int origem, int destino) {
        System.out.println("Identificando todos os caminhos: ");
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
        System.out.println("Grafo: " + this.hashMapAdjacencia);
    }

}
