---

# Relatório Técnico: Implementação de Biblioteca de Grafos (GraphTPA)

## 1. Estratégia de Representação de Grafos

Para a representação computacional do grafo, foi utilizada a estratégia de **Lista de Adjacências**. No entanto, em vez de um vetor simples de listas encadeadas (comum em C), utilizou-se a estrutura `HashMap` do Java para permitir flexibilidade nos tipos de dados dos vértices (Genéricos `<T>`).

Nesta estrutura, cada vértice é mapeado para uma lista de arestas que partem dele.

**Estrutura de dados principal:**
```java
// No arquivo Graph.java
private Map<Vertex<T>, List<Edge<T>>> adj;
```

**Classe Edge (Aresta):**
A classe `Edge` foi modelada para armazenar o vértice de destino e o peso. Para algoritmos específicos como Kruskal, a classe foi refatorada para armazenar também a origem.

```java
// Trecho de Edge.java refatorado
public class Edge<T> {
    Vertex<T> origem;  // Necessário para Kruskal
    Vertex<T> destino;
    float peso;

    // Construtor utilizado na Lista de Adjacência
    public Edge(Vertex<T> destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }
    
    // ...
}
```

**Justificativa:** A Lista de Adjacências é eficiente em termos de espaço para grafos esparsos ($O(V + E)$), o que é comum na maioria das aplicações práticas, ao contrário da Matriz de Adjacências que ocuparia $O(V^2)$ sempre.

---

## 2. Algoritmos Clássicos Implementados

Abaixo estão os algoritmos implementados no projeto, juntamente com a análise de complexidade baseada nas estruturas de dados escolhidas (HashMap e PriorityQueue).

### 2.1. Busca em Largura (BFS)
O algoritmo BFS (*Breadth-First Search*) foi implementado para percorrer o grafo nível por nível a partir de um vértice inicial.

*   **Implementação:** Utiliza uma `Queue` (Fila) para gerenciar os nós a visitar e um `Set` ou vetor de booleanos para marcar os visitados.
*   **Complexidade de Tempo:** $O(V + E)$, onde $V$ é o número de vértices e $E$ o número de arestas. Cada vértice e cada aresta são visitados no máximo uma vez.

### 2.2. Algoritmo de Dijkstra
Utilizado para encontrar o caminho mais curto de um vértice de origem para todos os outros vértices em um grafo com pesos não negativos.

*   **Implementação:**
    *   Utiliza um mapa `dist` para armazenar a menor distância conhecida.
    *   Utiliza uma `PriorityQueue` (Min-Heap) para selecionar sempre o vértice com a menor distância acumulada processar.
    
    ```java
    // Trecho ilustrativo
    public Map<Vertex<T>, Float> dijkstra(T inicio) {
        // ... inicialização ...
        PriorityQueue<Vertex<T>> pq = new PriorityQueue<>(...);
        // ... relaxamento das arestas ...
    }
    ```

*   **Complexidade de Tempo:** $O(E \log V)$. Devido ao uso da *Priority Queue* binária, cada extração do mínimo custa $\log V$ e cada relaxamento de aresta pode atualizar a fila.

### 2.3. Algoritmo de Kruskal
Utilizado para encontrar a Árvore Geradora Mínima (MST - Minimum Spanning Tree), ou seja, conectar todos os vértices com o menor custo total possível, sem ciclos.

*   **Implementação:**
    1.  Coleta todas as arestas do grafo em uma lista única.
    2.  Ordena as arestas por peso crescente.
    3.  Itera sobre as arestas e utiliza a estrutura de dados **Union-Find** para verificar se a adição da aresta formaria um ciclo.
    
    ```java
    // Trecho ilustrativo
    edges.sort(Comparator.comparing(e -> e.peso));
    UnionFind<Vertex<T>> uf = new UnionFind<>();
    // ... union e find ...
    ```

*   **Complexidade de Tempo:** $O(E \log E)$ ou $O(E \log V)$. O passo dominante é a ordenação das arestas. As operações de Union-Find, com compressão de caminho, são quase lineares ($O(\alpha(V))$), sendo desprezíveis comparadas à ordenação.

---

## 3. Problema Prático e Manual do Sistema

### Problema Resolvido
O aplicativo propõe resolver problemas de **otimização de rotas e conectividade**. 
1.  **Caminho Mínimo (Dijkstra):** Pode ser aplicado para sistemas de GPS ou redes de computadores, determinando a rota mais rápida ou barata entre dois pontos.
2.  **Conectividade de Custo Mínimo (Kruskal):** Pode ser aplicado para planejamento de infraestrutura (ex: cabos de fibra óptica ou tubulações de água), garantindo que todos os pontos sejam conectados gastando o mínimo de material.

### Manual de Uso

**1. Instalação e Execução:**
*   **Requisitos:** Java Development Kit (JDK) 11 ou superior.
*   **Como obter:** Clone o repositório ou baixe os arquivos fonte (`src`).
*   **Compilação e Execução:**
    ```bash
    javac Main.java
    java Main
    ```

**2. Utilizando a Biblioteca (Exemplo no `Main.java`):**

Para utilizar o sistema, você deve instanciar o grafo, adicionar vértices e arestas, e chamar os métodos.

```java
// 1. Criar o grafo
Graph<String> grafo = new Graph<>();

// 2. Adicionar vértices
grafo.adicionarVertice("A");
grafo.adicionarVertice("B");
grafo.adicionarVertice("C");

// 3. Adicionar arestas com peso (Origem, Destino, Peso)
grafo.adicionarAresta("A", "B", 1.2f);
grafo.adicionarAresta("A", "C", 2.5f);

// 4. Rodar Algoritmos
// Dijkstra a partir de A
var resultadosDijkstra = grafo.dijkstra("A"); 
System.out.println(resultadosDijkstra);

// Kruskal (MST)
var mst = grafo.kruskal();
System.out.println(mst);
```

---

## 4. Relato sobre o Uso de Ferramentas de IA/LLM

Durante o desenvolvimento deste trabalho, foram utilizadas ferramentas de Inteligência Artificial baseadas em LLM (Large Language Models), especificamente para auxílio na depuração de código e compreensão de conceitos.

**Ferramenta Utilizada:** ChatGPT / Assistente de Código.

**Processo de Utilização:**
1.  **Depuração (Debugging):** Durante a implementação do algoritmo de Kruskal, ocorreram erros de compilação relacionados à classe `Edge`, especificamente a ausência do atributo de origem necessário para a lógica do algoritmo (erros nas linhas 118-121 do `Graph.java`). A IA foi utilizada para analisar o erro e sugerir a refatoração da classe `Edge` para incluir construtores adequados sem quebrar o restante do código.
2.  **Refatoração:** A IA auxiliou na criação de uma sobrecarga no construtor da classe `Edge` para suportar tanto a lista de adjacência (destino, peso) quanto a lista de arestas do Kruskal (origem, destino, peso).
3.  **Análise de Complexidade:** A ferramenta foi consultada para validar a complexidade teórica dos algoritmos implementados em Java, garantindo que a análise no relatório estivesse correta.

**Resultados Obtidos:**
O uso da IA acelerou significativamente a identificação de bugs lógicos que poderiam levar horas para serem encontrados manualmente. Além disso, proporcionou uma escrita de código mais limpa e aderente aos princípios de orientação a objetos. O código final tornou-se funcional e robusto, permitindo a execução correta da Árvore Geradora Mínima e do Caminho Mínimo.
