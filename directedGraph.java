import java.util.*;

public class directedGraph {
    
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

    public static void dfs_topo(ArrayList<Edge>[] graph, int src, boolean[] vis, ArrayList<Integer> ans) {
        vis[src]= true;

        for(Edge e : graph[src]) {
            if(!vis[e.v]) {
                dfs_topo(graph, e.v, vis, ans);
            }
        }

        ans.add(src);
    }

    public static void topologicalOrder(ArrayList<Edge>[] graph) {
        ArrayList<Integer> ans = new ArrayList<>();
        boolean[] vis = new boolean[graph.length];
        for(int i = 0; i < graph.length; i++) {
            if(!vis[i]) {
                dfs_topo(graph, i, vis, ans);
            }
        }
    }

    public static void dfs_topo_isCycle(ArrayList<Edge>[] graph) {
        int N = graph.length;
        int[] vis = new int[N];
        Arrays.fill(vis, -1);
        ArrayList<Integer> ans = new ArrayList<>();
        boolean isCycle = false;
        for(int i = 0; i < N; i++) {
            if(vis[i] == -1)
            isCycle = isCycle || dfs_topo_isCycle(graph, i, vis, ans);
        }

        if(isCycle) ans.clear();
        else {
            for(int ele : ans) {
                System.out.print(ele + " ");
            }
        }

    }
    // -1 : unvisited, 0 : curr path, 1 : visited
    public static boolean dfs_topo_isCycle(ArrayList<Edge>[] graph, int src, int[] vis, ArrayList<Integer> ans) {
        vis[src] = 0;
        boolean res = false;
        for(Edge e : graph[src]) {
            if(vis[e.v] == -1) {
                res = res || dfs_topo_isCycle(graph, e.v, vis, ans);
            } else if(vis[e.v] == 0) {
                return true;
            }
        }
        ans.add(src);
        vis[src] = 1;
        return res;
    }

    public static ArrayList<Integer> kahnsAlgo(ArrayList<Edge>[] graph) {
        int[] indegree = new int[graph.length];
        int N = graph.length;
        for(int i = 0; i < N; i++) {
            for(Edge e : graph[i]) {
                indegree[e.v]++;
            }
        }
        
        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 0; i < N; i++) {
            if(indegree[i] == 0) {
                q.add(i);
            }
        }

        boolean[] visited = new boolean[N];

        ArrayList<Integer> ans = new ArrayList<>();
        while(q.size() != 0) {
            int sz = q.size();
            while(sz-->0) {
                int rm = q.remove();
                ans.add(rm);

                if(visited[rm]) {
                    continue;
                }

                for(Edge e : graph[rm]) {
                    if(!visited[e.v]) {
                        indegree[e.v]--;
                        if(indegree[e.v] == 0) {
                            q.add(e.v);
                        }
                    }
                }
            }
        }

        if(ans.size() != N) {
            ans.clear();
        }

        return ans;

    }


}
