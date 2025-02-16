// Approach : Brute Force Approach. 
// For every member out of n members check if they have trustees or not. If member trusts other member then he is not celebrity.
// If member being trusted by all members excpet himself (n-1) then he is the celebrity
// Build the in degrees array based out of the above. For celebrity the vaue should be n-1 and for all others it will be negative numbers
// 
// Time : O(n^2)
// Space : O(n)

/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

public class Solution extends Relation {
    public int findCelebrity(int n) {
        int[] indegrees = new int[n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i == j) continue; // member's self trust we can just ignore
                // i knows j -True then n degrees of i should be decremented.
                if(knows(i,j)){
                    indegrees[i]--;
                    indegrees[j]++;
                }
            }
        }

        for(int k=0;k<indegrees.length;k++){
            if(indegrees[k] == n-1){
                return k;
            }
        }
        return -1;
    }
}

// Approach : Brute Force Approach - Optimizied. 
// For every member out of n members check if they have trustees or not. 
// One member does not trusted by the other member then make the other member as potential celebrity and continue to check.
// After the potential celebrity found then check relation between the clebrity with the other members who are not being trusted.
// Time : O(n) + O(n)
// Space : O(n)

/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

public class Solution extends Relation {
    public int findCelebrity(int n) {
        int potentialCeleb = 0;
        for(int i =1;i<n;i++){
            if(knows(potentialCeleb,i)){
                potentialCeleb = i;
            }
        }

        for(int i =0;i<n;i++){
            if(i == potentialCeleb) continue; // celebrity knows himself so skip
            
            // if any of the person does not know celebrity or 
            // if celebrity knows the person
            if(knows(potentialCeleb,i) || !knows(i,potentialCeleb)){
                return -1;
            }
        }

        return potentialCeleb;
    }
}