import java.util.Map;

public class Grafo {
    private Map<Integer, Map<Integer, Integer>> hashMapAdjacencia;

    public void setHashMapAdjacencia(Map<Integer, Map<Integer, Integer>> hashMapAdjacencia) {
        this.hashMapAdjacencia = hashMapAdjacencia;
    }

    public void identificarTodosCaminhos(int origem, int destino) {
        System.out.println("Identificando todos os caminhos: ");
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
        System.out.println("Grafo: " + this.hashMapAdjacencia);
    }

    public void identificarMenorCaminho(int origem, int destino) {
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
    }

    public void identificarMaiorCaminho(int origem, int destino) {
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
    }

}
