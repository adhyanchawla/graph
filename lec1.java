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

        boolean[] vis = new boolean[N];
        //System.out.println(dfs_findPath(graph, 0, 6, vis));
        //System.out.println(printAllPath(graph, 0, 6, 0 + "", 0, vis));
        //gcc(graph);
        //BFS_forCycle(graph, 0, vis);
        BFS_withoutCycle(graph, 0, vis);
    }


    //O(E)
    public static int findEdge(ArrayList<Edge>[] graph, int u, int v) {
        ArrayList<Edge> list = graph[u];

        for(int i = 0; i < list.size(); i++) {
            Edge e = list.get(i);
            if(e.v == v) {
                return i;
            }
        }

        return -1;
    }

    //O(E)
    public static void removeEdge(ArrayList<Edge>[] graph, int u, int v) {
        int idx = findEdge(graph, u, v);
        graph[u].remove(idx);

        int idx1 = findEdge(graph, v, u);
        graph[v].remove(idx1);
    }

    //O(E), where E is the total number of edges in that component
    public static boolean dfs_findPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] visited) {
        if(src == dest) {
            return true;
        }

        visited[src] = true;
        boolean res = false;
        for(Edge e : graph[src]) {
            if(!visited[e.v]) {
                res = res || dfs_findPath(graph, e.v, dest, visited);
            }
        }

        return res;
    }

    public static int printAllPath(ArrayList<Edge>[] graph, int src, int dest, String psf, int wsf, boolean[] visited) {
        if(src == dest) {
            System.out.println(psf + "@" + wsf);
            return 1;
        }

        int count = 0;
        visited[src] = true;

        for(Edge e : graph[src]) {
            if(!visited[e.v]) {
                count += printAllPath(graph, e.v, dest, psf + e.v, wsf + e.w, visited);
            }
        }

        visited[src] = false;
        return count;
    }

    //get connected components O(E + V) // 25 calls
    public static void gcc(ArrayList<Edge>[] graph) {
        //ArrayList<Integer> comps = new ArrayList<>();
        int comps = 0;
        boolean[] vis = new boolean[graph.length];
        for(int i = 0; i < graph.length; i++) {
            if(!vis[i]) {
                comps++;
                gcc_dfs(graph, i, vis);
            }
        }

        System.out.println(comps);
    }

    public static void gcc_dfs(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        vis[src] = true;
        for(Edge e : graph[src]) {
            if(!vis[e.v]) {
                gcc_dfs(graph, e.v, vis);
            }
        }
    }


    //O(E)
    //cycle detection, shortest path
    public static void BFS_forCycle(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        LinkedList<Integer> que = new LinkedList<>();
        int level = 0;
        que.addLast(src);
        boolean cycle = false;

        while(que.size() != 0) {
            int size = que.size();
            System.out.print("Min Edges : " + level + " -> ");
            while(size --> 0) {
                int rvtx = que.removeFirst();

                if(vis[rvtx]) {
                    cycle = true;
                    continue;
                }

                System.out.print(rvtx + ", ");
                vis[rvtx] = true;

                for(Edge e : graph[rvtx]) {
                    if(!vis[e.v]) {
                        que.addLast(e.v);
                    }
                }
            }
            System.out.println();
            level++;
        }
    }

    //O(V) --> V < E
    public static void BFS_withoutCycle(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        LinkedList<Integer> que = new LinkedList<>();
        que.add(src);
        vis[src] = true;
        int level = 0;

        while(que.size() != 0) {
            int size = que.size();
            System.out.print("Min Edges : " + level + " -> ");
            while(size-- > 0) {
                int rvtx = que.removeFirst();

                System.out.print(rvtx + ", ");
                for(Edge e : graph[rvtx]) {
                    if(!vis[e.v]) {
                        que.addLast(e.v);
                        vis[e.v] = true;
                    }
                }
            }
            level++;
            System.out.println();
        }
    }

    public static void main(String[] args) {
        createGraph();
    }
}