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

}
