package csp_solver;

import java.util.ArrayList;

public class DegreeBackTracking {
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
					//以下是对当前var的所有邻居vars改变domain
					/*ArrayList<Integer> newDomain = csp.getVarDomain(var);
					newDomain.remove(value);*/
					
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
		ArrayList<Integer> varsList = MRVHeuristic(csp, assignment);
		return DegreeHeuristic(varsList, assignment, csp).get(0);
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

	private ArrayList<Integer> DegreeHeuristic(ArrayList<Integer> vars,
			Assignment assignment, ConstraintSatisfactionProblem csp) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int maxDegree = Integer.MIN_VALUE;
		for (int var : vars) {
			int degree = 0;
			for (Constraint constraint : csp.getConstraints(var)) {
				int neighbor = csp.getNeighbor(var, constraint);
				if (assignment.getAssignment(var)==-15
						&& csp.getVarDomain(neighbor).size() > 1)
					++degree;
			}
			if (degree >= maxDegree) {
				if (degree > maxDegree) {
					result.clear();
					maxDegree = degree;
				}
				result.add(var);
			}
		}
		return result;
	}
 
}

