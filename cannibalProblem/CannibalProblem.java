package cannibals;

import java.util.ArrayList;
import java.util.Arrays;


// for the first part of the assignment, you might not extend UUSearchProblem,
//  since UUSearchProblem is incomplete until you finish it.

public class CannibalProblem extends UUSearchProblem {

	// the following are the only instance variables you should need.
	//  (some others might be inherited from UUSearchProblem, but worry
	//  about that later.)

	private int goalm, goalc, goalb;
	private int totalMissionaries, totalCannibals; 
	
	
	public CannibalProblem(int sm, int sc, int sb, int gm, int gc, int gb) {
		// I (djb) wrote the constructor; nothing for you to do here.

		startNode = new CannibalNode(sm, sc, 1, 0);
		goalm = gm;
		goalc = gc;
		goalb = gb;
		totalMissionaries = sm;
		totalCannibals = sc;
		
	}
	
	// node class used by searches.  Searches themselves are implemented
	//  in UUSearchProblem.
	private class CannibalNode implements UUSearchNode {

		// do not change BOAT_SIZE without considering how it affect
		// getSuccessors. 
		
		private final static int BOAT_SIZE = 2;
	
		// how many missionaries, cannibals, and boats
		// are on the starting shore
		private int[] state; 
		
		// how far the current node is from the start.  Not strictly required
		//  for search, but useful information for debugging, and for comparing paths
		private int depth;  
        
        
		public CannibalNode(int m, int c, int b, int d) {
			state = new int[3];
			this.state[0] = m;//number of missionaries on the starting shore
			this.state[1] = c;//number of cannibals on the starting shore
			this.state[2] = b;//number of boat available
			
			depth = d;

		}
        
		public ArrayList<UUSearchNode> getSuccessors() {

			// add actions (denoted by how many missionaries and cannibals to put
			// in the boat) to current state. 

			// You write this method.  Factoring is usually worthwhile.  In my
			//  implementation, I wrote an additional private method 'isSafeState',
			//  that I made use of in getSuccessors.  You may write any method
			//  you like in support of getSuccessors.
            ArrayList<UUSearchNode> successors = new ArrayList<UUSearchNode>(); //In order to record the legal successors
            
            if (state[2] == 1) {
            	/*
            	 * when the boat is at the starting side, then it needs to traverse 
            	 * all possible states to find the safe states 
            	 * and add it to successors 
            	 * */
            	for(int i = 0; i < totalMissionaries; i++){
            		for(int j = 0; j < totalCannibals; j++){
            			CannibalNode c =new CannibalNode(state[0]-i,state[1]-j,state[2]-1,depth+1);
            			addSafeState(successors, c, i, j);
            			}
            		}
            	}else if(state[2] == 0){
            		/*
	               	 * when the boat is at the goal side, then it needs to traverse 
	            	 * all possible states to find the safe states 
	            	 * and add it to successors 
	            	 * */
            		for(int i = 0; i < totalMissionaries;i++){
            			for(int j = 0; j < totalCannibals; j++){
            				CannibalNode c =new CannibalNode(state[0]+i,state[1]+j,state[2]+1,depth+1);
            				addSafeState(successors, c, i, j);
            				}
            		}
            	}
            return successors;
            }
		
		private boolean isSafeState(int i, int j) {
			if(i+j >= 1 && 
			   i+j <= BOAT_SIZE &&
			   state[0]>=0 &&
		       state[1]>=0 &&
			   totalMissionaries - state[0] >= 0 &&
		       totalCannibals - state[1] >=0){
				if(state[0] == 0 && totalMissionaries - state[0] >= totalCannibals - state[1]){
					return true;
				}
				if(totalMissionaries - state[0] == 0 && state[0] >= state[1]){
					return true;
				}
				
				if(state[0] >= state[1] && (totalMissionaries - state[0]) >= (totalCannibals - state[1])){
					return true;
				}
			}
			return false;
		}
				
			
		
		
	     private void addSafeState(ArrayList<UUSearchNode> successors, CannibalNode newState, int i, int j) {
	    	 if (newState.isSafeState(i,j)) {
	    		 successors.add(newState);
	    		 }
	    	 }
		
		@Override
		public boolean goalTest() {
			// you write this method.  (It should be only one line long.)
			   return ((state[0]==0&&state[1]==0&&state[2]==0) ? true: false);
		}

		

		// an equality test is required so that visited lists in searches
		// can check for containment of states
		@Override
		public boolean equals(Object other) {
			return Arrays.equals(state, ((CannibalNode) other).state);
		}

		@Override
		public int hashCode() {
			return state[0] * 100 + state[1] * 10 + state[0];
		}

		@Override
		public String toString() {
			// you write this method
			return Arrays.toString(state);
		}

		
       // You might need this method when you start writing 
        //(and debugging) UUSearchProblem.
        
		@Override
		public int getDepth() {
			return depth;
		}
		

	}
	

}
