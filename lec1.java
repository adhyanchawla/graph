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
        System.out.println(printAllPath(graph, 0, 6, 0 + "", 0, vis));
    }

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

    public static void removeEdge(ArrayList<Edge>[] graph, int u, int v) {
        int idx = findEdge(graph, u, v);
        graph[u].remove(idx);

        int idx1 = findEdge(graph, v, u);
        graph[v].remove(idx1);
    }

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

    public static void main(String[] args) {
        createGraph();
    }
}