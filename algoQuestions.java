import java.util.*;

public class algoQuestions {
        
    //lc 1192 : critical points in a graph

    boolean[] vis;
    int calls = 0, time = 0;
    int[] low, disc;
    
    public void dfs(ArrayList<Integer>[] graph, int src, int par, List<List<Integer>> ans) {
        low[src] = disc[src] = time++;
        vis[src] = true;
        
        for(Integer v : graph[src]) {
            if(!vis[v]) {
                if(par == -1) 
                    calls++;
                
                dfs(graph, v, src, ans);
                
                if(disc[src] < low[v]) {
                    List<Integer> edge = new ArrayList<>();
                    edge.add(v);
                    edge.add(src);
                    ans.add(edge);   
                }
                
                low[src] = Math.min(low[src], low[v]);
            } else if(par != v) {
                low[src] = Math.min(low[src], disc[v]);
            }
        }
    }
    
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        low = new int[n];
        disc = new int[n];
        vis = new boolean[n];
        calls = 0;
        time = 0;
        
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for(List<Integer> arr : connections) {
            graph[arr.get(0)].add(arr.get(1));
            graph[arr.get(1)].add(arr.get(0));
        }
        
        List<List<Integer>> ans = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(!vis[i])
                dfs(graph, i, -1, ans);
        }
        return ans;
    }
}
