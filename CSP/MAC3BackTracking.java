package csp_solver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MAC3BackTracking {
	public Assignment recursiveBackTrackingSearch(ConstraintSatisfactionProblem csp,Assignment assignment) {
		Assignment result = null;
		if (assignment.isComplete(csp.getVariables())) {
			result = assignment;
			return result;
			} else {
				int var = UnassignedVariable(assignment, csp);
				if(var == -100){
					System.out.println("No unassigned");
					return null;
				}
				for (int value:csp.getVarDomain(var)) {
					assignment.setAssignment(var, value);
					ArrayList<Constraint> currentc = csp.getConstraints(var);
					
					for(Constraint eachc:currentc){
						int index =0;
						int neighborvar = csp.getNeighbor(var, eachc);
						if(csp.getVarDomain(neighborvar).contains(value)&&assignment.getAssignment(neighborvar)==-15){
							for(int i = 0; i<csp.getVarDomain(neighborvar).size();i++){
								if(value == csp.getVarDomain(neighborvar).get(i))
									index = i;
							}
							csp.getVarDomain(neighborvar).remove(index);
						}
					}
					
					updateDomain(csp, var,assignment);
					
					if (assignment.isLegal(csp.getConstraints(var))) {
							result = recursiveBackTrackingSearch(csp, assignment);
							if (result != null){
								return result;
							}
							else{
								for(Constraint eachc:currentc){
									int neighborvar = csp.getNeighbor(var, eachc);
									if(!csp.getVarDomain(neighborvar).contains(value)){
										csp.getVarDomain(neighborvar).add(value);
									}
								}
								
							}
								
							}
						}
					}
		return null;
		}


	private int UnassignedVariable(Assignment assignment,
			ConstraintSatisfactionProblem csp) {
		return MRVHeuristic(csp, assignment).get(0);
	}
	
	private ArrayList<Integer> MRVHeuristic(ConstraintSatisfactionProblem csp, Assignment assignment) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int mrv = Integer.MAX_VALUE;
		for (int var : csp.getVariables()) {
			if (assignment.getAssignment(var)==-15) {
				int num = csp.getVarDomain(var).size();
				if (num <= mrv) {
					if (num < mrv) {
						result.clear();
						mrv = num;
						}
					result.add(var);
					}
				}
			}
		return result;
		}
	
public void updateDomain(ConstraintSatisfactionProblem csp, int start,Assignment a){
		
		Queue<Constraint> queue = new LinkedList<Constraint>();
		ArrayList<Constraint> currentc = csp.getConstraints(start);
		
		ArrayList<Integer> neiblist = new ArrayList<Integer>();

		for(Constraint eachc:currentc){
			 int neighbor = csp.getNeighbor(start, eachc);
			 neiblist.add(neighbor);
		}
		
		
		
		if (neiblist!=null){
			for(int i = 0; i < neiblist.size();i++){
				if (a.getAssignment(i)==-15){
					ArrayList<Constraint> Nextc = csp.getConstraints(neiblist.get(i));

					for(Constraint eachc:Nextc){
						 int nextneighbor = csp.getNeighbor(neiblist.get(i), eachc);
						 Constraint node = new Constraint(neiblist.get(i),nextneighbor);
						 queue.add(node);
					}
					
						}
					}
				}
		
		while (!queue.isEmpty()){
			Constraint currentNode = queue.poll();
			if(removed(currentNode,csp)){
				ArrayList<Constraint> newconstraints = csp.getConstraints(currentNode.getSecondVar());
				for(int i =0;i<newconstraints.size();i++){
				if (newconstraints!=null){
					ArrayList<Integer> NextNeibolist = new ArrayList<Integer>();

					for(Constraint eachc:newconstraints){
						 int nextneighbor = csp.getNeighbor(currentNode.getSecondVar(), eachc);
						 NextNeibolist.add(nextneighbor);
						 Constraint node = new Constraint(currentNode.getSecondVar(),nextneighbor);
						 queue.add(node);
					}
				}
			}
			}}
		
		
	}



public boolean removed(Constraint currentNode, ConstraintSatisfactionProblem csp){
	
	int vO = currentNode.getFirstVar();
	int vR = currentNode.getSecondVar();
	int result = 0;
	ArrayList<Integer> dO = csp.getVarDomain(vO);		
	ArrayList<Integer> dR = csp.getVarDomain(vR);
	
	for (int i = 0; i < dR.size(); i ++){
		int flag = 0;
		for (int j = 0; j < dO.size(); j ++){
			if(dR.get(i) != dO.get(j)){
				flag = 1;
				break;
			}
		}
		if(flag == 0){
			result = 1;
			dR.remove(i);
			csp.setVarDomain(vR, dR);
		}
	}
	
	if (result == 1){
		return true;
	}
	else{
		return false;		
	}
	
}




}
