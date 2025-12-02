import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Graph<String> g = new Graph<>();
        Scanner sc = new Scanner(System.in);

        // === Leitura do arquivo ===
        try {
            File file = new File("entrada.txt");
            Scanner arq = new Scanner(file);

            boolean lendoVertices = false;
            boolean lendoArestas = false;

            while (arq.hasNextLine()) {
                String linha = arq.nextLine().trim();

                if (linha.equals("VERTICES")) {
                    lendoVertices = true;
                    lendoArestas = false;
                    continue;
                }

                if (linha.equals("ARESTAS")) {
                    lendoVertices = false;
                    lendoArestas = true;
                    continue;
                }

                if (lendoVertices && !linha.isEmpty()) {
                    g.addVertex(linha);
                }

                if (lendoArestas && !linha.isEmpty()) {
                    String[] p = linha.split(" ");
                    String o = p[0];
                    String d = p[1];
                    float peso = Float.parseFloat(p[2]);
                    g.addEdge(o, d, peso);
                }
            }

            arq.close();

        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }

        // === Menu do usuário ===
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Caminho mínimo (Dijkstra)");
            System.out.println("2 - Árvore geradora mínima (Kruskal)");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int op = sc.nextInt();

            switch (op) {

                case 1:
                    System.out.print("Vértice inicial: ");
                    String ini = sc.next();
                    Map<Vertex<String>, Float> dist = g.dijkstra(ini);

                    System.out.println("\nMenor distância a partir de " + ini + ":");
                    for (var v : dist.keySet()) {
                        System.out.println(v + ": " + dist.get(v));
                    }
                    break;

                case 2:
                    System.out.println("\nÁrvore Geradora Mínima (Kruskal):");
                    List<String> mst = g.kruskal();
                    mst.forEach(System.out::println);
                    break;

                case 0:
                    System.out.println("Encerrando.");
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
