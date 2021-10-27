import java.util.*;

public class lec1 {

    public static class Edge{
        int v = 0, w = 0;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        Edge() {

        }
    }

    public static void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {
        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w));
    }

    public static void display(ArrayList<Edge>[] graph) {
        int N = graph.length;
        for(int i = 0; i < N; i++) {
            System.out.print(i + " - ");
            for(Edge e : graph[i]) {
                System.out.print(e.v + "@" + e.w + ", ");
            }
            System.out.println();
        }
    }

    public static void createGraph() {
        int N = 7;
        ArrayList<Edge>[] graph = new ArrayList[N];
        for(int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        addEdge(graph, 0, 1, 10);
        addEdge(graph, 1, 2, 10);
        addEdge(graph, 0, 3, 10);
        addEdge(graph, 3, 2, 40);
        addEdge(graph, 3, 4, 3);
        addEdge(graph, 4, 5, 2);
        addEdge(graph, 5, 6, 3);
        addEdge(graph, 4, 6, 8);

        display(graph);
    }

    public static void main(String[] args) {
        createGraph();
        //display(graph);
    }
}