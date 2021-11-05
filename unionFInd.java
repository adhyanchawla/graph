import java.util.*;

public class unionFInd {

    public static class Edge {
        int v, w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        Edge() {

        }
    }

    static int[] par;
    static int[] size;

    //path compression increases searching in an array => amortised O(1)
    public static int findPar(int u) {
        if(par[u] == u) return u;

        return par[u] = findPar(par[u]);
    }

    //find the union
    public static void union(int p1, int p2) {
        if(size[p1] < size[p2]) {
            par[p1] = p2;
            size[p2] += size[p1];
        } else {
            par[p2] = p1;
            size[p1] += size[p2];
        }
    }

    //for graph implementation
    public static void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {
        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w));
    }
    
    //{{u1, v1, w1}, {u2, v2, w2}}
    public static void unionFind(int[][] edges) {
        int N = edges.length;
        ArrayList<Edge>[] graph = new ArrayList[N];

        for(int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        par = new int[N];
        size = new int[N];

        for(int i = 0; i < N; i++) {
            par[i] = i;
            size[i] = 1;
        }

        //travelling on neighbours
        for(int [] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];

            int p1 = findPar(u);
            int p2 = findPar(v); 

            if(p1 != p2) {
                union(u, v);
                addEdge(graph, u, v, w);
            }

        }
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
}
