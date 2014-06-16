package csp_solver;

import java.util.ArrayList;
import java.util.HashMap;


public class Constraint{

private int var1;
private int var2;
//private int[] list = new int[2];
ArrayList<Integer> list = new ArrayList<Integer>();

public Constraint(int var1, int var2) {
this.var1 = var1;
this.var2 = var2;
//list[0] = var1;
//list[1] = var2;
list.add(var1);
list.add(var2);
}



public ArrayList<Integer> getPair() {
return list;
}

public int getFirstVar(){
	return this.var1;
}

public int getSecondVar(){
	return this.var2;
}
public boolean isSatisfied(Assignment assignment) {
	/*Object value1 = assignment.getAssignment(var1);
	return value1 == null || !value1.equals(assignment.getAssignment(var2));*/
	int v1 = assignment.getAssignment(var1);
	int v2 = assignment.getAssignment(var2);
	return v1==-15||(v1!=v2);
}
}
