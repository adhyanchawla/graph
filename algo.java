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


    //articulation point and bridges

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


    //dijkstra
    public static class pair{
        int vtx;
        int par;
        int w;
        int wsf;

        pair(int vtx, int par, int w, int wsf) {
            this.vtx = vtx;
            this.par = par;
            this.w = w;
            this.wsf = wsf;
        }

        pair(int vtx, int wsf) {
            this.vtx = vtx;
            this.wsf = wsf;
        }
    }

    public static void dijkstra_01(ArrayList<Edge>[] graph) {
        int n = graph.length;
        ArrayList<Edge>[] ngraph = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            ngraph[i] = new ArrayList<>();
        }

        boolean[] vis = new boolean[n];

        int[] dist = new int[n];
        int[] parent = new int[n];

        PriorityQueue<pair> pq = new PriorityQueue<>((a, b) -> {
            return a.wsf - b.wsf;
        });

        pq.add(new pair(0, -1, 0, 0));

        while(pq.size() != 0) {
            int size = pq.size();

            while(size-->0) {

                pair rm = pq.remove();

                if(vis[rm.vtx]) {
                    continue;
                }

                vis[rm.vtx] = true;

                if(rm.par != -1)
                    addEdge(ngraph, rm.vtx, rm.par, rm.w);

                dist[rm.vtx] = rm.wsf;
                parent[rm.vtx] = rm.par;    

                for(Edge e : graph[rm.vtx]) {
                    if(!vis[rm.vtx]) {
                        pq.add(new pair(e.v, rm.vtx, e.w, rm.wsf + e.w));
                    }
                }
            }
        }
    }

    public static void dijkstra_02(ArrayList<Edge>[] graph) {
        int n = graph.length;
        ArrayList<Edge>[] ngraph = new ArrayList[n];

        for(int i = 0; i < n; i++) 
            ngraph[i] = new ArrayList<>();

        PriorityQueue<pair> pq = new PriorityQueue<>((a, b)-> {
            return a.wsf - b.wsf;
        });    

        int[] dist = new int[n];
        int[] par = new int[n];

        Arrays.fill(dist, (int)1e9);
        Arrays.fill(par, -1);

        pq.add(new pair(0, 0));
        dist[0] = 0;
        while(pq.size() != 0) {
            pair rm = pq.remove();

            if(rm.wsf >= dist[rm.vtx]) {
                continue;
            }

            for(Edge e : graph[rm.vtx]) {
                if(rm.wsf + e.w < dist[e.v]) {
                    dist[e.v] = rm.wsf + e.w;
                    par[e.v] = rm.vtx;
                    pq.add(new pair(e.v, e.w + rm.wsf));
                }
            }
        }
    }


    public static class primsPair{
        int vtx;
        int w;

        primsPair(int vtx, int w) {
            this.vtx = vtx;
            this.w = w;
        }
    }

    public static void prims(ArrayList<Edge>[] graph, int src) {
        int n = graph.length;
        int[] dis = new int[n];

        PriorityQueue<primsPair> pq = new PriorityQueue<>((a, b) -> {
            return a.w - b.w;
        });

        Arrays.fill(dis, (int)1e9);
        pq.add(new primsPair(src, 0));

        boolean[] vis = new boolean[n];
        while(pq.size() != 0) {
            primsPair rm = pq.remove();

            if(vis[rm.vtx]) {
                continue;
            }

            vis[rm.vtx] = true;

            for(Edge e : graph[rm.vtx]) {
                if(!vis[e.v] && e.w < dis[e.v]) {
                    dis[e.v] = e.w;
                    pq.add(new primsPair(e.v, e.w));
                }
            }
        }
    }

    //bellman ford algorithm
    public static void bellmanFord(int[][] edges, int src, int dest) {
        int n = edges.length;
        int[] prev = new int[n];
        Arrays.fill(prev, (int)1e9);

        prev[src] = 0;
        boolean isCycle = false;
        for(int i = 1; i <= n; i++) {
            int[] curr = new int[n];
            for(int j = 0; j < n; j++) {
                curr[j] = prev[j];
            }

            boolean anyUpdate = false;
            
            for(int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if(prev[u] != (int)1e9 && prev[u] + w < curr[v]) {
                    curr[v] = prev[u] + w;
                    anyUpdate = true;

                    if(i == n) {
                        isCycle = true;
                    }
                }
            }

            if(!anyUpdate) break;
        }

        if(isCycle) {
            System.out.println("Negative Cycle: " + isCycle);
        }

    }
}
