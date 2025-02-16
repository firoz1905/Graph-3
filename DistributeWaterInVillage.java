// Approach : Kruskal's ALgorithm uses union find to identify the Minimum Spanning Tree (MST). They operate on undirected graphs.
// First and foremost we need to form all the edges including pipes and wells (using n houses).
// We have to sort the edges array based on their weight/cost and then start unionizing the edges and keep track of the costs.
// In order to unionize them we need a DS to store whose parents.
// Time : E Log E
// Space : 
class Solution {
    int[] parent;
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        this.parent = new int[n+1]; // 1 to n
        int result = 0; // cost tracker

        // Initially every node is its own parent
        for(int i=1;i<=n;i++){
            parent[i] = i;
        }

        // collect all edges
        List<int[]> edges = new ArrayList<>();

        for(int[] pipe : pipes){
            edges.add(pipe);
        }

        for(int i=1;i<=n;i++){
            // digging a well between 0 and 1 house , 0 and 2 house , 0 and 3 house
            edges.add(new int[]{0,i,wells[i-1]});// wells - $[1,2,2]
        }

        Collections.sort(edges , (a,b)-> a[2] - b[2]); // sort in ascending order

        // go through these edges and unionize them
        for(int[] edge:edges){
            int x = edge[0];
            int y = edge[1];

            // first identify the ultimate parent
            int px = find(x);
            int py = find(y);

            // if ultimate parents are not the same then unionize (without any rules) with cost
            if(px!=py){
                result+=edge[2];
                // surrender ultimate parent (py) to ultimate parent (px)
                parent[py] = px;
            }
        }

        return result;

    }

    private int find(int x){
        // check until parent of x equal to x
        if(parent[x]!=x){
            // whenever recursion comes back update  the ulitmate parent
            parent[x]= find(parent[x]); // Path reduction
        }

        return parent[x];
    }
}