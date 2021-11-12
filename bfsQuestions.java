import java.util.*;

public class bfsQuestions {
    
    //lc 994 : rotten oranges
    public int orangeRotting(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        LinkedList<Integer> que = new LinkedList<>();
        int freshOranges = 0;

        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 1) freshOranges++;
                else if(grid[i][j] == 2) {
                    grid[i][j] = 2; //mark them visited
                    que.addLast(i * m + j); 
                }
            }
        }

        if(freshOranges == 0) return 0;

        int time = 0;
        while(que.size() != 0) {
            int size = que.size();
            while(size -- > 0) {
                int rottedOrangeIDX = que.removeFirst();
                int sr = rottedOrangeIDX / m;
                int sc = rottedOrangeIDX % m;

                for(int d = 0; d < 4; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if(r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1) {
                        if(--freshOranges == 0) {
                            return time + 1;
                        }
                        grid[r][c] = 2;
                        que.addLast(r * m + c);
                    }
                }
            }
            time++; 
        }

        return -1;

    }

    //lc 1091 : shortest path in a matrix
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if(grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return -1;
        } 
        
        if(n == 1) {
            return 1;
        }
        
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        
        boolean[][] visited = new boolean[n][n];
        return bfs(grid, 0, 0, visited, dir);
    }
    
    public class Pair {
        int r;
        int c;
        int wt;
        
        Pair(int r, int c, int wt) {
            this.r = r;
            this.c = c;
            this.wt = wt;
        }
    }
    
    public int bfs(int[][] grid, int sr, int sc, boolean[][] visited, int[][] dir) {
        int n = grid.length;
        int dr = n - 1, dc = n - 1;
        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(0, 0, 1));
        
        
        while(q.size() != 0) {
            int sz = q.size();
            
            while(sz-->0) {
                
                Pair rm = q.remove();
                if(rm.r == dr && rm.c == dc) {
                    return rm.wt;
                }
            
            for(int d = 0; d < dir.length; d++) {
                int r = rm.r + dir[d][0];
                int c = rm.c + dir[d][1];
                
                if(r >= 0 && c >= 0 && r < n && c < n && grid[r][c] == 0 && !visited[r][c]) {    
                    visited[r][c] = true;
                    q.add(new Pair(r, c, rm.wt + 1));
                }
            }    
        }
            
    }
        return -1;
    }

    //lc 542 : 0 1 matrix
    public int[][] updateMatrix(int[][] mat) {
        int n = mat.length, m = mat[0].length;
        
        boolean[][] visited = new boolean[n][m];
        Queue<Integer> q = new ArrayDeque<>();
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(mat[i][j] == 0) {
                    visited[i][j] = true;
                    q.add(i * m + j);
                }
            }
        }
        
        int level = 0;
        while(q.size() != 0) {
            int sz = q.size();
            while(sz-->0) {
                int idx = q.remove();
                int sr = idx / m;
                int sc = idx % m;
                
                for(int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    if(r >= 0 && c >= 0 && r < n && c < m && mat[r][c] == 1 && !visited[r][c]) {
                        visited[r][c] = true;
                        mat[r][c] = level + 1;
                        q.add(r * m + c);
                    }
                }
            }
            level++;
        }
        return mat;
    }


    // 785
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        for(int i = 0; i < n; i++) {
            if(!isBipartite(graph, i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isBipartite(int[][] graph, int sr) {
        int n = graph.length;
        int[] visited = new int[n];
        Arrays.fill(visited, -1);
        Queue<Integer> q = new ArrayDeque<>();
        q.add(sr);
        
        int lvl = 0;
        while(q.size() != 0) {
            int sz = q.size();
            while(sz-->0) {
                int rm = q.remove();
                
                if(visited[rm] != -1) {
                    if(visited[rm] != lvl) {
                        return false;
                    } else continue;
                }
                
                visited[rm] = lvl;
                
                for(int v : graph[rm]) {
                    if(visited[v] == -1) {
                        q.add(v);
                    }
                }
            }
            lvl = (lvl + 1) % 2;
        }
        
        return true;
        
    }

    //lc 886 : possible bipartite

    public boolean possibleBipartition(int n, int[][] dislikes) {

        //graph needs to be created in a different way in this ques
        List<List<Integer>> graph = new ArrayList<>(n + 1);
        //adj list initialised with arraylist at each index
        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        //addEdge 
        for(int[] d : dislikes) {
            graph.get(d[0]).add(d[1]);
            graph.get(d[1]).add(d[0]);
        }
        
        int[] visited = new int[n + 1];
        Arrays.fill(visited, -1);
        
    
        for(int i = 1; i <= n; i++) {
            if(visited[i] == -1) {
                if(!isBipartite(graph, i, n, visited)) {
                    return false;
                }
            }            
        }
        return true;
    }
    
    public boolean isBipartite(List<List<Integer>> graph, int src, int n, int[] visited) {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(src);
        int color = 0;
        while(q.size() != 0) {
            int sz = q.size();
            while(sz-->0) {
                int rm = q.remove();
                
                if(visited[rm] != -1) {
                    if(visited[rm] != color) {
                        return false;
                    } else continue;
                }
                
                visited[rm] = color;
                
                for(int v : graph.get(rm)) {
                    if(visited[v] == -1) {
                            q.add(v);
                        }    
                }
            }
            color = (color + 1) % 2;
        }
        return true;
    }

    // lc walls and gates
    // same as lc 01 matrix
    public void wallsAndGates(int[][] rooms) {
        // write your code here
        int n = rooms.length;
        int m = rooms[0].length;

        Queue<Integer> q = new ArrayDeque<>();

        int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        boolean[][] visited = new boolean[n][m];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(rooms[i][j] == 0) {
                    q.add(i * m + j);
                    visited[i][j] = true;
                }            
            }
        }

        while(q.size() != 0) {
            int sz = q.size();
            while(sz-->0) {
                int idx = q.remove();
                int sr = idx / m;
                int sc = idx % m;

                for(int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if(r >= 0 && c >= 0 && r < n && c < m && rooms[r][c] == 2147483647 && !visited[r][c]) {
                        visited[r][c] = true;
                        rooms[r][c] = rooms[sr][sc] + 1;
                        q.add(r * m + c);
                    }
                }
            }
        }

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

    // lc 329. Longest Increasing Path in a Matrix

    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        
        int[][] indegree = new int[n][m];
        
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                for(int d = 0; d < dir.length; d++) {
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];
                    
                    if(r >= 0 && c >= 0 && r < n && c < m) {
                        if(matrix[r][c] > matrix[i][j]) {
                            indegree[r][c]++;
                        }
                    }
                }
            }
        }
        
        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(indegree[i][j] == 0) {
                    q.add(i * m + j);
                }
            }
        }
        
        int level = 0;
        while(q.size() != 0) {
            int sz = q.size();
            while(sz--> 0) {
                int idx = q.remove();
                int sr = idx / m;
                int sc = idx % m;
                
                for(int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    if(r >= 0 && c >= 0 && r < n && c < m && matrix[sr][sc] < matrix[r][c]) {
                        if(--indegree[r][c] == 0) {
                            q.add(r * m + c);
                        }
                    }
                }
            }
            level++;
        }
        
        return level;
    }

    //using radius loop
    // https://www.lintcode.com/problem/787/
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        // write your code here
        int n = maze.length, m = maze[0].length, sr = start[0], sc = start[1], er = destination[0], ec = destination[1];
        boolean[][] vis = new boolean[n][m];

        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        Queue<Integer> q = new ArrayDeque<>();

        int len = Math.max(m, n);
        q.add(sr * m + sc);

        while(q.size() != 0) {
            int sz = q.size();
            while(sz-->0) {
                int idx = q.remove();
                int i = idx / m;
                int j = idx % m;

                for(int d = 0; d < dir.length; d++) {
                    int fr = i, fc = j;
                    for(int rad = 1; rad < len; rad++) {
                        int r = i + rad * dir[d][0];
                        int c = j + rad * dir[d][1];

                        if(r >= 0 && c >= 0 && r < n && c < m && maze[r][c] == 0) {
                            fr = r;
                            fc = c;
                        } else break;
                    }

                        if(vis[fr][fc]) continue;

                        vis[fr][fc] = true;
                        q.add(fr * m + fc);

                        if(fr == er && fc == ec) {
                            return true;
                        }
                }
            }
        }
        return false;
    }

    //without using radius loop
    // https://www.lintcode.com/problem/787/
    public boolean hasPath01(int[][] maze, int[] start, int[] destination) {
        // write your code here
        int n = maze.length, m = maze[0].length, sr = start[0], sc = start[1], er = destination[0], ec = destination[1];
        boolean[][] vis = new boolean[n][m];

        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        Queue<Integer> q = new ArrayDeque<>();

        int len = Math.max(m, n);
        q.add(sr * m + sc);

        while(q.size() != 0) {
            int sz = q.size();
            while(sz-->0) {
                int idx = q.remove();
                int i = idx / m;
                int j = idx % m;

                for(int[] d : dir) {
                    int r = i;
                    int c = j;

                    while(r >= 0 && c >= 0 && r < n && c < m && maze[r][c] == 0) {
                        r += d[0];
                        c += d[1];
                    }

                    r -= d[0];
                    c -= d[1];
                
                    if(vis[r][c]) continue;

                    vis[r][c] = true;
                    q.add(r * m + c);

                    if(r == er && c == ec) {
                            return true;
                        }
                    }
            }
        }
        return false;
    }

    //lintcode 788
    // https://www.lintcode.com/problem/788/
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        // write your code here
        class pair implements Comparable<pair>{
            int r, c, wsf;

            pair(int r, int c, int wsf) {
                this.r = r;
                this.c = c;
                this.wsf = wsf;
            }

            public int compareTo(pair o) {
                return this.wsf - o.wsf;
            }
        }
        int n = maze.length, m = maze[0].length, sr = start[0], sc = start[1], er = destination[0], ec = destination[1];
        int[][] distance = new int[n][m];
        for(int [] d : distance) {
            Arrays.fill(d, (int)1e8);
        }
        PriorityQueue<pair> pq = new PriorityQueue<>();
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        pq.add(new pair(sr, sc, 0));
        distance[sr][sc] = 0;
        while(pq.size() != 0) {
            pair rm = pq.remove();

            for(int[] d : dir) {
                int fr = rm.r, fc = rm.c, l = rm.wsf;
                while(fr >= 0 && fc >= 0 && fr < n && fc < m && maze[fr][fc] == 0) {
                    fr += d[0];
                    fc += d[1];
                    l++;
                }

                fr -= d[0];
                fc -= d[1];
                l--;

                if(l >= distance[fr][fc]) continue;

                distance[fr][fc] = l;

                pq.add(new pair(fr, fc, l));
                distance[fr][fc] = l;
            }
        } 
        return distance[er][ec] != (int) 1e8 ? distance[er][ec] : -1;
    }

}
