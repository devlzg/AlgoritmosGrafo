import java.util.ArrayList;

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

    public void identificarTodosCaminhos(int origem, int destino) {
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
        System.out.println("Matriz: " + this.matriz);
    }

    public void identificarMenorCaminho(int origem, int destino) {
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
    }

    public void identificarMaiorCaminho(int origem, int destino) {
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
    }

    public int getOrdem() {
        return ordem;
    }

}
