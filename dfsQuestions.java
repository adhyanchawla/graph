import java.util.*;

public class dfsQuestions {
    //lc 200, 695, 463, 130

    //lc 200
    //O(E + V) = N X M
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        int count = 0;
        boolean[][] vis = new boolean[n][m];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == '1' && !vis[i][j]) {
                    count++;
                    dfs(grid, i, j, vis, dir);
                }
            }
        }
        
        return count;
    }
    
    public void dfs(char[][] grid, int sr, int sc, boolean[][] vis, int[][] dir) {
        
        vis[sr][sc] = true;
        for(int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            if(r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == '1' && !vis[r][c]) {
                dfs(grid, r, c, vis, dir);
            }
        }
    }


    //lc 695
    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        boolean[][] vis = new boolean[n][m];
        
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        int max = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 1 && !vis[i][j]) {
                    max = Math.max(max, dfs(grid, i, j, vis, dir));
                }
            }
        }
        
        return max;
    }
    
    public int dfs(int[][] grid, int sr, int sc, boolean[][] vis, int[][] dir) {
        vis[sr][sc] = true;
        int maxArea = 0;
        
        for(int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0], c = sc + dir[d][1];
            if(r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 1 && !vis[r][c]) {
                maxArea += dfs(grid, r, c, vis, dir);
            }
        }
        
        return maxArea + 1;
    }


    //lc 463
    public int islandPerimeter(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        //boolean[][] vis = new boolean[n][m];
        int[][] dir = {{0, 1}, {1, 0}};
        
        int oneCount = 0, nbrCount = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 1) {
                    oneCount++;
                    for(int d = 0; d < dir.length; d++) {
                        int r = i + dir[d][0], c = j + dir[d][1];
                        if(r < n && c < m && grid[r][c] == 1) {
                            nbrCount++;
                        }
                    }
                }
            }
        }
        return 4 * oneCount - 2 * nbrCount;
    }

    //lc 130
    public void solve(char[][] grid) {
        int n = grid.length, m = grid[0].length;
        boolean[][] vis = new boolean[n][m];
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if((i == 0 || j == 0 || i == n - 1 || j == m - 1) && grid[i][j] == 'O') {
                    surrounded_DFS(grid, i, j, dir);
                }
            }
        }
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == '$') grid[i][j] = 'O';
                else if(grid[i][j] == 'O') grid[i][j] = 'X';
            }
        }
        
    }
    
    public void surrounded_DFS(char[][] grid, int sr, int sc, int[][] dir) {
        grid[sr][sc] = '$';
        
        for(int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0], c = sc + dir[d][1];
            if(r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 'O') {
                surrounded_DFS(grid, r, c, dir);
            }
        }
    }

    //lc 694
    public void noOfDistinctIslands(int[][] grid, int sr, int sc, int[][] dir, String[] dirS) {
        grid[sr][sc] = 0;
        for(int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0], c = sc + dir[d][1];
            if(r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == 1) {
                sb.append(dirS[d]);
                noOfDistinctIslands(grid, r, c, dir, dirS);
            }
        }
        //grid[sr][sc] = 1;
        sb.append('b');
    } 

    StringBuilder sb;
    public int numberofDistinctIslands(int[][] grid) {
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        String[] dirS = {"R", "D", "U", "L"};
        HashSet<String> hs = new HashSet<>();
        int n = grid.length, m = grid[0].length;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 1) {
                    sb = new StringBuilder();
                    noOfDistinctIslands(grid, i, j, dir, dirS);
                    if(!hs.contains(sb.toString())) {
                        hs.add(sb.toString());
                    }
                }
            }
        }
        return hs.size();
    }
}
