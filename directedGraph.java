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

    //lc 207 : course schedule using BFS
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>(numCourses);
        for(int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        
        for(int[] d : prerequisites) {
            adj.get(d[0]).add(d[1]);
        }
        
        int[] indegree = new int[numCourses];
        
        for(int i = 0; i < numCourses; i++) {
            for(int j = 0; j < adj.get(i).size(); j++) {
                indegree[adj.get(i).get(j)]++;
            }
        }
        
        Queue<Integer> q = new ArrayDeque<>();
        
        for(int i = 0; i < numCourses; i++) {
            if(indegree[i] == 0) {
                q.add(i);
            }
        }
        
        ArrayList<Integer> ans = new ArrayList<>();
        while(q.size() != 0) {
            int rm = q.remove();
            ans.add(rm);
            
            for(int ele : adj.get(rm)) {
                if(--indegree[ele] == 0) {
                    q.add(ele);
                }
            }
        }
        
        if(ans.size() == numCourses) return true;
        else return false;
    }

    //lc : 210 : course schedule II using BFS
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] ans = new int[numCourses];
        if(prerequisites.length == 0) {
            int c = ans.length - 1;
            for(int i = 0; i < ans.length; i++) {
                ans[i] = c;
                c--;
            }
            return ans;
        }
        
        List<List<Integer>> adj = new ArrayList<>(numCourses);
        for(int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        
        for(int[] d : prerequisites) {
            adj.get(d[0]).add(d[1]);
        }
        
        int[] indegree = new int[numCourses];
        
        for(int i = 0; i < numCourses; i++) {
            for(int j = 0; j < adj.get(i).size(); j++) {
                indegree[adj.get(i).get(j)]++;
            }
        }
        
        Queue<Integer> q = new ArrayDeque<>();
        
        for(int i = 0; i < numCourses; i++) {
            if(indegree[i] == 0) {
                q.add(i);
            }
        }
        
        int count = numCourses - 1;
        Arrays.fill(ans, -1);
        while(q.size() != 0) {
            int rm = q.remove();
            ans[count--] = rm;
            
            for(int ele : adj.get(rm)) {
                if(--indegree[ele] == 0) {
                    q.add(ele);
                }
            }
        }
        
        for(int ele : ans) {
            if(ele == -1) {
                int[] noRes = new int[0];
                return noRes;
            }
        }
        
        return ans;
    }
}
