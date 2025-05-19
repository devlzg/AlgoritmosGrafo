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

    private List<Integer> reconstruirCaminho(Map<Integer, VerticeInfo> infoVertices, int origem, int destino) {
        LinkedList<Integer> caminho = new LinkedList<>();

        // Se o destino não foi alcançado ou não existe, retorna lista vazia
        if (!infoVertices.containsKey(destino) || infoVertices.get(destino).distancia == Integer.MAX_VALUE) {
            return caminho;
        }

        // Reconstrói o caminho do destino até a origem
        Integer atual = destino;
        while (atual != null) {
            caminho.addFirst(atual); // Insere no início para manter a ordem [origem -> ... -> destino]
            atual = infoVertices.get(atual).predecessor;
        }

        return caminho;
    }

    public record ResultadoDijkstra(List<Integer> caminho, int somaPesos) {
    }

    public ResultadoDijkstra identificarMenorCaminho(int origem, int destino) {
        /* Verifica se o grafo está vazio */
        if (hashMapAdjacencia == null || hashMapAdjacencia.isEmpty()) {
            return new ResultadoDijkstra(Collections.emptyList(), 0);
        }

        /* Estruturas para Dijkstra */
        Map<Integer, VerticeInfo> infoVertices = new HashMap<>();
        PriorityQueue<VerticeInfo> filaPrioridade = new PriorityQueue<>(
                Comparator.comparingInt(v -> v.distancia));

        /* Inicialização */
        for (Integer vertice : hashMapAdjacencia.keySet()) {
            infoVertices.put(vertice, new VerticeInfo(vertice, Integer.MAX_VALUE, null));
        }
        infoVertices.get(origem).distancia = 0;
        filaPrioridade.add(new VerticeInfo(origem, 0, null));

        /* Processamento principal do Dijkstra */
        while (!filaPrioridade.isEmpty()) {
            VerticeInfo atual = filaPrioridade.poll();

            if (atual.vertice == destino) {
                break;
            }

            if (atual.distancia > infoVertices.get(atual.vertice).distancia) {
                continue;
            }

            Map<Integer, Integer> vizinhos = hashMapAdjacencia.getOrDefault(atual.vertice, new HashMap<>());

            for (Map.Entry<Integer, Integer> entrada : vizinhos.entrySet()) {
                int vizinho = entrada.getKey();
                int peso = entrada.getValue();
                int novaDistancia = atual.distancia + peso;

                VerticeInfo infoVizinho = infoVertices.get(vizinho);

                if (novaDistancia < infoVizinho.distancia) {
                    infoVizinho.distancia = novaDistancia;
                    infoVizinho.predecessor = atual.vertice;
                    filaPrioridade.add(new VerticeInfo(vizinho, novaDistancia, atual.vertice));
                }
            }
        }

        /* Reconstrução do caminho e cálculo da soma dos pesos */
        List<Integer> caminho = reconstruirCaminho(infoVertices, origem, destino);
        int somaPesos = infoVertices.get(destino).distancia; // A distância total é a soma dos pesos

        /* Se não há caminho, retorna lista vazia e soma zero */
        if (caminho.isEmpty()) {
            return new ResultadoDijkstra(Collections.emptyList(), 0);
        }

        return new ResultadoDijkstra(caminho, somaPesos);
    }

    public record ResultadoMaiorCaminho(List<Integer> caminho, int somaPesos) {
    }

    public ResultadoMaiorCaminho identificarMaiorCaminho(int origem, int destino) {
        if (hashMapAdjacencia == null || hashMapAdjacencia.isEmpty()) {
            return new ResultadoMaiorCaminho(Collections.emptyList(), 0);
        }

        // Estruturas para armazenar o melhor caminho encontrado
        List<Integer> melhorCaminho = new ArrayList<>();
        int[] maiorSoma = { Integer.MIN_VALUE };

        // Conjunto para rastrear vértices visitados no caminho atual
        Set<Integer> visitados = new HashSet<>();

        // Lista para armazenar o caminho atual durante a DFS
        List<Integer> caminhoAtual = new ArrayList<>();

        // Chamada inicial da DFS
        dfsMaiorCaminho(origem, destino, visitados, caminhoAtual, 0, melhorCaminho, maiorSoma);

        // Se nenhum caminho válido foi encontrado, retorna vazio
        if (maiorSoma[0] == Integer.MIN_VALUE) {
            return new ResultadoMaiorCaminho(Collections.emptyList(), 0);
        }

        return new ResultadoMaiorCaminho(melhorCaminho, maiorSoma[0]);
    }

    private void dfsMaiorCaminho(
            int atual,
            int destino,
            Set<Integer> visitados,
            List<Integer> caminhoAtual,
            int somaAtual,
            List<Integer> melhorCaminho,
            int[] maiorSoma) {

        // Marca o vértice atual como visitado e adiciona ao caminho
        visitados.add(atual);
        caminhoAtual.add(atual);

        // Se chegamos ao destino, verifica se é o melhor caminho até agora
        if (atual == destino) {
            if (somaAtual > maiorSoma[0]) {
                maiorSoma[0] = somaAtual;
                melhorCaminho.clear();
                melhorCaminho.addAll(new ArrayList<>(caminhoAtual));
            }
        } else {
            // Explora todos os vizinhos não visitados
            Map<Integer, Integer> vizinhos = hashMapAdjacencia.getOrDefault(atual, new HashMap<>());
            for (Map.Entry<Integer, Integer> entrada : vizinhos.entrySet()) {
                int vizinho = entrada.getKey();
                int peso = entrada.getValue();

                if (!visitados.contains(vizinho)) {
                    dfsMaiorCaminho(vizinho, destino, visitados, caminhoAtual, somaAtual + peso, melhorCaminho, maiorSoma);
                }
            }
        }

        // Backtrack: remove o vértice atual do caminho e desmarca como visitado
        visitados.remove(atual);
        caminhoAtual.removeLast();
    }
}
