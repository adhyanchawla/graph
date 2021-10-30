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

    
    //

}
