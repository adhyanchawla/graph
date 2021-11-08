import java.util.*;

public class algo {
    
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

    //O(2E)
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

    static int[] par, size;
    public static int findPar(int u) {
        return par[u] == u ? u : findPar(par[u]);
    }

    public static void union(int p1, int p2) {
        if(size[p1] > size[p2]) {
            par[p2] = p1;
            size[p1] += size[p2]; 
        } else {
            par[p1] = p2;
            size[p2] += size[p1];
        }
    }

    public static void unionFind(int[][] edges, ArrayList<Edge>[] graph) {
        int n = edges.length;
        par = new int[n];
        size = new int[n];

        for(int i = 0; i < n; i++) {
            par[i] = i;
            size[i] = 1;
        }

        for(int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];

            int p1 = findPar(u);
            int p2 = findPar(v);

            if(p1 != p2) {
                union(p1, p2);
                addEdge(graph, u, v, w);
            }
        }

    }

    public static void kruskalAlgo(int[][] edges) {
        //write heap sort if interviewer isnt satisfied
        Arrays.sort(edges, (a, b) ->{
            return a[2] - b[2];
        });

        int n = edges.length;
        ArrayList<Edge>[] graph = new ArrayList[n];

        for(int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        unionFind(edges, graph);
        display(graph);
    }

    public static void main(String[] args) {
        int[][] edges = {{0, 1, 4}, {0, 7, 8}, {1, 2, 8}, {1, 7, 11}, {7, 8, 7}, {2, 8, 2}, {6, 8, 6}, {7, 6, 1}, {6, 5, 2}, {2, 3, 7}, {2, 5, 4}, {3, 5, 14}, {3, 4, 9}, {4, 5, 10}};
        kruskalAlgo(edges);
    }

    static int[]low, disc;
    static boolean[] vis, articulation;
    static int time = 0, rootCalls = 0;

    public static void dfs(ArrayList<Edge>[] graph, int src, int par) {
        low[src] = disc[src] = time++;
        vis[src] = true;

        for(Edge e : graph[src]) {
            if(!vis[e.v]) {
                if(par == -1) 
                rootCalls++;    
                
                dfs(graph, e.v, src);
                if(disc[src] <= low[e.v]) {
                    articulation[src] = true;
                }

                if(disc[src] < low[e.v]) {
                    System.out.println("Articulation Edge: " + src + " -> " +  e.v);
                }
                    
                low[src] = Math.min(low[src], low[e.v]);
            } else if(e.v != par) {
                low[src] = Math.min(low[src], disc[e.v]);
            }
        }
    }

    public static void articulationPointsAndBridges(ArrayList<Edge>[] graph) {
        int n = graph.length;
        vis = new boolean[n];
        articulation = new boolean[n];
        low = new int[n];
        disc = new int[n];
        time = 0;
        rootCalls = 0;

        for(int i = 0; i < n; i++) {
            dfs(graph, 0, -1);
        }
    }

}
