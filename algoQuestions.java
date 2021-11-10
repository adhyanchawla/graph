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

    //lc 743
    public int networkDelayTime(int[][] times, int n, int k) {
        //{v, w}
        ArrayList<int[]>[] graph = new ArrayList[n + 1];
        for(int i = 0; i <= n; i++) 
            graph[i] = new ArrayList<>();
        
        
        for(int [] t : times) {
            int u = t[0], v = t[1], w = t[2];
            graph[u].add(new int[] {v, w});
        }
        
        int[] dist = new int[n + 1];
        Arrays.fill(dist, (int)1e9);
        //{vtx, wsf}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)-> {
            return a[1] - b[1];
        });
        
        pq.add(new int[]{k, 0});
        dist[k] = 0;
        
        while(pq.size() != 0) {
            int[] rm = pq.remove();
            int vtx = rm[0], wsf = rm[1];
            
            if(wsf > dist[vtx]) continue;
            
            for(int[] e : graph[vtx]) {
                int v = e[0], w = e[1];
                if(w + wsf < dist[v]) {
                    dist[v] = w + wsf;
                    pq.add(new int[]{v, w + wsf});
                }
            }
        }
        
        int max = 0;
        for(int i = 1; i <= n; i++) {
            if(dist[i] == (int)1e9) return -1;
            max = Math.max(dist[i], max);
        }
        return max;
    }


    //lc 787 using bellman ford
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] prev = new int[n];
        Arrays.fill(prev, (int)1e9);
        
        prev[src] = 0;
        
        for(int i = 1; i <= k + 1; i++) {
            
            int[] curr = new int[n];
            //Arrays.fill(curr, (int)1e9);
            for(int j = 0; j < n; j++) {
                curr[j] = prev[j]; 
            }
            
            boolean anyUpdate = false;
            for(int[] e : flights) {
                int u = e[0], v = e[1], w = e[2];
                
                if(prev[u] != (int)1e9 && prev[u] + w < curr[v]) {
                    curr[v] = prev[u] + w;
                    anyUpdate = true;
                }
            }
            
            if(!anyUpdate) break;
            
            prev = curr;
            
        }
        
        return prev[dst] != (int)1e9 ? prev[dst] : -1;
    }
}
