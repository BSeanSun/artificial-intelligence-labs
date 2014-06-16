
// CLEARLY INDICATE THE AUTHOR OF THE FILE HERE (YOU),
//  AND ATTRIBUTE ANY SOURCES USED (INCLUDING THIS STUB, BY
//  DEVIN BALKCOM).

package cannibals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class UUSearchProblem {
	
	// used to store performance information about search runs.
	//  these should be updated during the process of searches

	// see methods later in this class to update these values
	protected int nodesExplored;
	protected int maxMemory;
	protected int maxsuccessor;
	protected int maxvisited;
	protected UUSearchNode startNode;
	
	protected interface UUSearchNode {
		public ArrayList<UUSearchNode> getSuccessors();
		public boolean goalTest();
		public int getDepth();
	}

	// breadthFirstSearch:  return a list of connecting Nodes, or null
	// no parameters, since start and goal descriptions are problem-dependent.
	//  therefore, constructor of specific problems should set up start
	//  and goal conditions, etc.
	
	/*	        
	           frontier = new queue
				pack start state into a node
				add node to frontier
	
				explored = new set
				add start_state to explored
	
				while frontier is not empty:
				  get current_node from the frontier
				  get current_state from current_node
	
				  if current_state is the goal:
				    backchain from current_node and return solution
	
				  for each child of current_state:
				    if child not in explored:
				      add child to explored
				      pack the child state into a node, with backpointer to current_node
				      add the node to the frontier
	
				return failure    
				

				*/
	public List<UUSearchNode> breadthFirstSearch(){
		resetStats();
		// You will write this method
		Queue<UUSearchNode> frontier = new LinkedList<UUSearchNode>();
		frontier.offer(startNode);//add node to frontier
		updateMemory(frontier.size()) ;
		
		
		List<UUSearchNode>  solution = new ArrayList<UUSearchNode>();
		HashMap<UUSearchNode,UUSearchNode> visited = new HashMap<UUSearchNode,UUSearchNode>();
		visited.put(startNode, null);
		incrementNodeCount();
		updateMemory(visited.size()+frontier.size()) ;
		
		//int i =1;
		
		while (!frontier.isEmpty()) {//while frontier is not empty
		//	System.out.println("count"+ i);
			
            UUSearchNode current_node = frontier.poll();	//get current_node from the frontier
            //updateMemory(visited.size()+frontier.size()); 
            ArrayList<UUSearchNode> current_state = current_node.getSuccessors();//get current_state from current_node
          //  System.out.println("successor "+ current_state.size() + " ");
            updateMemory(visited.size()+frontier.size()+current_state.size()) ;
            
            if(current_node.goalTest()){
            	//System.out.println("~~~~~~~reach 000~~~~~~~~~");
    			solution = backchain(current_node,visited);//backchain from current_node and return solution
    		//	updateMemory(visited.size()+frontier.size()); 
    			return solution;
    		}	
    		
            
            for (UUSearchNode child : current_state) {
                    if (!visited.containsKey(child)) {
                            visited.put(child,current_node);
                            incrementNodeCount();
                            frontier.add(child);
                            updateMemory(visited.size()+frontier.size());
                    }
            }
		}
        return null;        // failure
	}

	
	// backchain should only be used by bfs, not the recursive dfs
	private List<UUSearchNode> backchain(UUSearchNode node,
			HashMap<UUSearchNode, UUSearchNode> visited) {
		// you will write this method
		List<UUSearchNode> path = new ArrayList<UUSearchNode>();
		path.add(0,node);
		while(!node.equals(startNode)){
			if(visited.containsKey(node)){
				node = visited.get(node);//key 是后一个发现的状态 value存前一个发现的状态
				path.add(0,node);
			}
		}
		System.out.println("number of nodes on path " + path.size());
		
		return path;
	}

	public List<UUSearchNode> depthFirstMemoizingSearch(int maxDepth) {
		resetStats(); 
		// You will write this method
		HashMap<UUSearchNode,Integer> visited = new HashMap<UUSearchNode,Integer>();
		
		return dfsrm(startNode,visited,0,maxDepth);
		
	}

	// recursive memoizing dfs. Private, because it has the extra
	// parameters needed for recursion.  
	private List<UUSearchNode> dfsrm(UUSearchNode currentNode, HashMap<UUSearchNode, Integer> visited, 
			int depth, int maxDepth) {
		
		// keep track of stats; these calls charge for the current node
        
		
		// you write this method.  Comments *must* clearly show the 
		//  "base case" and "recursive case" that any recursive function has.
		List<UUSearchNode> path = new ArrayList<UUSearchNode>();
		if(depth>maxDepth){
			return null;
			}else{
				if(depth==0){
					visited.put(startNode, 0);
					incrementNodeCount();
				}
				
				//base case
				if(currentNode.goalTest()){
					path.add(currentNode);
					incrementNodeCount();
					return path;
				}
				
				List<UUSearchNode> successor = currentNode.getSuccessors();
				if(successor.size()>maxsuccessor){
					maxsuccessor = successor.size();
				}
				updateMemory(maxsuccessor);

				//recursive case
				 if(!successor.isEmpty()){
						for(UUSearchNode child: successor){		
							if(!visited.containsKey(child)){
									visited.put(child,depth+1);
									incrementNodeCount();
									updateMemory(visited.size()+maxsuccessor);
									List<UUSearchNode> next_path = dfsrm(child,visited,depth+1,maxDepth);	
									if(next_path!=null){
										path = next_path;
										path.add(0,currentNode);
										break;
										}
									}
							}
						return (path.isEmpty()) ? null:path;
				}
			}

		return null;
		
	}
	
	
	// set up the iterative deepening search, and make use of dfspc
	public List<UUSearchNode> IDSearch(int maxDepth) {
		resetStats();
		HashSet<UUSearchNode> currentPath = new HashSet<UUSearchNode>();
		List<UUSearchNode> solutionpath = new ArrayList<UUSearchNode>();
		// you write this method
		for(int i=0;i<maxDepth+1;i++){
			currentPath.clear();
			solutionpath=dfsrpc(startNode,currentPath,0,i);
			if(solutionpath!=null){
				break;
			}
		}
		return solutionpath;
		
	}

	// set up the depth-first-search (path-checking version), 
		//  but call dfspc to do the real work
		public List<UUSearchNode> depthFirstPathCheckingSearch(int maxDepth) {
			resetStats();
			
			// I wrote this method for you.  Nothing to do.
			HashSet<UUSearchNode> currentPath = new HashSet<UUSearchNode>();
			return dfsrpc(startNode, currentPath, 0, maxDepth);

		}

		// recursive path-checking dfs. Private, because it has the extra
		// parameters needed for recursion.
		private List<UUSearchNode> dfsrpc(UUSearchNode currentNode, HashSet<UUSearchNode> currentPath,
				int depth, int maxDepth) {
			// you write this method
			List<UUSearchNode> solutionpath = new ArrayList<UUSearchNode>();
			if(depth == 0){
				currentPath.add(startNode);
				incrementNodeCount();
			}
			if(currentNode.goalTest()){
				solutionpath.add(currentNode);
				updateMemory(maxsuccessor+maxvisited);
				return solutionpath;
			}
			
			List<UUSearchNode> successors = currentNode.getSuccessors();
			if(successors.size()>maxsuccessor){
				maxsuccessor = successors.size();
			}
			
			if(!successors.isEmpty()){
				if(currentNode.getDepth()>maxDepth){
					 return null;
				}
				else{	
					for(UUSearchNode child: successors){
						if(!currentPath.contains(child)){
							currentPath.add(child);
							incrementNodeCount();
							if(currentPath.size()>maxvisited){
								maxvisited = currentPath.size();
							}
							List<UUSearchNode> next_path = dfsrpc(child,currentPath,depth+1,maxDepth);	
							if(next_path!=null){
								solutionpath = next_path;
								solutionpath.add(0,currentNode);
								break;
							}
							else{
								currentPath.remove(child);
							}
						}
					}
					return (solutionpath.isEmpty()?null:solutionpath);
				}
				}
			return null;
		}



	protected void resetStats() {
		nodesExplored = 0;
		maxMemory = 0;
	}
	
	protected void printStats() {
		System.out.println("Nodes explored during last search:  " + nodesExplored);
		System.out.println("Maximum memory usage during last search " + maxMemory);
	}
	
	protected void updateMemory(int currentMemory) {
		maxMemory = Math.max(currentMemory, maxMemory);
	}
	
	protected void incrementNodeCount() {
		nodesExplored++;
	}

}
