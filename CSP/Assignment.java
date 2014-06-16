package csp_solver;

import java.util.ArrayList;



public class Assignment {
int[] variables;
int[] assignment;

public Assignment(ArrayList<Integer> variableList) {
this.variables = new int[variableList.size()];
this.assignment = new int[this.variables.length];

for (int eachVariable : variableList) {
	this.variables[eachVariable] = eachVariable;
	this.assignment[eachVariable] = -15;
	}

}


public void setAssignment(int var, int dom){
		this.assignment[var] = dom;
}

public int getAssignment(int var) {
return this.assignment[var];
}

public boolean isLegal(ArrayList<Constraint> constraints) {
	for (Constraint c : constraints){
		if (!c.isSatisfied(this)){
			return false;
			}
		}
	return true;
	}



public boolean isComplete(ArrayList<Integer> vars) {
	for (int i = 0; i<vars.size();i++) {
		if (this.assignment[i] == -15)
			return false;
		}
	return true;
	}

@Override
public String toString() {
	boolean comma = false;
	StringBuffer result = new StringBuffer("{");
	for (int var : variables) {
		if (comma)
			result.append(", ");
		String t = null;
		String c = null;
		/*if(var == 0){
				t = "NSW"; 
			}else if(var == 1){
				t = "NT";
			}
			else if(var == 2){
				t = "Q";
			}
			else if(var == 3){
				t = "SA";
			}
			else if(var == 4){
				t = "T";
			}
			else if(var == 5){
				t = "V";
			}
			else if(var == 6){
				t = "WA";
			}*/
		if(var == 0){
			t = "WA"; 
		}else if(var == 1){
			t = "SA";
		}
		else if(var == 2){
			t = "NT";
		}
		else if(var == 3){
			t = "Q";
		}
		else if(var == 4){
			t = "NSW";
		}
		else if(var == 5){
			t = "V";
		}
		else if(var == 6){
			t = "T";
		}
		if(this.assignment[var]==0){
			c = "RED";
		}else if(this.assignment[var]==1){
			c = "GREEN";
		}else if(this.assignment[var]==2){
			c = "BLUE";
		}

		//result.append(var + "=" + this.assignment[var]);
		result.append(t + "=" + c);
		comma = true;
	}
	result.append("}");
	return result.toString();
}


	}
