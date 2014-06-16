package csp_solver;

public class BackTracking {
	
	
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
				for (int value:csp.getDomain()) {
					assignment.setAssignment(var, value);

					if (assignment.isLegal(csp.getConstraints(var))) {
							result = recursiveBackTrackingSearch(csp, assignment);
							if (result != null)
								return result;
							}
						}
					}
		return null;
		}


	private int UnassignedVariable(Assignment assignment,
			ConstraintSatisfactionProblem csp) {
		for (int var : csp.getVariables()) {
			if (assignment.getAssignment(var)==-15)
				return var;
			}
		return -100;
	}
}
