package assignment_mazeworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import assignment_mazeworld.SearchProblem.SearchNode;

public class InformedSearchProblem extends SearchProblem {

	public List<SearchNode> astarSearch() {
		    resetStats();
		    PriorityQueue<SearchNode> OpenList = new PriorityQueue<SearchNode>();
			HashMap<SearchNode, SearchNode> visited = new HashMap<SearchNode, SearchNode>();
			
		    OpenList.add(startNode);
		    visited.put(startNode,null);
				while(!OpenList.isEmpty()){
					incrementNodeCount();
					updateMemory(OpenList.size() + visited.size());
					
					SearchNode currentNode = OpenList.poll();
					
					if (currentNode.goalTest()) {
						return backchain(currentNode, visited);
					}
			        
					ArrayList<SearchNode> successors = currentNode.getSuccessors();
					for (SearchNode child : successors){
						if(!visited.containsKey(child)){
								OpenList.add(child);
								visited.put(child,currentNode);
						}else{
							if(!child.equals(startNode) && 
								visited.get(child).getCost() > child.getCost()){
								visited.remove(child);
								visited.put(child,currentNode); 
								}
						}
						
						
			        }
		//		if(OpenList.isEmpty())
				//	return null;
			    }
			   
				 OpenList.clear();
			     visited.clear();
			return null;
		}
	
	
}

