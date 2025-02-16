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

// Approach : Prim's ALgorithm uses union heaps/priority queues to find the Minimum Spanning Tree (MST). They operate on undirected graphs.
// First and foremost we need to form all the edges including pipes and wells (using n houses).
// Time : O((N+M)⋅log(N+M))
// Space : O(N+M)
class Solution {
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {

        // collect all edges
        List<int[]> edges = new ArrayList<>();

        for(int[] pipe:pipes){
            edges.add(pipe);
        }

        // digging a well between 0 and 1 house , 0 and 2 house , 0 and 3 house
        for(int i=1;i<=n;i++){
            edges.add(new int[]{0,i,wells[i-1]});
        }

        int result =0;

        // In order to traverse over the graph we need an adjacency List
        HashMap<Integer , List<int[]>> map = new HashMap<>();
        for(int[] edge:edges){
            map.putIfAbsent(edge[0],new ArrayList<>());
            map.putIfAbsent(edge[1],new ArrayList<>()); // since it is a undirected graph so we need entried for both
            map.get(edge[0]).add(new int[]{edge[1],edge[2]});
            map.get(edge[1]).add(new int[]{edge[0],edge[2]});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
        pq.add(new int[]{0,0}); // need to start with well. represent well as 0 and cost of digging a well at 0 is $0 cost

        boolean[] visited = new boolean[n+1];
        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int node = curr[0];
            int cost = curr[1];

            if(visited[node]) continue;
            visited[node] = true; // first time visiting  makr it as visited and incurr the cost
            // we always get min weight first - since it is heaps
            result+=cost;
            
            // go over all the neighbours - saved in map
            for(int[] ne:map.get(node)){
                pq.add(ne);
            }
        }

        return result;

    }
}