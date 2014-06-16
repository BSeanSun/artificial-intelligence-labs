package csp_solver;

import java.util.ArrayList;

public class MRVBackTracking {
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
}
