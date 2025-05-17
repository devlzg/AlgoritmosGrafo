import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Matriz {
    private ArrayList<ArrayList<Integer>> matriz = new ArrayList<>();
    private int ordem = 0;

    public void inicializarMatriz(int qtdVertices) {

        if (qtdVertices <= 0){
            throw new IllegalArgumentException("Quantidade inválida.");
        }

        if (matriz == null) {
            matriz = new ArrayList<>();
        } else {
            matriz.clear();
        }
        this.ordem = qtdVertices;

        for (int linha = 0; linha < qtdVertices; linha++) {
            ArrayList<Integer> novaLista = new ArrayList<>(qtdVertices);

            for (int coluna = 0; coluna < qtdVertices; coluna++){
                novaLista.add(0);
            }
            matriz.add(novaLista);
        }
        System.out.println("\nMatriz inicializada com sucesso!");
    }

    public void inserirValor(int origem, int destino, int valor) {
        if (origem > this.ordem - 1 || origem < 0 || destino > this.ordem - 1 || destino < 0) {
            throw new RuntimeException("Vértice além dos limites da matriz");
        }

        if (valor < 0) {
            throw new RuntimeException("Valor inválido, precisa ser positivo");
        }

        if (this.ordem == 0) {
            throw new RuntimeException("A Matriz precisa ser inicializada.");
        }

        if (origem == destino) {
            throw new RuntimeException("Esse grafo não permite laços.");
        }
        matriz.get(origem).set(destino, valor);
    }

    public void imprimirMatriz(){
        System.out.println();
        for (int linha = 0; linha < this.ordem; linha++) {
                // printa os rótulos das colunas
                if (linha == 0) {
                    for (int i = 0; i < this.ordem; i++){
                        if (i == 0) {
                            System.out.print("   ");
                        }
                        if (i < 10) {
                            System.out.print(" " + i + " ");
                        } else {
                            System.out.print(i + " ");
                        }
                    }
                    System.out.println();
                }

            for (int coluna = 0; coluna < this.ordem; coluna++) {
                // printa os rótulos das linhas
                if (coluna == 0) {
                    if (linha < 10) {
                        System.out.print(linha + "  ");
                    } else {
                        System.out.print(linha + " ");
                    }
                }

                if (linha == coluna){
                    System.out.print("XX ");
                } else {
                    if (matriz.get(linha).get(coluna) < 10){
                        System.out.print("0" + matriz.get(linha).get(coluna) + " ");
                    } else {
                        System.out.print(matriz.get(linha).get(coluna) + " ");
                    }
                }
            }
            System.out.print("\n");
        }
    }

    public Map<Integer, Map<Integer, Integer>> converterParaHashMap() {
        // primeiro cria a estrutura que armazenará tudo
        Map<Integer, Map<Integer, Integer>> grafo = new HashMap<>(); 
        
        // percorre cada vértice de origem, e cria um hashmap que representa seus vizinhos
        for (int origem = 0; origem < this.ordem; origem++){
            // mapa de vizinhos pra cada vértice
            Map<Integer, Integer> vizinhos = new HashMap<>();
            
            // pega todas as conexões possíveis
            for (int destino = 0; destino < this.ordem; destino++){
                // pega o peso da aresta
                int peso = matriz.get(origem).get(destino);
                
                // adiciona nos vizinhos somente se houver conexão
                if (peso > 0) {
                    vizinhos.put(destino, peso);
                }
            }
            // so adiciona no grafo vertices que possuem conexões
            if (!vizinhos.isEmpty()) {
                grafo.put(origem, vizinhos);
            }
        }
        return grafo;
    }

    public void gerarMatrizPronta() {
        matriz = new ArrayList<>();

        // Linha 0 - A
        matriz.add(new ArrayList<>(Arrays.asList(0, 12, 4, 0, 0, 0)));

        // Linha 1 - B
        matriz.add(new ArrayList<>(Arrays.asList(0, 0, 10, 6, 0, 0)));

        // Linha 2 - C
        matriz.add(new ArrayList<>(Arrays.asList(0, 6, 0, 8, 2, 0)));

        // Linha 3 - D
        matriz.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 6)));

        // Linha 4 - E
        matriz.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 6)));

        // Linha 5 - F
        matriz.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0)));

        this.ordem = 6;

    }

    public int getOrdem() {
        return ordem;
    }

}
