import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void menu(){
        System.out.println("\n----------Menu----------\n");
        System.out.println("1 - Inicializar a matriz");
        System.out.println("2 - Inserir valor na matriz");
        System.out.println("3 - Imprimir matriz");
        System.out.println("4 - Identificar menor caminho");
        System.out.println("5 - Identificar maior caminho");
        System.out.println("6 - Identificar caminhos possíveis");
        System.out.println("7 - Gerar grafo pronto");
        System.out.println("0 - Sair");
        System.out.print("Selecione uma opção: ");
    }

    static int menuSelecionarOrigem(Matriz matriz, Scanner scanner) {
        System.out.print("Digite o vértice de origem: ");
        int verticeOrigem = scanner.nextInt();
        if (verticeOrigem >= matriz.getOrdem()){
            throw new RuntimeException("Valor de origem fora dos limites da matriz, " +
                    "lembre-se que arrays começam no índice 0");
        }

        return verticeOrigem;
    }

    static int menuSelecionarDestino(Matriz matriz, Scanner scanner) {
        System.out.print("Digite o vértice de destino: ");
        int verticeDestino = scanner.nextInt();
        if (verticeDestino >= matriz.getOrdem()){
            throw new RuntimeException("Valor de destino fora dos limites da matriz, " +
                    "lembre-se que arrays começam no índice 0");
        }

        return verticeDestino;
    }


    public static void main(String[] args) {
        Matriz matriz = new Matriz();
        Grafo grafo = new Grafo();
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            menu();
            try {
                escolha = scanner.nextInt();

                switch (escolha) {
                    case 0:
                        System.out.println("\nSaindo do programa...");
                        break;
                    case 1:
                        System.out.print("\nDigite a quantidade de vértices do grafo: ");
                        try {
                            int qtdVertices = scanner.nextInt();
                            if (qtdVertices <= 0) {
                                System.out.println("O número deve ser positivo!");
                            } else {
                                matriz.inicializarMatriz(qtdVertices);
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Por favor, digite um número válido.");
                            scanner.next();
                        }
                        grafo.setHashMapAdjacencia(matriz.converterParaHashMap());
                        System.out.println();
                        break;
                    case 2:
                        try {
                            if (matriz.getOrdem() == 0){
                                throw new RuntimeException("A Matriz precisa ser inicializada.");
                            }

                            int verticeOrigem = menuSelecionarOrigem(matriz, scanner);
                            int verticeDestino = menuSelecionarDestino(matriz, scanner);

                            if (verticeOrigem == verticeDestino) {
                                throw new RuntimeException("Esse grafo não permite laços.");
                            }

                            System.out.print("\nDigite o valor que será inserido: ");
                            int valorInserido = scanner.nextInt();
                            if (valorInserido < 0){
                                throw new RuntimeException("O valor precisa ser um número positivo");
                            }

                            matriz.inserirValor(verticeOrigem, verticeDestino, valorInserido);
                        } catch (RuntimeException e){
                            System.out.println("\nErro: " + e.getMessage());
                        }
                        grafo.setHashMapAdjacencia(matriz.converterParaHashMap());
                        break;
                    case 3:
                        try {
                            if (matriz.getOrdem() == 0){
                                throw new RuntimeException("A Matriz precisa ser inicializada.");
                            }
                            matriz.imprimirMatriz();
                        } catch (RuntimeException e) {
                            System.out.println("\nErro: " + e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            if (matriz.getOrdem() == 0){
                                throw new RuntimeException("A Matriz precisa ser inicializada.");
                            }

                            int verticeOrigem = menuSelecionarOrigem(matriz, scanner);
                            int verticeDestino = menuSelecionarDestino(matriz, scanner);

                            List<Integer> menorCaminho = grafo.identificarMenorCaminho(verticeOrigem, verticeDestino);
                            System.out.println("\nO menor caminho entre " + verticeOrigem + " e " + verticeDestino + " é: " + menorCaminho);
                        } catch (RuntimeException e) {
                            System.out.println("\nErro: " + e.getMessage());
                        }
                        break;
                    case 5:
                        try {
                            if (matriz.getOrdem() == 0){
                                throw new RuntimeException("A Matriz precisa ser inicializada.");
                            }

                            int verticeOrigem = menuSelecionarOrigem(matriz, scanner);
                            int verticeDestino = menuSelecionarDestino(matriz, scanner);

                            grafo.identificarMaiorCaminho(verticeOrigem, verticeDestino);
                        } catch (RuntimeException e) {
                            System.out.println("\nErro: " + e.getMessage());
                        }
                        break;
                    case 6:
                        try {
                            if (matriz.getOrdem() == 0){
                                throw new RuntimeException("A Matriz precisa ser inicializada.");
                            }

                            int verticeOrigem = menuSelecionarOrigem(matriz, scanner);
                            int verticeDestino = menuSelecionarDestino(matriz, scanner);

                            grafo.identificarTodosCaminhos(verticeOrigem, verticeDestino);
                        } catch (RuntimeException e) {
                            System.out.println("\nErro: " + e.getMessage());
                        }
                        break;
                    case 7:
                        matriz.gerarMatrizPronta();
                        grafo.setHashMapAdjacencia(matriz.converterParaHashMap());
                        System.out.println("\nGrafo gerado com sucesso!");
                        break;
                    default:
                        System.out.println("\nOpção inválida");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Número digitado inválido.");
                scanner.next(); // limpa o buffer do scanner
                escolha = 0;   // força nova iteração
            }
        } while (escolha != 0);

        scanner.close();
    }
}
