import java.util.*;

public class dsuQuestions {
    

    //lc 695 : max area of islands
    int[] par;
    int[] size;
    
    public int findPar(int u) {
        return par[u] == u ? u : findPar(par[u]);
    }
    
    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        par = new int[n * m];
        size = new int[n * m];
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                par[i * m + j] = i * m + j;
                size[i * m + j] = 1;
            }
        }
        
        int maxSize = 0;
        int[][] dir = {{0, 1}, {1, 0}};
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                
                if(grid[i][j] == 1) {
                    int p1 = findPar(i * m + j);
                
                for(int d = 0; d < 2; d++) {
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];
                    
                    if(r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1) {
                        int p2 = findPar(r * m + c);
                        
                        if(p1 != p2) {
                            par[p2] = p1;
                            size[p1] += size[p2];
                        }
                    }
                }
                maxSize = Math.max(maxSize, size[p1]);   
                }
            }
        }
        return maxSize;
    }


    // https://www.codingninjas.com/codestudio/problems/smallest-equivalent-string_1381859?leftPanelTab=1
    // lexicographically smallest equivalent substring
    public String smallestString(String s, String t, String str) {
		par = new int[26];
        for(int i = 0; i < 26; i++) {
            par[i] = i;
        }
        
        for(int i = 0; i < s.length(); i++) {
            int p1 = findPar(s.charAt(i) - 'a');
            int p2 = findPar(t.charAt(i) - 'a');
            
            par[p1] = Math.min(p1, p2);
            par[p2] = Math.min(p1, p2);
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            char ch = (char)(findPar(str.charAt(i) - 'a') + 'a');
            sb.append(ch);
        }
        
        return sb.toString();
	}

    //lc 990
    public boolean equationsPossible(String[] equations) {
        par = new int[26];
        for(int i = 0; i < 26; i++) {
            par[i] = i;
        }
        
        for(String s : equations) {
            char ch1 = s.charAt(0), ch2 = s.charAt(1), ch3 = s.charAt(3);
            int p1 = findPar(ch1 - 'a');
            int p2 = findPar(ch3 - 'a');
            
            if(ch2 == '=' && p1 != p2) {
                par[p2] = p1;
            }
        }
        
        for(String s : equations) {
            char ch1 = s.charAt(0), ch2 = s.charAt(1), ch3 = s.charAt(3);
            int p1 = findPar(ch1 - 'a');
            int p2 = findPar(ch3 - 'a');
            
            if(ch2 == '!' && p1 == p2) {
                return false;
            }
        }
        return true;
    }


    //lc 839
    public boolean isSimilar(String s1, String s2) {
        int count = 0;
        for(int i = 0; i < s1.length(); i++) {
            if(s1.charAt(i) != s2.charAt(i) && ++count > 2) {
                return false;
            }
        }
        return true;
    }
    
    
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int groups = n;
        
        par = new int[n];
        for(int i = 0; i < n; i++) {
            par[i] = i;
        }
        
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                if(isSimilar(strs[i], strs[j])) {
                    int p1 = findPar(i);
                    int p2 = findPar(j);
                    
                    if(p1 != p2) {
                        par[p2] = p1;
                        groups--;
                    }
                }
            }
        }
        return groups;
    }

    class Point {
             int x;
             int y;
             Point() { x = 0; y = 0; }
             Point(int a, int b) { x = a; y = b; }
        }

    //num islands II https://www.lintcode.com/problem/434/
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        int[][] dir = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        par = new int[n * m];
        Arrays.fill(par, -1);

        List<Integer> ans = new ArrayList<>();
        if(operators == null) return ans;

        int count = 0;
        for(Point p : operators) {
            int i = p.x, j = p.y;
            if(par[i * m + j] == -1) {
                count++;
                par[i * m + j] = i * m + j;
                int p1 = findPar(i * m + j);

                for(int d = 0; d < dir.length; d++) {
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if(r >= 0 && c >= 0 && r < n && c < m && par[r * m + c] != -1) {
                        int p2 = findPar(r * m + c);
                        if(p1 != p2) {
                            par[p2] = p1;
                            count--;
                        }
                    }
                }
            }
            ans.add(count);
        }
        return ans;
    }

    // lc 684
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        par = new int[n + 1];
        
        for(int i = 1; i <= n; i++) {
            par[i] = i;
        }
        
        int[] ans = new int[2];
        
        for(int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            int p1 = findPar(u);
            int p2 = findPar(v);
            
            if(p1 != p2) {
                par[p2] = p1;
            } else {
                ans[0] = u;
                ans[1] = v;
                return ans;
            }
        }
        return ans;
    }
}
